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
    
    public static void registerModBlocks() {
        ArmorRestitched.LOGGER.debug("Registering ModBlocks for " + ArmorRestitched.MOD_ID);
    }

}
