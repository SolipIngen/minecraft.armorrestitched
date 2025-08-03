package solipingen.armorrestitched.item;

import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.minecraft.item.*;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.ArmorRestitched;
import solipingen.armorrestitched.block.ModBlocks;
import solipingen.armorrestitched.entity.ModEntityTypes;
import solipingen.armorrestitched.item.equipment.ModArmorMaterials;
import solipingen.armorrestitched.registry.tag.ModItemTags;

import java.util.function.Function;


public class ModItems {

    // Cotton Clothing
    public static final Item COTTON_HELMET = ModItems.registerItem("cotton_helmet", Item::new, (new Item.Settings()).armor(ModArmorMaterials.COTTON, EquipmentType.HELMET));
    public static final Item COTTON_CHESTPLATE = ModItems.registerItem("cotton_chestplate", Item::new, (new Item.Settings()).armor(ModArmorMaterials.COTTON, EquipmentType.CHESTPLATE));
    public static final Item COTTON_LEGGINGS = ModItems.registerItem("cotton_leggings", Item::new, (new Item.Settings()).armor(ModArmorMaterials.COTTON, EquipmentType.LEGGINGS));
    public static final Item COTTON_BOOTS = ModItems.registerItem("cotton_boots", Item::new, (new Item.Settings()).armor(ModArmorMaterials.COTTON, EquipmentType.BOOTS));

    // Fur Clothing
    public static final Item FUR_HELMET = ModItems.registerItem("fur_helmet", Item::new, (new Item.Settings()).armor(ModArmorMaterials.FUR, EquipmentType.HELMET));
    public static final Item FUR_CHESTPLATE = ModItems.registerItem("fur_chestplate", Item::new, (new Item.Settings()).armor(ModArmorMaterials.FUR, EquipmentType.CHESTPLATE));
    public static final Item FUR_LEGGINGS = ModItems.registerItem("fur_leggings", Item::new, (new Item.Settings()).armor(ModArmorMaterials.FUR, EquipmentType.LEGGINGS));
    public static final Item FUR_BOOTS = ModItems.registerItem("fur_boots", Item::new, (new Item.Settings()).armor(ModArmorMaterials.FUR, EquipmentType.BOOTS));

    // Linen Clothing
    public static final Item LINEN_HELMET = ModItems.registerItem("linen_helmet", Item::new, (new Item.Settings()).armor(ModArmorMaterials.LINEN, EquipmentType.HELMET));
    public static final Item LINEN_CHESTPLATE = ModItems.registerItem("linen_chestplate", Item::new, (new Item.Settings()).armor(ModArmorMaterials.LINEN, EquipmentType.CHESTPLATE));
    public static final Item LINEN_LEGGINGS = ModItems.registerItem("linen_leggings", Item::new, (new Item.Settings()).armor(ModArmorMaterials.LINEN, EquipmentType.LEGGINGS));
    public static final Item LINEN_BOOTS = ModItems.registerItem("linen_boots", Item::new, (new Item.Settings()).armor(ModArmorMaterials.LINEN, EquipmentType.BOOTS));

    // Silk Clothing
    public static final Item SILK_HELMET = ModItems.registerItem("silk_helmet", Item::new, (new Item.Settings()).armor(ModArmorMaterials.SILK, EquipmentType.HELMET));
    public static final Item SILK_CHESTPLATE = ModItems.registerItem("silk_chestplate", Item::new, (new Item.Settings()).armor(ModArmorMaterials.SILK, EquipmentType.CHESTPLATE));
    public static final Item SILK_LEGGINGS = ModItems.registerItem("silk_leggings", Item::new, (new Item.Settings()).armor(ModArmorMaterials.SILK, EquipmentType.LEGGINGS));
    public static final Item SILK_BOOTS = ModItems.registerItem("silk_boots", Item::new, (new Item.Settings()).armor(ModArmorMaterials.SILK, EquipmentType.BOOTS));

    // Wool Clothing
    public static final Item WOOL_HELMET = ModItems.registerItem("wool_helmet", Item::new, (new Item.Settings()).armor(ModArmorMaterials.WOOL, EquipmentType.HELMET));
    public static final Item WOOL_CHESTPLATE = ModItems.registerItem("wool_chestplate", Item::new, (new Item.Settings()).armor(ModArmorMaterials.WOOL, EquipmentType.CHESTPLATE));
    public static final Item WOOL_LEGGINGS = ModItems.registerItem("wool_leggings", Item::new, (new Item.Settings()).armor(ModArmorMaterials.WOOL, EquipmentType.LEGGINGS));
    public static final Item WOOL_BOOTS = ModItems.registerItem("wool_boots", Item::new, (new Item.Settings()).armor(ModArmorMaterials.WOOL, EquipmentType.BOOTS));

    // Paper Clothing
    public static final Item PAPER_HELMET = ModItems.registerItem("paper_helmet", Item::new, (new Item.Settings()).armor(ModArmorMaterials.PAPER, EquipmentType.HELMET));
    public static final Item PAPER_CHESTPLATE = ModItems.registerItem("paper_chestplate", Item::new, (new Item.Settings()).armor(ModArmorMaterials.PAPER, EquipmentType.CHESTPLATE));
    public static final Item PAPER_LEGGINGS = ModItems.registerItem("paper_leggings", Item::new, (new Item.Settings()).armor(ModArmorMaterials.PAPER, EquipmentType.LEGGINGS));
    public static final Item PAPER_BOOTS = ModItems.registerItem("paper_boots", Item::new, (new Item.Settings()).armor(ModArmorMaterials.PAPER, EquipmentType.BOOTS));

    // Copper Armor
    public static final Item COPPER_HELMET = ModItems.registerItem("copper_helmet", Item::new, (new Item.Settings()).armor(ModArmorMaterials.COPPER, EquipmentType.HELMET));
    public static final Item COPPER_CHESTPLATE = ModItems.registerItem("copper_chestplate", Item::new, (new Item.Settings()).armor(ModArmorMaterials.COPPER, EquipmentType.CHESTPLATE));
    public static final Item COPPER_LEGGINGS = ModItems.registerItem("copper_leggings", Item::new, (new Item.Settings()).armor(ModArmorMaterials.COPPER, EquipmentType.LEGGINGS));
    public static final Item COPPER_BOOTS = ModItems.registerItem("copper_boots", Item::new, (new Item.Settings()).armor(ModArmorMaterials.COPPER, EquipmentType.BOOTS));
    public static final Item COPPER_HORSE_ARMOR = ModItems.registerItem("copper_horse_armor", Item::new, (new Item.Settings()).horseArmor(ModArmorMaterials.COPPER));


    // Cotton & Flax
    public static final Item COTTON_SEEDS = ModItems.registerItem("cotton_seeds",
            settings -> new BlockItem(ModBlocks.COTTON_CROP, settings), new Item.Settings().useItemPrefixedTranslationKey());

    public static final Item COTTON = ModItems.registerItem("cotton", Item::new, new Item.Settings());

    public static final Item FLAX_STRAW = ModItems.registerItem("flax_straw", Item::new, new Item.Settings());

    public static final Item FLAXSEEDS = ModItems.registerItem("flaxseeds",
        settings -> new BlockItem(ModBlocks.FLAX_CROP, settings), new Item.Settings().food(ModFoodComponents.FLAXSEED).useItemPrefixedTranslationKey());

    public static final Item LINEN = ModItems.registerItem("linen", Item::new, new Item.Settings());

    // Silk & Silkworm
    public static final Item SILK = ModItems.registerItem("silk", Item::new,new Item.Settings());

    public static final Item SILKWORM_COCOON = ModItems.registerItem("silkworm_cocoon",
            SilkwormCocoonItem::new, new Item.Settings());
    
    public static final Item COOKED_SILKWORM_PUPA = ModItems.registerItem("cooked_silkworm_pupa", 
            Item::new, new Item.Settings().food(ModFoodComponents.COOKED_SILKWORM_PUPA));

    public static final Item SILK_MOTH_SPAWN_EGG = ModItems.registerItem("silk_moth_spawn_egg", 
        settings -> new SpawnEggItem(ModEntityTypes.SILK_MOTH_ENTITY, settings), new Item.Settings());


    // Registering Methods
    private static Item registerItem(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(ArmorRestitched.MOD_ID, name));
        Item item = itemFactory.apply(settings.registryKey(itemKey));
        return Registry.register(Registries.ITEM, itemKey, item);
    }

    public static void registerModItems() {
        FuelRegistryEvents.BUILD.register((builder, context) -> {
            builder.add(ModItemTags.COTTON_BLOCKS, 100);
            builder.add(ModItemTags.FUR_BLOCKS, 100);
            builder.add(ModItemTags.LINEN_BLOCKS, 100);
            builder.add(ModItemTags.SILK_BLOCKS, 100);

            builder.add(ModItemTags.COTTON_CARPETS, 67);
            builder.add(ModItemTags.FUR_CARPETS, 67);
            builder.add(ModItemTags.LINEN_CARPETS, 67);
            builder.add(ModItemTags.SILK_CARPETS, 67);

            builder.add(ModBlocks.MULBERRY_SAPLING, 100);
        });

        CompostingChanceRegistry.INSTANCE.add(ModItems.COTTON_SEEDS, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.FLAXSEEDS, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.COTTON_FLOWER, 0.65f);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.FLAX_FLOWER, 0.65f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.COTTON, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.FLAX_STRAW, 0.65f);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.FLAX_BLOCK, 0.85f);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.MULBERRY_SAPLING, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.MULBERRY_LEAVES, 0.3f);

        ArmorRestitched.LOGGER.debug("Registering Mod Items for " + ArmorRestitched.MOD_ID);
    }
    
    
}
