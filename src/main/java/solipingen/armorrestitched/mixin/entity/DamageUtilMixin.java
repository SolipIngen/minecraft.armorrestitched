package solipingen.armorrestitched.mixin.entity;


import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.DamageUtil;
import net.minecraft.util.math.MathHelper;


@Mixin(DamageUtil.class)
public abstract class DamageUtilMixin {
    

    @Inject(method = "getDamageLeft", at = @At("HEAD"), cancellable = true)
    private static void injectedGetDamageLeft(LivingEntity armorWearer, float damageAmount, DamageSource damageSource, float armor, float armorToughness, CallbackInfoReturnable<Float> cbireturn) {
        float i;
        float f = 1.0f + armorToughness/4.0f;
        float g = MathHelper.clamp(2.0f*armor - damageAmount/f, 0.1f*armor, 40.0f);
        float h = g/40.0f;
        ItemStack itemStack = damageSource.getWeaponStack();
        label12: {
            if (itemStack != null) {
                World var11 = armorWearer.getWorld();
                if (var11 instanceof ServerWorld serverWorld) {
                    i = MathHelper.clamp(EnchantmentHelper.getArmorEffectiveness(serverWorld, itemStack, armorWearer, damageSource, h), 0.0f, 1.0f);
                    break label12;
                }
            }
            i = h;
        }
        cbireturn.setReturnValue(damageAmount*(1.0f - i));
    }

    @Inject(method = "getInflictedDamage", at = @At("HEAD"), cancellable = true)
    private static void injectedGetInflictedDamage(float damageDealt, float protection, CallbackInfoReturnable<Float> cbireturn) {
        float f = (float)(68.0/Math.log(16 + 1)*Math.log(protection + 1));
        cbireturn.setReturnValue(damageDealt*(1.0f - f/100.0f));
    }


}

