package solipingen.armorrestitched.mixin.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.EnchantmentTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {


    @Inject(method = "canBeCombined", at = @At("HEAD"), cancellable = true)
    private static void injectedCabBeCombined(RegistryEntry<Enchantment> first, RegistryEntry<Enchantment> second, CallbackInfoReturnable<Boolean> cbireturn) {
        if ((first.matchesKey(Enchantments.PROTECTION) && second.isIn(EnchantmentTags.ARMOR_EXCLUSIVE_SET))
                || (first.isIn(EnchantmentTags.ARMOR_EXCLUSIVE_SET) && second.matchesKey(Enchantments.PROTECTION))) {
            cbireturn.setReturnValue(!first.equals(second));
        }
        else if ((first.matchesKey(Enchantments.BREACH) && second.isIn(EnchantmentTags.DAMAGE_EXCLUSIVE_SET))
                || (first.isIn(EnchantmentTags.DAMAGE_EXCLUSIVE_SET) && second.matchesKey(Enchantments.BREACH))) {
            cbireturn.setReturnValue(!first.equals(second));
        }
    }


}
