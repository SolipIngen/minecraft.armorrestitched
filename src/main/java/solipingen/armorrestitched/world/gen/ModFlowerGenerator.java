package solipingen.armorrestitched.world.gen;

import java.util.List;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import solipingen.armorrestitched.world.gen.feature.ModPlacedFeatures;


public class ModFlowerGenerator {
    private static final List<RegistryKey<Biome>> FLAX_FLOWER_BIOMES = List.of(BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS, BiomeKeys.FOREST, BiomeKeys.FLOWER_FOREST, BiomeKeys.BIRCH_FOREST, BiomeKeys.OLD_GROWTH_BIRCH_FOREST, 
        BiomeKeys.MEADOW, BiomeKeys.CHERRY_GROVE);


    public static void generateFlowers() {

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(FLAX_FLOWER_BIOMES), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.FLOWER_FLAX_PLACED_KEY);

    }


}
