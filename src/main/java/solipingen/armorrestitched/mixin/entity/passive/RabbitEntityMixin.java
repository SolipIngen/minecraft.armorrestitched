package solipingen.armorrestitched.mixin.entity.passive;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;


@Mixin(RabbitEntity.class)
public abstract class RabbitEntityMixin extends AnimalEntity {


    protected RabbitEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Redirect(method = "createRabbitAttributes", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;add(Lnet/minecraft/entity/attribute/EntityAttribute;D)Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;"))
    private static DefaultAttributeContainer.Builder redirectedCreateIronGolemAttributes(DefaultAttributeContainer.Builder attributeBuilder, EntityAttribute entityAttribute, double baseValue) {
        if (entityAttribute == EntityAttributes.GENERIC_MAX_HEALTH) {
            return attributeBuilder.add(entityAttribute, 6.0).add(EntityAttributes.GENERIC_ARMOR, 2.0);
        }
        return attributeBuilder.add(entityAttribute, baseValue);
    }

    @ModifyConstant(method = "getJumpVelocity", constant = @Constant(floatValue = 0.5f))
    private float modifiedJumpVelocity1(float originalf) {
        return 0.6f;
    }

    @ModifyConstant(method = "getJumpVelocity", constant = @Constant(floatValue = 0.2f))
    private float modifiedJumpVelocity2(float originalf) {
        return 0.3f;
    }

    @ModifyConstant(method = "getJumpVelocity", constant = @Constant(floatValue = 0.3f))
    private float modifiedJumpVelocity3(float originalf) {
        return 0.4f;
    }

    @Inject(method = "jump", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/AnimalEntity;jump()V", shift = At.Shift.AFTER))
    private void injectedJump(CallbackInfo cbi) {
        if (this.horizontalCollision || (this.moveControl.isMoving() && this.moveControl.getTargetY() < this.getY())) {
            this.updateVelocity(0.33f, new Vec3d(0.0, 0.0, 1.0));
        }
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    

    
}
