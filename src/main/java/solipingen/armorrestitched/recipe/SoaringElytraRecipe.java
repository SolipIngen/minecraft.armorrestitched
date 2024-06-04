package solipingen.armorrestitched.recipe;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;
import solipingen.armorrestitched.enchantment.ModEnchantments;


public class SoaringElytraRecipe extends SpecialCraftingRecipe {
    public static final RecipeSerializer<SoaringElytraRecipe> SOARING_ELYTRA_RECIPE_SERIALIZER = new SpecialRecipeSerializer<SoaringElytraRecipe>(SoaringElytraRecipe::new);

    
    public SoaringElytraRecipe(CraftingRecipeCategory category) {
        super(category);
    }
    
    @Override
    public boolean matches(RecipeInputInventory craftingInventory, World world) {
        if (craftingInventory.getWidth() != 3 || craftingInventory.getHeight() != 3) {
            return false;
        }
        for (int i = 0; i < craftingInventory.getWidth(); ++i) {
            for (int j = 0; j < craftingInventory.getHeight(); ++j) {
                ItemStack itemStack = craftingInventory.getStack(i + j * craftingInventory.getWidth());
                if (i == 1 && j == 1 && !(itemStack.getItem() instanceof ElytraItem)) {
                    return false;
                }
                if (i == 1 && (j == 0 || j == 2) && !itemStack.isOf(Items.BLAZE_POWDER)) {
                    return false;
                }
                if (i != 1 && !itemStack.isOf(Items.PHANTOM_MEMBRANE)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public ItemStack craft(RecipeInputInventory craftingInventory, RegistryWrapper.WrapperLookup lookup) {
        ItemStack itemStack = craftingInventory.getStack(1 + craftingInventory.getWidth());
        ItemStack resultItemStack = itemStack.copy();
        if (!(itemStack.getItem() instanceof ElytraItem)) {
            return ItemStack.EMPTY;
        }
        int soaringLevel = EnchantmentHelper.getLevel(ModEnchantments.SOARING, itemStack);
        if (soaringLevel >= 3) {
            return ItemStack.EMPTY;
        }
        resultItemStack.addEnchantment(ModEnchantments.SOARING, soaringLevel + 1);
        return resultItemStack;
    }

    @Override
    public boolean fits(int width, int height) {
        return width >= 2 && height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SOARING_ELYTRA_RECIPE_SERIALIZER;
    }
    

}
