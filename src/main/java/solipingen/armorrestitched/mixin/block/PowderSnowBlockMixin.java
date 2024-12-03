package solipingen.armorrestitched.mixin.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.block.Block;
import net.minecraft.block.PowderSnowBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import solipingen.armorrestitched.registry.tag.ModItemTags;


@Mixin(PowderSnowBlock.class)
public class PowderSnowBlockMixin extends Block {


    public PowderSnowBlockMixin(Settings settings) {
        super(settings);
    }

    @Redirect(method = "canWalkOnPowderSnow", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private static boolean redirectedIsOfLeatherBoots(ItemStack itemStack, Item originalItem) {
        return itemStack.isIn(ModItemTags.CLOTHING_BOOTS);
    }
    

    

}
