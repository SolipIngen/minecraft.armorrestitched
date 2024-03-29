package solipingen.armorrestitched.client.render.entity.model;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.ArmorRestitched;


@Environment(value = EnvType.CLIENT)
public class ModEntityModelLayers {

    
    public static final EntityModelLayer ELYTA_TRIM_LAYER = new EntityModelLayer(new Identifier(ArmorRestitched.MOD_ID, "elytra_trim"), "main");

    public static final EntityModelLayer ZOMBIE_HORSE_ARMOR_LAYER = new EntityModelLayer(new Identifier(ArmorRestitched.MOD_ID, "zombie_horse_armor"), "main");

    public static final EntityModelLayer ILLAGER_LEGS_ARMOR_LAYER = new EntityModelLayer(new Identifier(ArmorRestitched.MOD_ID, "illager_armor_legs"), "main");
    public static final EntityModelLayer ILLAGER_HEAD_ARMOR_LAYER = new EntityModelLayer(new Identifier(ArmorRestitched.MOD_ID, "illager_armor_head"), "main");
    public static final EntityModelLayer ILLAGER_FEET_ARMOR_LAYER = new EntityModelLayer(new Identifier(ArmorRestitched.MOD_ID, "illager_armor_feet"), "main");
    public static final EntityModelLayer ILLAGER_CHEST_ARMOR_LAYER = new EntityModelLayer(new Identifier(ArmorRestitched.MOD_ID, "illager_armor_chest"), "main");

    public static final EntityModelLayer VILLAGER_LEGS_ARMOR_LAYER = new EntityModelLayer(new Identifier(ArmorRestitched.MOD_ID, "villager_armor_legs"), "main");
    public static final EntityModelLayer VILLAGER_HEAD_ARMOR_LAYER = new EntityModelLayer(new Identifier(ArmorRestitched.MOD_ID, "villager_armor_head"), "main");
    public static final EntityModelLayer VILLAGER_FEET_ARMOR_LAYER = new EntityModelLayer(new Identifier(ArmorRestitched.MOD_ID, "villager_armor_feet"), "main");
    public static final EntityModelLayer VILLAGER_CHEST_ARMOR_LAYER = new EntityModelLayer(new Identifier(ArmorRestitched.MOD_ID, "villager_armor_chest"), "main");

    public static final EntityModelLayer SILK_MOTH_LAYER = new EntityModelLayer(new Identifier(ArmorRestitched.MOD_ID, "silk_moth"), "main");

    
    public static void registerModEntityLayers() {

        EntityModelLayerRegistry.registerModelLayer(ELYTA_TRIM_LAYER, ElytraTrimEntityModel::getTexturedModelData);

        EntityModelLayerRegistry.registerModelLayer(ZOMBIE_HORSE_ARMOR_LAYER, ZombieHorseArmorEntityModel::getTexturedModelData);

        EntityModelLayerRegistry.registerModelLayer(ILLAGER_LEGS_ARMOR_LAYER, IllagerArmorEntityModel::getLegsArmorTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ILLAGER_HEAD_ARMOR_LAYER, IllagerArmorEntityModel::getHeadArmorTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ILLAGER_FEET_ARMOR_LAYER, IllagerArmorEntityModel::getFeetArmorTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ILLAGER_CHEST_ARMOR_LAYER, IllagerArmorEntityModel::getChestArmorTexturedModelData);

        EntityModelLayerRegistry.registerModelLayer(VILLAGER_LEGS_ARMOR_LAYER, VillagerArmorEntityModel::getLegsArmorTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(VILLAGER_HEAD_ARMOR_LAYER, VillagerArmorEntityModel::getHeadArmorTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(VILLAGER_FEET_ARMOR_LAYER, VillagerArmorEntityModel::getFeetArmorTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(VILLAGER_CHEST_ARMOR_LAYER, VillagerArmorEntityModel::getChestArmorTexturedModelData);

        EntityModelLayerRegistry.registerModelLayer(SILK_MOTH_LAYER, SilkMothEntityModel::getTexturedModelData);
        

        ArmorRestitched.LOGGER.debug("Registering Mod Entity Model Layers for " + ArmorRestitched.MOD_ID);

    }

}

