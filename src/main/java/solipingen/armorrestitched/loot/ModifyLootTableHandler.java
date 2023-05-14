package solipingen.armorrestitched.loot;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableSource;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.EmptyEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.EnchantRandomlyLootFunction;
import net.minecraft.loot.function.LootingEnchantLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.block.ModBlocks;
import solipingen.armorrestitched.enchantment.ModEnchantments;
import solipingen.armorrestitched.item.ModItems;


public class ModifyLootTableHandler implements LootTableEvents.Modify {

    private static final Identifier DESERT_PYRAMID_ID = new Identifier("chests/desert_pyramid");
    private static final Identifier END_CITY_TREASURE_ID = new Identifier("chests/end_city_treasure");
    private static final Identifier JUNGLE_TEMPLE_ID = new Identifier("chests/jungle_temple");
    private static final Identifier UNDERWATER_RUIN_SMALL_ID = new Identifier("chests/underwater_ruin_small");
    private static final Identifier UNDERWATER_RUIN_BIG_ID = new Identifier("chests/underwater_ruin_big");
    private static final Identifier SHIPWRECK_SUPPLY_ID = new Identifier("chests/shipwreck_supply");
    private static final Identifier SHIPWRECK_TREASURE_ID = new Identifier("chests/shipwreck_treasure");
    private static final Identifier GOAT_ENTITY_ID = new Identifier("entities/goat");
    private static final Identifier PANDA_ENTITY_ID = new Identifier("entities/panda");
    private static final Identifier POLAR_BEAR_ENTITY_ID = new Identifier("entities/polar_bear");
    private static final Identifier WOLF_ENTITY_ID = new Identifier("entities/wolf");
    private static final Identifier[] ID_ARRAY = new Identifier[]{DESERT_PYRAMID_ID, END_CITY_TREASURE_ID, JUNGLE_TEMPLE_ID, UNDERWATER_RUIN_SMALL_ID, UNDERWATER_RUIN_BIG_ID, SHIPWRECK_SUPPLY_ID, SHIPWRECK_TREASURE_ID, 
        GOAT_ENTITY_ID, PANDA_ENTITY_ID, POLAR_BEAR_ENTITY_ID, WOLF_ENTITY_ID};


    @Override
    public void modifyLootTable(ResourceManager resourceManager, LootManager lootManager, Identifier id, LootTable.Builder tableBuilder, LootTableSource source) {
        for (Identifier identifier : ID_ARRAY) {
            if (identifier.getPath().matches(id.getPath())) {
                if (identifier.getPath().startsWith("chests")) {
                    LootPool.Builder poolBuilder = LootPool.builder();
                    if (identifier.getPath().matches(END_CITY_TREASURE_ID.getPath())) {
                        poolBuilder.rolls(ConstantLootNumberProvider.create(1))
                            .with(EmptyEntry.builder().weight(10))
                            .with(ItemEntry.builder(Items.BOOK).weight(3))
                            .apply(new EnchantRandomlyLootFunction.Builder().add(ModEnchantments.SOARING));
                        tableBuilder.pool(poolBuilder.build());
                    }
                    else if (identifier.getPath().startsWith("chests/underwater_ruin")) {
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
                            .apply(EnchantRandomlyLootFunction.builder());
                        tableBuilder.pool(poolBuilder.build());
                    }
                    else if (identifier.getPath().matches(SHIPWRECK_SUPPLY_ID.getPath())) {
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
                            .apply(EnchantRandomlyLootFunction.builder());
                        tableBuilder.pool(poolBuilder.build());
                    }
                    else if (identifier.getPath().matches(SHIPWRECK_TREASURE_ID.getPath())) {
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
                            .apply(EnchantRandomlyLootFunction.builder());
                        tableBuilder.pool(poolBuilder.build());
                    }
                }
                else if (identifier.getPath().startsWith("entities")) {
                    LootPool.Builder furPoolBuilder = LootPool.builder();
                    if (identifier.getPath().matches(GOAT_ENTITY_ID.getPath())) {
                        furPoolBuilder.rolls(ConstantLootNumberProvider.create(1))
                            .with(ItemEntry.builder(Blocks.WHITE_WOOL)
                            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))
                            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))));
                        tableBuilder.pool(furPoolBuilder.build());
                    }
                    else if (identifier.getPath().matches(PANDA_ENTITY_ID.getPath())) {
                        furPoolBuilder.rolls(ConstantLootNumberProvider.create(1))
                            .with(ItemEntry.builder(ModBlocks.BLACK_FUR)
                            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))
                            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))
                            .with(ItemEntry.builder(ModBlocks.WHITE_FUR)
                            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))
                            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))));
                        tableBuilder.pool(furPoolBuilder.build());
                    }
                    else if (identifier.getPath().matches(POLAR_BEAR_ENTITY_ID.getPath())) {
                        furPoolBuilder.rolls(ConstantLootNumberProvider.create(1))
                            .with(ItemEntry.builder(ModBlocks.WHITE_FUR)
                            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f)))
                            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))));
                        tableBuilder.pool(furPoolBuilder.build());
                    }
                    else if (identifier.getPath().matches(WOLF_ENTITY_ID.getPath())) {
                        furPoolBuilder.rolls(ConstantLootNumberProvider.create(1))
                            .with(ItemEntry.builder(ModBlocks.WHITE_FUR)
                            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))
                            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))));
                        tableBuilder.pool(furPoolBuilder.build());
                    }
                }
                if (source == LootTableSource.REPLACED || source == LootTableSource.MOD) {
                    LootPool.Builder trimPoolBuilder = LootPool.builder();
                    if (identifier.getPath().matches(DESERT_PYRAMID_ID.getPath())) {
                        trimPoolBuilder.rolls(ConstantLootNumberProvider.create(1))
                            .with(EmptyEntry.builder().weight(6))
                            .with(ItemEntry.builder(Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE).weight(1)
                            .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0f))));
                        tableBuilder.pool(trimPoolBuilder.build());
                    }
                    else if (identifier.getPath().matches(JUNGLE_TEMPLE_ID.getPath())) {
                        trimPoolBuilder.rolls(ConstantLootNumberProvider.create(1))
                            .with(EmptyEntry.builder().weight(2))
                            .with(ItemEntry.builder(Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE).weight(1))
                            .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0f)));
                        tableBuilder.pool(trimPoolBuilder.build());
                    }
                }
            }
        }
    }
}

