package solipingen.armorrestitched.mixin.enchantment;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.SwiftSneakEnchantment;


@Mixin(SwiftSneakEnchantment.class)
public abstract class SwiftSneakEnchantmentMixin extends Enchantment {


    protected SwiftSneakEnchantmentMixin(Enchantment.Properties properties) {
        super(properties);
    }

    @ModifyVariable(method = "<init>", at = @At("HEAD"), index = 1)
    private static Enchantment.Properties modifiedProperties(Enchantment.Properties properties) {
        return Enchantment.properties(properties.supportedItems(), properties.weight(), properties.maxLevel(), Enchantment.leveledCost(10, 20), properties.maxCost(), properties.anvilCost(), properties.slots());
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
