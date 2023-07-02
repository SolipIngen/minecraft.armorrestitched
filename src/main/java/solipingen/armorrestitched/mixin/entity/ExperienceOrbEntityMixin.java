package solipingen.armorrestitched.mixin.entity;

import java.util.Map;
import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.item.trim.ArmorTrimMaterials;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;


@Mixin(ExperienceOrbEntity.class)
public abstract class ExperienceOrbEntityMixin extends Entity {
    @Shadow private int amount;

    @Invoker("getMendingRepairAmount")
    public abstract int invokeGetMendingRepairAmount(int experienceAmount);


    public ExperienceOrbEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Redirect(method = "onPlayerCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;addExperience(I)V"))
    private void redirectedAddExperience(PlayerEntity player, int amount) {
        int i = amount;
        int trimLevel = 0;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() != EquipmentSlot.Type.ARMOR) continue;
            ItemStack equippedStack = player.getEquippedStack(slot);
            Optional<ArmorTrim> trimOptional = ArmorTrim.getTrim(player.getWorld().getRegistryManager(), equippedStack);
            if (trimOptional.isPresent() && trimOptional.get().getMaterial().matchesKey(ArmorTrimMaterials.LAPIS)) {
                trimLevel++;
                i = MathHelper.ceil(1.25f*i);
            }
        }
        if (trimLevel > 0 && this.random.nextInt(amount) > 0) {
            float healAmount = trimLevel/4.0f*Math.abs(i - amount);
            player.heal(healAmount);
        }
        player.addExperience(i);
    }

    @Redirect(method = "repairPlayerGears", at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I"))
    private int redirectedMendingRepairAmount(int repairAmount, int stackDamage, PlayerEntity player, int amount) {
        Map.Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.chooseEquipmentWith(Enchantments.MENDING, player, ItemStack::isDamaged);
        float trimModifier = 1.0f;
        if (entry != null) {
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (slot.getType() != EquipmentSlot.Type.ARMOR) continue;
                ItemStack equippedStack = player.getEquippedStack(slot);
                Optional<ArmorTrim> trimOptional = ArmorTrim.getTrim(player.getWorld().getRegistryManager(), equippedStack);
                if (trimOptional.isEmpty()) continue;
                if (trimOptional.get().getMaterial().matchesKey(ArmorTrimMaterials.LAPIS)) {
                    trimModifier *= 1.25f;
                }
            }
        }
        return Math.min(MathHelper.ceil(trimModifier*repairAmount), stackDamage);
    }

    
}
