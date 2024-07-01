package solipingen.armorrestitched.recipe;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;
import solipingen.armorrestitched.enchantment.ModEnchantments;


public class SoaringElytraRecipe extends SpecialCraftingRecipe {
    public static final RecipeSerializer<SoaringElytraRecipe> SOARING_ELYTRA_RECIPE_SERIALIZER = new SpecialRecipeSerializer<SoaringElytraRecipe>(SoaringElytraRecipe::new);

    
    public SoaringElytraRecipe(CraftingRecipeCategory category) {
        super(category);
    }
    
    @Override
    public boolean matches(CraftingRecipeInput craftingRecipeInput, World world) {
        if (craftingRecipeInput.getWidth() != 3 || craftingRecipeInput.getHeight() != 3) {
            return false;
        }
        for (int i = 0; i < craftingRecipeInput.getWidth(); ++i) {
            for (int j = 0; j < craftingRecipeInput.getHeight(); ++j) {
                ItemStack itemStack = craftingRecipeInput.getStackInSlot(i + j * craftingRecipeInput.getWidth());
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
    public ItemStack craft(CraftingRecipeInput craftingRecipeInput, RegistryWrapper.WrapperLookup lookup) {
        ItemStack itemStack = craftingRecipeInput.getStackInSlot(1 + craftingRecipeInput.getWidth());
        ItemStack resultItemStack = itemStack.copy();
        if (!(itemStack.getItem() instanceof ElytraItem)) {
            return ItemStack.EMPTY;
        }
        RegistryEntryLookup<Enchantment> enchantmentLookup = lookup.createRegistryLookup().getOrThrow(RegistryKeys.ENCHANTMENT);
        int soaringLevel = EnchantmentHelper.getLevel(enchantmentLookup.getOrThrow(ModEnchantments.SOARING), itemStack);
        if (soaringLevel >= 3) {
            return ItemStack.EMPTY;
        }
        resultItemStack.addEnchantment(enchantmentLookup.getOrThrow(ModEnchantments.SOARING), soaringLevel + 1);
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
