package solipingen.armorrestitched.mixin.entity.mob;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import solipingen.armorrestitched.item.ModItems;


@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity {


    protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initEquipment", at = @At("TAIL"))
    private void injectedInitEquipment(Random random, LocalDifficulty localDifficulty, CallbackInfo cbi) {
        float equipThreshold = ((MobEntity)(Object)this) instanceof IllagerEntity ? 0.15f*this.world.getDifficulty().getId() + 0.25f*localDifficulty.getClampedLocalDifficulty() : 0.08f*this.world.getDifficulty().getId() + 0.15f*localDifficulty.getClampedLocalDifficulty();
        float armorTypeThreshold = 0.04f*this.world.getDifficulty().getId() + 0.1f*localDifficulty.getClampedLocalDifficulty();
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() != EquipmentSlot.Type.ARMOR) continue;
            this.equipStack(slot, ItemStack.EMPTY);
            if (random.nextFloat() <= equipThreshold) {
                int level = 0;
                for (int j = 0; j <= 4; j++) {
                    if (random.nextFloat() <= armorTypeThreshold) {
                        level++;
                    }
                }
                Item item = MobEntityMixin.getModEquipmentForSlot(slot, level);
                if (item != null) {
                    ItemStack itemStack = new ItemStack(item);
                    this.equipStack(slot, itemStack);
                }
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

