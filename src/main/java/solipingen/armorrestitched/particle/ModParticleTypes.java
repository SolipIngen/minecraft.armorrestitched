package solipingen.armorrestitched.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.ArmorRestitched;


public class ModParticleTypes {

    public static final SimpleParticleType MULBERRY_LEAVES = ModParticleTypes.register("mulberry_leaves");


    private static SimpleParticleType register(String name) {
        return Registry.register(Registries.PARTICLE_TYPE, Identifier.of(ArmorRestitched.MOD_ID, name), FabricParticleTypes.simple());
    }

    public static void registerModParticleTypes() {
        ArmorRestitched.LOGGER.debug("Registering Mod Particle Types for " + ArmorRestitched.MOD_ID);
    }

}
