package solipingen.armorrestitched.mixin.entity.passive;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import solipingen.armorrestitched.item.ModItems;


@Mixin(AnimalEntity.class)
public abstract class AnimalEntityMixin extends PassiveEntity {


    protected AnimalEntityMixin(EntityType<? extends PassiveEntity> entityType, World world) {
        super(entityType, world);
    }

    @Redirect(method = "isBreedingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private boolean redirectedIsOf(ItemStack stack, Item originalItem) {
        return stack.isOf(originalItem) || stack.isOf(ModItems.FLAX_STEM);
    }

    
}
