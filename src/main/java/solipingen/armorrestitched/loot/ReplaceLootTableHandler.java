package solipingen.armorrestitched.loot;

import net.minecraft.registry.*;
import org.jetbrains.annotations.Nullable;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.minecraft.loot.LootTable;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.ArmorRestitched;

import java.util.List;


public class ReplaceLootTableHandler implements LootTableEvents.Replace {

    // Blocks
    public static final RegistryKey<LootTable> COBWEB_BLOCK = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(ArmorRestitched.MOD_ID, "blocks/vanilla_blocks/cobweb"));

    // Entities
    public static final RegistryKey<LootTable> CAVE_SPIDER_ENTITY = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(ArmorRestitched.MOD_ID, "entities/cave_spider"));
    public static final RegistryKey<LootTable> SPIDER_ENTITY = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(ArmorRestitched.MOD_ID, "entities/spider"));

    private static final List<RegistryKey<LootTable>> LOOT_TABLE_LIST = List.of(
            COBWEB_BLOCK,
            CAVE_SPIDER_ENTITY, SPIDER_ENTITY
    );


    @Override
    public @Nullable LootTable replaceLootTable(RegistryKey<LootTable> key, LootTable original, LootTableSource source, RegistryWrapper.WrapperLookup registries) {
        // Fill in if necessary.
        return null;
    }

}

