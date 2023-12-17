package solipingen.armorrestitched.mixin.entity.passive;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.world.World;
import solipingen.armorrestitched.item.ModItems;


@Mixin(ChickenEntity.class)
public abstract class ChickenEntityMixin extends AnimalEntity {

    
    protected ChickenEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initGoals", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/passive/ChickenEntity;BREEDING_INGREDIENT:Lnet/minecraft/recipe/Ingredient;", opcode = Opcodes.GETSTATIC, shift = At.Shift.AFTER))
    private void injectedTemptGoal(CallbackInfo cbi) {
        this.goalSelector.add(3, new TemptGoal(this, 1.0, Ingredient.ofItems(ModItems.COTTON_SEEDS, ModItems.FLAXSEED), false));
    }

    @Inject(method = "isBreedingItem", at = @At("HEAD"), cancellable = true)
    private void injectedIsBreedingIngredient(ItemStack stack, CallbackInfoReturnable<Boolean> cbireturn) {
        if (stack.isOf(ModItems.COTTON_SEEDS) || stack.isOf(ModItems.FLAXSEED)) {
            cbireturn.setReturnValue(true);
        }
    }



    
}
