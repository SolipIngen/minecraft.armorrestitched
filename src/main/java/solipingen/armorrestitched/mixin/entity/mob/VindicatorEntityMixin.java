package solipingen.armorrestitched.mixin.entity.mob;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.VindicatorEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;


@Mixin(VindicatorEntity.class)
public abstract class VindicatorEntityMixin extends IllagerEntity {

    
    protected VindicatorEntityMixin(EntityType<? extends IllagerEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initialize", at = @At("RETURN"), cancellable = true)
    private void injectedInitialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt, CallbackInfoReturnable<EntityData> cbireturn) {
        if (spawnReason == SpawnReason.STRUCTURE || this.isPatrolLeader()) {
            for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                if (equipmentSlot.getType() != EquipmentSlot.Type.ARMOR) continue;
                if (equipmentSlot == EquipmentSlot.HEAD) break;
                Item armorItem = MobEntity.getEquipmentForSlot(equipmentSlot, this.random.nextFloat() < 0.04f ? 4 : 3);
                this.equipStack(equipmentSlot, new ItemStack(armorItem));
            }
            this.updateEnchantments(world.getRandom(), difficulty);
        }
    }

    @Inject(method = "initEquipment", at = @At("TAIL"))
    private void injectedInitEquipment(Random random, LocalDifficulty localDifficulty, CallbackInfo cbi) {
        super.initEquipment(random, localDifficulty);
    }


    
}

