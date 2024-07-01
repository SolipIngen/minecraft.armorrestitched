package solipingen.armorrestitched.mixin.entity.mob;


import java.util.ArrayList;

import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.registry.tag.ItemTags;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.common.collect.Lists;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import solipingen.armorrestitched.item.ModItems;
import solipingen.armorrestitched.village.ModVillagerProfessions;


@Mixin(DrownedEntity.class)
public abstract class DrownedEntityMixin extends ZombieEntity implements RangedAttackMob {


    public DrownedEntityMixin(EntityType<? extends ZombieEntity> entityType, World world) {
        super(entityType, world);
    }

    @SuppressWarnings("incomplete-switch")
    @Inject(method = "initEquipment", at = @At("TAIL"))
    private void injectedInitEquipment(Random random, LocalDifficulty localDifficulty, CallbackInfo cbi) {
        float equipThreshold = 0.12f*this.getWorld().getDifficulty().getId() + 0.2f*localDifficulty.getClampedLocalDifficulty();
        float armorTypeThreshold = 0.03f*this.getWorld().getDifficulty().getId() + 0.15f*localDifficulty.getClampedLocalDifficulty();
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (!slot.isArmorSlot()) continue;
            this.equipStack(slot, ItemStack.EMPTY);
            if (random.nextFloat() <= equipThreshold) {
                int level = 0;
                for (int j = 0; j <= 4; j++) {
                    if (random.nextFloat() <= armorTypeThreshold) {
                        level++;
                    }
                }
                Item item = DrownedEntityMixin.getModEquipmentForSlot(slot, level);
                if (item != null && item.getDefaultStack().isIn(ItemTags.DYEABLE)) {
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
                    ItemStack itemStack = item.getDefaultStack().isIn(ItemTags.DYEABLE) ? this.getRandomlyDyedClothing(item) : new ItemStack(item);
                    this.equipStack(slot, itemStack);
                }
            }
        }
    }

    @Nullable
    private static Item getModEquipmentForSlot(EquipmentSlot equipmentSlot, int equipmentLevel) {
        switch (equipmentSlot) {
            case HEAD: {
                if (equipmentLevel == 0) {
                    return Items.LEATHER_HELMET;
                }
                else if (equipmentLevel == 1) {
                    return ModItems.COPPER_HELMET;
                }
                else if (equipmentLevel == 2) {
                    return Items.IRON_HELMET;
                }
                else if (equipmentLevel == 3) {
                    return Items.GOLDEN_HELMET;
                }
                else if (equipmentLevel == 4) {
                    return Items.DIAMOND_HELMET;
                }
            }
            case CHEST: {
                if (equipmentLevel == 0) {
                    return Items.LEATHER_CHESTPLATE;
                }
                else if (equipmentLevel == 1) {
                    return ModItems.COPPER_CHESTPLATE;
                }
                else if (equipmentLevel == 2) {
                    return Items.IRON_CHESTPLATE;
                }
                else if (equipmentLevel == 3) {
                    return Items.GOLDEN_CHESTPLATE;
                }
                else if (equipmentLevel == 4) {
                    return Items.DIAMOND_CHESTPLATE;
                }
            }
            case LEGS: {
                if (equipmentLevel == 0) {
                    return Items.LEATHER_LEGGINGS;
                }
                else if (equipmentLevel == 1) {
                    return ModItems.COPPER_LEGGINGS;
                }
                else if (equipmentLevel == 2) {
                    return Items.IRON_LEGGINGS;
                }
                else if (equipmentLevel == 3) {
                    return Items.GOLDEN_LEGGINGS;
                }
                else if (equipmentLevel == 4) {
                    return Items.DIAMOND_LEGGINGS;
                }
            }
            case FEET: {
                if (equipmentLevel == 0) {
                    return Items.LEATHER_BOOTS;
                }
                else if (equipmentLevel == 1) {
                    return ModItems.COPPER_BOOTS;
                }
                else if (equipmentLevel == 2) {
                    return Items.IRON_BOOTS;
                }
                else if (equipmentLevel == 3) {
                    return Items.GOLDEN_BOOTS;
                }
                else if (equipmentLevel == 4) {
                    return Items.DIAMOND_BOOTS;
                }
            }
            default: {
                return null;
            }
        }
    }

    private ItemStack getRandomlyDyedClothing(Item dyeableArmorItem) {
        if (dyeableArmorItem.getDefaultStack().isIn(ItemTags.DYEABLE)) {
            ItemStack itemStack = new ItemStack(dyeableArmorItem);
            ArrayList<DyeItem> list = Lists.newArrayList();
            list.add(ModVillagerProfessions.SellDyedItemFactory.getDye(this.random));
            if (this.random.nextFloat() > 0.7f) {
                list.add(ModVillagerProfessions.SellDyedItemFactory.getDye(this.random));
            }
            if (this.random.nextFloat() > 0.8f) {
                list.add(ModVillagerProfessions.SellDyedItemFactory.getDye(this.random));
            }
            itemStack = DyedColorComponent.setColor(itemStack, list);
            return itemStack;
        }
        return ItemStack.EMPTY;
    }

    
}
