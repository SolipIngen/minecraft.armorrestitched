package solipingen.armorrestitched.world.gen.feature;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import solipingen.armorrestitched.ArmorRestitched;
import solipingen.armorrestitched.block.ModBlocks;


public class ModConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?, ?>> FLOWER_COTTON_KEY = ModConfiguredFeatures.registerKey("flower_cotton");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FLOWER_FLAX_KEY = ModConfiguredFeatures.registerKey("flower_flax");
    public static final RegistryKey<ConfiguredFeature<?, ?>> TREE_MULBERRY_KEY = ModConfiguredFeatures.registerKey("tree_mulberry");


    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        ModConfiguredFeatures.register(context, FLOWER_COTTON_KEY, Feature.FLOWER, 
            new RandomPatchFeatureConfig(24, 4, 2, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, 
                new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.COTTON_FLOWER))
        )));
        ModConfiguredFeatures.register(context, FLOWER_FLAX_KEY, Feature.FLOWER, 
            new RandomPatchFeatureConfig(32, 6, 2, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, 
                new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.FLAX_FLOWER))
        )));
        ModConfiguredFeatures.register(context, TREE_MULBERRY_KEY, Feature.TREE, 
            new TreeFeatureConfig.Builder(BlockStateProvider.of(Blocks.DARK_OAK_LOG), 
                new StraightTrunkPlacer(4, 1, 0), 
                new WeightedBlockStateProvider(ModConfiguredFeatures.mulberryLeavesBuilder()), 
                new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 4), 
                new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());
    }
    
    private static DataPool.Builder<BlockState> mulberryLeavesBuilder() {
        DataPool.Builder<BlockState> dataPoolBuilder = DataPool.builder();
        dataPoolBuilder.add(ModBlocks.MULBERRY_LEAVES.getDefaultState(), 23).add(ModBlocks.MULBERRY_SILKWORM_LEAVES.getDefaultState(), 1);
        return dataPoolBuilder;
    }


    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(ArmorRestitched.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context, RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<FC, F>(feature, configuration));
    }

    
}
