package solipingen.armorrestitched.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.ArmorRestitched;


public class ModEnchantments {

    public static final RegistryKey<Enchantment> IMPACT_PROTECTION = ModEnchantments.enchantmentKeyOf("impact_protection");
    public static final RegistryKey<Enchantment> MAGIC_PROTECTION = ModEnchantments.enchantmentKeyOf("magic_protection");
    public static final RegistryKey<Enchantment> STRIKE_PROTECTION = ModEnchantments.enchantmentKeyOf("strike_protection");
    public static final RegistryKey<Enchantment> SOARING = ModEnchantments.enchantmentKeyOf("soaring");
    

    private static RegistryKey<Enchantment> enchantmentKeyOf(String name) {
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(ArmorRestitched.MOD_ID, name));
    } 

    public static void registerModEnchantments() {
        ArmorRestitched.LOGGER.debug("Registering Mod Enchantments for " + ArmorRestitched.MOD_ID);
    }


}
