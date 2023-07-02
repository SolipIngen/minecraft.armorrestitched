package solipingen.armorrestitched.world.tree;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import solipingen.armorrestitched.world.gen.feature.ModConfiguredFeatures;


public class MulberrySaplingGenerator extends SaplingGenerator {


    @Nullable
    @Override
    protected RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        return ModConfiguredFeatures.TREE_MULBERRY_KEY;
    }
    


}
