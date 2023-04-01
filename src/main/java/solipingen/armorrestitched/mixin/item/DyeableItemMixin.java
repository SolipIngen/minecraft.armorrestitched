package solipingen.armorrestitched.mixin.item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.ItemStack;
import solipingen.armorrestitched.item.ModArmorMaterials;


@Mixin(DyeableItem.class)
public interface DyeableItemMixin {
    

    @ModifyConstant(method = "getColor", constant = @Constant(intValue = 10511680))
    private int modifiedDefaultColor(int originalInt, ItemStack stack) {
        if (stack.getItem() instanceof DyeableArmorItem) {
            DyeableArmorItem armorItem = (DyeableArmorItem)stack.getItem();
            ArmorMaterial material = armorItem.getMaterial();
            if (material == ModArmorMaterials.COTTON) {
                return 0xFDFFF6;
            }
            else if (material == ModArmorMaterials.FUR) {
                return 0xFFD1B3;
            }
            else if (material == ModArmorMaterials.LINEN) {
                return 0x9B8866;
            }
            else if (material == ModArmorMaterials.SILK) {
                return 0xFFF6D4;
            }
            else if (material == ModArmorMaterials.WOOL) {
                return 0xD5CDA8;
            }
            else if (material == ModArmorMaterials.PAPER) {
                return 0xFFFCF0;
            }
            else if (material == ArmorMaterials.LEATHER) {
                return originalInt;
            }
        }
        return originalInt;
    }


}
