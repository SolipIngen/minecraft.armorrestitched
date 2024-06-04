package solipingen.armorrestitched.item;

import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import solipingen.armorrestitched.ArmorRestitched;
import solipingen.armorrestitched.block.ModBlocks;
import solipingen.armorrestitched.entity.ModEntityTypes;
import solipingen.armorrestitched.registry.tag.ModItemTags;


public class ModItems {

    // Cotton Clothing
    public static final Item COTTON_HELMET = ModItems.registerItem("cotton_helmet",
            new ArmorItem(ModArmorMaterials.COTTON, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(MathHelper.ceil(1.75f*ArmorItem.Type.HELMET.getMaxDamage(9)))));

    public static final Item COTTON_CHESTPLATE = ModItems.registerItem("cotton_chestplate",
            new ArmorItem(ModArmorMaterials.COTTON, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(MathHelper.ceil(1.75f*ArmorItem.Type.CHESTPLATE.getMaxDamage(9)))));

    public static final Item COTTON_LEGGINGS = ModItems.registerItem("cotton_leggings",
            new ArmorItem(ModArmorMaterials.COTTON, ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(MathHelper.ceil(1.75f*ArmorItem.Type.LEGGINGS.getMaxDamage(9)))));

    public static final Item COTTON_BOOTS = ModItems.registerItem("cotton_boots",
            new ArmorItem(ModArmorMaterials.COTTON, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(MathHelper.ceil(1.75f*ArmorItem.Type.BOOTS.getMaxDamage(9)))));


    // Fur Clothing
    public static final Item FUR_HELMET = ModItems.registerItem("fur_helmet",
            new ArmorItem(ModArmorMaterials.FUR, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(MathHelper.ceil(1.75f*ArmorItem.Type.HELMET.getMaxDamage(9)))));

    public static final Item FUR_CHESTPLATE = ModItems.registerItem("fur_chestplate",
            new ArmorItem(ModArmorMaterials.FUR, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(MathHelper.ceil(1.75f*ArmorItem.Type.CHESTPLATE.getMaxDamage(9)))));

    public static final Item FUR_LEGGINGS = ModItems.registerItem("fur_leggings",
            new ArmorItem(ModArmorMaterials.FUR, ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(MathHelper.ceil(1.75f*ArmorItem.Type.LEGGINGS.getMaxDamage(9)))));

    public static final Item FUR_BOOTS = ModItems.registerItem("fur_boots",
            new ArmorItem(ModArmorMaterials.FUR, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(MathHelper.ceil(1.75f*ArmorItem.Type.BOOTS.getMaxDamage(9)))));


    // Linen Clothing
    public static final Item LINEN_HELMET = ModItems.registerItem("linen_helmet",
            new ArmorItem(ModArmorMaterials.LINEN, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(MathHelper.ceil(1.75f*ArmorItem.Type.HELMET.getMaxDamage(9)))));

    public static final Item LINEN_CHESTPLATE = ModItems.registerItem("linen_chestplate",
            new ArmorItem(ModArmorMaterials.LINEN, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(MathHelper.ceil(1.75f*ArmorItem.Type.CHESTPLATE.getMaxDamage(9)))));

    public static final Item LINEN_LEGGINGS = ModItems.registerItem("linen_leggings",
            new ArmorItem(ModArmorMaterials.LINEN, ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(MathHelper.ceil(1.75f*ArmorItem.Type.LEGGINGS.getMaxDamage(9)))));

    public static final Item LINEN_BOOTS = ModItems.registerItem("linen_boots",
            new ArmorItem(ModArmorMaterials.LINEN, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(MathHelper.ceil(1.75f*ArmorItem.Type.BOOTS.getMaxDamage(9)))));


    // Silk Clothing
    public static final Item SILK_HELMET = ModItems.registerItem("silk_helmet",
            new ArmorItem(ModArmorMaterials.SILK, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(MathHelper.ceil(1.75f*ArmorItem.Type.HELMET.getMaxDamage(9)))));

    public static final Item SILK_CHESTPLATE = ModItems.registerItem("silk_chestplate",
            new ArmorItem(ModArmorMaterials.SILK, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(MathHelper.ceil(1.75f*ArmorItem.Type.CHESTPLATE.getMaxDamage(9)))));

    public static final Item SILK_LEGGINGS = ModItems.registerItem("silk_leggings",
            new ArmorItem(ModArmorMaterials.SILK, ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(MathHelper.ceil(1.75f*ArmorItem.Type.LEGGINGS.getMaxDamage(9)))));

    public static final Item SILK_BOOTS = ModItems.registerItem("silk_boots",
            new ArmorItem(ModArmorMaterials.SILK, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(MathHelper.ceil(1.75f*ArmorItem.Type.BOOTS.getMaxDamage(9)))));


    // Wool Clothing
    public static final Item WOOL_HELMET = ModItems.registerItem("wool_helmet",
            new ArmorItem(ModArmorMaterials.WOOL, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(MathHelper.ceil(1.75f*ArmorItem.Type.HELMET.getMaxDamage(9)))));

    public static final Item WOOL_CHESTPLATE = ModItems.registerItem("wool_chestplate",
            new ArmorItem(ModArmorMaterials.WOOL, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(MathHelper.ceil(1.75f*ArmorItem.Type.CHESTPLATE.getMaxDamage(9)))));

    public static final Item WOOL_LEGGINGS = ModItems.registerItem("wool_leggings",
            new ArmorItem(ModArmorMaterials.WOOL, ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(MathHelper.ceil(1.75f*ArmorItem.Type.LEGGINGS.getMaxDamage(9)))));

    public static final Item WOOL_BOOTS = ModItems.registerItem("wool_boots",
            new ArmorItem(ModArmorMaterials.WOOL, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(MathHelper.ceil(1.75f*ArmorItem.Type.BOOTS.getMaxDamage(9)))));

    // Paper Clothing
    public static final Item PAPER_HELMET = ModItems.registerItem("paper_helmet",
            new ArmorItem(ModArmorMaterials.PAPER, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(MathHelper.ceil(1.75f*ArmorItem.Type.HELMET.getMaxDamage(5)))));

    public static final Item PAPER_CHESTPLATE = ModItems.registerItem("paper_chestplate",
            new ArmorItem(ModArmorMaterials.PAPER, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(MathHelper.ceil(1.75f*ArmorItem.Type.CHESTPLATE.getMaxDamage(5)))));

    public static final Item PAPER_LEGGINGS = ModItems.registerItem("paper_leggings", 
        new ArmorItem(ModArmorMaterials.PAPER, ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(MathHelper.ceil(1.75f*ArmorItem.Type.LEGGINGS.getMaxDamage(5)))));

    public static final Item PAPER_BOOTS = ModItems.registerItem("paper_boots",
            new ArmorItem(ModArmorMaterials.PAPER, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(MathHelper.ceil(1.75f*ArmorItem.Type.BOOTS.getMaxDamage(5)))));


    // Copper Armor
    public static final Item COPPER_HELMET = ModItems.registerItem("copper_helmet", 
        new ArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(MathHelper.ceil(1.75f*ArmorItem.Type.HELMET.getMaxDamage(15)))));

    public static final Item COPPER_CHESTPLATE = ModItems.registerItem("copper_chestplate", 
        new ArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(MathHelper.ceil(1.75f*ArmorItem.Type.CHESTPLATE.getMaxDamage(15)))));

    public static final Item COPPER_LEGGINGS = ModItems.registerItem("copper_leggings", 
        new ArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(MathHelper.ceil(1.75f*ArmorItem.Type.LEGGINGS.getMaxDamage(15)))));

    public static final Item COPPER_BOOTS = ModItems.registerItem("copper_boots", 
        new ArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(MathHelper.ceil(1.75f*ArmorItem.Type.BOOTS.getMaxDamage(15)))));

    public static final Item COPPER_HORSE_ARMOR = ModItems.registerItem("copper_horse_armor", 
        new AnimalArmorItem(ModArmorMaterials.COPPER, AnimalArmorItem.Type.EQUESTRIAN, false, new Item.Settings().maxCount(1)));


    // Cotton & Flax
    public static final Item COTTON_SEEDS = ModItems.registerItem("cotton_seeds", 
        new AliasedBlockItem(ModBlocks.COTTON_CROP, new Item.Settings()));
    
    public static final Item COTTON = ModItems.registerItem("cotton", 
        new Item(new Item.Settings()));

    public static final Item FLAX_STRAW = ModItems.registerItem("flax_straw",
        new Item(new Item.Settings()));

    public static final Item FLAXSEED = ModItems.registerItem("flaxseed", 
        new AliasedBlockItem(ModBlocks.FLAX_CROP, new Item.Settings()));

    public static final Item LINEN = ModItems.registerItem("linen", 
        new Item(new Item.Settings()));

    // Silk
    public static final Item SILK = ModItems.registerItem("silk", 
        new Item(new Item.Settings()));

    public static final Item SILKWORM_COCOON = ModItems.registerItem("silkworm_cocoon", 
        new SilkwormCocoonItem(new Item.Settings()));
    
    public static final Item COOKED_SILKWORM_PUPA = ModItems.registerItem("cooked_silkworm_pupa", 
        new Item(new Item.Settings().food(ModFoodComponents.COOKED_SILKWORM_PUPA)));

    public static final Item SILK_MOTH_SPAWN_EGG = ModItems.registerItem("silk_moth_spawn_egg", 
        new SpawnEggItem(ModEntityTypes.SILK_MOTH_ENTITY, 0xf4ebd5, 0xddd0ad, new Item.Settings()));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(ArmorRestitched.MOD_ID, name), item);
    }

    public static void registerModItems() {
        FuelRegistry.INSTANCE.add(ModItemTags.COTTON_BLOCKS, 100);
        FuelRegistry.INSTANCE.add(ModItemTags.FUR_BLOCKS, 100);
        FuelRegistry.INSTANCE.add(ModItemTags.LINEN_BLOCKS, 100);
        FuelRegistry.INSTANCE.add(ModItemTags.SILK_BLOCKS, 100);

        FuelRegistry.INSTANCE.add(ModItemTags.COTTON_CARPETS, 67);
        FuelRegistry.INSTANCE.add(ModItemTags.FUR_CARPETS, 67);
        FuelRegistry.INSTANCE.add(ModItemTags.LINEN_CARPETS, 67);
        FuelRegistry.INSTANCE.add(ModItemTags.SILK_CARPETS, 67);

        FuelRegistry.INSTANCE.add(ModBlocks.MULBERRY_SAPLING, 100);

        CompostingChanceRegistry.INSTANCE.add(ModItems.COTTON_SEEDS, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.FLAXSEED, 0.3f);
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
