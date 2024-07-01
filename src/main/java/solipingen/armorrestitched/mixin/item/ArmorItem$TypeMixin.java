package solipingen.armorrestitched.mixin.item;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(ArmorItem.Type.class)
public abstract class ArmorItem$TypeMixin {
    @Shadow @Final private int baseMaxDamage;


    @Inject(method = "getMaxDamage", at = @At("HEAD"), cancellable = true)
    private void injectedGetMaxDamage(int multiplier, CallbackInfoReturnable<Integer> cbireturn) {
        if (((ArmorItem.Type)(Object)this).getEquipmentSlot() == EquipmentSlot.HEAD) {
            cbireturn.setReturnValue(MathHelper.ceil(1.5f*14*multiplier));
        }
        else {
            cbireturn.setReturnValue(MathHelper.ceil(1.5f*this.baseMaxDamage*multiplier));
        }
    }


}
