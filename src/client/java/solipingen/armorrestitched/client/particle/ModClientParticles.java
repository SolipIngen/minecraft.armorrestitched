package solipingen.armorrestitched.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.particle.LeavesParticle;
import solipingen.armorrestitched.ArmorRestitched;
import solipingen.armorrestitched.particle.ModParticleTypes;


@Environment(EnvType.CLIENT)
public class ModClientParticles {


    public static void registerModClientParticles() {
        ParticleFactoryRegistry.getInstance().register(ModParticleTypes.MULBERRY_LEAVES, LeavesParticle.PaleOakLeavesFactory::new);

        ArmorRestitched.LOGGER.debug("Registering Mod Client Particles for " + ArmorRestitched.MOD_ID);
    }

}
