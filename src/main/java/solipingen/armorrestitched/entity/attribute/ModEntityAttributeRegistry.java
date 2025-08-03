package solipingen.armorrestitched.entity.attribute;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import solipingen.armorrestitched.ArmorRestitched;
import solipingen.armorrestitched.entity.ModEntityTypes;
import solipingen.armorrestitched.entity.SilkMothEntity;


public class ModEntityAttributeRegistry {


    public static void registerModEntityAttributes() {
        
        FabricDefaultAttributeRegistry.register(ModEntityTypes.SILK_MOTH_ENTITY, SilkMothEntity.createSilkMothAttributes());

        ArmorRestitched.LOGGER.debug("Registering Mod Entity attributes for " + ArmorRestitched.MOD_ID);

    }
    
}
