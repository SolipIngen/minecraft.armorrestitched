package solipingen.armorrestitched.mixin.entity.mob;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.VariantHolder;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.world.World;


@Mixin(ShulkerEntity.class)
public abstract class ShulkerEntityMixin extends GolemEntity implements VariantHolder<Optional<DyeColor>>, Monster {
    private static final EntityAttributeModifier COVERED_ARMOR_TOUGHNESS_BONUS = new EntityAttributeModifier("Covered armor toughness bonus", 6.0, EntityAttributeModifier.Operation.ADDITION);


    protected ShulkerEntityMixin(EntityType<? extends GolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "setPeekAmount", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/ShulkerEntity;getAttributeInstance(Lnet/minecraft/entity/attribute/EntityAttribute;)Lnet/minecraft/entity/attribute/EntityAttributeInstance;", shift = At.Shift.AFTER))
    private void injectedSetPeekAmount(int peekAmount, CallbackInfo cbi) {
        World world = this.getWorld();
        if (!world.isClient) {
            this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR_TOUGHNESS).tryRemoveModifier(COVERED_ARMOR_TOUGHNESS_BONUS.getId());
            if (peekAmount == 0) {
                this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR_TOUGHNESS).addPersistentModifier(COVERED_ARMOR_TOUGHNESS_BONUS);
            }
        }
    }

    
}

