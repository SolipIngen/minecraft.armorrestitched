package solipingen.armorrestitched.mixin.entity.passive;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import solipingen.armorrestitched.block.ModBlocks;
import solipingen.armorrestitched.item.ModItems;


@Mixin(AbstractHorseEntity.class)
public abstract class AbstractHorseEntityMixin extends AnimalEntity {

    @Invoker("playEatingAnimation")
    public abstract void invokePlayEatingAnimation();

    protected AbstractHorseEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Redirect(method = "createBaseHorseAttributes", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;add(Lnet/minecraft/entity/attribute/EntityAttribute;D)Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;"))
    private static DefaultAttributeContainer.Builder redirectedCreateAbstractHorseAttributes(DefaultAttributeContainer.Builder attributeBuilder, EntityAttribute entityAttribute, double baseValue) {
        if (entityAttribute == EntityAttributes.GENERIC_MAX_HEALTH) {
            return attributeBuilder.add(entityAttribute, baseValue).add(EntityAttributes.GENERIC_ARMOR, 6.0);
        }
        return attributeBuilder.add(entityAttribute, baseValue);
    }

    @Inject(method = "receiveFood", at = @At("HEAD"), cancellable = true)
    private void injectedReceiveFoodIsOf(PlayerEntity player, ItemStack item, CallbackInfoReturnable<Boolean> cbireturn) {
        boolean bl = false;
        float f = 0.0f;
        int i = 0;
        int j = 0;
        World world = this.getWorld();
        if (item.isOf(ModItems.FLAX_STEM)) {
            f = 2.0f;
            i = 20;
            j = 3;
        } 
        else if (item.isOf(ModBlocks.FLAX_BLOCK.asItem())) {
            f = 20.0f;
            i = 180;
        }
        if (this.getHealth() < this.getMaxHealth() && f > 0.0f) {
            this.heal(f);
            bl = true;
        }
        if (this.isBaby() && i > 0) {
            world.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getParticleX(1.0), this.getRandomBodyY() + 0.5, this.getParticleZ(1.0), 0.0, 0.0, 0.0);
            if (!world.isClient) {
                this.growUp(i);
            }
            bl = true;
        }
        if (j > 0 && (bl || !((AbstractHorseEntity)(Object)this).isTame()) && ((AbstractHorseEntity)(Object)this).getTemper() < ((AbstractHorseEntity)(Object)this).getMaxTemper()) {
            bl = true;
            if (!world.isClient) {
                ((AbstractHorseEntity)(Object)this).addTemper(j);
            }
        }
        if (bl) {
            this.invokePlayEatingAnimation();
            this.emitGameEvent(GameEvent.EAT);
            cbireturn.setReturnValue(bl);
        }
    }

    @Inject(method = "isBreedingItem", at = @At("HEAD"), cancellable = true)
    private void injectedIsBreedingItem(ItemStack stack, CallbackInfoReturnable<Boolean> cbireturn) {
        if (stack.isOf(ModItems.FLAX_STEM) || stack.isOf(ModBlocks.FLAX_BLOCK.asItem())) {
            cbireturn.setReturnValue(true);
        }
    }


    
}
