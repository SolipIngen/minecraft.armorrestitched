package solipingen.armorrestitched.mixin.entity.mob;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.SkeletonHorseEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import solipingen.armorrestitched.util.interfaces.mixin.entity.mob.ZombieHorseEntityInterface;


@Mixin(SkeletonHorseEntity.class)
public abstract class SkeletonHorseEntityMixin extends AbstractHorseEntity implements ZombieHorseEntityInterface {
    

    protected SkeletonHorseEntityMixin(EntityType<? extends AbstractHorseEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tickMovement", at = @At("TAIL"))
    private void injectedBurnInSunlight(CallbackInfo cbi) {
        boolean bl = this.isAlive() && this.isAffectedByDaylight() && !((SkeletonHorseEntity)(Object)this).isTrapped();
        if (bl) {
            if (!this.isWearingBodyArmor()) {
                this.setOnFireFor(8);
            }
        }
    }

    @Redirect(method = "interactMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/SkeletonHorseEntity;isTame()Z"))
    private boolean redirectedIsTame(SkeletonHorseEntity skeletonHorseEntity) {
        return true;
    }

//    @Override
//    public void onInventoryChanged(Inventory sender) {
//        ItemStack itemStack = ((ZombieHorseEntityInterface)this).getArmorType();
//        super.onInventoryChanged(sender);
//        ItemStack itemStack2 = ((ZombieHorseEntityInterface)this).getArmorType();
//        if (this.age > 20 && this.isHorseArmor(itemStack2) && itemStack != itemStack2) {
//            this.playSound(SoundEvents.ENTITY_HORSE_ARMOR, 0.5f, 1.0f);
//        }
//    }

    @Override
    public boolean canUseSlot(EquipmentSlot slot) {
        return true;
    }

    @Override
    public ItemStack getArmorType() {
        return this.getEquippedStack(EquipmentSlot.BODY);
    }

    @Override
    public SimpleInventory getItems() {
        return this.items;
    }

    @Override
    @Nullable
    protected SoundEvent getAngrySound() {
        return SoundEvents.ENTITY_SKELETON_HORSE_AMBIENT;
    }

    
}

