package solipingen.armorrestitched.client.color;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Colors;
import org.jetbrains.annotations.Nullable;
import solipingen.armorrestitched.ArmorRestitched;
import solipingen.armorrestitched.item.ModArmorMaterials;
import solipingen.armorrestitched.item.ModItems;


@Environment(EnvType.CLIENT)
public class ModItemColorProvider {


    public static void registerModItemColors() {
        // KNOWN ISSUE: Undetermined issue with rendering default item colours. Colours seems to get flipped into a shade of complementary colour.
        // As stopgap, put in instead desired hex colour -> its complementary colour -> -1 x nth-darkest shade colour on colorhexa.com.
        // Check again with Fabric API / Loader update.
        ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
                tintIndex > 0 ? -1 : DyedColorComponent.getColor(stack, -0x02000B),
                ModItems.COTTON_HELMET, ModItems.COTTON_CHESTPLATE, ModItems.COTTON_LEGGINGS, ModItems.COTTON_BOOTS);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
                tintIndex > 0 ? -1 : DyedColorComponent.getColor(stack, -0x00253D),
                ModItems.FUR_HELMET, ModItems.FUR_CHESTPLATE, ModItems.FUR_LEGGINGS, ModItems.FUR_BOOTS);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
                tintIndex > 0 ? -1 : DyedColorComponent.getColor(stack, -0x47546C),
                ModItems.LINEN_HELMET, ModItems.LINEN_CHESTPLATE, ModItems.LINEN_LEGGINGS, ModItems.LINEN_BOOTS);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
                tintIndex > 0 ? -1 : DyedColorComponent.getColor(stack, -0x000723),
                ModItems.SILK_HELMET, ModItems.SILK_CHESTPLATE, ModItems.SILK_LEGGINGS, ModItems.SILK_BOOTS);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
                tintIndex > 0 ? -1 : DyedColorComponent.getColor(stack, -0x232948),
                ModItems.WOOL_HELMET, ModItems.WOOL_CHESTPLATE, ModItems.WOOL_LEGGINGS, ModItems.WOOL_BOOTS);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
                tintIndex > 0 ? -1 : DyedColorComponent.getColor(stack, -0x000105),
                ModItems.PAPER_HELMET, ModItems.PAPER_CHESTPLATE, ModItems.PAPER_LEGGINGS, ModItems.PAPER_BOOTS);

        ArmorRestitched.LOGGER.debug("Registering colors for mod items.");
    }

    public static int getDyeableArmorDefaultColor(ItemStack stack) {
        if (stack.isIn(ItemTags.DYEABLE) && stack.getItem() instanceof ArmorItem) {
            ArmorMaterial material = ((ArmorItem)stack.getItem()).getMaterial().value();
            if (material == ModArmorMaterials.COTTON.value()) {
                return 0xFDFFF6;
            }
            else if (material == ModArmorMaterials.FUR.value()) {
                return 0xFFD1B3;
            }
            else if (material == ModArmorMaterials.LINEN.value()) {
                return 0x9B8866;
            }
            else if (material == ModArmorMaterials.SILK.value()) {
                return 0xFFF6D4;
            }
            else if (material == ModArmorMaterials.WOOL.value()) {
                return 0xD5CDA8;
            }
            else if (material == ModArmorMaterials.PAPER.value()) {
                return 0xFFFCF0;
            }
            else if (material == ArmorMaterials.LEATHER.value()) {
                return DyedColorComponent.DEFAULT_COLOR;
            }
        }
        return Colors.WHITE;
    }


}
