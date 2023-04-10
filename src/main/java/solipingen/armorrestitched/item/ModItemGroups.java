package solipingen.armorrestitched.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import solipingen.armorrestitched.ArmorRestitched;
import solipingen.armorrestitched.block.ModBlocks;


public class ModItemGroups {


    public static void registerModItemsToVanillaGroups() {

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.COTTON_HELMET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.COTTON_CHESTPLATE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.COTTON_LEGGINGS));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.COTTON_BOOTS));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.FUR_HELMET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.FUR_CHESTPLATE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.FUR_LEGGINGS));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.FUR_BOOTS));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.LINEN_HELMET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.LINEN_CHESTPLATE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.LINEN_LEGGINGS));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.LINEN_BOOTS));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.SILK_HELMET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.SILK_CHESTPLATE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.SILK_LEGGINGS));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.SILK_BOOTS));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.WOOL_HELMET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.WOOL_CHESTPLATE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.WOOL_LEGGINGS));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.WOOL_BOOTS));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.PAPER_HELMET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.PAPER_CHESTPLATE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.PAPER_LEGGINGS));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.PAPER_BOOTS));
    
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.COPPER_HELMET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.COPPER_CHESTPLATE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.COPPER_LEGGINGS));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.COPPER_BOOTS));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ModItems.COPPER_HORSE_ARMOR));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.WHITE_COTTON));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.LIGHT_GRAY_COTTON));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.GRAY_COTTON));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.BLACK_COTTON));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.BROWN_COTTON));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.RED_COTTON));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.ORANGE_COTTON));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.YELLOW_COTTON));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.LIME_COTTON));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.GREEN_COTTON));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.CYAN_COTTON));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.LIGHT_BLUE_COTTON));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.BLUE_COTTON));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.PURPLE_COTTON));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.MAGENTA_COTTON));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.PINK_COTTON));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.WHITE_COTTON_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.LIGHT_GRAY_COTTON_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.GRAY_COTTON_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.BLACK_COTTON_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.BROWN_COTTON_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.RED_COTTON_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.ORANGE_COTTON_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.YELLOW_COTTON_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.LIME_COTTON_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.GREEN_COTTON_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.CYAN_COTTON_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.LIGHT_BLUE_COTTON_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.BLUE_COTTON_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.PURPLE_COTTON_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.MAGENTA_COTTON_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.PINK_COTTON_CARPET));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.WHITE_FUR));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.LIGHT_GRAY_FUR));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.GRAY_FUR));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.BLACK_FUR));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.BROWN_FUR));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.RED_FUR));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.ORANGE_FUR));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.YELLOW_FUR));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.LIME_FUR));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.GREEN_FUR));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.CYAN_FUR));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.LIGHT_BLUE_FUR));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.BLUE_FUR));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.PURPLE_FUR));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.MAGENTA_FUR));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.PINK_FUR));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.WHITE_FUR_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.LIGHT_GRAY_FUR_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.GRAY_FUR_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.BLACK_FUR_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.BROWN_FUR_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.RED_FUR_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.ORANGE_FUR_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.YELLOW_FUR_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.LIME_FUR_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.GREEN_FUR_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.CYAN_FUR_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.LIGHT_BLUE_FUR_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.BLUE_FUR_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.PURPLE_FUR_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.MAGENTA_FUR_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.PINK_FUR_CARPET));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.WHITE_LINEN));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.LIGHT_GRAY_LINEN));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.GRAY_LINEN));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.BLACK_LINEN));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.BROWN_LINEN));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.RED_LINEN));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.ORANGE_LINEN));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.YELLOW_LINEN));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.LIME_LINEN));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.GREEN_LINEN));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.CYAN_LINEN));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.LIGHT_BLUE_LINEN));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.BLUE_LINEN));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.PURPLE_LINEN));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.MAGENTA_LINEN));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.PINK_LINEN));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.WHITE_LINEN_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.LIGHT_GRAY_LINEN_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.GRAY_LINEN_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.BLACK_LINEN_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.BROWN_LINEN_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.RED_LINEN_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.ORANGE_LINEN_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.YELLOW_LINEN_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.LIME_LINEN_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.GREEN_LINEN_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.CYAN_LINEN_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.LIGHT_BLUE_LINEN_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.BLUE_LINEN_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.PURPLE_LINEN_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.MAGENTA_LINEN_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.PINK_LINEN_CARPET));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.WHITE_SILK));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.LIGHT_GRAY_SILK));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.GRAY_SILK));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.BLACK_SILK));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.BROWN_SILK));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.RED_SILK));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.ORANGE_SILK));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.YELLOW_SILK));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.LIME_SILK));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.GREEN_SILK));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.CYAN_SILK));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.LIGHT_BLUE_SILK));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.BLUE_SILK));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.PURPLE_SILK));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.MAGENTA_SILK));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.PINK_SILK));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.WHITE_SILK_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.LIGHT_GRAY_SILK_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.GRAY_SILK_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.BLACK_SILK_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.BROWN_SILK_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.RED_SILK_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.ORANGE_SILK_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.YELLOW_SILK_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.LIME_SILK_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.GREEN_SILK_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.CYAN_SILK_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.LIGHT_BLUE_SILK_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.BLUE_SILK_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.PURPLE_SILK_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.MAGENTA_SILK_CARPET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> entries.add(ModBlocks.PINK_SILK_CARPET));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(entries -> entries.add(ModBlocks.WHITE_COTTON));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(entries -> entries.add(ModBlocks.WHITE_FUR));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(entries -> entries.add(ModBlocks.WHITE_LINEN));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(entries -> entries.add(ModBlocks.WHITE_SILK));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> entries.add(ModBlocks.COTTON_FLOWER));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> entries.add(ModItems.COTTON_SEEDS));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(ModItems.COTTON));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> entries.add(ModBlocks.FLAX_BLOCK));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> entries.add(ModBlocks.FLAX_FLOWER));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> entries.add(ModItems.FLAXSEED));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> entries.add(ModBlocks.MULBERRY_LEAVES));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> entries.add(ModBlocks.MULBERRY_SILKWORM_LEAVES));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> entries.add(ModBlocks.MULBERRY_SAPLING));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> entries.add(ModItems.SILKWORM_COCOON));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> entries.add(ModItems.COOKED_SILKWORM_PUPA));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(ModItems.FLAXSEED));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(ModItems.FLAX_STEM));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(ModItems.LINEN));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(ModItems.SILK));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> entries.add(ModBlocks.SCUTCHER));


        ArmorRestitched.LOGGER.debug("Registering Mod Items to Vanilla Groups for " + ArmorRestitched.MOD_ID);

    }


}
