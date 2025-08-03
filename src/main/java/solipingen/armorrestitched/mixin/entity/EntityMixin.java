package solipingen.armorrestitched.mixin.entity;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.equipment.trim.ArmorTrim;
import net.minecraft.item.equipment.trim.ArmorTrimMaterials;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Nameable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.entity.EntityLike;
import solipingen.armorrestitched.enchantment.ModEnchantments;
import solipingen.armorrestitched.item.equipment.ModEquipmentAssetKeys;
import solipingen.armorrestitched.util.interfaces.mixin.entity.EntityInterface;


@Mixin(Entity.class)
public abstract class EntityMixin implements Nameable, EntityLike, CommandOutput, EntityInterface {
    @Shadow private int fireTicks;
    @Shadow public double fallDistance;

    @Unique private boolean lightningCharged = false;
    @Unique private int lightningChargeTime = 0;


    @Inject(method = "baseTick", at = @At("TAIL"))
    private void injectedBaseTick(CallbackInfo cbi) {
        if (this.lightningCharged) {
            if (((Entity)(Object)this).getWorld() instanceof ServerWorld) {
                ServerWorld serverWorld = (ServerWorld)((Entity)(Object)this).getWorld();
                Vec3d position = new Vec3d(((Entity)(Object)this).getPos().x, ((Entity)(Object)this).getBodyY(0.5), ((Entity)(Object)this).getPos().z);
                serverWorld.spawnParticles(ParticleTypes.ELECTRIC_SPARK, position.x, position.y, position.z, 1, 0.0, 0.0, 0.0, serverWorld.random.nextDouble());
            }
            this.lightningChargeTime--;
        }
        if (this.lightningChargeTime <= 0) {
            this.lightningCharged = false;
            this.lightningChargeTime = 0;
        }
    }

    @Inject(method = "limitFallDistance", at = @At("TAIL"))
    private void injectedLimitFallDistance(CallbackInfo cbi) {
        if ((Entity)(Object)this instanceof LivingEntity livingEntity) {
            RegistryEntryLookup<Enchantment> enchantmentLookup = livingEntity.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT);
            if (EnchantmentHelper.getEquipmentLevel(enchantmentLookup.getOrThrow(ModEnchantments.IMPACT_PROTECTION), livingEntity) > 0) {
                this.fallDistance *= (1.0f - 0.005f*EnchantmentHelper.getEquipmentLevel(enchantmentLookup.getOrThrow(ModEnchantments.IMPACT_PROTECTION), livingEntity));
            }
        }
    }

    @Inject(method = "onStruckByLightning", at = @At("HEAD"), cancellable = true)
    private void injectedOnStruckByLightning(ServerWorld world, LightningEntity lightning, CallbackInfo cbi) {
        World lightningWorld = lightning.getWorld();
        double randomFactor = Math.abs(lightningWorld.random.nextGaussian()) + 1.0;
        float damageAmount = (float)randomFactor*5.0f*lightningWorld.getDifficulty().getId();
        if (((Entity)(Object)this) instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity)(Object)this;
            boolean fireBl = true;
            int i = 0;
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (!slot.isArmorSlot()) continue;
                ItemStack equippedStack = livingEntity.getEquippedStack(slot);
                if (equippedStack.get(DataComponentTypes.EQUIPPABLE) != null && equippedStack.get(DataComponentTypes.EQUIPPABLE).assetId().get() == ModEquipmentAssetKeys.COPPER && livingEntity.isOnGround()) {
                    damageAmount *= 0.25f;
                    fireBl = false;
                }
                ArmorTrim trim = equippedStack.get(DataComponentTypes.TRIM);
                if (trim == null) continue;
                if (trim.material().matchesKey(ArmorTrimMaterials.COPPER)) {
                    damageAmount *= 0.75f;
                    i++;
                    this.lightningCharged = true;
                }
            }
            this.lightningChargeTime = this.lightningChargeTime <= 0 ? MathHelper.ceil(randomFactor*i*2400) : this.lightningChargeTime;
            StatusEffectInstance strengthStatusEffectInstance = new StatusEffectInstance(StatusEffects.STRENGTH, this.lightningChargeTime, 0, true, false);
            StatusEffectInstance speedStatusEffectInstance = new StatusEffectInstance(StatusEffects.SPEED, this.lightningChargeTime, 0, true, false);
            StatusEffectInstance jumpBoostStatusEffectInstance = new StatusEffectInstance(StatusEffects.JUMP_BOOST, this.lightningChargeTime, 0, true, false);
            livingEntity.addStatusEffect(strengthStatusEffectInstance);
            livingEntity.addStatusEffect(speedStatusEffectInstance);
            livingEntity.addStatusEffect(jumpBoostStatusEffectInstance);
            if (fireBl) {
                livingEntity.setFireTicks(this.fireTicks + 1);
                if (this.fireTicks == 0) {
                    livingEntity.setOnFireFor(8);
                }
            }
            livingEntity.damage(world, lightning.getDamageSources().lightningBolt(), damageAmount);
            cbi.cancel();
        }
    }

    @ModifyConstant(method = "onStruckByLightning", constant = @Constant(floatValue = 5.0f))
    private float modifiedLightningDamage(float originalf, ServerWorld world, LightningEntity lightning) {
        World lightningWorld = lightning.getWorld();
        return (float)(lightningWorld.random.nextGaussian() + 1.0f)*5.0f*lightningWorld.getDifficulty().getId();
    }

    @Override
    public boolean getLightningCharged() {
        return this.lightningCharged;
    }

    @Override
    public void setLightningCharged(boolean charged) {
        this.lightningCharged = charged;
    }

    @Override
    public int getLightningChargeTime() {
        return this.lightningChargeTime;
    }

    @Override
    public void setLightningChargeTime(int time) {
        this.lightningChargeTime = time;
    }


}
