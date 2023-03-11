package solipingen.armorrestitched.mixin.entity.mob;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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

    @Inject(method = "initEquipment", at = @At("HEAD"), cancellable = true)
    private void injectedInitEquipment(Random random, LocalDifficulty localDifficulty, CallbackInfo cbi) {
        float equipThreshold = ((MobEntity)(Object)this) instanceof IllagerEntity ? 0.25f*this.world.getDifficulty().getId() + 0.25f*localDifficulty.getClampedLocalDifficulty() : 0.15f*this.world.getDifficulty().getId() + 0.5f*localDifficulty.getClampedLocalDifficulty();
        float armorTypeThreshold = 0.08f*this.world.getDifficulty().getId() + 0.2f*localDifficulty.getClampedLocalDifficulty();
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() != EquipmentSlot.Type.ARMOR) continue;
            if (random.nextFloat() <= equipThreshold) {
                int level = 0;
                for (int j = 0; j <= 4; j++) {
                    if (random.nextFloat() <= armorTypeThreshold) {
                        level++;
                    }
                }
                Item item = MobEntity.getEquipmentForSlot(slot, level);
                ItemStack itemStack = new ItemStack(item);
                this.equipStack(slot, itemStack);
            }
        }
        cbi.cancel();
    }

    @SuppressWarnings("incomplete-switch")
    @Inject(method = "getEquipmentForSlot", at = @At("HEAD"), cancellable = true)
    private static void injectedModEquipmentForSlot(EquipmentSlot equipmentSlot, int equipmentLevel, CallbackInfoReturnable<Item> cbireturn) {
        switch (equipmentSlot) {
            case HEAD: {
                if (equipmentLevel == 0) {
                    cbireturn.setReturnValue(Items.LEATHER_HELMET);
                }
                if (equipmentLevel == 1) {
                    cbireturn.setReturnValue(ModItems.COPPER_HELMET);
                }
                if (equipmentLevel == 2) {
                    cbireturn.setReturnValue(Items.GOLDEN_HELMET);
                }
                if (equipmentLevel == 3) {
                    cbireturn.setReturnValue(Items.IRON_HELMET);
                }
                if (equipmentLevel == 4) {
                    cbireturn.setReturnValue(Items.DIAMOND_HELMET);
                }
            }
            case CHEST: {
                if (equipmentLevel == 0) {
                    cbireturn.setReturnValue(Items.LEATHER_CHESTPLATE);
                }
                if (equipmentLevel == 1) {
                    cbireturn.setReturnValue(ModItems.COPPER_CHESTPLATE);
                }
                if (equipmentLevel == 2) {
                    cbireturn.setReturnValue(Items.GOLDEN_CHESTPLATE);
                }
                if (equipmentLevel == 3) {
                    cbireturn.setReturnValue(Items.IRON_CHESTPLATE);
                }
                if (equipmentLevel == 4) {
                    cbireturn.setReturnValue(Items.DIAMOND_CHESTPLATE);
                }
            }
            case LEGS: {
                if (equipmentLevel == 0) {
                    cbireturn.setReturnValue(Items.LEATHER_LEGGINGS);
                }
                if (equipmentLevel == 1) {
                    cbireturn.setReturnValue(ModItems.COPPER_LEGGINGS);
                }
                if (equipmentLevel == 2) {
                    cbireturn.setReturnValue(Items.GOLDEN_LEGGINGS);
                }
                if (equipmentLevel == 3) {
                    cbireturn.setReturnValue(Items.IRON_LEGGINGS);
                }
                if (equipmentLevel == 4) {
                    cbireturn.setReturnValue(Items.DIAMOND_LEGGINGS);
                }
            }
            case FEET: {
                if (equipmentLevel == 0) {
                    cbireturn.setReturnValue(Items.LEATHER_BOOTS);
                }
                if (equipmentLevel == 1) {
                    cbireturn.setReturnValue(ModItems.COPPER_BOOTS);
                }
                if (equipmentLevel == 2) {
                    cbireturn.setReturnValue(Items.GOLDEN_BOOTS);
                }
                if (equipmentLevel == 3) {
                    cbireturn.setReturnValue(Items.IRON_BOOTS);
                }
                if (equipmentLevel == 4) {
                    cbireturn.setReturnValue(Items.DIAMOND_BOOTS);
                }
            }
        }
        cbireturn.setReturnValue(null);
    }

    
}

