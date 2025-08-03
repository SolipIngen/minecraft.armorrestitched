package solipingen.armorrestitched.mixin.entity.mob;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


@Mixin(ZombieEntity.class)
public abstract class ZombieEntityMixin extends HostileEntity {

    @Invoker("burnsInDaylight")
    public abstract boolean invokeBurnsInDaylight();


    protected ZombieEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyConstant(method = "createZombieAttributes", constant = @Constant(doubleValue = 2.0))
    private static double modifiedZombieArmorPoints(double originald) {
        return 0.0;
    }

    @Inject(method = "tickMovement", at = @At("HEAD"), cancellable = true)
    private void injectedBurnInSunlight(CallbackInfo cbi) {
        boolean bl = this.isAlive() && this.invokeBurnsInDaylight() && this.isAffectedByDaylight();
        if (bl) {
            boolean bl2 = false;
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (!slot.isArmorSlot()) continue;
                ItemStack itemStack = this.getEquippedStack(slot);
                bl2 |= itemStack.isEmpty();
            }
            if (bl2) {
                this.setOnFireFor(8);
            }
        }
        super.tickMovement();
        cbi.cancel();
    }


}
