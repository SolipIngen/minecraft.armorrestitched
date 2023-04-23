package solipingen.armorrestitched.mixin.entity.mob;

import java.util.ArrayList;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.common.collect.Lists;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.entity.passive.AllayEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import solipingen.armorrestitched.item.ModItems;
import solipingen.armorrestitched.util.interfaces.mixin.entity.mob.MobEntityInterface;
import solipingen.armorrestitched.village.ModVillagerProfessions;


@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity implements MobEntityInterface {
    private boolean entranced;
    private int entrancedTime;


    protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectedInit(CallbackInfo cbi) {
        this.entranced = false;
        this.entrancedTime = 0;
    }

    @Inject(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;baseTick()V", shift = At.Shift.AFTER), cancellable = true)
    private void injectedBaseTick(CallbackInfo cbi) {
        if (this.getEntranced()) {
            if (((MobEntity)(Object)this) instanceof VexEntity && this.random.nextFloat() >= 0.9f + 0.01f*this.entrancedTime) {
                AllayEntity allayEntity = EntityType.ALLAY.create(world);
                if (allayEntity != null && this.world instanceof ServerWorld) {
                    allayEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
                    allayEntity.initialize((ServerWorld)this.world, world.getLocalDifficulty(allayEntity.getBlockPos()), SpawnReason.CONVERSION, null, null);
                    allayEntity.setAiDisabled(((MobEntity)(Object)this).isAiDisabled());
                    if (this.hasCustomName()) {
                        allayEntity.setCustomName(this.getCustomName());
                        allayEntity.setCustomNameVisible(this.isCustomNameVisible());
                    }
                    allayEntity.setPersistent();
                    ((MobEntityInterface)allayEntity).setEntranced(false, 0);
                    ((ServerWorld)this.world).spawnEntityAndPassengers(allayEntity);
                    this.discard();
                    cbi.cancel();
                }
            }
            this.entrancedTime -= this.entrancedTime > 0 ? 1 : 0;
            if (this.entrancedTime <= 0) {
                this.setEntranced(false, 0);
            }
        }
    }

    @SuppressWarnings("incomplete-switch")
    @Inject(method = "initEquipment", at = @At("TAIL"))
    private void injectedInitEquipment(Random random, LocalDifficulty localDifficulty, CallbackInfo cbi) {
        float equipThreshold = ((MobEntity)(Object)this) instanceof IllagerEntity ? 0.2f*this.world.getDifficulty().getId() + 0.25f*localDifficulty.getClampedLocalDifficulty() : 0.12f*this.world.getDifficulty().getId() + 0.2f*localDifficulty.getClampedLocalDifficulty();
        float armorTypeThreshold = 0.03f*this.world.getDifficulty().getId() + 0.15f*localDifficulty.getClampedLocalDifficulty();
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() != EquipmentSlot.Type.ARMOR) continue;
            this.equipStack(slot, ItemStack.EMPTY);
            if (random.nextFloat() <= equipThreshold) {
                int level = 0;
                for (int j = 0; j <= 4; j++) {
                    if (random.nextFloat() <= armorTypeThreshold) {
                        level++;
                    }
                }
                Item item = MobEntity.getEquipmentForSlot(slot, level);
                if (item instanceof DyeableArmorItem) {
                    int randomInt = random.nextInt(6);
                    if (randomInt == 0) {
                        switch (slot) {
                            case HEAD: item = ModItems.COTTON_HELMET; break;
                            case CHEST: item = ModItems.COTTON_CHESTPLATE; break;
                            case LEGS: item = ModItems.COTTON_LEGGINGS; break;
                            case FEET: item = ModItems.COTTON_BOOTS; break;
                        }
                    }
                    else if (randomInt == 1) {
                        switch (slot) {
                            case HEAD: item = ModItems.FUR_HELMET; break;
                            case CHEST: item = ModItems.FUR_CHESTPLATE; break;
                            case LEGS: item = ModItems.FUR_LEGGINGS; break;
                            case FEET: item = ModItems.FUR_BOOTS; break;
                        }
                    }
                    else if (randomInt == 2) {
                        switch (slot) {
                            case HEAD: item = ModItems.LINEN_HELMET; break;
                            case CHEST: item = ModItems.LINEN_CHESTPLATE; break;
                            case LEGS: item = ModItems.LINEN_LEGGINGS; break;
                            case FEET: item = ModItems.LINEN_BOOTS; break;
                        }
                    }
                    else if (randomInt == 3) {
                        switch (slot) {
                            case HEAD: item = ModItems.SILK_HELMET; break;
                            case CHEST: item = ModItems.SILK_CHESTPLATE; break;
                            case LEGS: item = ModItems.SILK_LEGGINGS; break;
                            case FEET: item = ModItems.SILK_BOOTS; break;
                        }
                    }
                    else if (randomInt == 4) {
                        switch (slot) {
                            case HEAD: item = ModItems.PAPER_HELMET; break;
                            case CHEST: item = ModItems.PAPER_CHESTPLATE; break;
                            case LEGS: item = ModItems.PAPER_LEGGINGS; break;
                            case FEET: item = ModItems.PAPER_BOOTS; break;
                        }
                    }
                }
                if (item != null) {
                    ItemStack itemStack = item instanceof DyeableArmorItem ? this.getRandomlyDyedClothing(item) : new ItemStack(item);
                    this.equipStack(slot, itemStack);
                }
            }
        }
    }

    @Redirect(method = "getEquipmentForSlot", at = @At(value = "FIELD", target = "Lnet/minecraft/item/Items;GOLDEN_HELMET:Lnet/minecraft/item/Item;", opcode = Opcodes.GETSTATIC))
    private static Item redirectedGoldenHelmet(EquipmentSlot equipmentSlot, int equipmentLevel) {
        return ModItems.COPPER_HELMET;
    }

    @Redirect(method = "getEquipmentForSlot", at = @At(value = "FIELD", target = "Lnet/minecraft/item/Items;GOLDEN_CHESTPLATE:Lnet/minecraft/item/Item;", opcode = Opcodes.GETSTATIC))
    private static Item redirectedGoldenChestplate(EquipmentSlot equipmentSlot, int equipmentLevel) {
        return ModItems.COPPER_CHESTPLATE;
    }

    @Redirect(method = "getEquipmentForSlot", at = @At(value = "FIELD", target = "Lnet/minecraft/item/Items;GOLDEN_LEGGINGS:Lnet/minecraft/item/Item;", opcode = Opcodes.GETSTATIC))
    private static Item redirectedGoldenLeggings(EquipmentSlot equipmentSlot, int equipmentLevel) {
        return ModItems.COPPER_LEGGINGS;
    }

    @Redirect(method = "getEquipmentForSlot", at = @At(value = "FIELD", target = "Lnet/minecraft/item/Items;GOLDEN_BOOTS:Lnet/minecraft/item/Item;", opcode = Opcodes.GETSTATIC))
    private static Item redirectedGoldenBoots(EquipmentSlot equipmentSlot, int equipmentLevel) {
        return ModItems.COPPER_BOOTS;
    }

    @Redirect(method = "getEquipmentForSlot", at = @At(value = "FIELD", target = "Lnet/minecraft/item/Items;CHAINMAIL_HELMET:Lnet/minecraft/item/Item;", opcode = Opcodes.GETSTATIC))
    private static Item redirectedChainmailHelmet(EquipmentSlot equipmentSlot, int equipmentLevel) {
        return Items.IRON_HELMET;
    }

    @Redirect(method = "getEquipmentForSlot", at = @At(value = "FIELD", target = "Lnet/minecraft/item/Items;CHAINMAIL_CHESTPLATE:Lnet/minecraft/item/Item;", opcode = Opcodes.GETSTATIC))
    private static Item redirectedChainmailChestplate(EquipmentSlot equipmentSlot, int equipmentLevel) {
        return Items.IRON_CHESTPLATE;
    }

    @Redirect(method = "getEquipmentForSlot", at = @At(value = "FIELD", target = "Lnet/minecraft/item/Items;CHAINMAIL_LEGGINGS:Lnet/minecraft/item/Item;", opcode = Opcodes.GETSTATIC))
    private static Item redirectedChainmailLeggings(EquipmentSlot equipmentSlot, int equipmentLevel) {
        return Items.IRON_LEGGINGS;
    }

    @Redirect(method = "getEquipmentForSlot", at = @At(value = "FIELD", target = "Lnet/minecraft/item/Items;CHAINMAIL_BOOTS:Lnet/minecraft/item/Item;", opcode = Opcodes.GETSTATIC))
    private static Item redirectedChainmailBoots(EquipmentSlot equipmentSlot, int equipmentLevel) {
        return Items.IRON_BOOTS;
    }
    
    @Redirect(method = "getEquipmentForSlot", at = @At(value = "FIELD", target = "Lnet/minecraft/item/Items;IRON_HELMET:Lnet/minecraft/item/Item;", opcode = Opcodes.GETSTATIC))
    private static Item redirectedIronHelmet(EquipmentSlot equipmentSlot, int equipmentLevel) {
        return Items.GOLDEN_HELMET;
    }

    @Redirect(method = "getEquipmentForSlot", at = @At(value = "FIELD", target = "Lnet/minecraft/item/Items;IRON_CHESTPLATE:Lnet/minecraft/item/Item;", opcode = Opcodes.GETSTATIC))
    private static Item redirectedIronChestplate(EquipmentSlot equipmentSlot, int equipmentLevel) {
        return Items.GOLDEN_CHESTPLATE;
    }

    @Redirect(method = "getEquipmentForSlot", at = @At(value = "FIELD", target = "Lnet/minecraft/item/Items;IRON_LEGGINGS:Lnet/minecraft/item/Item;", opcode = Opcodes.GETSTATIC))
    private static Item redirectedIronLeggings(EquipmentSlot equipmentSlot, int equipmentLevel) {
        return Items.GOLDEN_LEGGINGS;
    }

    @Redirect(method = "getEquipmentForSlot", at = @At(value = "FIELD", target = "Lnet/minecraft/item/Items;IRON_BOOTS:Lnet/minecraft/item/Item;", opcode = Opcodes.GETSTATIC))
    private static Item redirectedIronBoots(EquipmentSlot equipmentSlot, int equipmentLevel) {
        return Items.GOLDEN_BOOTS;
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void injectedWriteCustomDataToNbt(NbtCompound nbt, CallbackInfo cbi) {
        nbt.putBoolean("Entranced", this.entranced);
        nbt.putInt("EntrancedTime", this.entrancedTime);
    }
    
    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void injectedReadCustomDataFromNbt(NbtCompound nbt, CallbackInfo cbi) {
        this.entranced = nbt.getBoolean("Entranced");
        this.entrancedTime = nbt.getInt("EntrancedTime");
        ((MobEntity)(Object)this).setAiDisabled(this.entranced);
    }

    @Override
    public boolean getEntranced() {
        return this.entranced;
    }

    @Override
    public void setEntranced(boolean entranced, int duration) {
        this.entranced = entranced;
        this.entrancedTime = duration;
        ((MobEntity)(Object)this).setAiDisabled(entranced);
        if (((MobEntity)(Object)this) instanceof Angerable && entranced) {
            ((Angerable)this).setAngryAt(null);
        }
    }

    private ItemStack getRandomlyDyedClothing(Item dyeableArmorItem) {
        if (dyeableArmorItem instanceof DyeableArmorItem) {
            ItemStack itemStack = new ItemStack(dyeableArmorItem);
            ArrayList<DyeItem> list = Lists.newArrayList();
            list.add(ModVillagerProfessions.SellDyedItemFactory.getDye(this.random));
            if (this.random.nextFloat() > 0.7f) {
                list.add(ModVillagerProfessions.SellDyedItemFactory.getDye(this.random));
            }
            if (this.random.nextFloat() > 0.8f) {
                list.add(ModVillagerProfessions.SellDyedItemFactory.getDye(this.random));
            }
            itemStack = DyeableItem.blendAndSetColor(itemStack, list);
            return itemStack;
        }
        return ItemStack.EMPTY;
    }



    
}

