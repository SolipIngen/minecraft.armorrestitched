package solipingen.armorrestitched.mixin.entity.mob;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.entity.passive.AllayEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import solipingen.armorrestitched.util.interfaces.mixin.entity.mob.MobEntityInterface;


@Mixin(VexEntity.class)
public abstract class VexEntityMixin extends HostileEntity {


    protected VexEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void injectedTick(CallbackInfo cbi) {
        if (((MobEntityInterface)this).getEntranced() && this.random.nextFloat() >= 0.9f + 0.01f*((MobEntityInterface)this).getEntrancedTime()) {
            World world = this.getWorld();
            AllayEntity allayEntity = EntityType.ALLAY.create(world, SpawnReason.CONVERSION);
            if (allayEntity != null && world instanceof ServerWorld) {
                allayEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
                allayEntity.initialize((ServerWorld)world, world.getLocalDifficulty(allayEntity.getBlockPos()), SpawnReason.CONVERSION, null);
                allayEntity.setAiDisabled(this.isAiDisabled());
                if (this.hasCustomName()) {
                    allayEntity.setCustomName(this.getCustomName());
                    allayEntity.setCustomNameVisible(this.isCustomNameVisible());
                }
                allayEntity.setPersistent();
                ((MobEntityInterface)allayEntity).setEntranced(false, 0);
                ((ServerWorld)world).spawnEntityAndPassengers(allayEntity);
                this.discard();
                cbi.cancel();
            }
        }
    }


}
