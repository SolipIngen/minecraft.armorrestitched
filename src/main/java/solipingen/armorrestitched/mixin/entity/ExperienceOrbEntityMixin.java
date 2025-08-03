package solipingen.armorrestitched.mixin.entity;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.equipment.trim.ArmorTrim;
import net.minecraft.item.equipment.trim.ArmorTrimMaterials;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;


@Mixin(ExperienceOrbEntity.class)
public abstract class ExperienceOrbEntityMixin extends Entity {


    public ExperienceOrbEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Redirect(method = "onPlayerCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;addExperience(I)V"))
    private void redirectedAddExperience(PlayerEntity player, int amount) {
        int i = amount;
        int trimLevel = 0;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (!slot.isArmorSlot()) continue;
            ItemStack equippedStack = player.getEquippedStack(slot);
            ArmorTrim trim = equippedStack.get(DataComponentTypes.TRIM);
            if (trim != null && trim.material().matchesKey(ArmorTrimMaterials.LAPIS)) {
                trimLevel++;
                i = MathHelper.ceil(1.2f*i);
            }
        }
        if (trimLevel > 0 && this.random.nextInt(amount) > 0) {
            float healAmount = trimLevel/4.0f*Math.abs(i - amount);
            player.heal(healAmount);
        }
        player.addExperience(i);
    }

    @ModifyVariable(method = "repairPlayerGears", at = @At("HEAD"), index = 2)
    private int modifiedMendingRepairAmount(int repairAmount, ServerPlayerEntity player, int amount) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (!slot.isArmorSlot()) continue;
            ItemStack equippedStack = player.getEquippedStack(slot);
            ArmorTrim trim = equippedStack.get(DataComponentTypes.TRIM);
            if (trim == null) continue;
            if (trim.material().matchesKey(ArmorTrimMaterials.LAPIS)) {
                repairAmount = MathHelper.ceil(1.2f*repairAmount);
            }
        }
        return repairAmount;
    }

    
}
