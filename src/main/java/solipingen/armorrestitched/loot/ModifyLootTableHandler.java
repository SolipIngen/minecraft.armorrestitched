package solipingen.armorrestitched.loot;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.EmptyEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.EnchantRandomlyLootFunction;
import net.minecraft.loot.function.EnchantedCountIncreaseLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.*;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.block.ModBlocks;
import solipingen.armorrestitched.enchantment.ModEnchantments;
import solipingen.armorrestitched.item.ModItems;


public class ModifyLootTableHandler implements LootTableEvents.Modify {

    // Entities
    public static final RegistryKey<LootTable> GOAT_ENTITY = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/goat"));
    public static final RegistryKey<LootTable> PANDA_ENTITY = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/panda"));
    public static final RegistryKey<LootTable> POLAR_BEAR_ENTITY = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/polar_bear"));


    @Override
    public void modifyLootTable(RegistryKey<LootTable> key, LootTable.Builder tableBuilder, LootTableSource source, RegistryWrapper.WrapperLookup registries) {
        String path = key.getValue().getPath();
        if (path.startsWith("chests")) {
            LootPool.Builder poolBuilder = LootPool.builder();
            if (path.matches(LootTables.SIMPLE_DUNGEON_CHEST.getValue().getPath())) {
                poolBuilder.rolls(ConstantLootNumberProvider.create(1))
                        .with(EmptyEntry.builder().weight(3))
                        .with(ItemEntry.builder(Items.LEATHER_HORSE_ARMOR).weight(2))
                        .with(ItemEntry.builder(ModItems.COPPER_HORSE_ARMOR).weight(1));
                tableBuilder.pool(poolBuilder.build());
            }
            else if (path.matches(LootTables.JUNGLE_TEMPLE_CHEST.getValue().getPath())) {
                poolBuilder.rolls(ConstantLootNumberProvider.create(1))
                        .with(EmptyEntry.builder().weight(5))
                        .with(ItemEntry.builder(ModItems.COPPER_HORSE_ARMOR).weight(1));
                tableBuilder.pool(poolBuilder.build());
            }
            else if (path.matches(LootTables.DESERT_PYRAMID_CHEST.getValue().getPath())) {
                poolBuilder.rolls(ConstantLootNumberProvider.create(1))
                        .with(EmptyEntry.builder().weight(4))
                        .with(ItemEntry.builder(ModItems.COPPER_HORSE_ARMOR).weight(1));
                tableBuilder.pool(poolBuilder.build());
            }
            else if (path.matches(LootTables.END_CITY_TREASURE_CHEST.getValue().getPath())) {
                poolBuilder.rolls(ConstantLootNumberProvider.create(1))
                        .with(EmptyEntry.builder().weight(7))
                        .with(ItemEntry.builder(Items.BOOK).weight(1))
                        .apply(new EnchantRandomlyLootFunction.Builder().option(registries.getOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(ModEnchantments.SOARING)));
                tableBuilder.pool(poolBuilder.build());
            }
            else if (path.startsWith("chests/underwater_ruin")) {
                poolBuilder.rolls(ConstantLootNumberProvider.create(1))
                        .with(EmptyEntry.builder().weight(9))
                        .with(ItemEntry.builder(ModItems.FUR_HELMET).weight(1))
                        .with(ItemEntry.builder(ModItems.FUR_CHESTPLATE).weight(1))
                        .with(ItemEntry.builder(ModItems.SILK_HELMET).weight(1))
                        .with(ItemEntry.builder(ModItems.SILK_CHESTPLATE).weight(1))
                        .with(ItemEntry.builder(ModItems.COTTON_HELMET).weight(1))
                        .with(ItemEntry.builder(ModItems.COTTON_CHESTPLATE).weight(1))
                        .with(ItemEntry.builder(ModItems.LINEN_HELMET).weight(1))
                        .with(ItemEntry.builder(ModItems.LINEN_CHESTPLATE).weight(1))
                        .with(ItemEntry.builder(ModItems.COPPER_HELMET).weight(2))
                        .with(ItemEntry.builder(ModItems.COPPER_CHESTPLATE).weight(2))
                        .apply(EnchantRandomlyLootFunction.builder(registries));
                tableBuilder.pool(poolBuilder.build());
            }
            else if (path.matches(LootTables.SHIPWRECK_SUPPLY_CHEST.getValue().getPath())) {
                poolBuilder.rolls(ConstantLootNumberProvider.create(1))
                        .with(EmptyEntry.builder().weight(7))
                        .with(ItemEntry.builder(ModItems.COTTON_HELMET).weight(1))
                        .with(ItemEntry.builder(ModItems.COTTON_CHESTPLATE).weight(1))
                        .with(ItemEntry.builder(ModItems.COTTON_LEGGINGS).weight(1))
                        .with(ItemEntry.builder(ModItems.COTTON_BOOTS).weight(1))
                        .with(ItemEntry.builder(ModItems.LINEN_HELMET).weight(1))
                        .with(ItemEntry.builder(ModItems.LINEN_CHESTPLATE).weight(1))
                        .with(ItemEntry.builder(ModItems.LINEN_LEGGINGS).weight(1))
                        .with(ItemEntry.builder(ModItems.LINEN_BOOTS).weight(1))
                        .apply(EnchantRandomlyLootFunction.builder(registries));
                tableBuilder.pool(poolBuilder.build());
            }
            else if (path.matches(LootTables.SHIPWRECK_TREASURE_CHEST.getValue().getPath())) {
                poolBuilder.rolls(ConstantLootNumberProvider.create(1))
                        .with(EmptyEntry.builder().weight(7))
                        .with(ItemEntry.builder(ModItems.FUR_HELMET).weight(1))
                        .with(ItemEntry.builder(ModItems.FUR_CHESTPLATE).weight(1))
                        .with(ItemEntry.builder(ModItems.FUR_LEGGINGS).weight(1))
                        .with(ItemEntry.builder(ModItems.FUR_BOOTS).weight(1))
                        .with(ItemEntry.builder(ModItems.SILK_HELMET).weight(1))
                        .with(ItemEntry.builder(ModItems.SILK_CHESTPLATE).weight(1))
                        .with(ItemEntry.builder(ModItems.SILK_LEGGINGS).weight(1))
                        .with(ItemEntry.builder(ModItems.SILK_BOOTS).weight(1))
                        .apply(EnchantRandomlyLootFunction.builder(registries));
                tableBuilder.pool(poolBuilder.build());
            }
            else if (path.startsWith("chests/underwater_ruin")) {
                poolBuilder.rolls(ConstantLootNumberProvider.create(1))
                        .with(EmptyEntry.builder().weight(9))
                        .with(ItemEntry.builder(ModItems.FUR_HELMET).weight(1))
                        .with(ItemEntry.builder(ModItems.FUR_CHESTPLATE).weight(1))
                        .with(ItemEntry.builder(ModItems.SILK_HELMET).weight(1))
                        .with(ItemEntry.builder(ModItems.SILK_CHESTPLATE).weight(1))
                        .with(ItemEntry.builder(ModItems.COTTON_HELMET).weight(1))
                        .with(ItemEntry.builder(ModItems.COTTON_CHESTPLATE).weight(1))
                        .with(ItemEntry.builder(ModItems.LINEN_HELMET).weight(1))
                        .with(ItemEntry.builder(ModItems.LINEN_CHESTPLATE).weight(1))
                        .with(ItemEntry.builder(ModItems.COPPER_HELMET).weight(2))
                        .with(ItemEntry.builder(ModItems.COPPER_CHESTPLATE).weight(2))
                        .apply(EnchantRandomlyLootFunction.builder(registries));
                tableBuilder.pool(poolBuilder.build());
            }
            else if (path.matches(LootTables.VILLAGE_ARMORER_CHEST.getValue().getPath())) {
                poolBuilder.rolls(UniformLootNumberProvider.create(1, 5))
                        .with(EmptyEntry.builder().weight(3))
                        .with(ItemEntry.builder(Items.COPPER_INGOT).weight(2).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 3))))
                        .with(ItemEntry.builder(ModItems.COPPER_HELMET).weight(1))
                        .with(ItemEntry.builder(ModItems.COPPER_HORSE_ARMOR).weight(1))
                        .with(ItemEntry.builder(Items.IRON_HORSE_ARMOR).weight(1))
                        .with(ItemEntry.builder(Items.GOLDEN_HORSE_ARMOR).weight(1))
                        .with(ItemEntry.builder(Items.DIAMOND_HORSE_ARMOR).weight(1));
                tableBuilder.pool(poolBuilder.build());
            }
            else if (path.matches(LootTables.VILLAGE_TANNERY_CHEST.getValue().getPath())) {
                poolBuilder.rolls(UniformLootNumberProvider.create(1, 5))
                        .with(EmptyEntry.builder().weight(4))
                        .with(ItemEntry.builder(ModBlocks.WHITE_FUR).weight(2).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 3))))
                        .with(ItemEntry.builder(ModItems.FUR_HELMET).weight(2))
                        .with(ItemEntry.builder(ModItems.FUR_CHESTPLATE).weight(2))
                        .with(ItemEntry.builder(ModItems.FUR_LEGGINGS).weight(2))
                        .with(ItemEntry.builder(ModItems.FUR_BOOTS).weight(2))
                        .with(ItemEntry.builder(Items.SADDLE).weight(2))
                        .with(ItemEntry.builder(Items.LEATHER_HORSE_ARMOR).weight(1));
                tableBuilder.pool(poolBuilder.build());
            }
            else if (path.matches(LootTables.VILLAGE_SHEPARD_CHEST.getValue().getPath())) {
                poolBuilder.rolls(UniformLootNumberProvider.create(1, 5))
                        .with(EmptyEntry.builder().weight(3))
                        .with(ItemEntry.builder(ModItems.SILK).weight(3))
                        .with(ItemEntry.builder(ModItems.SILK_HELMET).weight(2))
                        .with(ItemEntry.builder(ModItems.SILK_CHESTPLATE).weight(2))
                        .with(ItemEntry.builder(ModItems.SILK_LEGGINGS).weight(2))
                        .with(ItemEntry.builder(ModItems.SILK_BOOTS).weight(2))
                        .with(ItemEntry.builder(ModItems.WOOL_HELMET).weight(2))
                        .with(ItemEntry.builder(ModItems.WOOL_CHESTPLATE).weight(2))
                        .with(ItemEntry.builder(ModItems.WOOL_LEGGINGS).weight(2))
                        .with(ItemEntry.builder(ModItems.WOOL_BOOTS).weight(2));
                tableBuilder.pool(poolBuilder.build());
            }
        }
        else if (path.startsWith("entities")) {
            LootPool.Builder furPoolBuilder = LootPool.builder();
            if (path.matches(GOAT_ENTITY.getValue().getPath())) {
                furPoolBuilder.rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(Blocks.WHITE_WOOL)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))
                                .apply(EnchantedCountIncreaseLootFunction.builder(registries, UniformLootNumberProvider.create(0.0f, 1.0f))));
                tableBuilder.pool(furPoolBuilder.build());
            }
            else if (path.matches(PANDA_ENTITY.getValue().getPath())) {
                furPoolBuilder.rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModBlocks.BLACK_FUR)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))
                                .apply(EnchantedCountIncreaseLootFunction.builder(registries, UniformLootNumberProvider.create(0.0f, 1.0f))))
                        .with(ItemEntry.builder(ModBlocks.WHITE_FUR)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))
                                .apply(EnchantedCountIncreaseLootFunction.builder(registries, UniformLootNumberProvider.create(0.0f, 1.0f))));
                tableBuilder.pool(furPoolBuilder.build());
            }
            else if (path.matches(POLAR_BEAR_ENTITY.getValue().getPath())) {
                furPoolBuilder.rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModBlocks.WHITE_FUR)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f)))
                                .apply(EnchantedCountIncreaseLootFunction.builder(registries, UniformLootNumberProvider.create(0.0f, 1.0f))));
                tableBuilder.pool(furPoolBuilder.build());
            }
        }
    }

}

