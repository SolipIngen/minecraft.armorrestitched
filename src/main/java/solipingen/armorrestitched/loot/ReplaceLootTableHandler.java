package solipingen.armorrestitched.loot;

import net.minecraft.registry.*;
import org.jetbrains.annotations.Nullable;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableSource;
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
    @Nullable
    public LootTable replaceLootTable(RegistryKey<LootTable> key, LootTable lootTable, LootTableSource source) {
//        for (RegistryKey<LootTable> modKey : LOOT_TABLE_LIST) {
//            if (modKey.getValue().getPath().matches(key.getValue().getPath()) && (source.isBuiltin() || source == LootTableSource.DATA_PACK)) {
//                ArrayList<LootPool> poolArrayList = new ArrayList<>(((LootTableAccessor)lootTable).fabric_getPools());
//                poolArrayList.forEach(pool -> {
//                    int poolIndex = poolArrayList.indexOf(pool);
//                    LootNumberProvider rolls = ((LootPoolAccessor)pool).fabric_getRolls();
//                    LootNumberProvider bonusRolls = ((LootPoolAccessor)pool).fabric_getBonusRolls();
//                    ArrayList<LootPoolEntry> entries = new ArrayList<>(((LootPoolAccessor)pool).fabric_getEntries());
//                    ArrayList<LootCondition> conditions = new ArrayList<>(((LootPoolAccessor)pool).fabric_getConditions());
//                    ArrayList<LootFunction> functions = new ArrayList<>(((LootPoolAccessor)pool).fabric_getFunctions());
//                    LootPool.Builder replacementPoolBuilder = LootPool.builder();
//                    if (modKey.equals(COBWEB_BLOCK)) {
//                        for (LootPoolEntry entry : entries) {
//                            if (entry instanceof ItemEntry itemEntry && ((ItemEntryAccessor)itemEntry).getItem().value() == Items.STRING) {
//                                int entryIndex = entries.indexOf(entry);
//                                LootPoolEntry.Builder replacementEntryBuilder = ItemEntry.builder(ModItems.SILK)
//                                        .weight(((LeafEntryAccessor)itemEntry).getWeight())
//                                        .quality(((LeafEntryAccessor)itemEntry).getQuality())
//                                        .conditionally(SurvivesExplosionLootCondition.builder());
//                                entries.set(entryIndex, replacementEntryBuilder.build());
//                            }
//                        }
//                    }
//                    else if (modKey.equals(CAVE_SPIDER_ENTITY) || modKey.equals(SPIDER_ENTITY)) {
//                        for (LootPoolEntry entry : entries) {
//                            if (entry instanceof ItemEntry itemEntry && ((ItemEntryAccessor)itemEntry).getItem().value() == Items.STRING) {
//                                int entryIndex = entries.indexOf(entry);
//                                List<LootFunction> entryFunctions = ((LeafEntryAccessor)itemEntry).getFunctions();
//                                ItemEntry.Builder replacementEntryBuilder = ItemEntry.builder(ModItems.SILK)
//                                        .weight(((LeafEntryAccessor)itemEntry).getWeight())
//                                        .quality(((LeafEntryAccessor)itemEntry).getQuality());
//                                for (LootFunction entryFunction : entryFunctions) {
//                                    if (entryFunction instanceof SetCountLootFunction setCountLootFunction) {
//                                        replacementEntryBuilder.apply(SetCountLootFunction.builder(((SetCountLootFunctionAccessor)setCountLootFunction).getCountRange()));
//                                    }
//                                    else if (entryFunction instanceof EnchantedCountIncreaseLootFunction enchantedCountIncreaseLootFunction) {
//                                        replacementEntryBuilder.apply(new EnchantedCountIncreaseLootFunction.Builder(
//                                                ((EnchantedCountIncreaseLootFunctionAccessor)enchantedCountIncreaseLootFunction).getEnchantment(),
//                                                ((EnchantedCountIncreaseLootFunctionAccessor)enchantedCountIncreaseLootFunction).getCount()));
//                                    }
//                                }
//                                entries.set(entryIndex, replacementEntryBuilder.build());
//                            }
//                        }
//                    }
//                    poolArrayList.set(poolIndex,
//                            LootPool.builder().rolls(rolls)
//                                    .bonusRolls(bonusRolls)
//                                    .with(entries)
//                                    .conditionally(conditions)
//                                    .apply(functions).build());
//                });
//                return LootTable.builder().pools(poolArrayList).
//                        .
//                        .randomSequenceId();
//            }
//        }
        return null;
    }


    
}

