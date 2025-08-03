package solipingen.armorrestitched.mixin.screen;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.equipment.trim.ArmorTrim;
import net.minecraft.item.equipment.trim.ArmorTrimMaterials;
import net.minecraft.registry.DynamicRegistryManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.math.MathHelper;


@Mixin(EnchantmentScreenHandler.class)
public abstract class EnchantmentScreenHandlerMixin extends ScreenHandler {
    private PlayerInventory playerInventory;


    protected EnchantmentScreenHandlerMixin(ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }

    @Inject(method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/screen/ScreenHandlerContext;)V", at = @At("TAIL"))
    private void injectedInit(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context, CallbackInfo cbi) {
        this.playerInventory = playerInventory;
    }

    @ModifyVariable(method = "generateEnchantments", at = @At("HEAD"), index = 3)
    private int modifiedEnchantability(int level, DynamicRegistryManager registryManager, ItemStack stack, int slot) {
        int modifiedLevel = level;
        ArmorTrim stackTrim = stack.get(DataComponentTypes.TRIM);
        if (stackTrim != null && stackTrim.material().matchesKey(ArmorTrimMaterials.GOLD)) {
            modifiedLevel = MathHelper.ceil(2.0f*modifiedLevel);
        }
        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            ItemStack armorStack = this.playerInventory.getStack(equipmentSlot.getIndex());
            ArmorTrim trim = armorStack.get(DataComponentTypes.TRIM);
            if (trim == null) continue;
            if (trim.material().matchesKey(ArmorTrimMaterials.LAPIS)) {
                modifiedLevel = MathHelper.floor(1.2f*modifiedLevel);
            }
        }
        return modifiedLevel;
    }


}
