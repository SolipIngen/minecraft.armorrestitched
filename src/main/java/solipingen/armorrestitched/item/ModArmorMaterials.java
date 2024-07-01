package solipingen.armorrestitched.item;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import solipingen.armorrestitched.ArmorRestitched;
import solipingen.armorrestitched.registry.tag.ModItemTags;
import solipingen.armorrestitched.sound.ModSoundEvents;


public class ModArmorMaterials {

    public static final RegistryEntry<ArmorMaterial> COTTON = ModArmorMaterials.register("cotton", Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 1);
        map.put(ArmorItem.Type.LEGGINGS, 3);
        map.put(ArmorItem.Type.CHESTPLATE, 3);
        map.put(ArmorItem.Type.HELMET, 1);
        map.put(ArmorItem.Type.BODY, 3);
    }), 15, ModSoundEvents.COTTON_CLOTHING_EQUIP, 0.0f, 0.0f, () -> Ingredient.ofItems(ModItems.COTTON),
            List.of(new ArmorMaterial.Layer(Identifier.of(ArmorRestitched.MOD_ID, "cotton"), "", true), new ArmorMaterial.Layer(Identifier.of(ArmorRestitched.MOD_ID, "cotton"), "_overlay", false)));
    public static final RegistryEntry<ArmorMaterial> FUR = ModArmorMaterials.register("fur", Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 1);
        map.put(ArmorItem.Type.LEGGINGS, 3);
        map.put(ArmorItem.Type.CHESTPLATE, 3);
        map.put(ArmorItem.Type.HELMET, 1);
        map.put(ArmorItem.Type.BODY, 3);
    }), 15, ModSoundEvents.FUR_CLOTHING_EQUIP, 0.0f, 0.0f, () -> Ingredient.fromTag(ModItemTags.FUR_BLOCKS),
            List.of(new ArmorMaterial.Layer(Identifier.of(ArmorRestitched.MOD_ID, "fur"), "", true), new ArmorMaterial.Layer(Identifier.of(ArmorRestitched.MOD_ID, "fur"), "_overlay", false)));
    public static final RegistryEntry<ArmorMaterial> LINEN = ModArmorMaterials.register("linen", Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 1);
        map.put(ArmorItem.Type.LEGGINGS, 3);
        map.put(ArmorItem.Type.CHESTPLATE, 3);
        map.put(ArmorItem.Type.HELMET, 1);
        map.put(ArmorItem.Type.BODY, 3);
    }), 15, ModSoundEvents.LINEN_CLOTHING_EQUIP, 0.0f, 0.0f, () -> Ingredient.ofItems(ModItems.LINEN),
            List.of(new ArmorMaterial.Layer(Identifier.of(ArmorRestitched.MOD_ID, "linen"), "", true), new ArmorMaterial.Layer(Identifier.of(ArmorRestitched.MOD_ID, "linen"), "_overlay", false)));
    public static final RegistryEntry<ArmorMaterial> SILK = ModArmorMaterials.register("silk", Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 1);
        map.put(ArmorItem.Type.LEGGINGS, 3);
        map.put(ArmorItem.Type.CHESTPLATE, 3);
        map.put(ArmorItem.Type.HELMET, 1);
        map.put(ArmorItem.Type.BODY, 3);
    }), 15, ModSoundEvents.SILK_CLOTHING_EQUIP, 0.0f, 0.0f, () -> Ingredient.fromTag(ModItemTags.SILK_BLOCKS),
            List.of(new ArmorMaterial.Layer(Identifier.of(ArmorRestitched.MOD_ID, "linen"), "", true), new ArmorMaterial.Layer(Identifier.of(ArmorRestitched.MOD_ID, "linen"), "_overlay", false)));
    public static final RegistryEntry<ArmorMaterial> WOOL = ModArmorMaterials.register("wool", Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 1);
        map.put(ArmorItem.Type.LEGGINGS, 3);
        map.put(ArmorItem.Type.CHESTPLATE, 3);
        map.put(ArmorItem.Type.HELMET, 1);
        map.put(ArmorItem.Type.BODY, 3);
    }), 15, ModSoundEvents.WOOL_CLOTHING_EQUIP, 0.0f, 0.0f, () -> Ingredient.fromTag(ItemTags.WOOL),
            List.of(new ArmorMaterial.Layer(Identifier.of(ArmorRestitched.MOD_ID, "wool"), "", true), new ArmorMaterial.Layer(Identifier.of(ArmorRestitched.MOD_ID, "wool"), "_overlay", false)));
    public static final RegistryEntry<ArmorMaterial> PAPER = ModArmorMaterials.register("paper", Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 1);
        map.put(ArmorItem.Type.LEGGINGS, 3);
        map.put(ArmorItem.Type.CHESTPLATE, 3);
        map.put(ArmorItem.Type.HELMET, 1);
        map.put(ArmorItem.Type.BODY, 3);
    }), 15, ModSoundEvents.PAPER_CLOTHING_EQUIP, 0.0f, 0.0f, () -> Ingredient.ofItems(Items.PAPER),
            List.of(new ArmorMaterial.Layer(Identifier.of(ArmorRestitched.MOD_ID, "paper"), "", true), new ArmorMaterial.Layer(Identifier.of(ArmorRestitched.MOD_ID, "paper"), "_overlay", false)));
    public static final RegistryEntry<ArmorMaterial> COPPER = ModArmorMaterials.register("copper", Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 2);
        map.put(ArmorItem.Type.LEGGINGS, 4);
        map.put(ArmorItem.Type.CHESTPLATE, 4);
        map.put(ArmorItem.Type.HELMET, 2);
        map.put(ArmorItem.Type.BODY, 7);
    }), 12, ModSoundEvents.COPPER_ARMOR_EQUIP, 1.0f, 0.0f, () -> Ingredient.ofItems(Items.COPPER_INGOT));


    public static RegistryEntry<ArmorMaterial> getDefault(Registry<ArmorMaterial> registry) {
        return ArmorMaterials.LEATHER;
    }

    private static RegistryEntry<ArmorMaterial> register(String id, EnumMap<ArmorItem.Type, Integer> defense, int enchantability, RegistryEntry<SoundEvent> equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        List<ArmorMaterial.Layer> list = List.of(new ArmorMaterial.Layer(Identifier.of(ArmorRestitched.MOD_ID, id)));
        return ModArmorMaterials.register(id, defense, enchantability, equipSound, toughness, knockbackResistance, repairIngredient, list);
    }

    private static RegistryEntry<ArmorMaterial> register(String id, EnumMap<ArmorItem.Type, Integer> defense, int enchantability, RegistryEntry<SoundEvent> equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient, List<ArmorMaterial.Layer> layers) {
        EnumMap<ArmorItem.Type, Integer> enumMap = new EnumMap<ArmorItem.Type, Integer>(ArmorItem.Type.class);
        for (ArmorItem.Type type : ArmorItem.Type.values()) {
            enumMap.put(type, defense.get(type));
        }
        return Registry.registerReference(Registries.ARMOR_MATERIAL, Identifier.of(ArmorRestitched.MOD_ID, id),
                new ArmorMaterial(enumMap, enchantability, equipSound, repairIngredient, layers, toughness, knockbackResistance));
    }


}
