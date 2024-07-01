package solipingen.armorrestitched.mixin.entity.mob;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(WardenEntity.class)
public abstract class WardenEntityMixin extends HostileEntity {


    protected WardenEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "addAttributes", at = @At("HEAD"), cancellable = true)
    private static void injectedAddAttributes(CallbackInfoReturnable<Builder> cbireturn) {
        cbireturn.setReturnValue(HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 500.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1.5)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 30.0)
                .add(EntityAttributes.GENERIC_ARMOR, 20.0));
    }

    
}

