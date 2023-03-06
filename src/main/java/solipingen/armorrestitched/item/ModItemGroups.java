package solipingen.armorrestitched.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import solipingen.armorrestitched.ArmorRestitched;


public class ModItemGroups {


    public static void registerModItemsToVanillaGroups() {
    
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.COPPER_HELMET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.COPPER_CHESTPLATE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.COPPER_LEGGINGS));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.COPPER_BOOTS));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.COPPER_HORSE_ARMOR));

        ArmorRestitched.LOGGER.debug("Registering Mod Items to Vanilla Groups for " + ArmorRestitched.MOD_ID);

    }


}
