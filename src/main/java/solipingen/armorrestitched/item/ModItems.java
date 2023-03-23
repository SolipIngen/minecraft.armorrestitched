package solipingen.armorrestitched.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.DyeableHorseArmorItem;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.SaddleItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.ArmorRestitched;
import solipingen.armorrestitched.item.armor.ModArmorMaterials;


public class ModItems {

    // Cotton Clothing
    public static final Item COTTON_HELMET = ModItems.registerItem("cotton_helmet", 
        (Item)new DyeableArmorItem(ModArmorMaterials.COTTON, ArmorItem.Type.HELMET, new FabricItemSettings()));

    public static final Item COTTON_CHESTPLATE = ModItems.registerItem("cotton_chestplate", 
        (Item)new DyeableArmorItem(ModArmorMaterials.COTTON, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));

    public static final Item COTTON_LEGGINGS = ModItems.registerItem("cotton_leggings", 
        (Item)new DyeableArmorItem(ModArmorMaterials.COTTON, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));

    public static final Item COTTON_BOOTS = ModItems.registerItem("cotton_boots", 
        (Item)new DyeableArmorItem(ModArmorMaterials.COTTON, ArmorItem.Type.BOOTS, new FabricItemSettings()));


    // Fur Clothing
    public static final Item FUR_HELMET = ModItems.registerItem("fur_helmet", 
        (Item)new DyeableArmorItem(ModArmorMaterials.FUR, ArmorItem.Type.HELMET, new FabricItemSettings()));

    public static final Item FUR_CHESTPLATE = ModItems.registerItem("fur_chestplate", 
        (Item)new DyeableArmorItem(ModArmorMaterials.FUR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));

    public static final Item FUR_LEGGINGS = ModItems.registerItem("fur_leggings", 
        (Item)new DyeableArmorItem(ModArmorMaterials.FUR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));

    public static final Item FUR_BOOTS = ModItems.registerItem("fur_boots", 
        (Item)new DyeableArmorItem(ModArmorMaterials.FUR, ArmorItem.Type.BOOTS, new FabricItemSettings()));


    // Linen Clothing
    public static final Item LINEN_HELMET = ModItems.registerItem("linen_helmet", 
        (Item)new DyeableArmorItem(ModArmorMaterials.LINEN, ArmorItem.Type.HELMET, new FabricItemSettings()));

    public static final Item LINEN_CHESTPLATE = ModItems.registerItem("linen_chestplate", 
        (Item)new DyeableArmorItem(ModArmorMaterials.LINEN, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));

    public static final Item LINEN_LEGGINGS = ModItems.registerItem("linen_leggings", 
        (Item)new DyeableArmorItem(ModArmorMaterials.LINEN, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));

    public static final Item LINEN_BOOTS = ModItems.registerItem("linen_boots", 
        (Item)new DyeableArmorItem(ModArmorMaterials.LINEN, ArmorItem.Type.BOOTS, new FabricItemSettings()));


    // Silk Clothing
    public static final Item SILK_HELMET = ModItems.registerItem("silk_helmet", 
        (Item)new DyeableArmorItem(ModArmorMaterials.SILK, ArmorItem.Type.HELMET, new FabricItemSettings()));

    public static final Item SILK_CHESTPLATE = ModItems.registerItem("silk_chestplate", 
        (Item)new DyeableArmorItem(ModArmorMaterials.SILK, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));

    public static final Item SILK_LEGGINGS = ModItems.registerItem("silk_leggings", 
        (Item)new DyeableArmorItem(ModArmorMaterials.SILK, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));

    public static final Item SILK_BOOTS = ModItems.registerItem("silk_boots", 
        (Item)new DyeableArmorItem(ModArmorMaterials.SILK, ArmorItem.Type.BOOTS, new FabricItemSettings()));


    // Wool Clothing
    public static final Item WOOL_HELMET = ModItems.registerItem("wool_helmet", 
        (Item)new DyeableArmorItem(ModArmorMaterials.WOOL, ArmorItem.Type.HELMET, new FabricItemSettings()));

    public static final Item WOOL_CHESTPLATE = ModItems.registerItem("wool_chestplate", 
        (Item)new DyeableArmorItem(ModArmorMaterials.WOOL, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));

    public static final Item WOOL_LEGGINGS = ModItems.registerItem("wool_leggings", 
        (Item)new DyeableArmorItem(ModArmorMaterials.WOOL, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));

    public static final Item WOOL_BOOTS = ModItems.registerItem("wool_boots", 
        (Item)new DyeableArmorItem(ModArmorMaterials.WOOL, ArmorItem.Type.BOOTS, new FabricItemSettings()));


    // Copper Armor
    public static final Item COPPER_HELMET = ModItems.registerItem("copper_helmet", 
        (Item)new ArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.HELMET, new FabricItemSettings()));

    public static final Item COPPER_CHESTPLATE = ModItems.registerItem("copper_chestplate", 
        (Item)new ArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));

    public static final Item COPPER_LEGGINGS = ModItems.registerItem("copper_leggings", 
        (Item)new ArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));

    public static final Item COPPER_BOOTS = ModItems.registerItem("copper_boots", 
        (Item)new ArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item COPPER_HORSE_ARMOR = ModItems.registerItem("copper_horse_armor", 
        (Item)new HorseArmorItem(7, "copper", new FabricItemSettings()));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(ArmorRestitched.MOD_ID, name), item);
    }

    // Replace Vanilla Items with Mod Versions
    public static void replaceVanillaItems() {
        for (Item item : Registries.ITEM) {
            String name = item.getTranslationKey().substring(item.getTranslationKey().lastIndexOf(".") + 1);
            int rawId = Registries.ITEM.getRawId(item);
            if (item instanceof HorseArmorItem) {
                if (name.matches("leather_horse_armor")) {
                    Item newHorseArmorItem = (Item)new DyeableHorseArmorItem(3, "leather", new Item.Settings());
                    Registry.register(Registries.ITEM, rawId, name, newHorseArmorItem);
                }
                else if (name.matches("iron_horse_armor")) {
                    Item newHorseArmorItem = (Item)new HorseArmorItem(9, "iron", new Item.Settings());
                    Registry.register(Registries.ITEM, rawId, name, newHorseArmorItem);
                }
                else if (name.matches("golden_horse_armor")) {
                    Item newHorseArmorItem = (Item)new HorseArmorItem(7, "gold", new Item.Settings());
                    Registry.register(Registries.ITEM, rawId, name, newHorseArmorItem);
                }
                else if (name.matches("diamond_horse_armor")) {
                    Item newHorseArmorItem = (Item)new HorseArmorItem(12, "diamond", new Item.Settings());
                    Registry.register(Registries.ITEM, rawId, name, newHorseArmorItem);
                }
            }
            else if (item instanceof SaddleItem) {
                if (name.matches("saddle")) {
                    Item newSaddleItem = (Item)new SaddleItem(new Item.Settings());
                    Registry.register(Registries.ITEM, rawId, name, newSaddleItem);
                }
            }
        }
    }

    public static void registerModItems() {
        ArmorRestitched.LOGGER.debug("Registering Mod Items for " + ArmorRestitched.MOD_ID);
    }
    
    
}
