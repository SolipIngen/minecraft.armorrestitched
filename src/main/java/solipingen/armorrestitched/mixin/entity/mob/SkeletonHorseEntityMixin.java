package solipingen.armorrestitched.mixin.entity.mob;

import java.util.UUID;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.SkeletonHorseEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import solipingen.armorrestitched.util.interfaces.mixin.entity.mob.NonStandardHorseEntityInterface;


@Mixin(SkeletonHorseEntity.class)
public abstract class SkeletonHorseEntityMixin extends AbstractHorseEntity implements NonStandardHorseEntityInterface {
    private static final UUID HORSE_ARMOR_BONUS_ID = UUID.fromString("556E1665-8B10-40C8-8F9D-CF9B1667F295");
    

    protected SkeletonHorseEntityMixin(EntityType<? extends AbstractHorseEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void onInventoryChanged(Inventory sender) {
        ItemStack itemStack = this.getArmorType();
        super.onInventoryChanged(sender);
        ItemStack itemStack2 = this.getArmorType();
        if (this.age > 20 && this.isHorseArmor(itemStack2) && itemStack != itemStack2) {
            this.playSound(SoundEvents.ENTITY_HORSE_ARMOR, 0.5f, 1.0f);
        }
    }

    @Override
    public boolean hasArmorSlot() {
        return true;
    }

    @Override
    public boolean isHorseArmor(ItemStack item) {
        return item.getItem() instanceof HorseArmorItem;
    }

    @Override
    protected void updateSaddle() {
        if (this.world.isClient) {
            return;
        }
        super.updateSaddle();
        this.setArmorTypeFromStack(this.items.getStack(1));
        this.setEquipmentDropChance(EquipmentSlot.CHEST, 0.0f);
    }

    private void setArmorTypeFromStack(ItemStack stack) {
        this.equipArmor(stack);
        if (!this.world.isClient) {
            int i;
            this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR).removeModifier(HORSE_ARMOR_BONUS_ID);
            if (this.isHorseArmor(stack) && (i = ((HorseArmorItem)stack.getItem()).getBonus()) != 0) {
                this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR).addTemporaryModifier(new EntityAttributeModifier(HORSE_ARMOR_BONUS_ID, "Horse armor bonus", (double)i, EntityAttributeModifier.Operation.ADDITION));
            }
        }
    }

    private void equipArmor(ItemStack stack) {
        this.equipStack(EquipmentSlot.CHEST, stack);
        this.setEquipmentDropChance(EquipmentSlot.CHEST, 0.0f);
    }

    @Override
    public ItemStack getArmorType() {
        return this.getEquippedStack(EquipmentSlot.CHEST);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void injectedWriteCustomDataToNbt(NbtCompound nbt, CallbackInfo cbi) {
        if (!this.items.getStack(1).isEmpty()) {
            nbt.put("ArmorItem", this.items.getStack(1).writeNbt(new NbtCompound()));
        }
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void injectedReadCustomDataFromNbt(NbtCompound nbt, CallbackInfo cbi) {
        ItemStack itemStack;
        if (nbt.contains("ArmorItem", NbtElement.COMPOUND_TYPE) && !(itemStack = ItemStack.fromNbt(nbt.getCompound("ArmorItem"))).isEmpty() && this.isHorseArmor(itemStack)) {
            this.items.setStack(1, itemStack);
        }
        this.updateSaddle();
    }

    @Override
    @Nullable
    protected SoundEvent getAngrySound() {
        return SoundEvents.ENTITY_SKELETON_HORSE_AMBIENT;
    }

    @Redirect(method = "interactMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/SkeletonHorseEntity;isTame()Z"))
    private boolean redirectedIsTame(SkeletonHorseEntity skeletonHorseEntity) {
        return true;
    }

    
}
