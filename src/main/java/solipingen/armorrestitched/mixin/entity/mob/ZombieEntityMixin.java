package solipingen.armorrestitched.mixin.entity.mob;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


@Mixin(ZombieEntity.class)
public abstract class ZombieEntityMixin extends HostileEntity {


    protected ZombieEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyConstant(method = "createZombieAttributes", constant = @Constant(doubleValue = 2.0))
    private static double modifiedZombieArmorPoints(double originald) {
        return 0.0;
    }

    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/ZombieEntity;getEquippedStack(Lnet/minecraft/entity/EquipmentSlot;)Lnet/minecraft/item/ItemStack;"))
    private ItemStack redirectedHeadStackDamageInDaylight(ZombieEntity zombieEntity, EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }


}
