package solipingen.armorrestitched.mixin.entity.mob;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;


@Mixin(SlimeEntity.class)
public abstract class SlimeEntityMixin extends MobEntity implements Monster {


    protected SlimeEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "setSize", at = @At("TAIL"))
    private void injectedSetSize(int size, boolean heal, CallbackInfo cbi) {
        int i = MathHelper.clamp(size, 1, 20);
        this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR).setBaseValue(i);
        this.getAttributeInstance(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(i/20.0);
        this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_KNOCKBACK).setBaseValue(0.2*i);
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        Vec3d velocity = this.getVelocity();
        this.setVelocity(velocity.x, -velocity.y, velocity.z);
        return false;
    }

    
}

