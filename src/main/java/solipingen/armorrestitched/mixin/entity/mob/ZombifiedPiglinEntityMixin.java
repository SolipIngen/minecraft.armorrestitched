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
import net.minecraft.util.math.MathHelper;
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
        float armorThreshold = 0.1f*this.world.getDifficulty().getId();
        if (random.nextFloat() < armorThreshold) {
            if (random.nextFloat() < MathHelper.clamp(this.world.getDifficulty().getId() * localDifficulty.getClampedLocalDifficulty(), 0.0f, 0.75f)) {
                this.equipStack(EquipmentSlot.HEAD, new ItemStack(Items.GOLDEN_HELMET));
            }
            if (random.nextFloat() < MathHelper.clamp(this.world.getDifficulty().getId() * localDifficulty.getClampedLocalDifficulty(), 0.0f, 0.75f)) {
                this.equipStack(EquipmentSlot.CHEST, new ItemStack(Items.GOLDEN_CHESTPLATE));
            }
            if (random.nextFloat() < MathHelper.clamp(this.world.getDifficulty().getId() * localDifficulty.getClampedLocalDifficulty(), 0.0f, 0.75f)) {
                this.equipStack(EquipmentSlot.LEGS, new ItemStack(Items.GOLDEN_LEGGINGS));
            }
            if (random.nextFloat() < MathHelper.clamp(this.world.getDifficulty().getId() * localDifficulty.getClampedLocalDifficulty(), 0.0f, 0.75f)) {
                this.equipStack(EquipmentSlot.FEET, new ItemStack(Items.GOLDEN_BOOTS));
            }
        }
    }


    
}

