package solipingen.armorrestitched.mixin.entity.mob;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PatrolEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.village.raid.Raid;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import solipingen.armorrestitched.item.ModItems;


@Mixin(PatrolEntity.class)
public abstract class PatrolEntityMixin extends HostileEntity {


    protected PatrolEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Redirect(method = "initialize", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/PatrolEntity;equipStack(Lnet/minecraft/entity/EquipmentSlot;Lnet/minecraft/item/ItemStack;)V"))
    private void redirectedEquipStack(PatrolEntity patrolEntity, EquipmentSlot equipmentSlot, ItemStack itemStack) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() != EquipmentSlot.Type.ARMOR) continue;
            if (slot == EquipmentSlot.HEAD) continue;
            Item armorItem = PatrolEntityMixin.getModEquipmentForSlot(slot, 2);
            this.equipStack(slot, new ItemStack(armorItem));
        }
        this.equipStack(EquipmentSlot.HEAD, Raid.getOminousBanner());
        this.setEquipmentDropChance(EquipmentSlot.HEAD, 2.0f);
        this.updateEnchantments(world.getRandom(), this.world.getLocalDifficulty(this.getBlockPos()));
    }

    @Inject(method = "initialize", at = @At("RETURN"), cancellable = true)
    private void injectedInitialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt, CallbackInfoReturnable<EntityData> cbireturn) {
        if (spawnReason == SpawnReason.STRUCTURE && !((PatrolEntity)(Object)this).isPatrolLeader()) {
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (slot.getType() != EquipmentSlot.Type.ARMOR) continue;
                Item armorItem = PatrolEntityMixin.getModEquipmentForSlot(slot, this.random.nextFloat() < 0.2f*this.world.getDifficulty().getId() + 0.1f*difficulty.getClampedLocalDifficulty() ? 3 : 1);
                this.equipStack(slot, new ItemStack(armorItem));
            }
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
