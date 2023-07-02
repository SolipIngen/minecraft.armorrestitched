package solipingen.armorrestitched.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.ArmorRestitched;


public class ModEntityTypes {

    public static final EntityType<SilkMothEntity> SILK_MOTH_ENTITY = Registry.register(Registries.ENTITY_TYPE,
        new Identifier(ArmorRestitched.MOD_ID, "silk_moth"),
        FabricEntityTypeBuilder.<SilkMothEntity>create(SpawnGroup.AMBIENT, SilkMothEntity::new)
            .dimensions(EntityDimensions.fixed(0.25f, 0.2f))
            .trackRangeBlocks(64)
            .build());


    public static void registerModEntityTypes() {
        ArmorRestitched.LOGGER.debug("Registering Mod Entity Types for " + ArmorRestitched.MOD_ID);
    }

}
