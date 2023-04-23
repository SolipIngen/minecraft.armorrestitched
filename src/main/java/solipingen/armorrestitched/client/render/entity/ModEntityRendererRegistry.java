package solipingen.armorrestitched.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import solipingen.armorrestitched.ArmorRestitched;
import solipingen.armorrestitched.entity.ModEntityTypes;


@Environment(value=EnvType.CLIENT)
public class ModEntityRendererRegistry {

    
    public static void registerModEntityRenderers() {

        // Spears
        EntityRendererRegistry.register(ModEntityTypes.SILK_MOTH_ENTITY, (context) -> new SilkMothEntityRenderer(context));


        ArmorRestitched.LOGGER.debug("Registering Mod Entity Renderers for" + ArmorRestitched.MOD_ID);

    }

}
