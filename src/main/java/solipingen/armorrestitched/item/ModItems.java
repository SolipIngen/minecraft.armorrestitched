package solipingen.armorrestitched.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.ArmorRestitched;
import solipingen.armorrestitched.item.armor.ModArmorMaterials;


public class ModItems {

    // Copper Armor
    public static final Item COPPER_HELMET = registerItem("copper_helmet", 
        (Item)new ArmorItem(ModArmorMaterials.COPPER, EquipmentSlot.HEAD, new FabricItemSettings()));

    public static final Item COPPER_CHESTPLATE = registerItem("copper_chestplate", 
        (Item)new ArmorItem(ModArmorMaterials.COPPER, EquipmentSlot.CHEST, new FabricItemSettings()));

    public static final Item COPPER_LEGGINGS = registerItem("copper_leggings", 
        (Item)new ArmorItem(ModArmorMaterials.COPPER, EquipmentSlot.LEGS, new FabricItemSettings()));

    public static final Item COPPER_BOOTS = registerItem("copper_boots", 
        (Item)new ArmorItem(ModArmorMaterials.COPPER, EquipmentSlot.FEET, new FabricItemSettings()));

    public static final Item COPPER_HORSE_ARMOR = registerItem("copper_horse_armor", 
        (Item)new HorseArmorItem(7, "copper", new FabricItemSettings()));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(ArmorRestitched.MOD_ID, name), item);
    }

    public static void registerModItems() {
        ArmorRestitched.LOGGER.debug("Registering Mod Items for " + ArmorRestitched.MOD_ID);
    }
    
    
}