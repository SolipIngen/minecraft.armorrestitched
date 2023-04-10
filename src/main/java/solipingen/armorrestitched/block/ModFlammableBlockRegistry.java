package solipingen.armorrestitched.block;

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import solipingen.armorrestitched.registry.tag.ModBlockTags;


public class ModFlammableBlockRegistry {


    public static void registerFlammableBlocks() {
        FlammableBlockRegistry flammableBlockRegistry = FlammableBlockRegistry.getDefaultInstance();

        flammableBlockRegistry.add(ModBlocks.MULBERRY_LEAVES, 30, 60);
        flammableBlockRegistry.add(ModBlocks.MULBERRY_SILKWORM_LEAVES, 30, 60);

        flammableBlockRegistry.add(ModBlockTags.COTTON, 5, 20);
        flammableBlockRegistry.add(ModBlockTags.COTTON_CARPETS, 5, 20);
        flammableBlockRegistry.add(ModBlockTags.FUR, 5, 20);
        flammableBlockRegistry.add(ModBlockTags.FUR_CARPETS, 5, 20);
        flammableBlockRegistry.add(ModBlockTags.LINEN, 5, 20);
        flammableBlockRegistry.add(ModBlockTags.LINEN_CARPETS, 5, 20);
        flammableBlockRegistry.add(ModBlockTags.SILK, 5, 20);
        flammableBlockRegistry.add(ModBlockTags.SILK_CARPETS, 5, 20);

    }
    
}
