package solipingen.armorrestitched.mixin.enchantment;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.SwiftSneakEnchantment;
import net.minecraft.entity.EquipmentSlot;


@Mixin(SwiftSneakEnchantment.class)
public abstract class SwiftSneakEnchantmentMixin extends Enchantment {


    protected SwiftSneakEnchantmentMixin(Rarity weight, EnchantmentTarget target, EquipmentSlot[] slotTypes) {
        super(weight, target, slotTypes);
    }

    @ModifyConstant(method = "getMinPower", constant = @Constant(intValue = 25))
    private int modifiedMinPowerFactor(int originalI, int level) {
        return 10 + 20*(level - 1);
    }



    @Inject(method = "isAvailableForEnchantedBookOffer", at = @At("HEAD"), cancellable = true)
    private void injectedIsAvailableForEnchantedBookOffer(CallbackInfoReturnable<Boolean> cbireturn) {
        cbireturn.setReturnValue(true);
    }

    @Inject(method = "isAvailableForRandomSelection", at = @At("HEAD"), cancellable = true)
    private void injectedIsAvailableForRandomSelection(CallbackInfoReturnable<Boolean> cbireturn) {
        cbireturn.setReturnValue(true);
    }

    
}
