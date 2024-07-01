package solipingen.armorrestitched.mixin.entity.passive;

import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(RabbitEntity.class)
public abstract class RabbitEntityMixin extends AnimalEntity {


    protected RabbitEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "createRabbitAttributes", at = @At("HEAD"), cancellable = true)
    private static void injectedRabbitAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cbireturn) {
        cbireturn.setReturnValue(MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0f)
                .add(EntityAttributes.GENERIC_ARMOR, 2.0));
    }
    

    
}
