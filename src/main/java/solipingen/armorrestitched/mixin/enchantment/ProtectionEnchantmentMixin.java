package solipingen.armorrestitched.mixin.enchantment;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.TagKey;


@Mixin(ProtectionEnchantment.class)
public abstract class ProtectionEnchantmentMixin extends Enchantment {

    
    protected ProtectionEnchantmentMixin(Rarity weight, EnchantmentTarget target, EquipmentSlot[] slotTypes) {
        super(weight, target, slotTypes);
    }

    @Redirect(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/enchantment/EnchantmentTarget;ARMOR_FEET:Lnet/minecraft/enchantment/EnchantmentTarget;", opcode = Opcodes.GETSTATIC))
    private static EnchantmentTarget redirectedArmorFeetEnchantmentTarget(Enchantment.Rarity weight, ProtectionEnchantment.Type protectionType, EquipmentSlot... slotTypes) {
        for (EquipmentSlot slot : slotTypes) {
            if (slot == EquipmentSlot.HEAD) {
                return EnchantmentTarget.ARMOR_HEAD;
            }
        }
        return EnchantmentTarget.ARMOR_FEET;
    }

    @Redirect(method = "getProtectionAmount", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/damage/DamageSource;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"))
    private boolean redirectedIsInFall(DamageSource source, TagKey<DamageType> damageTypeTag) {
        if (damageTypeTag == DamageTypeTags.IS_FALL) {
            return source.isIn(damageTypeTag) || source.isOf(DamageTypes.FLY_INTO_WALL);
        }
        return source.isIn(damageTypeTag);
    }

    @Inject(method = "canAccept", at = @At("HEAD"), cancellable = true)
    private void injectedCanAccept(Enchantment other, CallbackInfoReturnable<Boolean> cbireturn) {
        if (other instanceof ProtectionEnchantment) {
            ProtectionEnchantment protectionEnchantment = (ProtectionEnchantment)other;
            boolean bl = false;
            if (((ProtectionEnchantment)(Object)this).protectionType == protectionEnchantment.protectionType) {
                cbireturn.setReturnValue(bl);
            }
            else {
                switch(((ProtectionEnchantment)(Object)this).protectionType) {
                    case FIRE: {
                        bl = !(protectionEnchantment.protectionType == ProtectionEnchantment.Type.EXPLOSION || protectionEnchantment.protectionType == ProtectionEnchantment.Type.PROJECTILE);
                        break;
                    }
                    case EXPLOSION: {
                        bl = !(protectionEnchantment.protectionType == ProtectionEnchantment.Type.FIRE || protectionEnchantment.protectionType == ProtectionEnchantment.Type.PROJECTILE);
                        break;
                    }
                    case PROJECTILE: {
                        bl = !(protectionEnchantment.protectionType == ProtectionEnchantment.Type.FIRE || protectionEnchantment.protectionType == ProtectionEnchantment.Type.EXPLOSION);
                        break;
                    }
                    default: {
                        bl = true;
                        break;
                    }
                }
                cbireturn.setReturnValue(bl);
            }
        }
        else {
            cbireturn.setReturnValue(super.canAccept(other));
        }
    }
    
    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem || (stack.getItem() instanceof ElytraItem && ((ProtectionEnchantment)(Object)this).protectionType != ProtectionEnchantment.Type.FALL) || super.isAcceptableItem(stack);
    }

    @Override
    public boolean isTreasure() {
        return ((ProtectionEnchantment)(Object)this).protectionType == ProtectionEnchantment.Type.FALL && ((ProtectionEnchantment)(Object)this).target == EnchantmentTarget.ARMOR_HEAD;
    }

    @ModifyConstant(method = "transformFireDuration", constant = @Constant(floatValue = 0.15f))
    private static float modifiedFireDurationFactor(float originalf) {
        return 0.2f;
    }

    @ModifyConstant(method = "transformExplosionKnockback", constant = @Constant(doubleValue = 0.15))
    private static double modifiedExplosionKnockbackFactor(double originald) {
        return 0.2;
    }
    


}
