package solipingen.armorrestitched.mixin.entity.passive;

import java.util.ArrayList;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.world.World;
import solipingen.armorrestitched.block.ModBlocks;
import solipingen.armorrestitched.item.ModItems;


@Mixin(LlamaEntity.class)
public abstract class LlamaEntityMixin extends AbstractDonkeyEntity {
    @Shadow @Final private static Ingredient TAMING_INGREDIENT;


    protected LlamaEntityMixin(EntityType<? extends AbstractDonkeyEntity> entityType, World world) {
        super(entityType, world);
    }

    @Redirect(method = "initGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/recipe/Ingredient;ofItems([Lnet/minecraft/item/ItemConvertible;)Lnet/minecraft/recipe/Ingredient;"))
    private Ingredient redirectedTemptIngredient(ItemConvertible... originaItemConvertibles) {
        ArrayList<ItemConvertible> itemConvertibleList = new ArrayList<ItemConvertible>();
        for (ItemConvertible itemConvertible : originaItemConvertibles) {
            itemConvertibleList.add(itemConvertible);
        }
        itemConvertibleList.add(ModBlocks.FLAX_BLOCK);
        ItemConvertible[] itemConvertibles = new ItemConvertible[]{};
        itemConvertibleList.toArray(itemConvertibles);
        return Ingredient.ofItems(itemConvertibles);
    }

    @Redirect(method = "isBreedingItem", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/passive/LlamaEntity;TAMING_INGREDIENT:Lnet/minecraft/recipe/Ingredient;", opcode = Opcodes.GETSTATIC))
    private Ingredient redirectedBreedingIngredient() {
        ItemStack[] itemStackList = TAMING_INGREDIENT.getMatchingStacks();
        ArrayList<ItemConvertible> itemList = new ArrayList<ItemConvertible>();
        for (ItemStack stack : itemStackList) {
            itemList.add(stack.getItem());
        }
        itemList.add(ModItems.FLAX_STEM);
        ItemConvertible[] breedingItems = new ItemConvertible[]{};
        itemList.toArray(breedingItems);
        return Ingredient.ofItems(breedingItems);
    }


    
}
