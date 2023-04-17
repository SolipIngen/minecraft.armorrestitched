package solipingen.armorrestitched.mixin.entity;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
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
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.item.trim.ArmorTrimMaterials;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Nameable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.entity.EntityLike;
import solipingen.armorrestitched.item.ModArmorMaterials;
import solipingen.armorrestitched.util.interfaces.mixin.entity.EntityInterface;


@Mixin(Entity.class)
public abstract class EntityMixin implements Nameable, EntityLike, CommandOutput, EntityInterface {
    @Shadow private int fireTicks;
    private boolean lightningCharged = false;
    private int lightningChargeTime = 0;


    @Inject(method = "baseTick", at = @At("TAIL"))
    private void injectedBaseTick(CallbackInfo cbi) {
        if (this.lightningCharged) {
            if (((Entity)(Object)this).world instanceof ServerWorld) {
                ServerWorld serverWorld = (ServerWorld)((Entity)(Object)this).world;
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

    @Inject(method = "onStruckByLightning", at = @At("HEAD"), cancellable = true)
    private void injectedOnStruckByLightning(ServerWorld world, LightningEntity lightning, CallbackInfo cbi) {
        double randomFactor = Math.abs(lightning.world.random.nextGaussian()) + 1.0;
        float damageAmount = (float)randomFactor*5.0f*lightning.world.getDifficulty().getId();
        if (((Entity)(Object)this) instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity)(Object)this;
            boolean fireBl = true;
            int i = 0;
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (slot.getType() != EquipmentSlot.Type.ARMOR) continue;
                ItemStack equippedStack = livingEntity.getEquippedStack(slot);
                if (equippedStack.getItem() instanceof ArmorItem && ((ArmorItem)equippedStack.getItem()).getMaterial() == ModArmorMaterials.COPPER) {
                    damageAmount *= 0.25f;
                    fireBl = false;
                }
                Optional<ArmorTrim> trimOptional = ArmorTrim.getTrim(livingEntity.world.getRegistryManager(), equippedStack);
                if (trimOptional.isEmpty()) continue;
                if (trimOptional.get().getMaterial().matchesKey(ArmorTrimMaterials.COPPER)) {
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
            livingEntity.damage(lightning.getDamageSources().lightningBolt(), damageAmount);
            cbi.cancel();
        }
    }

    @ModifyConstant(method = "onStruckByLightning", constant = @Constant(floatValue = 5.0f))
    private float modifiedLightningDamage(float originalf, ServerWorld world, LightningEntity lightning) {
        return (float)(lightning.world.random.nextGaussian() + 1.0f)*5.0f*lightning.world.getDifficulty().getId();
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
