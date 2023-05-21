package solipingen.armorrestitched.mixin.entity.ai.goal;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.SkeletonHorseTrapTriggerGoal;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;


@Mixin(SkeletonHorseTrapTriggerGoal.class)
public abstract class SkeletonHorseTrapTriggerGoalMixin extends Goal {
    

    @Redirect(method = "getSkeleton", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/SkeletonEntity;setPosition(DDD)V"))
    private void modifiedEquippedSkeleton(SkeletonEntity skeletonEntity, double x, double y, double z) {
        if (!(skeletonEntity.getEquippedStack(EquipmentSlot.HEAD).isOf(Items.CARVED_PUMPKIN) || skeletonEntity.getEquippedStack(EquipmentSlot.HEAD).isOf(Items.JACK_O_LANTERN))) {
            skeletonEntity.equipStack(EquipmentSlot.HEAD, new ItemStack(Items.CHAINMAIL_HELMET));
        }
        skeletonEntity.equipStack(EquipmentSlot.LEGS, new ItemStack(Items.CHAINMAIL_LEGGINGS));
        skeletonEntity.equipStack(EquipmentSlot.FEET, new ItemStack(Items.CHAINMAIL_BOOTS));
        if (skeletonEntity.getRandom().nextBoolean()) {
            skeletonEntity.equipStack(EquipmentSlot.CHEST, new ItemStack(Items.CHAINMAIL_CHESTPLATE));
        }
        else {
            skeletonEntity.equipStack(EquipmentSlot.CHEST, ItemStack.EMPTY);
        }
        skeletonEntity.setPosition(x, y, z);
    }




}
