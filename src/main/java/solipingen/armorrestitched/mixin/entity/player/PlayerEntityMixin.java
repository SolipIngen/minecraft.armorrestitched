package solipingen.armorrestitched.mixin.entity.player;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.world.World;


@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {


    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Redirect(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;onUserDamaged(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/Entity;)V"))
    private void redirectedOnUserDamaged(LivingEntity target, Entity attacker) {
        // Removes vanilla Thorns effect, see LivingEntityMixin for mod Thorns effect
    }

    @Inject(method = "damageArmor", at = @At("TAIL"))
    private void injectedDamageArmor(DamageSource source, float amount, CallbackInfo cbi) {
        if (this.getEquippedStack(EquipmentSlot.CHEST).isOf(Items.ELYTRA)) {
            this.getEquippedStack(EquipmentSlot.CHEST).damage((int)amount, this, player -> player.sendEquipmentBreakStatus(EquipmentSlot.CHEST));
        }
    }


    
}
