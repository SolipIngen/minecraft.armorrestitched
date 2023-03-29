package solipingen.armorrestitched.mixin.entity;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.common.collect.Multimap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.item.trim.ArmorTrimMaterial;
import net.minecraft.item.trim.ArmorTrimMaterials;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import solipingen.armorrestitched.util.interfaces.mixin.entity.EntityInterface;
import solipingen.armorrestitched.util.interfaces.mixin.entity.mob.MobEntityInterface;


@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow @Final private Map<StatusEffect, StatusEffectInstance> activeStatusEffects;

    @Invoker("onStatusEffectApplied")
    public abstract void invokeOnStatusEffectApplied(StatusEffectInstance effect, @Nullable Entity source);

    @Invoker("onStatusEffectUpgraded")
    public abstract void invokeOnStatusEffectUpgraded(StatusEffectInstance effect, boolean reapplyEffect, @Nullable Entity source);


    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @SuppressWarnings("incomplete-switch")
    @Inject(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;tickStatusEffects()V"))
    private void injectedBaseTick(CallbackInfo cbi) {
        if (this.age % 4 == 0) {
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (slot.getType() != EquipmentSlot.Type.ARMOR) continue;
                ItemStack equippedStack = ((LivingEntity)(Object)this).getEquippedStack(slot);
                Optional<ArmorTrim> trimOptional = ArmorTrim.getTrim(this.world.getRegistryManager(), equippedStack);
                if (trimOptional.isEmpty()) continue;
                if (trimOptional.get().getMaterial().matchesKey(ArmorTrimMaterials.QUARTZ)) {
                    int sunlightLevel = this.world.getLightLevel(LightType.SKY, this.getBlockPos()) - this.world.getAmbientDarkness();
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
    }

    @Inject(method = "damage", at = @At("TAIL"), cancellable = true)
    private void injectedDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cbireturn) {
        int i = 0;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() != EquipmentSlot.Type.ARMOR) continue;
            ItemStack equippedStack = ((LivingEntity)(Object)this).getEquippedStack(slot);
            Optional<ArmorTrim> trimOptional = ArmorTrim.getTrim(this.world.getRegistryManager(), equippedStack);
            if (trimOptional.isPresent() && trimOptional.get().getMaterial().matchesKey(ArmorTrimMaterials.AMETHYST)) {
                i++;
            }
        }
        if (i > 0) {
            ((LivingEntity)(Object)this).addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, this.random.nextBetween(50, Math.max(MathHelper.ceil(((LivingEntity)(Object)this).getSoundPitch()*50), 50)), i - 1, true, true, false));
            this.world.playSound(null, this.getBlockPos(), SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.PLAYERS, MathHelper.clamp(i*amount, 4.0f, i*4.0f), ((LivingEntity)(Object)this).getSoundPitch());
        }
        Box amethystSoundBox = new Box(this.getBlockPos()).expand(4.0*i);
        List<Entity> amethystSoundEntities = this.world.getOtherEntities(this, amethystSoundBox);
        for (Entity entity : amethystSoundEntities) {
            if (entity.distanceTo(entity) > 4.0f*i) continue;
            if (entity instanceof MobEntity && entity.world instanceof ServerWorld) {
                MobEntity mobEntity = (MobEntity)entity;
                if (mobEntity.getTarget() == null && mobEntity.getGroup() != EntityGroup.UNDEAD) {
                    int duration = Math.max(MathHelper.ceil(5*i*amount/Math.max(mobEntity.squaredDistanceTo(this), 1.0)), 5);
                    mobEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, mobEntity.getRandom().nextBetween(5, Math.max(duration, 5)), 0, true, true, false));
                }
                else if (mobEntity.getTarget() != null && mobEntity.getGroup() != EntityGroup.UNDEAD && (mobEntity instanceof HostileEntity || mobEntity.getTarget() == ((LivingEntity)(Object)this))) {
                    ((MobEntityInterface)mobEntity).setEntranced(true, mobEntity.getRandom().nextBetween(10, Math.max(i*20, 10)));
                }
            }
            else if (entity instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity)entity;
                if (source.getAttacker() == player || ((LivingEntity)(Object)this).getLastAttacker() == player) continue;
                int duration = Math.max(MathHelper.ceil(5*i*amount/Math.max(player.squaredDistanceTo(this), 1.0)), 5);
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, player.getRandom().nextBetween(5, Math.max(duration, 5)), 0, true, true, false));
            }
        }
    }

    @Redirect(method = "getEquipmentChanges", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getAttributeModifiers(Lnet/minecraft/entity/EquipmentSlot;)Lcom/google/common/collect/Multimap;"))
    private Multimap<EntityAttribute, EntityAttributeModifier> redirectedAttributeModifiers(ItemStack itemStack, EquipmentSlot slot) {
        Multimap<EntityAttribute, EntityAttributeModifier> multimap = itemStack.getAttributeModifiers(slot);
        Optional<ArmorTrim> trimOptional = ArmorTrim.getTrim(this.world.getRegistryManager(), itemStack);
        if (trimOptional.isPresent()) {
            RegistryEntry<ArmorTrimMaterial> trimMaterial = trimOptional.get().getMaterial();
            if (trimMaterial.matchesKey(ArmorTrimMaterials.DIAMOND)) {
                Collection<EntityAttributeModifier> currentAttributeModifiers = itemStack.getAttributeModifiers(slot).get(EntityAttributes.GENERIC_ARMOR_TOUGHNESS);
                for (EntityAttributeModifier currentAttributeModifier : currentAttributeModifiers) {
                    EntityAttributeModifier boostedAttributeModifier = new EntityAttributeModifier(currentAttributeModifier.getId(), currentAttributeModifier.getName(), 1.25*currentAttributeModifier.getValue(), currentAttributeModifier.getOperation());
                    multimap.remove(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, currentAttributeModifier);
                    multimap.put(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, boostedAttributeModifier);
                }
            }
            else if (trimMaterial.matchesKey(ArmorTrimMaterials.NETHERITE)) {
                Collection<EntityAttributeModifier> currentAttributeModifiers = itemStack.getAttributeModifiers(slot).get(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE);
                for (EntityAttributeModifier currentAttributeModifier : currentAttributeModifiers) {
                    EntityAttributeModifier boostedAttributeModifier = new EntityAttributeModifier(currentAttributeModifier.getId(), currentAttributeModifier.getName(), 1.25*currentAttributeModifier.getValue(), currentAttributeModifier.getOperation());
                    multimap.remove(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, currentAttributeModifier);
                    multimap.put(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, boostedAttributeModifier);
                }
            }
        }
        return multimap;
    }

    @SuppressWarnings("incomplete-switch")
    @Inject(method = "getEquipmentChanges", at = @At("TAIL"), cancellable = true)
    private void injectedGetEquipmentChanges(CallbackInfoReturnable<Map<EquipmentSlot, ItemStack>> cbireturn) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() != EquipmentSlot.Type.ARMOR) continue;
            ItemStack equippedStack = ((LivingEntity)(Object)this).getEquippedStack(slot);
            Optional<ArmorTrim> trimOptional = ArmorTrim.getTrim(this.world.getRegistryManager(), equippedStack);
            if (trimOptional.isEmpty()) continue;
            if (trimOptional.get().getMaterial().matchesKey(ArmorTrimMaterials.QUARTZ)) {
                int sunlightLevel = this.world.getLightLevel(LightType.SKY, this.getBlockPos()) - this.world.getAmbientDarkness();
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
            if (slot.getType() != EquipmentSlot.Type.ARMOR) continue;
            ItemStack equippedStack = ((LivingEntity)(Object)this).getEquippedStack(slot);
            Optional<ArmorTrim> trimOptional = ArmorTrim.getTrim(this.world.getRegistryManager(), equippedStack);
            if (trimOptional.isEmpty()) continue;
            if (trimOptional.get().getMaterial().matchesKey(ArmorTrimMaterials.REDSTONE)) {
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
            cbireturn.setReturnValue(true);
        }
        else if (oldStatusEffectInstance != null && oldStatusEffectInstance.upgrade(statusEffectInstance)) {
            this.invokeOnStatusEffectUpgraded(oldStatusEffectInstance, true, source);
            cbireturn.setReturnValue(true);
        }
        else {
            cbireturn.setReturnValue(false);
        }
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void injectedWriteCustomDataToNbt(NbtCompound nbt, CallbackInfo cbi) {
        nbt.putBoolean("LightningCharged", ((EntityInterface)this).getLightningCharged());
        nbt.putInt("LightningChargeTime", ((EntityInterface)this).getLightningChargeTime());
    }
    
    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void injectedReadCustomDataFromNbt(NbtCompound nbt, CallbackInfo cbi) {
        ((EntityInterface)this).setLightningCharged(nbt.getBoolean("LightningCharged"));
        ((EntityInterface)this).setLightningChargeTime(nbt.getInt("LightningChargeTime"));
    }


    
}
