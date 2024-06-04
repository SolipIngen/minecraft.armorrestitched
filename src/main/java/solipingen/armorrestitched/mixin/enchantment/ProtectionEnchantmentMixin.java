package solipingen.armorrestitched.mixin.enchantment;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import solipingen.armorrestitched.enchantment.ModEnchantments;


@Mixin(ProtectionEnchantment.class)
public abstract class ProtectionEnchantmentMixin extends Enchantment {
    @Shadow @Final public ProtectionEnchantment.Type protectionType;


    protected ProtectionEnchantmentMixin(Enchantment.Properties properties, ProtectionEnchantment.Type protectionType) {
        super(properties);
    }

    @Inject(method = "getProtectionAmount", at = @At("HEAD"), cancellable = true)
    private void injectedFallProtectionAmount(int level, DamageSource source, CallbackInfoReturnable<Integer> cbireturn) {
        if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            cbireturn.setReturnValue(0);
        }
        if (this.protectionType == ProtectionEnchantment.Type.FIRE && source.isIn(DamageTypeTags.IS_FIRE)) {
            cbireturn.setReturnValue(level + 1);
        }
        if (this.protectionType == ProtectionEnchantment.Type.EXPLOSION && source.isIn(DamageTypeTags.IS_EXPLOSION)) {
            cbireturn.setReturnValue(level + 1);
        }
        if (this.protectionType == ProtectionEnchantment.Type.PROJECTILE && source.isIn(DamageTypeTags.IS_PROJECTILE)) {
            cbireturn.setReturnValue(level + 1);
        }
        if (((ProtectionEnchantment)(Object)this) == ModEnchantments.IMPACT_PROTECTION && source.isOf(DamageTypes.FLY_INTO_WALL)) {
            cbireturn.setReturnValue(level + 2);
        }
        if (((ProtectionEnchantment)(Object)this) == Enchantments.FEATHER_FALLING && source.isIn(DamageTypeTags.IS_FALL) && !source.isOf(DamageTypes.FLY_INTO_WALL)) {
            cbireturn.setReturnValue(level + 2);
        }
        if (this.protectionType == ProtectionEnchantment.Type.ALL) {
            if (((ProtectionEnchantment)(Object)this) == ModEnchantments.STRIKE_PROTECTION
                    && source.isOf(DamageTypes.PLAYER_ATTACK) || source.isOf(DamageTypes.MOB_ATTACK)) {
                cbireturn.setReturnValue(level + 1);
            }
            else if (((ProtectionEnchantment)(Object)this) == ModEnchantments.MAGIC_PROTECTION
                    && (source.isOf(DamageTypes.MAGIC) || source.isOf(DamageTypes.INDIRECT_MAGIC) || source.isOf(DamageTypes.DRAGON_BREATH))) {
                cbireturn.setReturnValue(level + 1);
            }
            else {
                cbireturn.setReturnValue(level);
            }
        }
        else {
            cbireturn.setReturnValue(0);
        }
    }

    @Inject(method = "canAccept", at = @At("HEAD"), cancellable = true)
    private void injectedCanAccept(Enchantment other, CallbackInfoReturnable<Boolean> cbireturn) {
        if (other instanceof ProtectionEnchantment) {
            ProtectionEnchantment protectionEnchantment = (ProtectionEnchantment)other;
            boolean bl = false;
            if (((ProtectionEnchantment)(Object)this) != protectionEnchantment) {
                cbireturn.setReturnValue(bl);
            }
            switch(((ProtectionEnchantment)(Object)this).protectionType) {
                case FIRE: {
                    bl = !(protectionEnchantment.protectionType == ProtectionEnchantment.Type.EXPLOSION
                            || protectionEnchantment.protectionType == ProtectionEnchantment.Type.PROJECTILE
                            || protectionEnchantment.protectionType == ProtectionEnchantment.Type.FALL
                            || other == ModEnchantments.STRIKE_PROTECTION
                            || other == ModEnchantments.MAGIC_PROTECTION);
                    break;
                }
                case EXPLOSION: {
                    bl = !(protectionEnchantment.protectionType == ProtectionEnchantment.Type.FIRE
                            || protectionEnchantment.protectionType == ProtectionEnchantment.Type.PROJECTILE
                            || protectionEnchantment.protectionType == ProtectionEnchantment.Type.FALL
                            || other == ModEnchantments.STRIKE_PROTECTION
                            || other == ModEnchantments.MAGIC_PROTECTION);
                    break;
                }
                case PROJECTILE: {
                    bl = !(protectionEnchantment.protectionType == ProtectionEnchantment.Type.FIRE
                            || protectionEnchantment.protectionType == ProtectionEnchantment.Type.EXPLOSION
                            || protectionEnchantment.protectionType == ProtectionEnchantment.Type.FALL
                            || other == ModEnchantments.STRIKE_PROTECTION
                            || other == ModEnchantments.MAGIC_PROTECTION);
                    break;
                }
                case FALL : {
                    bl = !(protectionEnchantment.protectionType == ProtectionEnchantment.Type.FIRE
                            || protectionEnchantment.protectionType == ProtectionEnchantment.Type.EXPLOSION
                            || protectionEnchantment.protectionType == ProtectionEnchantment.Type.PROJECTILE
                            || other == ModEnchantments.STRIKE_PROTECTION
                            || other == ModEnchantments.MAGIC_PROTECTION);
                    break;
                }
                default: {
                    bl = true;
                    break;
                }
            }
            cbireturn.setReturnValue(bl);
        }
        else {
            cbireturn.setReturnValue(super.canAccept(other));
        }
    }

    @Inject(method = "transformFireDuration", at = @At("HEAD"), cancellable = true)
    private static void injectedFireDuration(LivingEntity entity, int duration, CallbackInfoReturnable<Integer> cbireturn) {
        duration -= MathHelper.floor(Math.min(entity.getAttributeValue(EntityAttributes.GENERIC_ARMOR)/50.0f, 1.0f)*duration);
        int netheriteCount = 0;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = entity.getEquippedStack(slot);
            if (stack.getItem() instanceof ArmorItem armorItem && armorItem.getMaterial() == ArmorMaterials.NETHERITE) {
                netheriteCount++;
            }
        }
        duration -= MathHelper.floor((0.1f*netheriteCount)*duration);
        int i = EnchantmentHelper.getEquipmentLevel(Enchantments.FIRE_PROTECTION, entity);
        if (i > 0) {
            duration -= MathHelper.floor((0.2f*i)*duration);
        }
        cbireturn.setReturnValue(duration);
    }

    @Inject(method = "transformExplosionKnockback", at = @At("HEAD"), cancellable = true)
    private static void injectedExplosionKnockback(LivingEntity entity, double velocity, CallbackInfoReturnable<Double> cbireturn) {
        velocity *= MathHelper.clamp(1.0 - entity.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE), 0.0, 1.0);
        int netheriteCount = 0;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = entity.getEquippedStack(slot);
            if (stack.getItem() instanceof ArmorItem armorItem && armorItem.getMaterial() == ArmorMaterials.NETHERITE) {
                netheriteCount++;
            }
        }
        velocity *= MathHelper.clamp(1.0 - 0.1*netheriteCount, 0.0, 1.0);
        int i = EnchantmentHelper.getEquipmentLevel(Enchantments.BLAST_PROTECTION, entity);
        if (i > 0) {
            velocity *= MathHelper.clamp(1.0 - 0.2*i, 0.0, 1.0);
        }
        cbireturn.setReturnValue(velocity);
    }
    


}
