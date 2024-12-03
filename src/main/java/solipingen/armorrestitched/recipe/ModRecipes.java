package solipingen.armorrestitched.recipe;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.ArmorRestitched;


public class ModRecipes {
    
    
    public static void registerModRecipes() {

        Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(ArmorRestitched.MOD_ID, "crafting_special_soaringelytra"), 
            SoaringElytraRecipe.SOARING_ELYTRA_RECIPE_SERIALIZER);

    }

}
