package solipingen.armorrestitched.mixin.entity.passive;

import java.util.ArrayList;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.item.ItemConvertible;
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

    @Redirect(method = "initGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/recipe/Ingredient;ofItems([Lnet/minecraft/item/ItemConvertible;)Lnet/minecraft/recipe/Ingredient;"))
    private Ingredient redirectedTemptIngredient(ItemConvertible... originaItemConvertibles) {
        ArrayList<ItemConvertible> itemConvertibleList = new ArrayList<ItemConvertible>();
        for (ItemConvertible itemConvertible : originaItemConvertibles) {
            itemConvertibleList.add(itemConvertible);
        }
        itemConvertibleList.add(ModItems.FLAX_STEM);
        ItemConvertible[] itemConvertibles = itemConvertibleList.toArray(new ItemConvertible[itemConvertibleList.size()]);
        return Ingredient.ofItems(itemConvertibles);
    }

    
}
