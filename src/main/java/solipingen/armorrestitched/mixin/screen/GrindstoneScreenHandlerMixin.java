package solipingen.armorrestitched.mixin.screen;

import java.util.Optional;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.screen.GrindstoneScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.world.World;


@Mixin(GrindstoneScreenHandler.class)
public abstract class GrindstoneScreenHandlerMixin extends ScreenHandler {
    @Shadow @Final private Inventory result;
    @Shadow @Final Inventory input;
    @Final private World world;


    protected GrindstoneScreenHandlerMixin(ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }

    @Inject(method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/screen/ScreenHandlerContext;)V", at = @At("TAIL"))
    private void injectedInit(int syncId, PlayerInventory playerInventory, final ScreenHandlerContext context, CallbackInfo cbi) {
        this.world = playerInventory.player.world;
    }

    @Inject(method = "updateResult", at = @At("HEAD"), cancellable = true)
    private void injectedUpdateResult(CallbackInfo cbi) {
        ItemStack itemStack = this.input.getStack(0);
        ItemStack itemStack2 = this.input.getStack(1);
        if (this.world != null) {
            if ((!itemStack.isEmpty() && !itemStack.hasEnchantments() && itemStack2.isEmpty()) || (!itemStack2.isEmpty() && !itemStack2.hasEnchantments() && itemStack.isEmpty())) {
                ItemStack stack = itemStack2.isEmpty() ? itemStack.copy() : itemStack2.copy();
                Optional<ArmorTrim> trimOptional = ArmorTrim.getTrim(this.world.getRegistryManager(), stack);
                if (trimOptional.isPresent()) {
                    stack.removeSubNbt("Trim");
                    this.result.setStack(0, stack);
                    this.sendContentUpdates();
                    cbi.cancel();
                }
            }
        }
    }
    

    
}
