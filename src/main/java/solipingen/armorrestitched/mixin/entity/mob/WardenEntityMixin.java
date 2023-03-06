package solipingen.armorrestitched.mixin.entity.mob;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer.*;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.world.World;
import net.minecraft.world.event.listener.VibrationListener;


@Mixin(WardenEntity.class)
public abstract class WardenEntityMixin extends HostileEntity implements VibrationListener.Callback {


    protected WardenEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Redirect(method = "addAttributes", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;add(Lnet/minecraft/entity/attribute/EntityAttribute;D)Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;"))
    private static Builder redirectedAddAttributes(Builder attributeBuilder, EntityAttribute entityAttribute, double baseValue) {
        if (entityAttribute == EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE) {
            return attributeBuilder.add(EntityAttributes.GENERIC_ARMOR, 20.0).add(entityAttribute, baseValue);
        }
        return attributeBuilder.add(entityAttribute, baseValue);
    }

    
}

