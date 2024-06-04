package solipingen.armorrestitched.mixin.entity;


import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.DamageUtil;
import net.minecraft.util.math.MathHelper;


@Mixin(DamageUtil.class)
public abstract class DamageUtilMixin {
    

    @Inject(method = "getDamageLeft", at = @At("HEAD"), cancellable = true)
    private static void injectedGetDamageLeft(float damage, DamageSource source, float armor, float armorToughness, CallbackInfoReturnable<Float> cbireturn) {
        float f = 1.0f + armorToughness/4.0f;
        float g = MathHelper.clamp(2.0f*armor - damage/f, 0.1f*armor, 40.0f);
        float h = EnchantmentHelper.getBreachFactor(source.getAttacker(), g/40.0f);
        cbireturn.setReturnValue(damage*(1.0f - h));
    }

    @Inject(method = "getInflictedDamage", at = @At("HEAD"), cancellable = true)
    private static void injectedGetInflictedDamage(float damageDealt, float protection, CallbackInfoReturnable<Float> cbireturn) {
        float f = MathHelper.clamp(1.8f*protection, 0.0f, 40.0f);
        cbireturn.setReturnValue(damageDealt*(1.0f - f/40.0f));
    }


}

