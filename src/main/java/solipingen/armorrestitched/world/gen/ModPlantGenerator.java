package solipingen.armorrestitched.world.gen;

import java.util.List;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import solipingen.armorrestitched.world.gen.feature.ModPlacedFeatures;


public class ModPlantGenerator {
    private static final List<RegistryKey<Biome>> COTTON_FLOWER_BIOMES = List.of(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE, BiomeKeys.BAMBOO_JUNGLE);
    private static final List<RegistryKey<Biome>> FLAX_FLOWER_BIOMES = List.of(BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS, BiomeKeys.FOREST, BiomeKeys.FLOWER_FOREST, BiomeKeys.BIRCH_FOREST, BiomeKeys.OLD_GROWTH_BIRCH_FOREST, 
        BiomeKeys.MEADOW, BiomeKeys.CHERRY_GROVE);


    public static void generatePlants() {

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(COTTON_FLOWER_BIOMES), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.FLOWER_COTTON_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(FLAX_FLOWER_BIOMES), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.FLOWER_FLAX_PLACED_KEY);

    }


}
