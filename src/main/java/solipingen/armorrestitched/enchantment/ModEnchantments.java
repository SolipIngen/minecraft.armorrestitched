package solipingen.armorrestitched.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.ArmorRestitched;
import solipingen.armorrestitched.registry.tag.ModItemTags;


public class ModEnchantments {
    private static final EquipmentSlot[] ALL_ARMOR = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

    public static final Enchantment STRIKE_PROTECTION = ModEnchantments.registerEnchantment("strike_protection",
            new ProtectionEnchantment(Enchantment.properties(ItemTags.ARMOR_ENCHANTABLE, 2, 4,
                    Enchantment.leveledCost(5, 8), Enchantment.leveledCost(13, 8), 4, ALL_ARMOR), ProtectionEnchantment.Type.ALL));
    public static final Enchantment MAGIC_PROTECTION = ModEnchantments.registerEnchantment("magic_protection",
            new ProtectionEnchantment(Enchantment.properties(ItemTags.ARMOR_ENCHANTABLE, 2, 4,
                    Enchantment.leveledCost(5, 8), Enchantment.leveledCost(13, 8), 4, ALL_ARMOR), ProtectionEnchantment.Type.ALL));
    public static final Enchantment IMPACT_PROTECTION = ModEnchantments.registerEnchantment("impact_protection",
            new ProtectionEnchantment(Enchantment.properties(ItemTags.HEAD_ARMOR_ENCHANTABLE, 5, 4,
                    Enchantment.leveledCost(5, 6), Enchantment.leveledCost(11, 6), 2, ALL_ARMOR), ProtectionEnchantment.Type.FALL));
    public static final Enchantment SOARING = ModEnchantments.registerEnchantment("soaring",
            new SoaringEnchantment(Enchantment.properties(ModItemTags.ELYTRA_ENCHANTABLE, 2, 3,
                    Enchantment.leveledCost(10, 7), Enchantment.constantCost(50), 2, EquipmentSlot.CHEST)));
    

    private static Enchantment registerEnchantment(String name, Enchantment enchantment) {
        return Registry.register(Registries.ENCHANTMENT, new Identifier(ArmorRestitched.MOD_ID, name), enchantment);
    } 

    public static void registerModEnchantments() {
        ArmorRestitched.LOGGER.debug("Registering Mod Enchantments for " + ArmorRestitched.MOD_ID);
    }


}
