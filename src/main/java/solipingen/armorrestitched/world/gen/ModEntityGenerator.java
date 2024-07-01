package solipingen.armorrestitched.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;
import solipingen.armorrestitched.entity.ModEntityTypes;
import solipingen.armorrestitched.entity.SilkMothEntity;


public class ModEntityGenerator {


    public static void addSpawns() {

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.DARK_FOREST), SpawnGroup.AMBIENT, 
            ModEntityTypes.SILK_MOTH_ENTITY, 20, 1, 3);
        SpawnRestriction.register(ModEntityTypes.SILK_MOTH_ENTITY, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING, SilkMothEntity::canSpawn);
        
    }

    
}
