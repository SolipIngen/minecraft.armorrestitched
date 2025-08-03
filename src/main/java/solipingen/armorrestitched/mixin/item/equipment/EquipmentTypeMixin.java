package solipingen.armorrestitched.mixin.item.equipment;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(EquipmentType.class)
public abstract class EquipmentTypeMixin {
    @Shadow @Final private int baseMaxDamage;


    @Inject(method = "getMaxDamage", at = @At("HEAD"), cancellable = true)
    private void injectedGetMaxDamage(int multiplier, CallbackInfoReturnable<Integer> cbireturn) {
        if (((EquipmentType)(Object)this).getEquipmentSlot() == EquipmentSlot.HEAD) {
            cbireturn.setReturnValue(MathHelper.ceil(1.5f*14*multiplier));
        }
        else {
            cbireturn.setReturnValue(MathHelper.ceil(1.5f*this.baseMaxDamage*multiplier));
        }
    }


}
