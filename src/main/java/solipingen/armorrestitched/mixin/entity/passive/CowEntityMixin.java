package solipingen.armorrestitched.mixin.entity.passive;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.recipe.Ingredient;
import net.minecraft.world.World;
import solipingen.armorrestitched.item.ModItems;


@Mixin(CowEntity.class)
public abstract class CowEntityMixin extends AnimalEntity {


    protected CowEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Redirect(method = "createCowAttributes", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;add(Lnet/minecraft/entity/attribute/EntityAttribute;D)Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;"))
    private static DefaultAttributeContainer.Builder redirectedCreateCowAttributes(DefaultAttributeContainer.Builder attributeBuilder, EntityAttribute entityAttribute, double baseValue) {
        if (entityAttribute == EntityAttributes.GENERIC_MAX_HEALTH) {
            return attributeBuilder.add(entityAttribute, baseValue).add(EntityAttributes.GENERIC_ARMOR, 6.0);
        }
        return attributeBuilder.add(entityAttribute, baseValue);
    }

    @Inject(method = "initGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/recipe/Ingredient;ofItems([Lnet/minecraft/item/ItemConvertible;)Lnet/minecraft/recipe/Ingredient;", shift = At.Shift.AFTER))
    private void injectedTemptGoal(CallbackInfo cbi) {
        this.goalSelector.add(3, new TemptGoal(this, 1.25, Ingredient.ofItems(ModItems.FLAX_STEM), false));
    }

    
}
