package solipingen.armorrestitched.mixin.recipe;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.SmithingRecipe;
import net.minecraft.recipe.SmithingTrimRecipe;


@Mixin(SmithingTrimRecipe.class)
public abstract class SmithingTrimRecipeMixin implements SmithingRecipe {
    

//    @Redirect(method = "getResult", at = @At(value = "FIELD", target = "Lnet/minecraft/item/Items;IRON_CHESTPLATE:Lnet/minecraft/item/Item;", opcode = Opcodes.GETSTATIC))
//    private Item redirectedChestplate() {
//        return Items.GOLDEN_CHESTPLATE;
//    }

}
