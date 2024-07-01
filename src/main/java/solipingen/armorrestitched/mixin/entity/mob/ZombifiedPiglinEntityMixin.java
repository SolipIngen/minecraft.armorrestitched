package solipingen.armorrestitched.mixin.entity.mob;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;


@Mixin(ZombifiedPiglinEntity.class)
public abstract class ZombifiedPiglinEntityMixin extends ZombieEntity implements Angerable {


    public ZombifiedPiglinEntityMixin(EntityType<? extends ZombieEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initEquipment", at = @At("TAIL"))
    private void injectedInitEquipment(Random random, LocalDifficulty localDifficulty, CallbackInfo cbi) {
        World world = this.getWorld();
        float armorThreshold = 0.25f*world.getDifficulty().getId() + 0.25f*localDifficulty.getClampedLocalDifficulty();
        float typeThreshold = 0.16f*world.getDifficulty().getId() + 0.08f*localDifficulty.getClampedLocalDifficulty();
        if (random.nextFloat() < typeThreshold) {
            if (random.nextFloat() < armorThreshold) {
                this.equipStack(EquipmentSlot.HEAD, new ItemStack(Items.GOLDEN_HELMET));
            }
            else {
                this.equipStack(EquipmentSlot.HEAD, new ItemStack(Items.LEATHER_HELMET));
            }
            if (random.nextFloat() < typeThreshold) {
                this.equipStack(EquipmentSlot.CHEST, new ItemStack(Items.GOLDEN_CHESTPLATE));
            }
            else {
                this.equipStack(EquipmentSlot.CHEST, new ItemStack(Items.LEATHER_CHESTPLATE));
            }
            if (random.nextFloat() < typeThreshold) {
                this.equipStack(EquipmentSlot.LEGS, new ItemStack(Items.GOLDEN_LEGGINGS));
            }
            else {
                this.equipStack(EquipmentSlot.LEGS, new ItemStack(Items.LEATHER_LEGGINGS));
            }
            if (random.nextFloat() < typeThreshold) {
                this.equipStack(EquipmentSlot.FEET, new ItemStack(Items.GOLDEN_BOOTS));
            }
            else {
                this.equipStack(EquipmentSlot.FEET, new ItemStack(Items.LEATHER_BOOTS));
            }
        }
    }


    
}

