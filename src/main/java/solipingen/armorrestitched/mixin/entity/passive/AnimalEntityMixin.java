package solipingen.armorrestitched.mixin.entity.passive;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import solipingen.armorrestitched.item.ModItems;


@Mixin(AnimalEntity.class)
public abstract class AnimalEntityMixin extends PassiveEntity {


    protected AnimalEntityMixin(EntityType<? extends PassiveEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "isBreedingItem", at = @At("HEAD"), cancellable = true)
    private void redirectedIsOf(ItemStack stack, CallbackInfoReturnable<Boolean> cbireturn) {
        if (stack.isOf(ModItems.FLAX_STEM)) {
            cbireturn.setReturnValue(true);
        }
    }

    
}
