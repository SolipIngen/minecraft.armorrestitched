package solipingen.armorrestitched.mixin.item;

import net.minecraft.block.Block;
import net.minecraft.block.DyedCarpetBlock;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.util.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import solipingen.armorrestitched.item.equipment.ModArmorMaterials;
import solipingen.armorrestitched.registry.tag.ModEntityTypeTags;


@Mixin(BlockItem.class)
public abstract class BlockItemMixin extends Item {


    public BlockItemMixin(Settings settings) {
        super(settings);
    }

    @ModifyVariable(method = "<init>", at = @At("HEAD"), index = 2)
    private static Item.Settings modifiedSettings(Item.Settings settings, Block block, Item.Settings settings2) {
        if (block instanceof DyedCarpetBlock dyedCarpetBlock) {
            return BlockItemMixin.carpetArmor(settings, dyedCarpetBlock.getDyeColor());
        }
        else {
            return settings;
        }
    }

    @Unique
    private static Item.Settings carpetArmor(Item.Settings settings, DyeColor dyeColor) {
        return settings.attributeModifiers(ModArmorMaterials.WOOL.createAttributeModifiers(EquipmentType.BODY))
                .component(DataComponentTypes.EQUIPPABLE, EquippableComponent.ofCarpet(dyeColor));
    }


}
