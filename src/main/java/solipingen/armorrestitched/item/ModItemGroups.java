package solipingen.armorrestitched.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import solipingen.armorrestitched.ArmorRestitched;


public class ModItemGroups {


    public static void registerModItemsToVanillaGroups() {

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.COTTON_HELMET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.COTTON_CHESTPLATE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.COTTON_LEGGINGS));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.COTTON_BOOTS));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.COTTON_HORSE_ARMOR));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.FUR_HELMET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.FUR_CHESTPLATE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.FUR_LEGGINGS));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.FUR_BOOTS));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.FUR_HORSE_ARMOR));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.LINEN_HELMET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.LINEN_CHESTPLATE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.LINEN_LEGGINGS));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.LINEN_BOOTS));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.LINEN_HORSE_ARMOR));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.SILK_HELMET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.SILK_CHESTPLATE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.SILK_LEGGINGS));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.SILK_BOOTS));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.SILK_HORSE_ARMOR));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.WOOL_HELMET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.WOOL_CHESTPLATE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.WOOL_LEGGINGS));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.WOOL_BOOTS));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.WOOL_HORSE_ARMOR));
    
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.COPPER_HELMET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.COPPER_CHESTPLATE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.COPPER_LEGGINGS));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.COPPER_BOOTS));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.COPPER_HORSE_ARMOR));

        ArmorRestitched.LOGGER.debug("Registering Mod Items to Vanilla Groups for " + ArmorRestitched.MOD_ID);

    }


}
