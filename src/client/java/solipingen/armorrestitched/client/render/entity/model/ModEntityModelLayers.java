package solipingen.armorrestitched.client.render.entity.model;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.ArmorRestitched;


@Environment(value = EnvType.CLIENT)
public class ModEntityModelLayers {
    private static final Dilation OUTER_DILATION = new Dilation(1.0f);
    private static final Dilation INNER_DILATION = new Dilation(0.5f);
    
    public static final EntityModelLayer ELYTA_TRIM_LAYER = new EntityModelLayer(new Identifier(ArmorRestitched.MOD_ID, "elytra_trim"), "main");

    public static final EntityModelLayer ILLAGER_INNER_ARMOR_LAYER = new EntityModelLayer(new Identifier(ArmorRestitched.MOD_ID, "illager_inner_armor"), "main");
    public static final EntityModelLayer ILLAGER_OUTER_ARMOR_LAYER = new EntityModelLayer(new Identifier(ArmorRestitched.MOD_ID, "illager_outer_armor"), "main");

    public static final EntityModelLayer VILLAGER_INNER_ARMOR_LAYER = new EntityModelLayer(new Identifier(ArmorRestitched.MOD_ID, "villager_inner_armor"), "main");
    public static final EntityModelLayer VILLAGER_OUTER_ARMOR_LAYER = new EntityModelLayer(new Identifier(ArmorRestitched.MOD_ID, "villager_outer_armor"), "main");

    public static final EntityModelLayer SILK_MOTH_LAYER = new EntityModelLayer(new Identifier(ArmorRestitched.MOD_ID, "silk_moth"), "main");

    
    public static void registerModEntityLayers() {

        EntityModelLayerRegistry.registerModelLayer(ELYTA_TRIM_LAYER, ElytraTrimEntityModel::getTexturedModelData);

        EntityModelLayerRegistry.registerModelLayer(ILLAGER_INNER_ARMOR_LAYER, () -> IllagerArmorEntityModel.getArmorTexturedModelData(INNER_DILATION));
        EntityModelLayerRegistry.registerModelLayer(ILLAGER_OUTER_ARMOR_LAYER, () -> IllagerArmorEntityModel.getArmorTexturedModelData(OUTER_DILATION));

        EntityModelLayerRegistry.registerModelLayer(VILLAGER_INNER_ARMOR_LAYER, () -> VillagerArmorEntityModel.getArmorTexturedModelData(INNER_DILATION));
        EntityModelLayerRegistry.registerModelLayer(VILLAGER_OUTER_ARMOR_LAYER, () -> VillagerArmorEntityModel.getArmorTexturedModelData(OUTER_DILATION));

        EntityModelLayerRegistry.registerModelLayer(SILK_MOTH_LAYER, SilkMothEntityModel::getTexturedModelData);
        

        ArmorRestitched.LOGGER.debug("Registering Mod Entity Model Layers for " + ArmorRestitched.MOD_ID);

    }

}

