package solipingen.armorrestitched.mixin.entity.passive;

import java.util.ArrayList;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.world.World;
import solipingen.armorrestitched.item.ModItems;


@Mixin(ChickenEntity.class)
public abstract class ChickenEntityMixin extends AnimalEntity {
    @Shadow @Final private static Ingredient BREEDING_INGREDIENT;

    
    protected ChickenEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Redirect(method = "initGoals", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/passive/ChickenEntity;BREEDING_INGREDIENT:Lnet/minecraft/recipe/Ingredient;", opcode = Opcodes.GETSTATIC))
    private Ingredient redirectedTemptIngredient() {
        ItemStack[] itemStackList = BREEDING_INGREDIENT.getMatchingStacks();
        ArrayList<ItemConvertible> itemList = new ArrayList<ItemConvertible>();
        for (ItemStack stack : itemStackList) {
            itemList.add(stack.getItem());
        }
        itemList.add(ModItems.FLAXSEEDS);
        ItemConvertible[] breedingItems = new ItemConvertible[]{};
        itemList.toArray(breedingItems);
        return Ingredient.ofItems(breedingItems);
    }

    @Redirect(method = "isBreedingItem", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/passive/ChickenEntity;BREEDING_INGREDIENT:Lnet/minecraft/recipe/Ingredient;", opcode = Opcodes.GETSTATIC))
    private Ingredient redirectedBreedingIngredient() {
        ItemStack[] itemStackList = BREEDING_INGREDIENT.getMatchingStacks();
        ArrayList<ItemConvertible> itemList = new ArrayList<ItemConvertible>();
        for (ItemStack stack : itemStackList) {
            itemList.add(stack.getItem());
        }
        itemList.add(ModItems.FLAXSEEDS);
        ItemConvertible[] breedingItems = new ItemConvertible[]{};
        itemList.toArray(breedingItems);
        return Ingredient.ofItems(breedingItems);
    }



    
}
