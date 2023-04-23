package solipingen.armorrestitched.mixin.entity.mob;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer.*;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.AbstractPiglinEntity;
import net.minecraft.entity.mob.PiglinBruteEntity;
import net.minecraft.world.World;


@Mixin(PiglinBruteEntity.class)
public abstract class PiglinBruteEntityMixin extends AbstractPiglinEntity {


    
    public PiglinBruteEntityMixin(EntityType<? extends AbstractPiglinEntity> entityType, World world) {
        super(entityType, world);
    }

    @Redirect(method = "createPiglinBruteAttributes", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;add(Lnet/minecraft/entity/attribute/EntityAttribute;D)Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;"))
    private static Builder redirectedCreateHoglinAttributes(Builder attributeBuilder, EntityAttribute entityAttribute, double baseValue) {
        if (entityAttribute == EntityAttributes.GENERIC_ATTACK_DAMAGE) {
            return attributeBuilder.add(EntityAttributes.GENERIC_ARMOR, 6.0).add(entityAttribute, baseValue);
        }
        return attributeBuilder.add(entityAttribute, baseValue);
    }

    
}
