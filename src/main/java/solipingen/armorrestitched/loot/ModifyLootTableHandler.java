package solipingen.armorrestitched.loot;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableSource;
import net.minecraft.item.Items;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.EmptyEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.block.ModBlocks;


public class ModifyLootTableHandler implements LootTableEvents.Modify {

    private static final Identifier DESERT_PYRAMID_ID = new Identifier("chests/desert_pyramid");
    private static final Identifier JUNGLE_TEMPLE_ID = new Identifier("chests/jungle_temple");
    private static final Identifier PANDA_ENTITY_ID = new Identifier("entities/panda");
    private static final Identifier POLAR_BEAR_ENTITY_ID = new Identifier("entities/polar_bear");
    private static final Identifier WOLF_ENTITY_ID = new Identifier("entities/wolf");
    private static final Identifier[] ID_ARRAY = new Identifier[]{DESERT_PYRAMID_ID, JUNGLE_TEMPLE_ID, 
        PANDA_ENTITY_ID, POLAR_BEAR_ENTITY_ID, WOLF_ENTITY_ID};


    @Override
    public void modifyLootTable(ResourceManager resourceManager, LootManager lootManager, Identifier id, LootTable.Builder tableBuilder, LootTableSource source) {
        for (Identifier identifier : ID_ARRAY) {
            if (id.getPath().matches(identifier.getPath()) && id.getPath().startsWith("entities")) {
                LootPool.Builder furPoolBuilder = LootPool.builder();
                if (id.getPath().matches(PANDA_ENTITY_ID.getPath())) {
                    furPoolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0f))
                        .with(ItemEntry.builder(ModBlocks.BLACK_FUR)
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))
                        .with(ItemEntry.builder(ModBlocks.WHITE_FUR)
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))));
                }
                else if (id.getPath().matches(POLAR_BEAR_ENTITY_ID.getPath())) {
                    furPoolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1.0f))
                    .with(ItemEntry.builder(ModBlocks.WHITE_FUR)
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))));
                }
                else if (id.getPath().matches(WOLF_ENTITY_ID.getPath())) {
                    furPoolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1.0f))
                    .with(ItemEntry.builder(ModBlocks.WHITE_FUR)
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))));
                }
                tableBuilder.pool(furPoolBuilder.build());
            }
            if (id.getPath().matches(identifier.getPath()) && (source == LootTableSource.REPLACED || source == LootTableSource.MOD)) {
                LootPool.Builder trimPoolBuilder = LootPool.builder();
                if (id.getPath().matches(DESERT_PYRAMID_ID.getPath())) {
                    trimPoolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0f))
                        .with(EmptyEntry.builder().weight(6))
                        .with(ItemEntry.builder(Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE).weight(1)
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0f))));
                }
                else if (id.getPath().matches(JUNGLE_TEMPLE_ID.getPath())) {
                    trimPoolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0f))
                        .with(EmptyEntry.builder().weight(2))
                        .with(ItemEntry.builder(Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE).weight(1))
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0f)));
                }
                tableBuilder.pool(trimPoolBuilder.build());
            }
        }
    }
}

