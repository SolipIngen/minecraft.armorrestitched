package solipingen.armorrestitched.mixin.screen;

import java.util.Optional;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.item.ItemStack;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.screen.GrindstoneScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;


@Mixin(GrindstoneScreenHandler.class)
public abstract class GrindstoneScreenHandlerMixin extends ScreenHandler {
    @Shadow @Final private ScreenHandlerContext context;
    @Nullable private final World contextWorld;


    protected GrindstoneScreenHandlerMixin(ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
        this.contextWorld = this.context.get((world, blockPos) -> {
            return world;
        }).get();
    }

    @Inject(method = "grind", at = @At("HEAD"), cancellable = true)
    private void injectedGrind(ItemStack item, int damage, int amount, CallbackInfoReturnable<ItemStack> cbireturn) {
        if (this.contextWorld != null && this.contextWorld instanceof ServerWorld) {
            if (!item.hasEnchantments()) {
                ItemStack itemStack = item.copy();
                Optional<ArmorTrim> trimOptional = ArmorTrim.getTrim(this.contextWorld.getRegistryManager(), itemStack);
                if (trimOptional.isPresent()) {
                    itemStack.removeSubNbt("Trim");
                    cbireturn.setReturnValue(itemStack);
                }
            }
        }
    }
    

    
}
