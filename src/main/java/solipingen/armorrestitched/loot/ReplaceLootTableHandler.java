package solipingen.armorrestitched.loot;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableSource;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.LootTable;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.ArmorRestitched;


public class ReplaceLootTableHandler implements LootTableEvents.Replace {
    private static final Identifier ARMORER_VILLAGER_CHEST_ID = new Identifier(ArmorRestitched.MOD_ID, "chests/village/village_armorer");
    private static final Identifier LEATHERWORKER_VILLAGER_CHEST_ID = new Identifier(ArmorRestitched.MOD_ID, "chests/village/village_tannery");
    private static final Identifier DUNGEON_CHEST_ID = new Identifier(ArmorRestitched.MOD_ID, "chests/simple_dungeon");
    private static final Identifier DESERT_PYRAMID_ID = new Identifier(ArmorRestitched.MOD_ID, "chests/desert_pyramid");
    private static final Identifier JUNGLE_TEMPLE_ID = new Identifier(ArmorRestitched.MOD_ID, "chests/jungle_temple");
    private static final Identifier[] ID_ARRAY = new Identifier[]{ 
        DUNGEON_CHEST_ID, DESERT_PYRAMID_ID, JUNGLE_TEMPLE_ID, 
        ARMORER_VILLAGER_CHEST_ID, LEATHERWORKER_VILLAGER_CHEST_ID
    };


    @Override
    @Nullable
    public LootTable replaceLootTable(ResourceManager resourceManager, LootManager lootManager, Identifier id, LootTable original, LootTableSource source) {
        for (int i = 0; i < ID_ARRAY.length; i++) {
            if (id.getPath().equals(ID_ARRAY[i].getPath()) && source.isBuiltin()) {
                LootTable newTable = lootManager.getTable(ID_ARRAY[i]);
                return newTable;
            }
        }
        return original;
    }


    
}

