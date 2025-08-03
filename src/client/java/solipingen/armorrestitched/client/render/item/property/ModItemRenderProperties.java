package solipingen.armorrestitched.client.render.item.property;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.item.property.select.SelectProperties;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.ArmorRestitched;


@Environment(EnvType.CLIENT)
public class ModItemRenderProperties {

    public static void registerModItemRenderProperties() {
        SelectProperties.ID_MAPPER.put(Identifier.of(ArmorRestitched.MOD_ID, "broken_elytra_trim_material"), BrokenElytraTrimMaterialProperty.TYPE);

        ArmorRestitched.LOGGER.debug("Registering Mod Item Render Properties for " + ArmorRestitched.MOD_ID);
    }

}
