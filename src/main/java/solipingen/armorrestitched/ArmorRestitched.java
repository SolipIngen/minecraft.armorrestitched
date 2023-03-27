package solipingen.armorrestitched;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import solipingen.armorrestitched.block.ModBlocks;
import solipingen.armorrestitched.item.ModItemGroups;
import solipingen.armorrestitched.item.ModItems;
import solipingen.armorrestitched.loot.ModifyLootTableHandler;
import solipingen.armorrestitched.loot.ReplaceLootTableHandler;
import solipingen.armorrestitched.sound.ModSoundEvents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArmorRestitched implements ModInitializer {

	public static final String MOD_ID = "armorrestitched";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


	@Override
	public void onInitialize() {

		ModBlocks.registerModBlocks();
		ModItems.registerModItems();
		ModItemGroups.registerModItemsToVanillaGroups();
		ModSoundEvents.registerModSoundEvents();

		LootTableEvents.REPLACE.register(new ReplaceLootTableHandler());
		LootTableEvents.MODIFY.register(new ModifyLootTableHandler());

	}
	

}
