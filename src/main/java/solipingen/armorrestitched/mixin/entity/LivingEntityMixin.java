package solipingen.armorrestitched.mixin.entity;

import java.util.List;
import java.util.Map;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.equipment.trim.ArmorTrim;
import net.minecraft.item.equipment.trim.ArmorTrimMaterials;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.event.Vibrations;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import solipingen.armorrestitched.enchantment.ModEnchantments;
import solipingen.armorrestitched.registry.tag.ModItemTags;
import solipingen.armorrestitched.util.interfaces.mixin.entity.EntityInterface;
import solipingen.armorrestitched.util.interfaces.mixin.entity.mob.MobEntityInterface;


@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow @Final private Map<RegistryEntry<StatusEffect>, StatusEffectInstance> activeStatusEffects;
    @Unique private static final int[] REDSTONE_CHARGE_COLORS = Util.make(new int[16], (colors) -> {
        for(int i = 0; i <= 15; ++i) {
            float f = (float)i / 15.0f;
            float g = f * 0.6f + (f > 0.0f ? 0.4f : 0.3f);
            float h = MathHelper.clamp(f * f * 0.7f - 0.5f, 0.0f, 1.0f);
            float j = MathHelper.clamp(f * f * 0.6f - 0.7f, 0.0f, 1.0f);
            colors[i] = ColorHelper.fromFloats(1.0f, g, h, j);
        }
    });
    @Unique private boolean redstoneCharged = false;
    @Unique private int redstoneChargeTime = 0;
    @Unique private int redstoneChargePower = 0;
    @Unique private double redstoneParticleYScale = 0.0;

    @Invoker("onStatusEffectApplied")
    public abstract void invokeOnStatusEffectApplied(StatusEffectInstance effect, @Nullable Entity source);

    @Invoker("onStatusEffectUpgraded")
    public abstract void invokeOnStatusEffectUpgraded(StatusEffectInstance effect, boolean reapplyEffect, @Nullable Entity source);

    @Invoker("clearPotionSwirls")
    public abstract void invokeClearPotionSwirls();


    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @SuppressWarnings("incomplete-switch")
    @Inject(method = "tick", at = @At("TAIL"))
    private void injectedBaseTick(CallbackInfo cbi) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (!slot.isArmorSlot()) continue;
            ItemStack equippedStack = ((LivingEntity)(Object)this).getEquippedStack(slot);
            ArmorTrim trim = equippedStack.get(DataComponentTypes.TRIM);
            if (trim == null) continue;
            if (trim.material().matchesKey(ArmorTrimMaterials.QUARTZ)) {
                int sunlightLevel = this.getWorld().getLightLevel(LightType.SKY, this.getBlockPos()) - this.getWorld().getAmbientDarkness();
                if (sunlightLevel <= 7) {
                    switch (slot) {
                        case HEAD: {
                            ((LivingEntity)(Object)this).addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 4, 0, true, false));
                            break;
                        }
                        case CHEST: {
                            ((LivingEntity)(Object)this).addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 4, 0, true, false));
                            break;
                        }
                    }
                }
                else {
                    switch (slot) {
                        case LEGS: {
                            ((LivingEntity)(Object)this).addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 4, 0, true, false));
                            break;
                        }
                        case FEET: {
                            ((LivingEntity)(Object)this).addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 4, 0, true, false));
                            break;
                        }
                    }
                }
            }
            else if (trim.material().matchesKey(ArmorTrimMaterials.REDSTONE)) {
                int powerLevel = this.getWorld().getReceivedRedstonePower(this.getBlockPos());
                if (this.verticalCollision) {
                    if (this.isOnGround()) {
                        if (this.getWorld().isEmittingRedstonePower(this.getBlockPos().down(), Direction.UP)) {
                            powerLevel = Math.max(this.getWorld().getEmittedRedstonePower(this.getBlockPos().down(), Direction.UP), powerLevel);
                        }
                        else {
                            boolean bl = true;
                            for (Direction emitDirection : Direction.values()) {
                                if (emitDirection == Direction.UP || emitDirection == Direction.DOWN) continue;
                                if (this.getWorld().isEmittingRedstonePower(this.getBlockPos().down(), emitDirection)) {
                                    bl = false;
                                    break;
                                }
                            }
                            if (bl) {
                                powerLevel = Math.max(this.getWorld().getReceivedStrongRedstonePower(this.getBlockPos().down()), powerLevel);
                            }
                        }
                    }
                    else {
                        if (this.getWorld().isEmittingRedstonePower(this.getBlockPos().up(MathHelper.ceil(this.getHeight())), Direction.DOWN)) {
                            powerLevel = Math.max(this.getWorld().getEmittedRedstonePower(this.getBlockPos().up(MathHelper.ceil(this.getHeight())), Direction.DOWN), powerLevel);
                        }
                        else {
                            boolean bl = true;
                            for (Direction emitDirection : Direction.values()) {
                                if (emitDirection == Direction.UP || emitDirection == Direction.DOWN) continue;
                                if (this.getWorld().isEmittingRedstonePower(this.getBlockPos().up(MathHelper.ceil(this.getHeight())), emitDirection)) {
                                    bl = false;
                                    break;
                                }
                            }
                            if (bl) {
                                powerLevel = Math.max(this.getWorld().getReceivedStrongRedstonePower(this.getBlockPos().up(MathHelper.ceil(this.getHeight()))), powerLevel);
                            }
                        }
                    }
                }
                if (this.horizontalCollision) {
                    Box box = this.getBoundingBox();
                    int minBlockPosY = MathHelper.floor(box.minY);
                    int maxBlockPosY = MathHelper.floor(box.maxY);
                    for (int y = minBlockPosY; y <= maxBlockPosY; y++) {
                        Vec3d centerPosVec3d = new Vec3d(this.getX(), y, this.getZ());
                        for (Direction currentDirection : Direction.values()) {
                            if (currentDirection == Direction.UP || currentDirection == Direction.DOWN) continue;
                            BlockPos currentBlockPos = BlockPos.ofFloored(centerPosVec3d).offset(currentDirection);
                            if (this.getWorld().isEmittingRedstonePower(currentBlockPos, currentDirection.getOpposite())) {
                                powerLevel = Math.max(this.getWorld().getEmittedRedstonePower(currentBlockPos, currentDirection.getOpposite()), powerLevel);
                            }
                            else {
                                boolean bl = true;
                                for (Direction emitDirection : Direction.values()) {
                                    if (emitDirection == Direction.UP || emitDirection == Direction.DOWN) continue;
                                    if (this.getWorld().isEmittingRedstonePower(currentBlockPos, emitDirection)) {
                                        bl = false;
                                        break;
                                    }
                                }
                                if (bl) {
                                    powerLevel = Math.max(this.getWorld().getReceivedStrongRedstonePower(currentBlockPos), powerLevel);
                                }
                            }
                        }
                    }
                }
                if (powerLevel <= 0) continue;
                this.redstoneCharged = true;
                this.redstoneChargeTime = 40*powerLevel;
                this.redstoneChargePower = powerLevel;
                switch (slot) {
                    case HEAD: {
                        ((LivingEntity)(Object)this).addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, this.redstoneChargeTime, 0, true, false));
                        this.redstoneParticleYScale = 0.85;
                        break;
                    }
                    case CHEST: {
                        ((LivingEntity)(Object)this).addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, this.redstoneChargeTime, 0, true, false));
                        this.redstoneParticleYScale = 0.65;
                        break;
                    }
                    case LEGS: {
                        ((LivingEntity)(Object)this).addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, this.redstoneChargeTime, 0, true, false));
                        this.redstoneParticleYScale = 0.4;
                        break;
                    }
                    case FEET: {
                        ((LivingEntity)(Object)this).addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, this.redstoneChargeTime, 0, true, false));
                        this.redstoneParticleYScale = 0.2;
                        break;
                    }
                }
            }
        }
        if (this.getWorld() instanceof ServerWorld serverWorld && this.redstoneCharged && this.redstoneChargeTime % 4 == 0 && this.redstoneChargePower > 0) {
            serverWorld.spawnParticles(new DustParticleEffect(REDSTONE_CHARGE_COLORS[this.redstoneChargePower], 0.5f), this.getX(), this.getBodyY(this.redstoneParticleYScale), this.getZ(), this.random.nextInt(this.redstoneChargePower), 0.0, 0.0, 0.0, this.random.nextDouble());
        }
        if (this.redstoneChargeTime > 0) {
            if (this.redstoneChargeTime % 40 == 0) {
                this.redstoneChargePower--;
            }
            this.redstoneChargeTime--;
        }
        if (this.redstoneCharged && this.redstoneChargeTime <= 0) {
            this.redstoneCharged = false;
            this.redstoneChargePower = 0;
            this.redstoneChargeTime = 0;
            this.redstoneParticleYScale = 0.0;
        }
    }

    @ModifyConstant(method = "calcGlidingVelocity", constant = @Constant(doubleValue = 0.75))
    private double modifiedFallFlyingVerticalFactor(double originald) {
        RegistryEntryLookup<Enchantment> enchantmentLookup = this.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT);
        int soaringLevel = EnchantmentHelper.getLevel(enchantmentLookup.getOrThrow(ModEnchantments.SOARING), ((LivingEntity)(Object)this).getEquippedStack(EquipmentSlot.CHEST));
        return originald + 0.01*soaringLevel;
    }

    @ModifyConstant(method = "calcGlidingVelocity", constant = @Constant(doubleValue = 0.1))
    private double modifiedFallFlyingHorizontalFactor(double originald) {
        if (((LivingEntity)(Object)this).isGliding()) {
            RegistryEntryLookup<Enchantment> enchantmentLookup = this.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT);
            int soaringLevel = EnchantmentHelper.getLevel(enchantmentLookup.getOrThrow(ModEnchantments.SOARING), ((LivingEntity)(Object)this).getEquippedStack(EquipmentSlot.CHEST));
            return originald + 0.001*soaringLevel;
        }
        return originald;
    }

    @Redirect(method = "calcGlidingVelocity", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Vec3d;multiply(DDD)Lnet/minecraft/util/math/Vec3d;"))
    private Vec3d redirectedFallFlyingDrag(Vec3d vec3d, double x, double y, double z) {
        if (((LivingEntity)(Object)this).isGliding()) {
            RegistryEntryLookup<Enchantment> enchantmentLookup = this.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT);
            int soaringLevel = EnchantmentHelper.getLevel(enchantmentLookup.getOrThrow(ModEnchantments.SOARING), ((LivingEntity)(Object)this).getEquippedStack(EquipmentSlot.CHEST));
            x += 0.0025*soaringLevel;
            y += 0.0015*soaringLevel;
            z += 0.0025*soaringLevel;
        }
        return vec3d.multiply(x, y, z);
    }

    @ModifyConstant(method = "damage", constant = @Constant(floatValue = 0.75f))
    private float modifiedHelmetDamageModifier(float originalf) {
        RegistryEntryLookup<Enchantment> enchantmentLookup = this.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT);
        int impactProtectionLevel = EnchantmentHelper.getLevel(enchantmentLookup.getOrThrow(ModEnchantments.IMPACT_PROTECTION), ((LivingEntity)(Object)this).getEquippedStack(EquipmentSlot.HEAD));
        float modifier = 0.7f - 0.1f*impactProtectionLevel;
        return modifier;
    }

    @Inject(method = "damage", at = @At("TAIL"), cancellable = true)
    private void injectedDamage(ServerWorld world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cbireturn) {
        boolean blockingBl = ((LivingEntity)(Object)this).isBlocking() && ((LivingEntity)(Object)this).getDamageBlockedAmount(world, source, amount) >= amount;
        if (((LivingEntity)(Object)this) instanceof MobEntity mob && ((MobEntityInterface)mob).getEntranced()) {
            ((MobEntityInterface)mob).setEntranced(false, 0);
        }
        if (!blockingBl) {
            RegistryEntryLookup<Enchantment> enchantmentLookup = this.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT);
            int thornsLevel = EnchantmentHelper.getLevel(enchantmentLookup.getOrThrow(Enchantments.THORNS), ((LivingEntity)(Object)this).getEquippedStack(EquipmentSlot.CHEST));
            if (thornsLevel > 0 && source.getSource() != null && source.getSource() instanceof LivingEntity && LivingEntityMixin.shouldThornsDamageAttacker(thornsLevel, this.random) && !source.isIn(DamageTypeTags.BYPASSES_ARMOR)) {
                source.getSource().damage(world, this.getDamageSources().thorns(this), MathHelper.floor(0.15f*thornsLevel*amount) + this.random.nextInt(thornsLevel + 1));
            }
            int i = 0;
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (!slot.isArmorSlot()) continue;
                ItemStack equippedStack = ((LivingEntity)(Object)this).getEquippedStack(slot);
                ArmorTrim trim = equippedStack.get(DataComponentTypes.TRIM);
                if (trim != null && trim.material().matchesKey(ArmorTrimMaterials.AMETHYST)) {
                    i++;
                }
            }
            if (i > 0) {
                ((LivingEntity)(Object)this).addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, i*this.random.nextBetween(10, Math.max(MathHelper.ceil(((LivingEntity)(Object)this).getSoundPitch()*10), 11)), 0, true, true, false));
                this.getWorld().playSound(null, this.getBlockPos(), SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.PLAYERS, MathHelper.clamp(i*amount, 4.0f, i*4.0f), ((LivingEntity)(Object)this).getSoundPitch());
                Box amethystSoundBox = new Box(this.getBlockPos()).expand(4.0*i);
                List<Entity> amethystSoundEntities = this.getWorld().getOtherEntities(this, amethystSoundBox);
                for (Entity entity : amethystSoundEntities) {
                    if (entity.distanceTo(entity) > 4.0f*i) continue;
                    if (entity instanceof MobEntity && entity.getWorld() instanceof ServerWorld) {
                        MobEntity mobEntity = (MobEntity)entity;
                        if (mobEntity.getTarget() == null && !mobEntity.getType().isIn(EntityTypeTags.UNDEAD)) {
                            int maxDuration = Math.max(MathHelper.ceil(5*i*amount/Math.max(mobEntity.squaredDistanceTo(this), 1.0)), 11);
                            mobEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, mobEntity.getRandom().nextBetween(10, maxDuration), 0, true, true, false));
                        }
                        else if (mobEntity.getTarget() != null && !mobEntity.getType().isIn(EntityTypeTags.UNDEAD) && (mobEntity.getAttacker() != (LivingEntity)(Object)this && mobEntity.getLastAttacker() != (LivingEntity)(Object)this)) {
                            int duration = mobEntity.getRandom().nextBetween(10, Math.max(i*20, 11));
                            duration *= mobEntity instanceof Vibrations ? 2 : 1;
                            ((MobEntityInterface)mobEntity).setEntranced(true, duration);
                        }
                    }
                    else if (entity instanceof PlayerEntity) {
                        PlayerEntity player = (PlayerEntity)entity;
                        if (source.getAttacker() == player || ((LivingEntity)(Object)this).getLastAttacker() == player) continue;
                        int duration = Math.max(MathHelper.ceil(5*i*amount/Math.max(player.squaredDistanceTo(this), 1.0)), 6);
                        player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, player.getRandom().nextBetween(5, duration), 0, true, true, false));
                    }
                }
            }
        }
    }

    @ModifyVariable(method = "modifyAppliedDamage", at = @At("STORE"), ordinal = 3)
    private float modifiedProtectionAmount(float k, DamageSource source, float amount) {
        RegistryEntryLookup<Enchantment> enchantmentLookup = this.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT);
        if (source.getSource() instanceof PersistentProjectileEntity persistentProjectileEntity) {
            k *= (1.0f - 0.10f*persistentProjectileEntity.getPierceLevel());
        }
        else if (!(source.getSource() instanceof PersistentProjectileEntity) && source.getAttacker() instanceof LivingEntity) {
            int breachLevel = EnchantmentHelper.getEquipmentLevel(enchantmentLookup.getOrThrow(Enchantments.BREACH), (LivingEntity)source.getAttacker());
            k *= (1.0f - 0.10f*breachLevel);
        }
        return k;
    }

    @Redirect(method = "applyArmorToDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/damage/DamageSource;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"))
    private boolean redirectedBypassesArmor(DamageSource source, TagKey<DamageType> tag) {
        return source.isIn(tag) && !source.isIn(DamageTypeTags.IS_FALL);
    }

    @Inject(method = "damageEquipment", at = @At("HEAD"), cancellable = true)
    private void injectedDamageEquipment(DamageSource source, float amount, EquipmentSlot[] slots, CallbackInfo cbi) {
        if (amount <= 0.0f) {
            cbi.cancel();
        }
        int i = (int)Math.max(1.0f, amount / 4.0f);
        for (EquipmentSlot equipmentSlot : slots) {
            ItemStack itemStack = ((LivingEntity)(Object)this).getEquippedStack(equipmentSlot);
            if (!itemStack.isIn(ItemTags.TRIMMABLE_ARMOR) || !itemStack.takesDamageFrom(source)
                    || (itemStack.isIn(ModItemTags.TRIMMABLE_ELYTRA) && itemStack.willBreakNextUse())) continue;
            if (itemStack.isIn(ModItemTags.TRIMMABLE_ELYTRA) && itemStack.getDamage() + amount >= itemStack.getMaxDamage() - 1) {
                itemStack.setDamage(itemStack.getMaxDamage() - 1);
            }
            else {
                itemStack.damage(i, ((LivingEntity)(Object)this), equipmentSlot);
            }
        }
        cbi.cancel();
    }

    @SuppressWarnings("incomplete-switch")
    @Inject(method = "getEquipmentChanges", at = @At("TAIL"), cancellable = true)
    private void injectedGetEquipmentChanges(CallbackInfoReturnable<Map<EquipmentSlot, ItemStack>> cbireturn) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (!slot.isArmorSlot()) continue;
            ItemStack equippedStack = ((LivingEntity)(Object)this).getEquippedStack(slot);
            ArmorTrim trim = equippedStack.get(DataComponentTypes.TRIM);
            if (trim == null) continue;
            if (trim.material().matchesKey(ArmorTrimMaterials.QUARTZ)) {
                int sunlightLevel = this.getWorld().getLightLevel(LightType.SKY, this.getBlockPos()) - this.getWorld().getAmbientDarkness();
                if (sunlightLevel <= 7) {
                    switch (slot) {
                        case HEAD: {
                            ((LivingEntity)(Object)this).addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 4, 0, true, false));
                            break;
                        }
                        case CHEST: {
                            ((LivingEntity)(Object)this).addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 4, 0, true, false));
                            break;
                        }
                    }
                }
                else {
                    switch (slot) {
                        case LEGS: {
                            ((LivingEntity)(Object)this).addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 4, 0, true, false));
                            break;
                        }
                        case FEET: {
                            ((LivingEntity)(Object)this).addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 4, 0, true, false));
                            break;
                        }
                    }
                }
            }
        }
    }

    @Inject(method = "addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;Lnet/minecraft/entity/Entity;)Z", at = @At("HEAD"), cancellable = true)
    private void injectedAddStatusEffectInstance(StatusEffectInstance effect, @Nullable Entity source, CallbackInfoReturnable<Boolean> cbireturn) {
        StatusEffectInstance statusEffectInstance = new StatusEffectInstance(effect);
        int duration = statusEffectInstance.getDuration();
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (!slot.isArmorSlot()) continue;
            ItemStack equippedStack = ((LivingEntity)(Object)this).getEquippedStack(slot);
            ArmorTrim trim = equippedStack.get(DataComponentTypes.TRIM);
            if (trim == null) continue;
            if (trim.material().matchesKey(ArmorTrimMaterials.REDSTONE)) {
                duration = MathHelper.ceil(1.25f*duration);
                statusEffectInstance = new StatusEffectInstance(statusEffectInstance.getEffectType(), duration, statusEffectInstance.getAmplifier(), statusEffectInstance.isAmbient(), statusEffectInstance.shouldShowParticles(), statusEffectInstance.shouldShowIcon());
            }
        }
        if (!((LivingEntity)(Object)this).canHaveStatusEffect(statusEffectInstance)) {
            cbireturn.setReturnValue(false);
        }
        StatusEffectInstance oldStatusEffectInstance = this.activeStatusEffects.get(statusEffectInstance.getEffectType());
        if (oldStatusEffectInstance == null) {
            this.activeStatusEffects.put(statusEffectInstance.getEffectType(), statusEffectInstance);
            this.invokeOnStatusEffectApplied(statusEffectInstance, source);
            effect.onApplied((LivingEntity)(Object)this);
            cbireturn.setReturnValue(true);
        }
        else if (oldStatusEffectInstance != null && oldStatusEffectInstance.upgrade(statusEffectInstance)) {
            this.invokeOnStatusEffectUpgraded(oldStatusEffectInstance, true, source);
            effect.onApplied((LivingEntity)(Object)this);
            cbireturn.setReturnValue(true);
        }
        else {
            cbireturn.setReturnValue(false);
        }
    }

    @Inject(method = "updatePotionVisibility", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;updatePotionSwirls()V", shift = At.Shift.AFTER))
    private void injectedPotionInvisbility(CallbackInfo cbi) {
        if (((LivingEntity)(Object)this).hasStatusEffect(StatusEffects.INVISIBILITY)) {
            this.invokeClearPotionSwirls();
        }
    }

    @Inject(method = "writeCustomData", at = @At("TAIL"))
    private void injectedWriteCustomDataToNbt(WriteView view, CallbackInfo cbi) {
        view.putBoolean("LightningCharged", ((EntityInterface)this).getLightningCharged());
        view.putInt("LightningChargeTime", ((EntityInterface)this).getLightningChargeTime());
        view.putBoolean("RedstoneCharged", this.redstoneCharged);
        view.putInt("RedstoneChargeTime", this.redstoneChargeTime);
        view.putInt("RedstoneChargePower", this.redstoneChargePower);
        view.putDouble("RedstoneParticleYScale", this.redstoneParticleYScale);
    }
    
    @Inject(method = "readCustomData", at = @At("TAIL"))
    private void injectedReadCustomDataFromNbt(ReadView view, CallbackInfo cbi) {
        ((EntityInterface)this).setLightningCharged(view.getBoolean("LightningCharged", false));
        ((EntityInterface)this).setLightningChargeTime(view.getInt("LightningChargeTime", 0));
        this.redstoneCharged  = view.getBoolean("RedstoneCharged", false);
        this.redstoneChargeTime = view.getInt("RedstoneChargeTime", 0);
        this.redstoneChargePower = view.getInt("RedstoneChargePower", 0);
        this.redstoneParticleYScale = view.getDouble("RedstoneParticleYScale", 0.0);
    }

    @Unique
    private static boolean shouldThornsDamageAttacker(int level, Random random) {
        if (level <= 0) {
            return false;
        }
        else {
            return random.nextFloat() < 0.15f*(float)level;
        }
    }

    
}
