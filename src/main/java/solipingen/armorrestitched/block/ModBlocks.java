package solipingen.armorrestitched.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.ArmorRestitched;

@SuppressWarnings("unused")
public class ModBlocks {

    public static final Block WHITE_COTTON = ModBlocks.registerBlock("white_cotton", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.WHITE_WOOL)), true);
    public static final Block ORANGE_COTTON = ModBlocks.registerBlock("orange_cotton", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.ORANGE_WOOL)), true);
    public static final Block MAGENTA_COTTON = ModBlocks.registerBlock("magenta_cotton", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.MAGENTA_WOOL)), true);
    public static final Block LIGHT_BLUE_COTTON = ModBlocks.registerBlock("light_blue_cotton", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.LIGHT_BLUE_WOOL)), true);
    public static final Block YELLOW_COTTON = ModBlocks.registerBlock("yellow_cotton", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.YELLOW_WOOL)), true);
    public static final Block LIME_COTTON = ModBlocks.registerBlock("lime_cotton", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.LIME_WOOL)), true);
    public static final Block PINK_COTTON = ModBlocks.registerBlock("pink_cotton", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.PINK_WOOL)), true);
    public static final Block GRAY_COTTON = ModBlocks.registerBlock("gray_cotton", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.GRAY_WOOL)), true);
    public static final Block LIGHT_GRAY_COTTON = ModBlocks.registerBlock("light_gray_cotton", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.LIGHT_GRAY_WOOL)), true);
    public static final Block CYAN_COTTON = ModBlocks.registerBlock("cyan_cotton", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.CYAN_WOOL)), true);
    public static final Block PURPLE_COTTON = ModBlocks.registerBlock("purple_cotton", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.PURPLE_WOOL)), true);
    public static final Block BLUE_COTTON = ModBlocks.registerBlock("blue_cotton", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.BLUE_WOOL)), true);
    public static final Block BROWN_COTTON = ModBlocks.registerBlock("brown_cotton", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.BROWN_WOOL)), true);
    public static final Block GREEN_COTTON = ModBlocks.registerBlock("green_cotton", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.GREEN_WOOL)), true);
    public static final Block RED_COTTON = ModBlocks.registerBlock("red_cotton", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.RED_WOOL)), true);
    public static final Block BLACK_COTTON = ModBlocks.registerBlock("black_cotton", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.BLACK_WOOL)), true);

    public static final Block WHITE_FUR = ModBlocks.registerBlock("white_fur", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.WHITE_WOOL)), true);
    public static final Block ORANGE_FUR = ModBlocks.registerBlock("orange_fur", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.ORANGE_WOOL)), true);
    public static final Block MAGENTA_FUR = ModBlocks.registerBlock("magenta_fur", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.MAGENTA_WOOL)), true);
    public static final Block LIGHT_BLUE_FUR = ModBlocks.registerBlock("light_blue_fur", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.LIGHT_BLUE_WOOL)), true);
    public static final Block YELLOW_FUR = ModBlocks.registerBlock("yellow_fur", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.YELLOW_WOOL)), true);
    public static final Block LIME_FUR = ModBlocks.registerBlock("lime_fur", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.LIME_WOOL)), true);
    public static final Block PINK_FUR = ModBlocks.registerBlock("pink_fur", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.PINK_WOOL)), true);
    public static final Block GRAY_FUR = ModBlocks.registerBlock("gray_fur", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.GRAY_WOOL)), true);
    public static final Block LIGHT_GRAY_FUR = ModBlocks.registerBlock("light_gray_fur", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.LIGHT_GRAY_WOOL)), true);
    public static final Block CYAN_FUR = ModBlocks.registerBlock("cyan_fur", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.CYAN_WOOL)), true);
    public static final Block PURPLE_FUR = ModBlocks.registerBlock("purple_fur", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.PURPLE_WOOL)), true);
    public static final Block BLUE_FUR = ModBlocks.registerBlock("blue_fur", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.BLUE_WOOL)), true);
    public static final Block BROWN_FUR = ModBlocks.registerBlock("brown_fur", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.BROWN_WOOL)), true);
    public static final Block GREEN_FUR = ModBlocks.registerBlock("green_fur", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.GREEN_WOOL)), true);
    public static final Block RED_FUR = ModBlocks.registerBlock("red_fur", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.RED_WOOL)), true);
    public static final Block BLACK_FUR = ModBlocks.registerBlock("black_fur", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.BLACK_WOOL)), true);

    public static final Block WHITE_LINEN = ModBlocks.registerBlock("white_linen", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.WHITE_WOOL)), true);
    public static final Block ORANGE_LINEN = ModBlocks.registerBlock("orange_linen", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.ORANGE_WOOL)), true);
    public static final Block MAGENTA_LINEN = ModBlocks.registerBlock("magenta_linen", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.MAGENTA_WOOL)), true);
    public static final Block LIGHT_BLUE_LINEN = ModBlocks.registerBlock("light_blue_linen", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.LIGHT_BLUE_WOOL)), true);
    public static final Block YELLOW_LINEN = ModBlocks.registerBlock("yellow_linen", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.YELLOW_WOOL)), true);
    public static final Block LIME_LINEN = ModBlocks.registerBlock("lime_linen", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.LIME_WOOL)), true);
    public static final Block PINK_LINEN = ModBlocks.registerBlock("pink_linen", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.PINK_WOOL)), true);
    public static final Block GRAY_LINEN = ModBlocks.registerBlock("gray_linen", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.GRAY_WOOL)), true);
    public static final Block LIGHT_GRAY_LINEN = ModBlocks.registerBlock("light_gray_linen", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.LIGHT_GRAY_WOOL)), true);
    public static final Block CYAN_LINEN = ModBlocks.registerBlock("cyan_linen", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.CYAN_WOOL)), true);
    public static final Block PURPLE_LINEN = ModBlocks.registerBlock("purple_linen", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.PURPLE_WOOL)), true);
    public static final Block BLUE_LINEN = ModBlocks.registerBlock("blue_linen", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.BLUE_WOOL)), true);
    public static final Block BROWN_LINEN = ModBlocks.registerBlock("brown_linen", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.BROWN_WOOL)), true);
    public static final Block GREEN_LINEN = ModBlocks.registerBlock("green_linen", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.GREEN_WOOL)), true);
    public static final Block RED_LINEN = ModBlocks.registerBlock("red_linen", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.RED_WOOL)), true);
    public static final Block BLACK_LINEN = ModBlocks.registerBlock("black_linen", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.BLACK_WOOL)), true);

    public static final Block WHITE_SILK = ModBlocks.registerBlock("white_silk", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.WHITE_WOOL)), true);
    public static final Block ORANGE_SILK = ModBlocks.registerBlock("orange_silk", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.ORANGE_WOOL)), true);
    public static final Block MAGENTA_SILK = ModBlocks.registerBlock("magenta_silk", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.MAGENTA_WOOL)), true);
    public static final Block LIGHT_BLUE_SILK = ModBlocks.registerBlock("light_blue_silk", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.LIGHT_BLUE_WOOL)), true);
    public static final Block YELLOW_SILK = ModBlocks.registerBlock("yellow_silk", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.YELLOW_WOOL)), true);
    public static final Block LIME_SILK = ModBlocks.registerBlock("lime_silk", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.LIME_WOOL)), true);
    public static final Block PINK_SILK = ModBlocks.registerBlock("pink_silk", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.PINK_WOOL)), true);
    public static final Block GRAY_SILK = ModBlocks.registerBlock("gray_silk", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.GRAY_WOOL)), true);
    public static final Block LIGHT_GRAY_SILK = ModBlocks.registerBlock("light_gray_silk", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.LIGHT_GRAY_WOOL)), true);
    public static final Block CYAN_SILK = ModBlocks.registerBlock("cyan_silk", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.CYAN_WOOL)), true);
    public static final Block PURPLE_SILK = ModBlocks.registerBlock("purple_silk", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.PURPLE_WOOL)), true);
    public static final Block BLUE_SILK = ModBlocks.registerBlock("blue_silk", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.BLUE_WOOL)), true);
    public static final Block BROWN_SILK = ModBlocks.registerBlock("brown_silk", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.BROWN_WOOL)), true);
    public static final Block GREEN_SILK = ModBlocks.registerBlock("green_silk", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.GREEN_WOOL)), true);
    public static final Block RED_SILK = ModBlocks.registerBlock("red_silk", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.RED_WOOL)), true);
    public static final Block BLACK_SILK = ModBlocks.registerBlock("black_silk", (Block)new WoollikeBlock(AbstractBlock.Settings.copy(Blocks.BLACK_WOOL)), true);


    private static Block registerBlock(String name, Block block, boolean withBlockItem) {
        if (withBlockItem) {
            ModBlocks.registerBlockItem(name, block);
        }
        return Registry.register(Registries.BLOCK, new Identifier(ArmorRestitched.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(ArmorRestitched.MOD_ID, name), new BlockItem(block, new FabricItemSettings()));
    }
    
    public static void registerModBlocks() {
        ArmorRestitched.LOGGER.debug("Registering ModBlocks for " + ArmorRestitched.MOD_ID);
    }

}
