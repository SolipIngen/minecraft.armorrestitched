package solipingen.armorrestitched.mixin.entity.mob;

import net.minecraft.entity.mob.EvokerEntity;
import net.minecraft.registry.RegistryKeys;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.mob.SpellcastingIllagerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.raid.Raid;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.Inject;
import solipingen.armorrestitched.item.ModItems;


@Mixin(EvokerEntity.class)
public abstract class EvokerEntityMixin extends IllagerEntity {


    protected EvokerEntityMixin(EntityType<? extends SpellcastingIllagerEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        EntityData entityData2 = super.initialize(world, difficulty, spawnReason, entityData);
        Random random = world.getRandom();
        this.initEquipment(random, difficulty);
        this.updateEnchantments(world, random, difficulty);
        if (spawnReason == SpawnReason.STRUCTURE || this.isPatrolLeader()) {
            int level = this.random.nextFloat() < 0.2f*this.getWorld().getDifficulty().getId() + 0.2f*difficulty.getClampedLocalDifficulty() ? 4 : 3;
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (!slot.isArmorSlot()) continue;
                if (slot == EquipmentSlot.HEAD && this.isPatrolLeader()) continue;
                Item armorItem = EvokerEntityMixin.getModEquipmentForSlot(slot, level);
                this.equipStack(slot, new ItemStack(armorItem));
            }
        }
        return entityData2;
    }

    @Override
    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        super.initEquipment(random, localDifficulty);
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (!slot.isArmorSlot()) continue;
            if (slot == EquipmentSlot.HEAD && this.isPatrolLeader()) continue;
            Item armorItem = EvokerEntityMixin.getModEquipmentForSlot(slot, random.nextFloat() > 0.2f*this.getWorld().getDifficulty().getId() + 0.2f*localDifficulty.getClampedLocalDifficulty() ? 4 : 3);
            this.equipStack(slot, new ItemStack(armorItem));
            this.setEquipmentDropChance(slot, 0.0f);
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

