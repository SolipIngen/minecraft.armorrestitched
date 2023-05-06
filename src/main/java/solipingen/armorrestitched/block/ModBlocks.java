package solipingen.armorrestitched.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.DyedCarpetBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.HayBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.Material;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.TallFlowerBlock;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.ArmorRestitched;
import solipingen.armorrestitched.registry.tag.ModBlockTags;
import solipingen.armorrestitched.world.tree.MulberrySaplingGenerator;


public class ModBlocks {

    // Fabric Blocks
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

    public static final Block WHITE_COTTON_CARPET = ModBlocks.registerBlock("white_cotton_carpet", new DyedCarpetBlock(DyeColor.WHITE, AbstractBlock.Settings.copy(Blocks.WHITE_CARPET)), true);
    public static final Block ORANGE_COTTON_CARPET = ModBlocks.registerBlock("orange_cotton_carpet", new DyedCarpetBlock(DyeColor.ORANGE, AbstractBlock.Settings.copy(Blocks.ORANGE_CARPET)), true);
    public static final Block MAGENTA_COTTON_CARPET = ModBlocks.registerBlock("magenta_cotton_carpet", new DyedCarpetBlock(DyeColor.MAGENTA, AbstractBlock.Settings.copy(Blocks.MAGENTA_CARPET)), true);
    public static final Block LIGHT_BLUE_COTTON_CARPET = ModBlocks.registerBlock("light_blue_cotton_carpet", new DyedCarpetBlock(DyeColor.LIGHT_BLUE, AbstractBlock.Settings.copy(Blocks.LIGHT_BLUE_CARPET)), true);
    public static final Block YELLOW_COTTON_CARPET = ModBlocks.registerBlock("yellow_cotton_carpet", new DyedCarpetBlock(DyeColor.YELLOW, AbstractBlock.Settings.copy(Blocks.YELLOW_CARPET)), true);
    public static final Block LIME_COTTON_CARPET = ModBlocks.registerBlock("lime_cotton_carpet", new DyedCarpetBlock(DyeColor.LIME, AbstractBlock.Settings.copy(Blocks.LIME_CARPET)), true);
    public static final Block PINK_COTTON_CARPET = ModBlocks.registerBlock("pink_cotton_carpet", new DyedCarpetBlock(DyeColor.PINK, AbstractBlock.Settings.copy(Blocks.PINK_CARPET)), true);
    public static final Block GRAY_COTTON_CARPET = ModBlocks.registerBlock("gray_cotton_carpet", new DyedCarpetBlock(DyeColor.GRAY, AbstractBlock.Settings.copy(Blocks.GRAY_CARPET)), true);
    public static final Block LIGHT_GRAY_COTTON_CARPET = ModBlocks.registerBlock("light_gray_cotton_carpet", new DyedCarpetBlock(DyeColor.LIGHT_GRAY, AbstractBlock.Settings.copy(Blocks.LIGHT_GRAY_CARPET)), true);
    public static final Block CYAN_COTTON_CARPET = ModBlocks.registerBlock("cyan_cotton_carpet", new DyedCarpetBlock(DyeColor.CYAN, AbstractBlock.Settings.copy(Blocks.CYAN_CARPET)), true);
    public static final Block PURPLE_COTTON_CARPET = ModBlocks.registerBlock("purple_cotton_carpet", new DyedCarpetBlock(DyeColor.PURPLE, AbstractBlock.Settings.copy(Blocks.PURPLE_CARPET)), true);
    public static final Block BLUE_COTTON_CARPET = ModBlocks.registerBlock("blue_cotton_carpet", new DyedCarpetBlock(DyeColor.BLUE, AbstractBlock.Settings.copy(Blocks.BLUE_CARPET)), true);
    public static final Block BROWN_COTTON_CARPET = ModBlocks.registerBlock("brown_cotton_carpet", new DyedCarpetBlock(DyeColor.BROWN, AbstractBlock.Settings.copy(Blocks.BROWN_CARPET)), true);
    public static final Block GREEN_COTTON_CARPET = ModBlocks.registerBlock("green_cotton_carpet", new DyedCarpetBlock(DyeColor.GREEN, AbstractBlock.Settings.copy(Blocks.GREEN_CARPET)), true);
    public static final Block RED_COTTON_CARPET = ModBlocks.registerBlock("red_cotton_carpet", new DyedCarpetBlock(DyeColor.RED, AbstractBlock.Settings.copy(Blocks.RED_CARPET)), true);
    public static final Block BLACK_COTTON_CARPET = ModBlocks.registerBlock("black_cotton_carpet", new DyedCarpetBlock(DyeColor.BLACK, AbstractBlock.Settings.copy(Blocks.BLACK_CARPET)), true);

    public static final Block WHITE_FUR_CARPET = ModBlocks.registerBlock("white_fur_carpet", new DyedCarpetBlock(DyeColor.WHITE, AbstractBlock.Settings.copy(Blocks.WHITE_CARPET)), true);
    public static final Block ORANGE_FUR_CARPET = ModBlocks.registerBlock("orange_fur_carpet", new DyedCarpetBlock(DyeColor.ORANGE, AbstractBlock.Settings.copy(Blocks.ORANGE_CARPET)), true);
    public static final Block MAGENTA_FUR_CARPET = ModBlocks.registerBlock("magenta_fur_carpet", new DyedCarpetBlock(DyeColor.MAGENTA, AbstractBlock.Settings.copy(Blocks.MAGENTA_CARPET)), true);
    public static final Block LIGHT_BLUE_FUR_CARPET = ModBlocks.registerBlock("light_blue_fur_carpet", new DyedCarpetBlock(DyeColor.LIGHT_BLUE, AbstractBlock.Settings.copy(Blocks.LIGHT_BLUE_CARPET)), true);
    public static final Block YELLOW_FUR_CARPET = ModBlocks.registerBlock("yellow_fur_carpet", new DyedCarpetBlock(DyeColor.YELLOW, AbstractBlock.Settings.copy(Blocks.YELLOW_CARPET)), true);
    public static final Block LIME_FUR_CARPET = ModBlocks.registerBlock("lime_fur_carpet", new DyedCarpetBlock(DyeColor.LIME, AbstractBlock.Settings.copy(Blocks.LIME_CARPET)), true);
    public static final Block PINK_FUR_CARPET = ModBlocks.registerBlock("pink_fur_carpet", new DyedCarpetBlock(DyeColor.PINK, AbstractBlock.Settings.copy(Blocks.PINK_CARPET)), true);
    public static final Block GRAY_FUR_CARPET = ModBlocks.registerBlock("gray_fur_carpet", new DyedCarpetBlock(DyeColor.GRAY, AbstractBlock.Settings.copy(Blocks.GRAY_CARPET)), true);
    public static final Block LIGHT_GRAY_FUR_CARPET = ModBlocks.registerBlock("light_gray_fur_carpet", new DyedCarpetBlock(DyeColor.LIGHT_GRAY, AbstractBlock.Settings.copy(Blocks.LIGHT_GRAY_CARPET)), true);
    public static final Block CYAN_FUR_CARPET = ModBlocks.registerBlock("cyan_fur_carpet", new DyedCarpetBlock(DyeColor.CYAN, AbstractBlock.Settings.copy(Blocks.CYAN_CARPET)), true);
    public static final Block PURPLE_FUR_CARPET = ModBlocks.registerBlock("purple_fur_carpet", new DyedCarpetBlock(DyeColor.PURPLE, AbstractBlock.Settings.copy(Blocks.PURPLE_CARPET)), true);
    public static final Block BLUE_FUR_CARPET = ModBlocks.registerBlock("blue_fur_carpet", new DyedCarpetBlock(DyeColor.BLUE, AbstractBlock.Settings.copy(Blocks.BLUE_CARPET)), true);
    public static final Block BROWN_FUR_CARPET = ModBlocks.registerBlock("brown_fur_carpet", new DyedCarpetBlock(DyeColor.BROWN, AbstractBlock.Settings.copy(Blocks.BROWN_CARPET)), true);
    public static final Block GREEN_FUR_CARPET = ModBlocks.registerBlock("green_fur_carpet", new DyedCarpetBlock(DyeColor.GREEN, AbstractBlock.Settings.copy(Blocks.GREEN_CARPET)), true);
    public static final Block RED_FUR_CARPET = ModBlocks.registerBlock("red_fur_carpet", new DyedCarpetBlock(DyeColor.RED, AbstractBlock.Settings.copy(Blocks.RED_CARPET)), true);
    public static final Block BLACK_FUR_CARPET = ModBlocks.registerBlock("black_fur_carpet", new DyedCarpetBlock(DyeColor.BLACK, AbstractBlock.Settings.copy(Blocks.BLACK_CARPET)), true);

    public static final Block WHITE_LINEN_CARPET = ModBlocks.registerBlock("white_linen_carpet", new DyedCarpetBlock(DyeColor.WHITE, AbstractBlock.Settings.copy(Blocks.WHITE_CARPET)), true);
    public static final Block ORANGE_LINEN_CARPET = ModBlocks.registerBlock("orange_linen_carpet", new DyedCarpetBlock(DyeColor.ORANGE, AbstractBlock.Settings.copy(Blocks.ORANGE_CARPET)), true);
    public static final Block MAGENTA_LINEN_CARPET = ModBlocks.registerBlock("magenta_linen_carpet", new DyedCarpetBlock(DyeColor.MAGENTA, AbstractBlock.Settings.copy(Blocks.MAGENTA_CARPET)), true);
    public static final Block LIGHT_BLUE_LINEN_CARPET = ModBlocks.registerBlock("light_blue_linen_carpet", new DyedCarpetBlock(DyeColor.LIGHT_BLUE, AbstractBlock.Settings.copy(Blocks.LIGHT_BLUE_CARPET)), true);
    public static final Block YELLOW_LINEN_CARPET = ModBlocks.registerBlock("yellow_linen_carpet", new DyedCarpetBlock(DyeColor.YELLOW, AbstractBlock.Settings.copy(Blocks.YELLOW_CARPET)), true);
    public static final Block LIME_LINEN_CARPET = ModBlocks.registerBlock("lime_linen_carpet", new DyedCarpetBlock(DyeColor.LIME, AbstractBlock.Settings.copy(Blocks.LIME_CARPET)), true);
    public static final Block PINK_LINEN_CARPET = ModBlocks.registerBlock("pink_linen_carpet", new DyedCarpetBlock(DyeColor.PINK, AbstractBlock.Settings.copy(Blocks.PINK_CARPET)), true);
    public static final Block GRAY_LINEN_CARPET = ModBlocks.registerBlock("gray_linen_carpet", new DyedCarpetBlock(DyeColor.GRAY, AbstractBlock.Settings.copy(Blocks.GRAY_CARPET)), true);
    public static final Block LIGHT_GRAY_LINEN_CARPET = ModBlocks.registerBlock("light_gray_linen_carpet", new DyedCarpetBlock(DyeColor.LIGHT_GRAY, AbstractBlock.Settings.copy(Blocks.LIGHT_GRAY_CARPET)), true);
    public static final Block CYAN_LINEN_CARPET = ModBlocks.registerBlock("cyan_linen_carpet", new DyedCarpetBlock(DyeColor.CYAN, AbstractBlock.Settings.copy(Blocks.CYAN_CARPET)), true);
    public static final Block PURPLE_LINEN_CARPET = ModBlocks.registerBlock("purple_linen_carpet", new DyedCarpetBlock(DyeColor.PURPLE, AbstractBlock.Settings.copy(Blocks.PURPLE_CARPET)), true);
    public static final Block BLUE_LINEN_CARPET = ModBlocks.registerBlock("blue_linen_carpet", new DyedCarpetBlock(DyeColor.BLUE, AbstractBlock.Settings.copy(Blocks.BLUE_CARPET)), true);
    public static final Block BROWN_LINEN_CARPET = ModBlocks.registerBlock("brown_linen_carpet", new DyedCarpetBlock(DyeColor.BROWN, AbstractBlock.Settings.copy(Blocks.BROWN_CARPET)), true);
    public static final Block GREEN_LINEN_CARPET = ModBlocks.registerBlock("green_linen_carpet", new DyedCarpetBlock(DyeColor.GREEN, AbstractBlock.Settings.copy(Blocks.GREEN_CARPET)), true);
    public static final Block RED_LINEN_CARPET = ModBlocks.registerBlock("red_linen_carpet", new DyedCarpetBlock(DyeColor.RED, AbstractBlock.Settings.copy(Blocks.RED_CARPET)), true);
    public static final Block BLACK_LINEN_CARPET = ModBlocks.registerBlock("black_linen_carpet", new DyedCarpetBlock(DyeColor.BLACK, AbstractBlock.Settings.copy(Blocks.BLACK_CARPET)), true);

    public static final Block WHITE_SILK_CARPET = ModBlocks.registerBlock("white_silk_carpet", new DyedCarpetBlock(DyeColor.WHITE, AbstractBlock.Settings.copy(Blocks.WHITE_CARPET)), true);
    public static final Block ORANGE_SILK_CARPET = ModBlocks.registerBlock("orange_silk_carpet", new DyedCarpetBlock(DyeColor.ORANGE, AbstractBlock.Settings.copy(Blocks.ORANGE_CARPET)), true);
    public static final Block MAGENTA_SILK_CARPET = ModBlocks.registerBlock("magenta_silk_carpet", new DyedCarpetBlock(DyeColor.MAGENTA, AbstractBlock.Settings.copy(Blocks.MAGENTA_CARPET)), true);
    public static final Block LIGHT_BLUE_SILK_CARPET = ModBlocks.registerBlock("light_blue_silk_carpet", new DyedCarpetBlock(DyeColor.LIGHT_BLUE, AbstractBlock.Settings.copy(Blocks.LIGHT_BLUE_CARPET)), true);
    public static final Block YELLOW_SILK_CARPET = ModBlocks.registerBlock("yellow_silk_carpet", new DyedCarpetBlock(DyeColor.YELLOW, AbstractBlock.Settings.copy(Blocks.YELLOW_CARPET)), true);
    public static final Block LIME_SILK_CARPET = ModBlocks.registerBlock("lime_silk_carpet", new DyedCarpetBlock(DyeColor.LIME, AbstractBlock.Settings.copy(Blocks.LIME_CARPET)), true);
    public static final Block PINK_SILK_CARPET = ModBlocks.registerBlock("pink_silk_carpet", new DyedCarpetBlock(DyeColor.PINK, AbstractBlock.Settings.copy(Blocks.PINK_CARPET)), true);
    public static final Block GRAY_SILK_CARPET = ModBlocks.registerBlock("gray_silk_carpet", new DyedCarpetBlock(DyeColor.GRAY, AbstractBlock.Settings.copy(Blocks.GRAY_CARPET)), true);
    public static final Block LIGHT_GRAY_SILK_CARPET = ModBlocks.registerBlock("light_gray_silk_carpet", new DyedCarpetBlock(DyeColor.LIGHT_GRAY, AbstractBlock.Settings.copy(Blocks.LIGHT_GRAY_CARPET)), true);
    public static final Block CYAN_SILK_CARPET = ModBlocks.registerBlock("cyan_silk_carpet", new DyedCarpetBlock(DyeColor.CYAN, AbstractBlock.Settings.copy(Blocks.CYAN_CARPET)), true);
    public static final Block PURPLE_SILK_CARPET = ModBlocks.registerBlock("purple_silk_carpet", new DyedCarpetBlock(DyeColor.PURPLE, AbstractBlock.Settings.copy(Blocks.PURPLE_CARPET)), true);
    public static final Block BLUE_SILK_CARPET = ModBlocks.registerBlock("blue_silk_carpet", new DyedCarpetBlock(DyeColor.BLUE, AbstractBlock.Settings.copy(Blocks.BLUE_CARPET)), true);
    public static final Block BROWN_SILK_CARPET = ModBlocks.registerBlock("brown_silk_carpet", new DyedCarpetBlock(DyeColor.BROWN, AbstractBlock.Settings.copy(Blocks.BROWN_CARPET)), true);
    public static final Block GREEN_SILK_CARPET = ModBlocks.registerBlock("green_silk_carpet", new DyedCarpetBlock(DyeColor.GREEN, AbstractBlock.Settings.copy(Blocks.GREEN_CARPET)), true);
    public static final Block RED_SILK_CARPET = ModBlocks.registerBlock("red_silk_carpet", new DyedCarpetBlock(DyeColor.RED, AbstractBlock.Settings.copy(Blocks.RED_CARPET)), true);
    public static final Block BLACK_SILK_CARPET = ModBlocks.registerBlock("black_silk_carpet", new DyedCarpetBlock(DyeColor.BLACK, AbstractBlock.Settings.copy(Blocks.BLACK_CARPET)), true);

    // Mulberry
    public static final Block MULBERRY_LEAVES = ModBlocks.registerBlock("mulberry_leaves", new LeavesBlock(AbstractBlock.Settings.copy(Blocks.DARK_OAK_LEAVES)), true);
    public static final Block MULBERRY_SILKWORM_LEAVES = ModBlocks.registerBlock("mulberry_silkworm_leaves", new SilkwormLeavesBlock(AbstractBlock.Settings.copy(Blocks.DARK_OAK_LEAVES)), true);
    public static final Block MULBERRY_SAPLING = ModBlocks.registerBlock("mulberry_sapling", new SaplingBlock(new MulberrySaplingGenerator(), AbstractBlock.Settings.copy(Blocks.OAK_SAPLING)), true);

    // Cotton & Flax
    public static final Block COTTON_FLOWER = ModBlocks.registerBlock("cotton_flower", new CottonFlowerBlock(StatusEffects.ABSORPTION, 8, AbstractBlock.Settings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS)), true);
    public static final Block COTTON_CROP = ModBlocks.registerBlock("cotton_crop", new CottonCropBlock(AbstractBlock.Settings.copy(Blocks.WHEAT)), false);
    public static final Block POTTED_COTTON_FLOWER = ModBlocks.registerBlock("potted_cotton_flower", new FlowerPotBlock(COTTON_FLOWER, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque()), false);

    public static final Block FLAX_BLOCK = ModBlocks.registerBlock("flax_block", new HayBlock(AbstractBlock.Settings.copy(Blocks.HAY_BLOCK)), true);
    public static final Block FLAX_FLOWER = ModBlocks.registerBlock("flax_flower", new TallFlowerBlock(AbstractBlock.Settings.copy(Blocks.ROSE_BUSH)), true);
    public static final Block FLAX_CROP = ModBlocks.registerBlock("flax_crop", new FlaxCropBlock(AbstractBlock.Settings.copy(Blocks.WHEAT)), false);

    // Scutcher
    public static final Block SCUTCHER = ModBlocks.registerBlock("scutcher", new ScutcherBlock(AbstractBlock.Settings.copy(Blocks.GRINDSTONE)), true);


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
        FlammableBlockRegistry flammableBlockRegistry = FlammableBlockRegistry.getDefaultInstance();

        flammableBlockRegistry.add(ModBlocks.MULBERRY_LEAVES, 30, 60);
        flammableBlockRegistry.add(ModBlocks.MULBERRY_SILKWORM_LEAVES, 30, 60);
        flammableBlockRegistry.add(ModBlocks.FLAX_BLOCK, 30, 60);

        flammableBlockRegistry.add(ModBlockTags.COTTON, 5, 20);
        flammableBlockRegistry.add(ModBlockTags.COTTON_CARPETS, 5, 20);
        flammableBlockRegistry.add(ModBlockTags.FUR, 5, 20);
        flammableBlockRegistry.add(ModBlockTags.FUR_CARPETS, 5, 20);
        flammableBlockRegistry.add(ModBlockTags.LINEN, 5, 20);
        flammableBlockRegistry.add(ModBlockTags.LINEN_CARPETS, 5, 20);
        flammableBlockRegistry.add(ModBlockTags.SILK, 5, 20);
        flammableBlockRegistry.add(ModBlockTags.SILK_CARPETS, 5, 20);

        ArmorRestitched.LOGGER.debug("Registering ModBlocks for " + ArmorRestitched.MOD_ID);
    }

}
