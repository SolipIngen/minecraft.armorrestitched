package solipingen.armorrestitched.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.ArmorRestitched;

@SuppressWarnings("unused")
public class ModBlocks {


    private static Block registerBlock(String name, Block block, boolean withBlockItem) {
        if (withBlockItem) {
            ModBlocks.registerBlockItem(name, block);
        }
        return Registry.register(Registries.BLOCK, new Identifier(ArmorRestitched.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(ArmorRestitched.MOD_ID, name), new BlockItem(block, new FabricItemSettings()));
    }

    public static void replaceVanillaBlocks() {
        for (Block vanillaBlock : Registries.BLOCK) {
            String name = vanillaBlock.getTranslationKey().substring(vanillaBlock.getTranslationKey().lastIndexOf(".") + 1);
            if (name.endsWith("wool")) {
                int rawId = Registries.BLOCK.getRawId(vanillaBlock);
                Block woolBlock = new WoolBlock(AbstractBlock.Settings.copy(vanillaBlock));
                Registry.register(Registries.BLOCK, rawId, name, woolBlock);
                Item vanillaBlockItem = vanillaBlock.asItem();
                for (Item item : Registries.ITEM) {
                    if (item != vanillaBlockItem) continue;
                    int itemRawId = Registries.ITEM.getRawId(item);
                    Registry.register(Registries.ITEM, itemRawId, name, new BlockItem(woolBlock, new FabricItemSettings()));
                }
            }
        }
        ArmorRestitched.LOGGER.debug("Replacing Vanilla Blocks for " + ArmorRestitched.MOD_ID);
    }

    public static void registerModBlocks() {
        ArmorRestitched.LOGGER.debug("Registering ModBlocks for " + ArmorRestitched.MOD_ID);
    }

}
