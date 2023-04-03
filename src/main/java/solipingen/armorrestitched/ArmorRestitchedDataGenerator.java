package solipingen.armorrestitched;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import solipingen.armorrestitched.data.ModWorldGenerator;
import solipingen.armorrestitched.world.gen.feature.ModConfiguredFeatures;
import solipingen.armorrestitched.world.gen.feature.ModPlacedFeatures;


public class ArmorRestitchedDataGenerator implements DataGeneratorEntrypoint {


    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        
        pack.addProvider(ModWorldGenerator::new);

    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, (context) -> ModConfiguredFeatures.bootstrap(context));
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, (context) -> ModPlacedFeatures.bootstrap(context));
    }

    
}
