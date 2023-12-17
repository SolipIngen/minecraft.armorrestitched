package solipingen.armorrestitched.mixin.entity.ai.goal;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.SkeletonHorseTrapTriggerGoal;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.SkeletonHorseEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import solipingen.armorrestitched.util.interfaces.mixin.entity.mob.ZombieHorseEntityInterface;


@Mixin(SkeletonHorseTrapTriggerGoal.class)
public abstract class SkeletonHorseTrapTriggerGoalMixin extends Goal {
    @Shadow @Final private SkeletonHorseEntity skeletonHorse;


    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/SkeletonEntity;startRiding(Lnet/minecraft/entity/Entity;)Z"))
    private void saddledSkeletonHorseEntity(CallbackInfo cbi) {
        this.skeletonHorse.saddle(null);
        ((ZombieHorseEntityInterface)this.skeletonHorse).getItems().setStack(1, new ItemStack(Items.IRON_HORSE_ARMOR));
    }
    
    @ModifyVariable(method = "getHorse", at = @At("STORE"), ordinal = 0)
    private SkeletonHorseEntity saddledOtherSkeletonHorseEntity(SkeletonHorseEntity skeletonHorseEntity) {
        skeletonHorseEntity.saddle(null);
        ((ZombieHorseEntityInterface)skeletonHorseEntity).getItems().setStack(1, new ItemStack(Items.IRON_HORSE_ARMOR));
        return skeletonHorseEntity;
    }

    @Redirect(method = "getSkeleton", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/SkeletonEntity;setPosition(DDD)V"))
    private void modifiedEquippedSkeleton(SkeletonEntity skeletonEntity, double x, double y, double z) {
        if (!(skeletonEntity.getEquippedStack(EquipmentSlot.HEAD).isOf(Items.CARVED_PUMPKIN) || skeletonEntity.getEquippedStack(EquipmentSlot.HEAD).isOf(Items.JACK_O_LANTERN))) {
            skeletonEntity.equipStack(EquipmentSlot.HEAD, new ItemStack(Items.CHAINMAIL_HELMET));
        }
        skeletonEntity.equipStack(EquipmentSlot.CHEST, new ItemStack(Items.CHAINMAIL_CHESTPLATE));
        skeletonEntity.equipStack(EquipmentSlot.LEGS, new ItemStack(Items.CHAINMAIL_LEGGINGS));
        skeletonEntity.equipStack(EquipmentSlot.FEET, new ItemStack(Items.CHAINMAIL_BOOTS));
        skeletonEntity.setPosition(x, y, z);
    }




}
