package solipingen.armorrestitched.mixin.entity.mob;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer.*;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.Hoglin;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZoglinEntity;
import net.minecraft.world.World;


@Mixin(ZoglinEntity.class)
public abstract class ZoglinEntityMixin extends HostileEntity implements Hoglin {


    protected ZoglinEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Redirect(method = "createZoglinAttributes", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;add(Lnet/minecraft/entity/attribute/EntityAttribute;D)Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;"))
    private static Builder redirectedCreateZoglinAttributes(Builder attributeBuilder, EntityAttribute entityAttribute, double baseValue) {
        if (entityAttribute == EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE) {
            return attributeBuilder.add(EntityAttributes.GENERIC_ARMOR, 8.0).add(entityAttribute, baseValue);
        }
        return attributeBuilder.add(entityAttribute, baseValue);
    }
        

    
}

