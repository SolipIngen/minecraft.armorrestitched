package solipingen.armorrestitched.mixin.entity.mob;

import net.minecraft.registry.RegistryKeys;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.raid.Raid;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import solipingen.armorrestitched.item.ModItems;


@Mixin(PillagerEntity.class)
public abstract class PillagerEntityMixin extends IllagerEntity {

    @Shadow
    protected abstract void initEquipment(Random random, LocalDifficulty localDifficulty);


    protected PillagerEntityMixin(EntityType<? extends IllagerEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initialize", at = @At("HEAD"), cancellable = true)
    private void injectedInitialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, CallbackInfoReturnable<EntityData> cbireturn) {
        this.initEquipment(world.getRandom(), difficulty);
        if (this.isPatrolLeader()) {
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (!slot.isArmorSlot()) continue;
                if (slot == EquipmentSlot.HEAD) continue;
                Item armorItem = PillagerEntityMixin.getModEquipmentForSlot(slot, 3);
                if (armorItem != null) {
                    this.equipStack(slot, new ItemStack(armorItem));
                }
            }
        }
        if (!this.isPatrolLeader() && spawnReason == SpawnReason.STRUCTURE) {
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (!slot.isArmorSlot()) continue;
                int level = this.random.nextFloat() < 0.1f*this.getWorld().getDifficulty().getId() + 0.1f*difficulty.getClampedLocalDifficulty() ? 2 : 1;
                Item armorItem = PillagerEntityMixin.getModEquipmentForSlot(slot, level);
                if (armorItem != null) {
                    this.equipStack(slot, new ItemStack(armorItem));
                }
            }
        }
        this.updateEnchantments(world, world.getRandom(), this.getWorld().getLocalDifficulty(this.getBlockPos()));
        cbireturn.setReturnValue(super.initialize(world, difficulty, spawnReason, entityData));
    }

    @Inject(method = "initEquipment", at = @At("TAIL"))
    private void injectedInitEquipment(Random random, LocalDifficulty localDifficulty, CallbackInfo cbi) {
        super.initEquipment(random, localDifficulty);
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (!slot.isArmorSlot()) continue;
            boolean randomBl = this.random.nextBoolean();
            if (!randomBl || this.isPatrolLeader()) continue;
            ItemStack equippedStack = this.getEquippedStack(slot);
            if (equippedStack.isOf(Items.GOLDEN_HELMET)) {
                this.equipStack(slot, new ItemStack(Items.CHAINMAIL_HELMET));
            }
            else if (equippedStack.isOf(Items.GOLDEN_CHESTPLATE)) {
                this.equipStack(slot, new ItemStack(Items.CHAINMAIL_CHESTPLATE));
            }
            else if (equippedStack.isOf(Items.GOLDEN_LEGGINGS)) {
                this.equipStack(slot, new ItemStack(Items.CHAINMAIL_LEGGINGS));
            }
            else if (equippedStack.isOf(Items.GOLDEN_BOOTS)) {
                this.equipStack(slot, new ItemStack(Items.CHAINMAIL_BOOTS));
            }
        }
        if (this.isPatrolLeader()) {
            this.equipStack(EquipmentSlot.HEAD, Raid.getOminousBanner(this.getRegistryManager().getWrapperOrThrow(RegistryKeys.BANNER_PATTERN)));
            this.setEquipmentDropChance(EquipmentSlot.HEAD, 2.0f);
        }
    }

    @Unique
    @Nullable
    private static Item getModEquipmentForSlot(EquipmentSlot equipmentSlot, int equipmentLevel) {
        switch (equipmentSlot) {
            case HEAD: {
                if (equipmentLevel == 0) {
                    return Items.LEATHER_HELMET;
                }
                else if (equipmentLevel == 1) {
                    return ModItems.COPPER_HELMET;
                }
                else if (equipmentLevel == 2) {
                    return Items.IRON_HELMET;
                }
                else if (equipmentLevel == 3) {
                    return Items.GOLDEN_HELMET;
                }
                else if (equipmentLevel == 4) {
                    return Items.DIAMOND_HELMET;
                }
            }
            case CHEST: {
                if (equipmentLevel == 0) {
                    return Items.LEATHER_CHESTPLATE;
                }
                else if (equipmentLevel == 1) {
                    return ModItems.COPPER_CHESTPLATE;
                }
                else if (equipmentLevel == 2) {
                    return Items.IRON_CHESTPLATE;
                }
                else if (equipmentLevel == 3) {
                    return Items.GOLDEN_CHESTPLATE;
                }
                else if (equipmentLevel == 4) {
                    return Items.DIAMOND_CHESTPLATE;
                }
            }
            case LEGS: {
                if (equipmentLevel == 0) {
                    return Items.LEATHER_LEGGINGS;
                }
                else if (equipmentLevel == 1) {
                    return ModItems.COPPER_LEGGINGS;
                }
                else if (equipmentLevel == 2) {
                    return Items.IRON_LEGGINGS;
                }
                else if (equipmentLevel == 3) {
                    return Items.GOLDEN_LEGGINGS;
                }
                else if (equipmentLevel == 4) {
                    return Items.DIAMOND_LEGGINGS;
                }
            }
            case FEET: {
                if (equipmentLevel == 0) {
                    return Items.LEATHER_BOOTS;
                }
                else if (equipmentLevel == 1) {
                    return ModItems.COPPER_BOOTS;
                }
                else if (equipmentLevel == 2) {
                    return Items.IRON_BOOTS;
                }
                else if (equipmentLevel == 3) {
                    return Items.GOLDEN_BOOTS;
                }
                else if (equipmentLevel == 4) {
                    return Items.DIAMOND_BOOTS;
                }
            }
            default: {
                return null;
            }
        }
    }
    

}
