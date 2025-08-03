package solipingen.armorrestitched.client.render.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.render.BlockRenderLayer;
import solipingen.armorrestitched.block.ModBlocks;


@Environment(value = EnvType.CLIENT)
public class ModBlockRenderLayers {

    
    public static void registerModBlockRenderLayers() {

        BlockRenderLayerMap.putBlock(ModBlocks.COTTON_CROP, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.COTTON_FLOWER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.POTTED_COTTON_FLOWER, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModBlocks.FLAX_CROP, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.FLAX_FLOWER, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(ModBlocks.MULBERRY_LEAVES, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.MULBERRY_SILKWORM_LEAVES, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.MULBERRY_SAPLING, BlockRenderLayer.CUTOUT);

    }
    
}
