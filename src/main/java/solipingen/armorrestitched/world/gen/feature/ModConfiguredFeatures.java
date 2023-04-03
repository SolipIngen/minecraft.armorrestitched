package solipingen.armorrestitched.world.gen.feature;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import solipingen.armorrestitched.ArmorRestitched;
import solipingen.armorrestitched.block.ModBlocks;

public class ModConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?, ?>> FLOWER_FLAX_KEY = ModConfiguredFeatures.registerKey("flower_flax");


    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        ModConfiguredFeatures.register(context, FLOWER_FLAX_KEY, Feature.FLOWER, 
            new RandomPatchFeatureConfig(64, 6, 2, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, 
                new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.FLAX_FLOWER))
        )));
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(ArmorRestitched.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context, RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<FC, F>(feature, configuration));
    }

    
}
