package solipingen.armorrestitched.mixin.screen;

import java.util.Optional;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.item.trim.ArmorTrimMaterials;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;


@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {
    @Nullable private final World contextWorld;


    public AnvilScreenHandlerMixin(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
        this.contextWorld = context.get((world, blockPos) -> {
            return world;
        }).get();
    }

    @Redirect(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;setRepairCost(I)V"))
    private void redirectedSetRepairCost(ItemStack itemStack, int originalRepairCost) {
        if (this.contextWorld != null && this.contextWorld instanceof ServerWorld) {
            Optional<ArmorTrim> trimOptional = ArmorTrim.getTrim(this.contextWorld.getRegistryManager(), itemStack);
            if (trimOptional.isPresent() && trimOptional.get().getMaterial().matchesKey(ArmorTrimMaterials.GOLD)) {
                int repairCost = MathHelper.floor(0.75f*originalRepairCost);
                itemStack.setRepairCost(repairCost);
            }
            else {
                itemStack.setRepairCost(originalRepairCost);
            }
        }
        else {
            itemStack.setRepairCost(originalRepairCost);
        }
    }


    
}
