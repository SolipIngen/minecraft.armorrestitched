package solipingen.armorrestitched.world.gen.feature;

import java.util.List;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;
import net.minecraft.world.gen.placementmodifier.SurfaceWaterDepthFilterPlacementModifier;
import solipingen.armorrestitched.ArmorRestitched;
import solipingen.armorrestitched.block.ModBlocks;

public class ModPlacedFeatures {

    public static final RegistryKey<PlacedFeature> FLOWER_COTTON_PLACED_KEY = ModPlacedFeatures.registerKey("flower_cotton_placed");
    public static final RegistryKey<PlacedFeature> FLOWER_FLAX_PLACED_KEY = ModPlacedFeatures.registerKey("flower_flax_placed");
    public static final RegistryKey<PlacedFeature> TREE_MULBERRY_PLACED_KEY = ModPlacedFeatures.registerKey("tree_mulberry_placed");
    

    public static void bootstrap(Registerable<PlacedFeature> context) {
        RegistryEntryLookup<ConfiguredFeature<?, ?>> configuredFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        ModPlacedFeatures.register(context, FLOWER_COTTON_PLACED_KEY, 
            configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.FLOWER_COTTON_KEY), 
            List.of(RarityFilterPlacementModifier.of(48), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()));
        ModPlacedFeatures.register(context, FLOWER_FLAX_PLACED_KEY, 
            configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.FLOWER_FLAX_KEY), 
            List.of(RarityFilterPlacementModifier.of(32), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()));
        ModPlacedFeatures.register(context, TREE_MULBERRY_PLACED_KEY, 
            configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.TREE_MULBERRY_KEY), 
            List.of(CountPlacementModifier.of(1), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(0), PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP, PlacedFeatures.wouldSurvive(ModBlocks.MULBERRY_SAPLING), BiomePlacementModifier.of()));
    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(ArmorRestitched.MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, modifiers));
    }


}
