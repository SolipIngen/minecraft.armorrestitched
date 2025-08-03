package solipingen.armorrestitched;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import solipingen.armorrestitched.block.ModBlocks;
import solipingen.armorrestitched.enchantment.ModEnchantments;
import solipingen.armorrestitched.entity.ModEntityTypes;
import solipingen.armorrestitched.entity.attribute.ModEntityAttributeRegistry;
import solipingen.armorrestitched.item.ModItemGroups;
import solipingen.armorrestitched.item.ModItems;
import solipingen.armorrestitched.item.ModifyItemComponentHandler;
import solipingen.armorrestitched.loot.ModifyLootTableHandler;
import solipingen.armorrestitched.loot.ReplaceLootTableHandler;
import solipingen.armorrestitched.particle.ModParticleTypes;
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
		ModItems.registerModItems();
		ModItemGroups.registerModItemsToVanillaGroups();
		ModParticleTypes.registerModParticleTypes();
		ModRecipes.registerModRecipes();
		ModSoundEvents.registerModSoundEvents();
		ModVillagerProfessions.registerModVillagerProfessions();
		ModWorldGenerator.generateModWorldGen();

		DefaultItemComponentEvents.MODIFY.register(new ModifyItemComponentHandler());

		LootTableEvents.REPLACE.register(new ReplaceLootTableHandler());
		LootTableEvents.MODIFY.register(new ModifyLootTableHandler());

	}
	

}
