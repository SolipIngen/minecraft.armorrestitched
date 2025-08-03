package solipingen.armorrestitched.mixin.entity.mob;

import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.world.World;


@Mixin(ShulkerEntity.class)
public abstract class ShulkerEntityMixin extends GolemEntity implements Monster {
    @Unique private static final EntityAttributeModifier COVERED_ARMOR_TOUGHNESS_BONUS = new EntityAttributeModifier(Identifier.ofVanilla("covered"), 6.0, EntityAttributeModifier.Operation.ADD_VALUE);


    protected ShulkerEntityMixin(EntityType<? extends GolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "setPeekAmount", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/ShulkerEntity;getAttributeInstance(Lnet/minecraft/registry/entry/RegistryEntry;)Lnet/minecraft/entity/attribute/EntityAttributeInstance;", shift = At.Shift.AFTER))
    private void injectedSetPeekAmount(int peekAmount, CallbackInfo cbi) {
        World world = this.getWorld();
        if (!world.isClient) {
            this.getAttributeInstance(EntityAttributes.ARMOR_TOUGHNESS).removeModifier(COVERED_ARMOR_TOUGHNESS_BONUS.id());
            if (peekAmount == 0) {
                this.getAttributeInstance(EntityAttributes.ARMOR_TOUGHNESS).addPersistentModifier(COVERED_ARMOR_TOUGHNESS_BONUS);
            }
        }
    }

    
}

