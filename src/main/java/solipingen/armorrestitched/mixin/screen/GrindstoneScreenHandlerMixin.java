package solipingen.armorrestitched.mixin.screen;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.trim.ArmorTrim;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import net.minecraft.item.ItemStack;
import net.minecraft.screen.GrindstoneScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(GrindstoneScreenHandler.class)
public abstract class GrindstoneScreenHandlerMixin extends ScreenHandler {


    protected GrindstoneScreenHandlerMixin(ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }

    @Inject(method = "getOutputStack", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;hasEnchantments(Lnet/minecraft/item/ItemStack;)Z"), cancellable = true)
    private void injectedGrind(ItemStack firstInput, ItemStack secondInput, CallbackInfoReturnable<ItemStack> cbireturn) {
        ItemStack itemStack = !firstInput.isEmpty() ? firstInput : secondInput;
        ArmorTrim trim = itemStack.get(DataComponentTypes.TRIM);
        if (!EnchantmentHelper.hasEnchantments(itemStack) && trim != null) {
            ItemStack itemStack2 = itemStack.copy();
            itemStack2.remove(DataComponentTypes.TRIM);
            cbireturn.setReturnValue(itemStack2);
        }
    }
    

    
}
