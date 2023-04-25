package solipingen.armorrestitched.mixin.entity.mob;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
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
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.raid.Raid;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import solipingen.armorrestitched.item.ModItems;


@Mixin(PillagerEntity.class)
public abstract class PillagerEntityMixin extends IllagerEntity {
    

    protected PillagerEntityMixin(EntityType<? extends IllagerEntity> entityType, World world) {
        super(entityType, world);
    }

    @Redirect(method = "initialize", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/IllagerEntity;initialize(Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/world/LocalDifficulty;Lnet/minecraft/entity/SpawnReason;Lnet/minecraft/entity/EntityData;Lnet/minecraft/nbt/NbtCompound;)Lnet/minecraft/entity/EntityData;"))
    private EntityData redirectedInitialize(IllagerEntity illagerEntity, ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        EntityData entityData2 = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        if (this.isPatrolLeader()) {
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (slot.getType() != EquipmentSlot.Type.ARMOR) continue;
                if (slot == EquipmentSlot.HEAD) continue;
                Item armorItem = PillagerEntityMixin.getModEquipmentForSlot(slot, 3);
                this.equipStack(slot, new ItemStack(armorItem));
            }
        }
        if (!this.isPatrolLeader() && spawnReason == SpawnReason.STRUCTURE) {
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (slot.getType() != EquipmentSlot.Type.ARMOR) continue;
                int level = this.random.nextFloat() < 0.1f*this.world.getDifficulty().getId() + 0.1f*difficulty.getClampedLocalDifficulty() ? 2 : 1;
                Item armorItem = PillagerEntityMixin.getModEquipmentForSlot(slot, level);
                this.equipStack(slot, new ItemStack(armorItem));
            }
        }
        this.updateEnchantments(world.getRandom(), this.world.getLocalDifficulty(this.getBlockPos()));
        return entityData2;
    }

    @Inject(method = "initEquipment", at = @At("TAIL"))
    private void injectedInitEquipment(Random random, LocalDifficulty localDifficulty, CallbackInfo cbi) {
        super.initEquipment(random, localDifficulty);
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() != EquipmentSlot.Type.ARMOR) continue;
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
            this.equipStack(EquipmentSlot.HEAD, Raid.getOminousBanner());
            this.setEquipmentDropChance(EquipmentSlot.HEAD, 2.0f);
        }
    }

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
