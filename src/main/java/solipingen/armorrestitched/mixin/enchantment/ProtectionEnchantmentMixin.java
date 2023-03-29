package solipingen.armorrestitched.mixin.enchantment;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;


@Mixin(ProtectionEnchantment.class)
public abstract class ProtectionEnchantmentMixin extends Enchantment {


    protected ProtectionEnchantmentMixin(Rarity weight, EnchantmentTarget target, EquipmentSlot[] slotTypes) {
        super(weight, target, slotTypes);
    }
    
    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem || stack.isIn(ItemTags.TRIMMABLE_ARMOR) || super.isAcceptableItem(stack);
    }

    @ModifyConstant(method = "transformFireDuration", constant = @Constant(floatValue = 0.15f))
    private static float modifiedFireDurationFactor(float originalf) {
        return 0.2f;
    }

    @ModifyConstant(method = "transformExplosionKnockback", constant = @Constant(doubleValue = 0.15))
    private static double modifiedExplosionKnockbackFactor(double originald) {
        return 0.2;
    }
    


}
