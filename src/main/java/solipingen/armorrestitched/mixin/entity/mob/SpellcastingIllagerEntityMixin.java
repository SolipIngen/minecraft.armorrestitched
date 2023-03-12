package solipingen.armorrestitched.mixin.entity.mob;

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
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.raid.Raid;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import solipingen.armorrestitched.item.ModItems;


@Mixin(SpellcastingIllagerEntity.class)
public abstract class SpellcastingIllagerEntityMixin extends IllagerEntity {


    protected SpellcastingIllagerEntityMixin(EntityType<? extends SpellcastingIllagerEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        EntityData entityData2 = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        Random random = world.getRandom();
        this.initEquipment(random, difficulty);
        if (spawnReason == SpawnReason.STRUCTURE || this.isPatrolLeader()) {
            int level = this.random.nextFloat() < 0.08f*this.world.getDifficulty().getId() + 0.04f*difficulty.getClampedLocalDifficulty() ? 4 : 2;
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (slot.getType() != EquipmentSlot.Type.ARMOR) continue;
                if (slot == EquipmentSlot.HEAD && !this.getEquippedStack(slot).isEmpty()) continue;
                Item armorItem = SpellcastingIllagerEntityMixin.getModEquipmentForSlot(slot, level);
                this.equipStack(slot, new ItemStack(armorItem));
                this.setEquipmentDropChance(slot, 0.0f);
            }
        }
        this.updateEnchantments(random, difficulty);
        return entityData2;
    }

    @Override
    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        super.initEquipment(random, localDifficulty);
        if (this.isPatrolLeader()) {
            this.equipStack(EquipmentSlot.HEAD, Raid.getOminousBanner());
            this.setEquipmentDropChance(EquipmentSlot.HEAD, 2.0f);
        }
    }

    @Nullable
    private static Item getModEquipmentForSlot(EquipmentSlot equipmentSlot, int equipmentLevel) {
        if (equipmentSlot == EquipmentSlot.HEAD) {
            if (equipmentLevel == 0) {
                return Items.LEATHER_HELMET;
            }
            else if (equipmentLevel == 1) {
                return ModItems.COPPER_HELMET;
            }
            else if (equipmentLevel == 2) {
                return Items.GOLDEN_HELMET;
            }
            else if (equipmentLevel == 3) {
                return Items.IRON_HELMET;
            }
            else if (equipmentLevel == 4) {
                return Items.DIAMOND_HELMET;
            }
        }
        else if (equipmentSlot == EquipmentSlot.CHEST) {
            if (equipmentLevel == 0) {
                return Items.LEATHER_CHESTPLATE;
            }
            else if (equipmentLevel == 1) {
                return ModItems.COPPER_CHESTPLATE;
            }
            else if (equipmentLevel == 2) {
                return Items.GOLDEN_CHESTPLATE;
            }
            else if (equipmentLevel == 3) {
                return Items.IRON_CHESTPLATE;
            }
            else if (equipmentLevel == 4) {
                return Items.DIAMOND_CHESTPLATE;
            }
        }
        else if (equipmentSlot == EquipmentSlot.LEGS) {
            if (equipmentLevel == 0) {
                return Items.LEATHER_LEGGINGS;
            }
            else if (equipmentLevel == 1) {
                return ModItems.COPPER_LEGGINGS;
            }
            else if (equipmentLevel == 2) {
                return Items.GOLDEN_LEGGINGS;
            }
            else if (equipmentLevel == 3) {
                return Items.IRON_LEGGINGS;
            }
            else if (equipmentLevel == 4) {
                return Items.DIAMOND_LEGGINGS;
            }
        }
        else if (equipmentSlot == EquipmentSlot.FEET) {
            if (equipmentLevel == 0) {
                return Items.LEATHER_BOOTS;
            }
            else if (equipmentLevel == 1) {
                return ModItems.COPPER_BOOTS;
            }
            else if (equipmentLevel == 2) {
                return Items.GOLDEN_BOOTS;
            }
            else if (equipmentLevel == 3) {
                return Items.IRON_BOOTS;
            }
            else if (equipmentLevel == 4) {
                return Items.DIAMOND_BOOTS;
            }
        }
        return null;
    }

    
}

