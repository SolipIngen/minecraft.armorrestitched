package solipingen.armorrestitched.mixin.client.gui.screen.ingame;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.ForgingScreen;
import net.minecraft.client.gui.screen.ingame.SmithingScreen;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.SmithingScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;


@Mixin(SmithingScreen.class)
@Environment(value=EnvType.CLIENT)
public abstract class SmithingScreenMixin extends ForgingScreen<SmithingScreenHandler> {


    public SmithingScreenMixin(SmithingScreenHandler handler, PlayerInventory playerInventory, Text title, Identifier texture) {
        super(handler, playerInventory, title, texture);
    }

    @Redirect(method = "equipArmorStand", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/EquipmentSlot;OFFHAND:Lnet/minecraft/entity/EquipmentSlot;", opcode = Opcodes.GETSTATIC))
    private EquipmentSlot redirectedArmorStandEquipmentSlot(ItemStack stack) {
        if (!stack.isEmpty() && stack.getItem() instanceof ElytraItem) {
            return EquipmentSlot.CHEST;
        }
        return EquipmentSlot.OFFHAND;
    }


    
}
