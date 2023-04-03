package solipingen.armorrestitched.mixin.entity.passive;

import java.util.ArrayList;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.world.World;
import solipingen.armorrestitched.item.ModItems;


@Mixin(SheepEntity.class)
public abstract class SheepEntityMixin extends AnimalEntity {
    

    protected SheepEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Redirect(method = "initGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/recipe/Ingredient;ofItems([Lnet/minecraft/item/ItemConvertible;)Lnet/minecraft/recipe/Ingredient;"))
    private Ingredient redirectedTemptIngredient(ItemConvertible... originaItemConvertibles) {
        ArrayList<ItemConvertible> itemConvertibleList = new ArrayList<ItemConvertible>();
        for (ItemConvertible itemConvertible : originaItemConvertibles) {
            itemConvertibleList.add(itemConvertible);
        }
        itemConvertibleList.add(ModItems.FLAX_STEM);
        ItemConvertible[] itemConvertibles = new ItemConvertible[]{};
        itemConvertibleList.toArray(itemConvertibles);
        return Ingredient.ofItems(itemConvertibles);
    }



}
