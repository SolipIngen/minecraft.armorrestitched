package solipingen.armorrestitched.mixin.entity.mob;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer.*;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.AbstractPiglinEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;


@Mixin(PiglinEntity.class)
public abstract class PiglinEntityMixin extends AbstractPiglinEntity {
    
    
    public PiglinEntityMixin(EntityType<? extends AbstractPiglinEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initialize", at = @At("RETURN"), cancellable = true)
    private void injectedInitialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt, CallbackInfoReturnable<EntityData> cbireturn) {
        if (spawnReason == SpawnReason.STRUCTURE) {
            this.equipStack(EquipmentSlot.HEAD, new ItemStack(Items.GOLDEN_HELMET));
            this.equipStack(EquipmentSlot.CHEST, new ItemStack(Items.GOLDEN_CHESTPLATE));
            this.equipStack(EquipmentSlot.LEGS, new ItemStack(Items.GOLDEN_LEGGINGS));
            this.equipStack(EquipmentSlot.FEET, new ItemStack(Items.GOLDEN_BOOTS));
            this.updateEnchantments(world.getRandom(), difficulty);
        }
    }

    @Redirect(method = "createPiglinAttributes", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;add(Lnet/minecraft/entity/attribute/EntityAttribute;D)Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;"))
    private static Builder redirectedCreateHoglinAttributes(Builder attributeBuilder, EntityAttribute entityAttribute, double baseValue) {
        if (entityAttribute == EntityAttributes.GENERIC_ATTACK_DAMAGE) {
            return attributeBuilder.add(EntityAttributes.GENERIC_ARMOR, 6.0).add(entityAttribute, baseValue);
        }
        return attributeBuilder.add(entityAttribute, baseValue);
    }

    @ModifyConstant(method = "equipAtChance", constant = @Constant(floatValue = 0.1f))
    private float modifiedEquipThreshold(float originalf, EquipmentSlot slot, ItemStack stack, Random random) {
        LocalDifficulty localDifficulty = this.world.getLocalDifficulty(this.getBlockPos());
        return 0.25f*this.world.getDifficulty().getId() + 0.25f*localDifficulty.getClampedLocalDifficulty();
    }

    @Redirect(method = "equipAtChance", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/PiglinEntity;equipStack(Lnet/minecraft/entity/EquipmentSlot;Lnet/minecraft/item/ItemStack;)V"))
    private void redirectedEquipAtChance(PiglinEntity piglinEntity, EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.HEAD) {
            ItemStack itemStack = this.random.nextFloat() < 0.16f*this.world.getDifficulty().getId() + 0.08f*this.world.getLocalDifficulty(this.getBlockPos()).getClampedLocalDifficulty() ? new ItemStack(Items.GOLDEN_HELMET) : new ItemStack(Items.LEATHER_HELMET);
            this.equipStack(slot, itemStack);
        }
        else if (slot == EquipmentSlot.CHEST) {
            ItemStack itemStack = this.random.nextFloat() < 0.16f*this.world.getDifficulty().getId() + 0.08f*this.world.getLocalDifficulty(this.getBlockPos()).getClampedLocalDifficulty() ? new ItemStack(Items.GOLDEN_CHESTPLATE) : new ItemStack(Items.LEATHER_CHESTPLATE);
            this.equipStack(slot, itemStack);
        }
        else if (slot == EquipmentSlot.LEGS) {
            ItemStack itemStack = this.random.nextFloat() < 0.16f*this.world.getDifficulty().getId() + 0.08f*this.world.getLocalDifficulty(this.getBlockPos()).getClampedLocalDifficulty() ? new ItemStack(Items.GOLDEN_LEGGINGS) : new ItemStack(Items.LEATHER_LEGGINGS);
            this.equipStack(slot, itemStack);
        }
        else if (slot == EquipmentSlot.FEET) {
            ItemStack itemStack = this.random.nextFloat() < 0.16f*this.world.getDifficulty().getId() + 0.08f*this.world.getLocalDifficulty(this.getBlockPos()).getClampedLocalDifficulty() ? new ItemStack(Items.GOLDEN_BOOTS) : new ItemStack(Items.LEATHER_BOOTS);
            this.equipStack(slot, itemStack);
        }
    }


}
