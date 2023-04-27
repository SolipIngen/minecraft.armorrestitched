package solipingen.armorrestitched.recipe;

import java.util.Map;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import solipingen.armorrestitched.enchantment.ModEnchantments;


public class SoaringElytraRecipe extends SpecialCraftingRecipe {
    public static final RecipeSerializer<SoaringElytraRecipe> SOARING_ELYTRA_RECIPE_SERIALIZER = new SpecialRecipeSerializer<SoaringElytraRecipe>(SoaringElytraRecipe::new);

    
    public SoaringElytraRecipe(Identifier id, CraftingRecipeCategory category) {
        super(id, category);
    }
    
    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        if (craftingInventory.getWidth() != 3 || craftingInventory.getHeight() != 3) {
            return false;
        }
        for (int i = 0; i < craftingInventory.getWidth(); ++i) {
            for (int j = 0; j < craftingInventory.getHeight(); ++j) {
                ItemStack itemStack = craftingInventory.getStack(i + j * craftingInventory.getWidth());
                if (i == 1 && j == 1 && !(itemStack.getItem() instanceof ElytraItem)) {
                    return false;
                }
                if (i == 1 && (j == 0 || j == 2) && !itemStack.isEmpty()) {
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
    public ItemStack craft(CraftingInventory craftingInventory, DynamicRegistryManager dynamicRegistryManager) {
        ItemStack itemStack = craftingInventory.getStack(1 + craftingInventory.getWidth());
        if (!(itemStack.getItem() instanceof ElytraItem)) {
            return ItemStack.EMPTY;
        }

        int soaringLevel = EnchantmentHelper.getLevel(ModEnchantments.SOARING, itemStack);
        if (soaringLevel >= 3) {
            return ItemStack.EMPTY;
        }

        Map<Enchantment, Integer> enchantments = EnchantmentHelper.get(itemStack);
        if (soaringLevel <= 0) {
            enchantments.put(ModEnchantments.SOARING, soaringLevel + 1);
        }
        else if (soaringLevel > 0) {
            enchantments.replace(ModEnchantments.SOARING, soaringLevel + 1);
        }
        ItemStack resultItemStack = itemStack.copy();
        EnchantmentHelper.set(enchantments, resultItemStack);
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
