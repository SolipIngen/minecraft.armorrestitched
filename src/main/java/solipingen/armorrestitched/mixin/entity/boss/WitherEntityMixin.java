package solipingen.armorrestitched.mixin.entity.boss;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(WitherEntity.class)
public abstract class WitherEntityMixin extends HostileEntity {


    protected WitherEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "createWitherAttributes", at = @At("HEAD"), cancellable = true)
    private static void injectedWitherAttributes(CallbackInfoReturnable<Builder> cbireturn) {
        cbireturn.setReturnValue(HostileEntity.createHostileAttributes().add(EntityAttributes.MAX_HEALTH, 300.0)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.6f)
                .add(EntityAttributes.FLYING_SPEED, 0.6f)
                .add(EntityAttributes.FOLLOW_RANGE, 40.0)
                .add(EntityAttributes.ARMOR, 20.0)
                .add(EntityAttributes.ARMOR_TOUGHNESS, 8.0));
    }
    
}

