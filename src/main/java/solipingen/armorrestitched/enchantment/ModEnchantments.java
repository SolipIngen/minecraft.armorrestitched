package solipingen.armorrestitched.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.ArmorRestitched;


public class ModEnchantments {
    
    public static final Enchantment IMPACT_PROTECTION = ModEnchantments.registerEnchantment("impact_protection", new ProtectionEnchantment(Enchantment.Rarity.UNCOMMON, ProtectionEnchantment.Type.FALL, EquipmentSlot.HEAD));
    public static final Enchantment SOARING = ModEnchantments.registerEnchantment("soaring", new SoaringEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.CHEST));
    

    private static Enchantment registerEnchantment(String name, Enchantment enchantment) {
        return Registry.register(Registries.ENCHANTMENT, new Identifier(ArmorRestitched.MOD_ID, name), enchantment);
    } 

    public static void registerModEnchantments() {
        ArmorRestitched.LOGGER.debug("Registering Mod Enchantments for " + ArmorRestitched.MOD_ID);
    }


}
