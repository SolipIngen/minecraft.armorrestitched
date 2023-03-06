package solipingen.armorrestitched.mixin.entity.mob;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.attribute.DefaultAttributeContainer.*;
import net.minecraft.entity.mob.Hoglin;
import net.minecraft.entity.mob.HoglinEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.World;


@Mixin(HoglinEntity.class)
public abstract class HoglinEntityMixin extends AnimalEntity implements Monster, Hoglin {


    protected HoglinEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Redirect(method = "createHoglinAttributes", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;add(Lnet/minecraft/entity/attribute/EntityAttribute;D)Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;"))
    private static Builder redirectedCreateHoglinAttributes(Builder attributeBuilder, EntityAttribute entityAttribute, double baseValue) {
        if (entityAttribute == EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE) {
            return attributeBuilder.add(EntityAttributes.GENERIC_ARMOR, 8.0).add(entityAttribute, baseValue);
        }
        return attributeBuilder.add(entityAttribute, baseValue);
    }
        

    
}

