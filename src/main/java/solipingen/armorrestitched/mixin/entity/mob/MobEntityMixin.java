package solipingen.armorrestitched.mixin.entity.mob;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import solipingen.armorrestitched.item.ModItems;


@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity {


    protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initEquipment", at = @At("TAIL"))
    private void injectedInitEquipment(Random random, LocalDifficulty localDifficulty, CallbackInfo cbi) {
        float equipThreshold = ((MobEntity)(Object)this) instanceof IllagerEntity ? 0.15f*this.world.getDifficulty().getId() + 0.25f*localDifficulty.getClampedLocalDifficulty() : 0.08f*this.world.getDifficulty().getId() + 0.15f*localDifficulty.getClampedLocalDifficulty();
        float armorTypeThreshold = 0.04f*this.world.getDifficulty().getId() + 0.1f*localDifficulty.getClampedLocalDifficulty();
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
                if (item != null) {
                    ItemStack itemStack = new ItemStack(item);
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



    
}

