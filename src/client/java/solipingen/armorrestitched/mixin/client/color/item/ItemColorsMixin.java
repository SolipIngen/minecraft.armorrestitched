package solipingen.armorrestitched.mixin.client.color.item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.item.DyeableItem;
import solipingen.armorrestitched.item.ModItems;


@Mixin(ItemColors.class)
@Environment(value = EnvType.CLIENT)
public abstract class ItemColorsMixin {
    

    @ModifyVariable(method = "create", at = @At("STORE"), ordinal = 0)
    private static ItemColors modifiedItemColors(ItemColors originalItemColors) {
        ItemColors itemColors = originalItemColors;
        itemColors.register((stack, tintIndex) -> tintIndex > 0 ? -1 : ((DyeableItem)((Object)stack.getItem())).getColor(stack), 
            ModItems.COTTON_HELMET, 
            ModItems.COTTON_CHESTPLATE, 
            ModItems.COTTON_LEGGINGS, 
            ModItems.COTTON_BOOTS,
            ModItems.FUR_HELMET, 
            ModItems.FUR_CHESTPLATE, 
            ModItems.FUR_LEGGINGS, 
            ModItems.FUR_BOOTS,
            ModItems.LINEN_HELMET, 
            ModItems.LINEN_CHESTPLATE, 
            ModItems.LINEN_LEGGINGS, 
            ModItems.LINEN_BOOTS,
            ModItems.SILK_HELMET, 
            ModItems.SILK_CHESTPLATE, 
            ModItems.SILK_LEGGINGS, 
            ModItems.SILK_BOOTS,
            ModItems.WOOL_HELMET, 
            ModItems.WOOL_CHESTPLATE, 
            ModItems.WOOL_LEGGINGS, 
            ModItems.WOOL_BOOTS, 
            ModItems.PAPER_HELMET, 
            ModItems.PAPER_CHESTPLATE, 
            ModItems.PAPER_LEGGINGS, 
            ModItems.PAPER_BOOTS);
        return itemColors;
    }
    


}
