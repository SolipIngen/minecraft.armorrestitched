package solipingen.armorrestitched.item;

import java.util.EnumMap;
import java.util.function.Supplier;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Lazy;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.Util;
import solipingen.armorrestitched.registry.tag.ModItemTags;
import solipingen.armorrestitched.sound.ModSoundEvents;

@SuppressWarnings("deprecation")
public enum ModArmorMaterials implements ArmorMaterial, StringIdentifiable {
    COTTON("cotton", 5, Util.make(new EnumMap<ArmorItem.Type, Integer>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 1);
        map.put(ArmorItem.Type.LEGGINGS, 3);
        map.put(ArmorItem.Type.CHESTPLATE, 3);
        map.put(ArmorItem.Type.HELMET, 1);
    }), 15, ModSoundEvents.COTTON_CLOTHING_EQUIP, 0.0f, 0.0f, () -> Ingredient.fromTag(ModItemTags.COTTON)), 
    FUR("fur", 5, Util.make(new EnumMap<ArmorItem.Type, Integer>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 1);
        map.put(ArmorItem.Type.LEGGINGS, 3);
        map.put(ArmorItem.Type.CHESTPLATE, 3);
        map.put(ArmorItem.Type.HELMET, 1);
    }), 15, ModSoundEvents.FUR_CLOTHING_EQUIP, 0.0f, 0.0f, () -> Ingredient.fromTag(ModItemTags.FUR)), 
    LINEN("linen", 5, Util.make(new EnumMap<ArmorItem.Type, Integer>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 1);
        map.put(ArmorItem.Type.LEGGINGS, 3);
        map.put(ArmorItem.Type.CHESTPLATE, 3);
        map.put(ArmorItem.Type.HELMET, 1);
    }), 15, ModSoundEvents.LINEN_CLOTHING_EQUIP, 0.0f, 0.0f, () -> Ingredient.fromTag(ModItemTags.LINEN)), 
    SILK("silk", 5, Util.make(new EnumMap<ArmorItem.Type, Integer>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 1);
        map.put(ArmorItem.Type.LEGGINGS, 3);
        map.put(ArmorItem.Type.CHESTPLATE, 3);
        map.put(ArmorItem.Type.HELMET, 1);
    }), 15, ModSoundEvents.SILK_CLOTHING_EQUIP, 0.0f, 0.0f, () -> Ingredient.fromTag(ModItemTags.SILK)), 
    WOOL("wool", 5, Util.make(new EnumMap<ArmorItem.Type, Integer>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 1);
        map.put(ArmorItem.Type.LEGGINGS, 3);
        map.put(ArmorItem.Type.CHESTPLATE, 3);
        map.put(ArmorItem.Type.HELMET, 1);
    }), 15, ModSoundEvents.WOOL_CLOTHING_EQUIP, 0.0f, 0.0f, () -> Ingredient.fromTag(ItemTags.WOOL)), 
    PAPER("paper", 5, Util.make(new EnumMap<ArmorItem.Type, Integer>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 1);
        map.put(ArmorItem.Type.LEGGINGS, 3);
        map.put(ArmorItem.Type.CHESTPLATE, 3);
        map.put(ArmorItem.Type.HELMET, 1);
    }), 15, ModSoundEvents.PAPER_CLOTHING_EQUIP, 0.0f, 0.0f, () -> Ingredient.ofItems(Items.PAPER)), 
    COPPER("copper", 15, Util.make(new EnumMap<ArmorItem.Type, Integer>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 2);
        map.put(ArmorItem.Type.LEGGINGS, 4);
        map.put(ArmorItem.Type.CHESTPLATE, 4);
        map.put(ArmorItem.Type.HELMET, 2);
    }), 12, ModSoundEvents.COPPER_ARMOR_EQUIP, 1.0f, 0.0f, () -> Ingredient.ofItems(Items.COPPER_INGOT));

    public static final StringIdentifiable.Codec<ModArmorMaterials> CODEC;
    private static final EnumMap<ArmorItem.Type, Integer> BASE_DURABILITY;
    private final String name;
    private final int durabilityMultiplier;
    private final EnumMap<ArmorItem.Type, Integer> protectionAmounts;
    private final int enchantability;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Lazy<Ingredient> repairIngredientSupplier;
    

    private ModArmorMaterials(String name, int durabilityMultiplier, EnumMap<ArmorItem.Type, Integer> protectionAmounts, int enchantability, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredientSupplier) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredientSupplier = new Lazy<Ingredient>(repairIngredientSupplier);
    }

    @Override
    public int getDurability(ArmorItem.Type type) {
        return BASE_DURABILITY.get((Object)type) * this.durabilityMultiplier;
    }

    @Override
    public int getProtection(ArmorItem.Type type) {
        return this.protectionAmounts.get((Object)type);
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredientSupplier.get();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }

    @Override
    public String asString() {
        return this.name;
    }

    static {
        CODEC = StringIdentifiable.createCodec(ModArmorMaterials::values);
        BASE_DURABILITY = Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
            map.put(ArmorItem.Type.BOOTS, 20);
            map.put(ArmorItem.Type.LEGGINGS, 27);
            map.put(ArmorItem.Type.CHESTPLATE, 28);
            map.put(ArmorItem.Type.HELMET, 25);
        });
    }
}
