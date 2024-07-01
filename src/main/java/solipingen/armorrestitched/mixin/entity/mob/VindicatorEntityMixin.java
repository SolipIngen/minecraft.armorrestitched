package solipingen.armorrestitched.mixin.entity.mob;

import net.minecraft.registry.RegistryKeys;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.mob.VindicatorEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.raid.Raid;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;


@Mixin(VindicatorEntity.class)
public abstract class VindicatorEntityMixin extends IllagerEntity {

    
    protected VindicatorEntityMixin(EntityType<? extends IllagerEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initialize", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/VindicatorEntity;initEquipment(Lnet/minecraft/util/math/random/Random;Lnet/minecraft/world/LocalDifficulty;)V", shift = At.Shift.AFTER))
    private void injectedInitialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, CallbackInfoReturnable<EntityData> cbireturn) {
        if (spawnReason == SpawnReason.STRUCTURE || this.isPatrolLeader()) {
            int level = this.random.nextFloat() < 0.2f*this.getWorld().getDifficulty().getId() + 0.2f*difficulty.getClampedLocalDifficulty() ? 2 : 1;
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (!slot.isArmorSlot()) continue;
                if (slot == EquipmentSlot.HEAD && this.isPatrolLeader()) continue;
                Item armorItem = VindicatorEntityMixin.getModEquipmentForSlot(slot, level);
                this.equipStack(slot, new ItemStack(armorItem));
            }
        }
    }

    @Inject(method = "initEquipment", at = @At("TAIL"))
    private void injectedInitEquipment(Random random, LocalDifficulty localDifficulty, CallbackInfo cbi) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (!slot.isArmorSlot()) continue;
            if (slot == EquipmentSlot.HEAD && this.isPatrolLeader()) continue;
            if (random.nextFloat() < 0.2f*this.getWorld().getDifficulty().getId() + 0.25f*localDifficulty.getClampedLocalDifficulty()) {
                Item armorItem = VindicatorEntityMixin.getModEquipmentForSlot(slot, random.nextFloat() > 0.2f*this.getWorld().getDifficulty().getId() + 0.2f*localDifficulty.getClampedLocalDifficulty() ? 1 : 2);
                this.equipStack(slot, new ItemStack(armorItem));
            }
        }
        if (this.isPatrolLeader()) {
            this.equipStack(EquipmentSlot.HEAD, Raid.getOminousBanner(this.getRegistryManager().getWrapperOrThrow(RegistryKeys.BANNER_PATTERN)));
            this.setEquipmentDropChance(EquipmentSlot.HEAD, 2.0f);
        }
    }

    @Nullable
    private static Item getModEquipmentForSlot(EquipmentSlot equipmentSlot, int equipmentLevel) {
        switch (equipmentSlot) {
            case HEAD: {
                if (equipmentLevel == 1) {
                    return Items.CHAINMAIL_HELMET;
                }
                else if (equipmentLevel == 2) {
                    return Items.IRON_HELMET;
                }
            }
            case CHEST: {
                if (equipmentLevel == 1) {
                    return Items.CHAINMAIL_CHESTPLATE;
                }
                else if (equipmentLevel == 2) {
                    return Items.IRON_CHESTPLATE;
                }
            }
            case LEGS: {
                if (equipmentLevel == 1) {
                    return Items.CHAINMAIL_LEGGINGS;
                }
                else if (equipmentLevel == 2) {
                    return Items.IRON_LEGGINGS;
                }
            }
            case FEET: {
                if (equipmentLevel == 1) {
                    return Items.CHAINMAIL_BOOTS;
                }
                else if (equipmentLevel == 2) {
                    return Items.IRON_BOOTS;
                }
            }
            default: {
                return null;
            }
        }
    }

    
}

