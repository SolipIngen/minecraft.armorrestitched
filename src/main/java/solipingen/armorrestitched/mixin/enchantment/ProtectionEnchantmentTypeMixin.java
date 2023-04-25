package solipingen.armorrestitched.mixin.enchantment;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.enchantment.ProtectionEnchantment;


@Mixin(ProtectionEnchantment.Type.class)
public abstract class ProtectionEnchantmentTypeMixin {


    @Inject(method = "getBasePower", at = @At("HEAD"), cancellable = true)
    private void injectedGetBasePower(CallbackInfoReturnable<Integer> cbireturn) {
        if (((ProtectionEnchantment.Type)(Object)this) == ProtectionEnchantment.Type.PROJECTILE) {
            cbireturn.setReturnValue(5);
        }
    }

    @Inject(method = "getPowerPerLevel", at = @At("HEAD"), cancellable = true)
    private void injectedGetPowerPerLevel(CallbackInfoReturnable<Integer> cbireturn) {
        if (((ProtectionEnchantment.Type)(Object)this) == ProtectionEnchantment.Type.PROJECTILE) {
            cbireturn.setReturnValue(8);
        }
    }
    


}
