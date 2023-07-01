package solipingen.armorrestitched;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import solipingen.armorrestitched.client.render.block.ModBlockRenderLayers;
import solipingen.armorrestitched.client.render.entity.ModEntityRendererRegistry;
import solipingen.armorrestitched.client.render.entity.model.ModEntityModelLayers;


@Environment(value = EnvType.CLIENT)
public class ArmorRestitchedClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {

        ModBlockRenderLayers.registerModBlockRenderLayers();
        ModEntityModelLayers.registerModEntityLayers();
        ModEntityRendererRegistry.registerModEntityRenderers();
        
    }


}
