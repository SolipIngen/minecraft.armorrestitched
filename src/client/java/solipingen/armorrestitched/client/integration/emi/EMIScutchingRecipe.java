package solipingen.armorrestitched.client.integration.emi;


import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import solipingen.armorrestitched.ArmorRestitched;

import java.util.List;
import java.util.Optional;


@Environment(EnvType.CLIENT)
public class EMIScutchingRecipe implements EmiRecipe {
    protected final EmiIngredient ingredient;
    protected final EmiStack output;


    public EMIScutchingRecipe(ItemStack input, ItemStack output) {
        this.ingredient = EmiIngredient.of(Ingredient.ofStacks(input));
        this.output = EmiStack.of(output);
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return ModEMIClientPlugin.SCUTCHING_CATEGORY;
    }

    @Override
    @Nullable
    public Identifier getId() {
        Optional<RegistryEntry<Potion>> potionOptional = this.output.getOrDefault(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT).potion();
        return potionOptional.map(potionEntry -> new Identifier(ArmorRestitched.MOD_ID, this.output.getId().getPath() + "_scutching")).orElse(null);
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return List.of(this.ingredient);
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of(this.output);
    }

    @Override
    public int getDisplayWidth() {
        return 64;
    }

    @Override
    public int getDisplayHeight() {
        return 48;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(EmiTexture.EMPTY_ARROW, 20, 16);
        widgets.addSlot(this.ingredient, 2, 3);
        widgets.addTexture(new EmiTexture(new Identifier(ArmorRestitched.MOD_ID, "textures/emi/gui/emi_scutching.png"), 16, 0, 16, 16), 4, 25);
        widgets.addSlot(this.output, 45, 16).recipeContext(this);
    }
}
