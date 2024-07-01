package solipingen.armorrestitched.mixin.entity.mob;

import net.minecraft.entity.attribute.DefaultAttributeContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer.*;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.Hoglin;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZoglinEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(ZoglinEntity.class)
public abstract class ZoglinEntityMixin extends HostileEntity implements Hoglin {


    protected ZoglinEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "createZoglinAttributes", at = @At("HEAD"), cancellable = true)
    private static void injectedZoglinAttributes(CallbackInfoReturnable<Builder> cbireturn) {
        cbireturn.setReturnValue(HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 40.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.6f)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0)
                .add(EntityAttributes.GENERIC_ARMOR, 8.0));
    }

    
}

