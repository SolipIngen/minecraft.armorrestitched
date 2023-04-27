package solipingen.armorrestitched;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import solipingen.armorrestitched.block.ModBlocks;
import solipingen.armorrestitched.block.ModFlammableBlockRegistry;
import solipingen.armorrestitched.enchantment.ModEnchantments;
import solipingen.armorrestitched.entity.ModEntityTypes;
import solipingen.armorrestitched.entity.attribute.ModEntityAttributeRegistry;
import solipingen.armorrestitched.item.ModItemGroups;
import solipingen.armorrestitched.item.ModItems;
import solipingen.armorrestitched.loot.ModifyLootTableHandler;
import solipingen.armorrestitched.loot.ReplaceLootTableHandler;
import solipingen.armorrestitched.recipe.ModRecipes;
import solipingen.armorrestitched.sound.ModSoundEvents;
import solipingen.armorrestitched.village.ModVillagerProfessions;
import solipingen.armorrestitched.world.gen.ModWorldGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArmorRestitched implements ModInitializer {

	public static final String MOD_ID = "armorrestitched";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


	@Override
	public void onInitialize() {

		ModBlocks.registerModBlocks();
		ModEnchantments.registerModEnchantments();
		ModEntityAttributeRegistry.registerModEntityAttributes();
		ModEntityTypes.registerModEntityTypes();
		ModFlammableBlockRegistry.registerFlammableBlocks();
		ModItems.registerModItems();
		ModItemGroups.registerModItemsToVanillaGroups();
		ModRecipes.registerModRecipes();
		ModSoundEvents.registerModSoundEvents();
		ModVillagerProfessions.registerModVillagerProfessions();
		ModWorldGenerator.generateModWorldGen();

		LootTableEvents.REPLACE.register(new ReplaceLootTableHandler());
		LootTableEvents.MODIFY.register(new ModifyLootTableHandler());

	}
	

}
