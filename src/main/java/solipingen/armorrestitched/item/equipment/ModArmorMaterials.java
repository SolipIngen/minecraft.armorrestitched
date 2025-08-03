package solipingen.armorrestitched.item.equipment;

import java.util.Map;

import com.google.common.collect.Maps;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentType;
import solipingen.armorrestitched.registry.tag.ModItemTags;
import solipingen.armorrestitched.sound.ModSoundEvents;


public class ModArmorMaterials {

    public static final ArmorMaterial COTTON = new ArmorMaterial(9, ModArmorMaterials.createDefenseMap(1, 3, 3, 1, 3), 15, ModSoundEvents.COTTON_CLOTHING_EQUIP, 0.0f, 0.0f, ModItemTags.REPAIRS_COTTON_CLOTHING, ModEquipmentAssetKeys.COTTON);
    public static final ArmorMaterial FUR = new ArmorMaterial(9, ModArmorMaterials.createDefenseMap(1, 3, 3, 1, 3), 15, ModSoundEvents.FUR_CLOTHING_EQUIP, 0.0f, 0.0f, ModItemTags.REPAIRS_FUR_CLOTHING, ModEquipmentAssetKeys.FUR);
    public static final ArmorMaterial LINEN = new ArmorMaterial(9, ModArmorMaterials.createDefenseMap(1, 3, 3, 1, 3), 15, ModSoundEvents.LINEN_CLOTHING_EQUIP, 0.0f, 0.0f, ModItemTags.REPAIRS_LINEN_CLOTHING, ModEquipmentAssetKeys.LINEN);
    public static final ArmorMaterial SILK = new ArmorMaterial(9, ModArmorMaterials.createDefenseMap(1, 3, 3, 1, 3), 15, ModSoundEvents.SILK_CLOTHING_EQUIP, 0.0f, 0.0f, ModItemTags.REPAIRS_SILK_CLOTHING, ModEquipmentAssetKeys.SILK);
    public static final ArmorMaterial WOOL = new ArmorMaterial(9, ModArmorMaterials.createDefenseMap(1, 3, 3, 1, 3), 15, ModSoundEvents.WOOL_CLOTHING_EQUIP, 0.0f, 0.0f, ModItemTags.REPAIRS_WOOL_CLOTHING, ModEquipmentAssetKeys.WOOL);
    public static final ArmorMaterial PAPER = new ArmorMaterial(5, ModArmorMaterials.createDefenseMap(1, 3, 3, 1, 3), 15, ModSoundEvents.PAPER_CLOTHING_EQUIP, 0.0f, 0.0f, ModItemTags.REPAIRS_PAPER_CLOTHING, ModEquipmentAssetKeys.PAPER);
    public static final ArmorMaterial COPPER = new ArmorMaterial(15, ModArmorMaterials.createDefenseMap(2, 4, 4, 2, 7), 12, ModSoundEvents.COPPER_ARMOR_EQUIP, 1.0f, 0.0f, ModItemTags.REPAIRS_COPPER_ARMOR, ModEquipmentAssetKeys.COPPER);


    public static Map<EquipmentType, Integer> createDefenseMap(int bootsDefense, int leggingsDefense, int chestplateDefense, int helmetDefense, int bodyDefense) {
        return Maps.newEnumMap(Map.of(EquipmentType.BOOTS, bootsDefense, EquipmentType.LEGGINGS, leggingsDefense, EquipmentType.CHESTPLATE, chestplateDefense, EquipmentType.HELMET, helmetDefense, EquipmentType.BODY, bodyDefense));
    }


}
