package solipingen.armorrestitched.client.integration.emi;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.ArmorRestitched;
import solipingen.armorrestitched.block.ModBlocks;
import solipingen.armorrestitched.item.ModItems;


@Environment(EnvType.CLIENT)
public class ModEMIClientPlugin implements EmiPlugin {
    public static final EmiStack SCUTCHER = EmiStack.of(ModBlocks.SCUTCHER);
    public static final EmiRecipeCategory SCUTCHING_CATEGORY = new EmiRecipeCategory(Identifier.of(ArmorRestitched.MOD_ID, "scutching"), SCUTCHER,
            new EmiTexture(Identifier.of(ArmorRestitched.MOD_ID, "textures/emi/gui/emi_scutching.png"), 0, 0, 16, 16));


    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(SCUTCHING_CATEGORY);
        registry.addWorkstation(SCUTCHING_CATEGORY, SCUTCHER);

        registry.addRecipe(new EMIScutchingRecipe(new ItemStack(ModItems.FLAX_STRAW), new ItemStack(ModItems.LINEN)));
    }


}
