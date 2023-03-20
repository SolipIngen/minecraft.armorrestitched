package solipingen.armorrestitched.mixin.entity;


import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.DamageUtil;
import net.minecraft.util.math.MathHelper;


@Mixin(DamageUtil.class)
public abstract class DamageUtilMixin {
    

    @Inject(method = "getDamageLeft", at = @At("HEAD"), cancellable = true)
    private static void injectedGetDamageLeft(float damage, float armor, float armorToughness, CallbackInfoReturnable<Float> cbireturn) {
        float f = 1.0f + armorToughness/2.0f;
        float g = MathHelper.clamp(2.0f*armor - damage / f, armor*0.1f, 50.0f);
        cbireturn.setReturnValue(damage*(1.0f - g/50.0f));
    }

    @Inject(method = "getInflictedDamage", at = @At("HEAD"), cancellable = true)
    private static void injectedGetInflictedDamage(float damageDealt, float protection, CallbackInfoReturnable<Float> cbireturn) {
        float f = MathHelper.clamp(2.25f*protection, 0.0f, 47.5f);
        cbireturn.setReturnValue(damageDealt*(1.0f - f/50.0f));
    }



}

