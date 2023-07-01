package solipingen.armorrestitched.mixin.enchantment;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.ThornsEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;


@Mixin(ThornsEnchantment.class)
public abstract class ThornsEnchantmentMixin extends Enchantment {


    protected ThornsEnchantmentMixin(Rarity weight, EnchantmentTarget target, EquipmentSlot[] slotTypes) {
        super(weight, target, slotTypes);
    }

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;<init>(Lnet/minecraft/enchantment/Enchantment$Rarity;Lnet/minecraft/enchantment/EnchantmentTarget;[Lnet/minecraft/entity/EquipmentSlot;)V"), index = 2)
    private static EquipmentSlot[] redirectedEquipmentSlots(EquipmentSlot... slotTypes) {
        return new EquipmentSlot[]{EquipmentSlot.CHEST};
    }

    @Inject(method = "isAcceptableItem", at = @At("RETURN"), cancellable = true)
    private void injectedIsAcceptableItem(ItemStack stack, CallbackInfoReturnable<Boolean> cbireturn) {
        if (stack.getItem() instanceof ArmorItem) {
            if (((ArmorItem)stack.getItem()).getType() == ArmorItem.Type.CHESTPLATE) {
                cbireturn.setReturnValue(true);
            }
            else {
                cbireturn.setReturnValue(false);
            }
        }
    }
    
    @Override
    public boolean isTreasure() {
        return true;
    }

    
}
