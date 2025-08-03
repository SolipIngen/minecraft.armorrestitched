package solipingen.armorrestitched.mixin.screen;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.equipment.trim.ArmorTrim;
import net.minecraft.item.equipment.trim.ArmorTrimMaterials;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.ForgingSlotsManager;
import net.minecraft.util.StringHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {
    @Shadow @Final private Property levelCost;
    @Shadow @Nullable private String newItemName;


    public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context, ForgingSlotsManager forgingSlotsManager) {
        super(type, syncId, playerInventory, context, forgingSlotsManager);
    }

    @Inject(method = "canTakeOutput", at = @At("HEAD"), cancellable = true)
    private void injectedCanTakeOutput(PlayerEntity player, boolean present, CallbackInfoReturnable<Boolean> cbireturn) {
        cbireturn.setReturnValue(player.isInCreativeMode() || player.experienceLevel >= this.levelCost.get());
    }

    // Removes renaming cost, adds gold trim discount.
    @ModifyArg(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;clamp(JJJ)J"), index = 0)
    private long modifiedLevelCost(long originalL) {
        long t = originalL;
        ItemStack itemStack = this.input.getStack(0);
        ArmorTrim trim = itemStack.get(DataComponentTypes.TRIM);
        if (trim != null && trim.material().matchesKey(ArmorTrimMaterials.GOLD)) {
            t = Math.round(2.0f/3.0f*t);
        }
        if (this.newItemName == null || StringHelper.isBlank(this.newItemName)) {
            if (itemStack.contains(DataComponentTypes.CUSTOM_NAME)) {
                t -= 1;
            }
        }
        else if (!this.newItemName.equals(itemStack.getName().getString())) {
            t -= 1;
        }
        return t;
    }

    // Resets cost if item was only renamed.
    @Inject(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/AnvilScreenHandler;sendContentUpdates()V"))
    private void injectedUpdateResult(CallbackInfo cbi) {
        ItemStack itemStack = this.input.getStack(0).copy();
        ItemStack itemStack2 = this.output.getStack(0).copy();
        if (itemStack.get(DataComponentTypes.CUSTOM_NAME) != null) {
            itemStack.remove(DataComponentTypes.CUSTOM_NAME);
        }
        if (itemStack2.get(DataComponentTypes.CUSTOM_NAME) != null) {
            itemStack2.remove(DataComponentTypes.CUSTOM_NAME);
        }
        if (ItemStack.areItemsAndComponentsEqual(itemStack, itemStack2)) {
            this.levelCost.set(0);
        }
    }


    
}
