package solipingen.armorrestitched.mixin.entity.passive;

import java.util.ArrayList;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.world.World;
import solipingen.armorrestitched.block.ModBlocks;
import solipingen.armorrestitched.item.ModItems;


@Mixin(AbstractHorseEntity.class)
public abstract class AbstractHorseEntityMixin extends AnimalEntity {
    @Shadow @Final private static Ingredient BREEDING_INGREDIENT;


    protected AbstractHorseEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Redirect(method = "initCustomGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/recipe/Ingredient;ofItems([Lnet/minecraft/item/ItemConvertible;)Lnet/minecraft/recipe/Ingredient;"))
    private Ingredient redirectedTemptIngredient(ItemConvertible... originaItemConvertibles) {
        ArrayList<ItemConvertible> itemConvertibleList = new ArrayList<ItemConvertible>();
        for (ItemConvertible itemConvertible : originaItemConvertibles) {
            itemConvertibleList.add(itemConvertible);
        }
        itemConvertibleList.add(ModItems.FLAX_STEM);
        ItemConvertible[] itemConvertibles = itemConvertibleList.toArray(new ItemConvertible[itemConvertibleList.size()]);
        return Ingredient.ofItems(itemConvertibles);
    }

    @Redirect(method = "isBreedingItem", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/passive/AbstractHorseEntity;BREEDING_INGREDIENT:Lnet/minecraft/recipe/Ingredient;", opcode = Opcodes.GETSTATIC))
    private Ingredient redirectedBreedingIngredient() {
        ItemStack[] itemStackList = BREEDING_INGREDIENT.getMatchingStacks();
        ArrayList<ItemConvertible> itemList = new ArrayList<ItemConvertible>();
        for (ItemStack stack : itemStackList) {
            itemList.add(stack.getItem());
        }
        itemList.add(ModItems.FLAX_STEM);
        itemList.add(ModBlocks.FLAX_BLOCK);
        ItemConvertible[] itemConvertibles = itemList.toArray(new ItemConvertible[itemList.size()]);
        return Ingredient.ofItems(itemConvertibles);
    }

    @Redirect(method = "receiveFood", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private boolean redirectedReceiveFoodIsOf(ItemStack itemStack, Item item) {
        if (item == Items.WHEAT) {
            return itemStack.isOf(item) || itemStack.isOf(ModItems.FLAX_STEM);
        }
        else if (item == Blocks.HAY_BLOCK.asItem()) {
            return itemStack.isOf(item) || itemStack.isOf(ModBlocks.FLAX_BLOCK.asItem());
        }
        return itemStack.isOf(item);
    }


    
}
