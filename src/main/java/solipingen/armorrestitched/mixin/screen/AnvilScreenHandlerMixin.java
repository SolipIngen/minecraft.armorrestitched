package solipingen.armorrestitched.mixin.screen;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.screen.*;
import net.minecraft.util.StringHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.item.trim.ArmorTrimMaterials;
import org.spongepowered.asm.mixin.injection.ModifyArg;


@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {
    @Shadow @Nullable private String newItemName;


    public AnvilScreenHandlerMixin(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }

    // Removes renaming cost, adds gold trim discount.
    @ModifyArg(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;clamp(JJJ)J"), index = 0)
    private long modifiedLevelCost(long originalL) {
        long t = originalL;
        ItemStack itemStack = this.input.getStack(0);
        ArmorTrim trim = itemStack.get(DataComponentTypes.TRIM);
        if (trim != null && trim.getMaterial().matchesKey(ArmorTrimMaterials.GOLD)) {
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


    
}
