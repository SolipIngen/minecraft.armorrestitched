package solipingen.armorrestitched.mixin.entity.mob;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.AbstractPiglinEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;


@Mixin(PiglinEntity.class)
public abstract class PiglinEntityMixin extends AbstractPiglinEntity {
    
    
    public PiglinEntityMixin(EntityType<? extends AbstractPiglinEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initialize", at = @At("RETURN"), cancellable = true)
    private void injectedInitialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, CallbackInfoReturnable<EntityData> cbireturn) {
        if (spawnReason == SpawnReason.STRUCTURE) {
            this.equipStack(EquipmentSlot.HEAD, new ItemStack(Items.GOLDEN_HELMET));
            this.equipStack(EquipmentSlot.CHEST, new ItemStack(Items.GOLDEN_CHESTPLATE));
            this.equipStack(EquipmentSlot.LEGS, new ItemStack(Items.GOLDEN_LEGGINGS));
            this.equipStack(EquipmentSlot.FEET, new ItemStack(Items.GOLDEN_BOOTS));
            this.updateEnchantments(world, world.getRandom(), difficulty);
        }
    }

    @ModifyConstant(method = "createPiglinAttributes", constant = @Constant(doubleValue = 16.0))
    private static double modifiedHealth(double originald) {
        return 20.0;
    }

    @Inject(method = "equipAtChance", at = @At("HEAD"), cancellable = true)
    private void redirectedEquipAtChance(EquipmentSlot slot, ItemStack stack, Random random, CallbackInfo cbi) {
        LocalDifficulty localDifficulty = this.getWorld().getLocalDifficulty(this.getBlockPos());
        if (random.nextFloat() < 0.25f*localDifficulty.getGlobalDifficulty().getId() + 0.25f*localDifficulty.getClampedLocalDifficulty()) {
            if (slot == EquipmentSlot.HEAD) {
                ItemStack itemStack = this.random.nextFloat() < 0.16f*localDifficulty.getGlobalDifficulty().getId() + 0.08f*localDifficulty.getClampedLocalDifficulty() ? new ItemStack(Items.GOLDEN_HELMET) : new ItemStack(Items.LEATHER_HELMET);
                this.equipStack(slot, itemStack);
                cbi.cancel();
            }
            else if (slot == EquipmentSlot.CHEST) {
                ItemStack itemStack = this.random.nextFloat() < 0.16f*localDifficulty.getGlobalDifficulty().getId() + 0.08f*localDifficulty.getClampedLocalDifficulty() ? new ItemStack(Items.GOLDEN_CHESTPLATE) : new ItemStack(Items.LEATHER_CHESTPLATE);
                this.equipStack(slot, itemStack);
                cbi.cancel();
            }
            else if (slot == EquipmentSlot.LEGS) {
                ItemStack itemStack = this.random.nextFloat() < 0.16f*localDifficulty.getGlobalDifficulty().getId() + 0.08f*localDifficulty.getClampedLocalDifficulty() ? new ItemStack(Items.GOLDEN_LEGGINGS) : new ItemStack(Items.LEATHER_LEGGINGS);
                this.equipStack(slot, itemStack);
                cbi.cancel();
            }
            else if (slot == EquipmentSlot.FEET) {
                ItemStack itemStack = this.random.nextFloat() < 0.16f*localDifficulty.getGlobalDifficulty().getId() + 0.08f*localDifficulty.getClampedLocalDifficulty() ? new ItemStack(Items.GOLDEN_BOOTS) : new ItemStack(Items.LEATHER_BOOTS);
                this.equipStack(slot, itemStack);
                cbi.cancel();
            }
        }
    }


}
