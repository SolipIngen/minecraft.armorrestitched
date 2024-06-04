package solipingen.armorrestitched.mixin.entity.player;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.DamageTypeTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import solipingen.armorrestitched.enchantment.ModEnchantments;


@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {


    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Redirect(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;onUserDamaged(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/Entity;)V"))
    private void redirectedOnUserDamaged(LivingEntity target, Entity attacker) {
        // Removes vanilla Thorns effect, see LivingEntityMixin for mod Thorns effect
    }

    @ModifyVariable(method = "damageHelmet", at = @At("HEAD"), index = 2)
    private float modifiedHelmetDamage(float amount, DamageSource source) {
        if (source.isIn(DamageTypeTags.DAMAGES_HELMET) && !this.getEquippedStack(EquipmentSlot.HEAD).isEmpty()) {
            ItemStack itemStack = this.getEquippedStack(EquipmentSlot.HEAD);
            return (0.7f - 0.1f*EnchantmentHelper.getLevel(ModEnchantments.IMPACT_PROTECTION, itemStack))*amount;
        }
        return amount;
    }


    
}
