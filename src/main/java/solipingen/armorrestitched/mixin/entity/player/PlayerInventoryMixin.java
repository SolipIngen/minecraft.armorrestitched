package solipingen.armorrestitched.mixin.entity.player;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.item.trim.ArmorTrimMaterial;
import net.minecraft.item.trim.ArmorTrimMaterials;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.sound.SoundEvents;


@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin {
    

    @Inject(method = "damageArmor", at = @At("HEAD"), cancellable = true)
    private void injectedDamageArmor(DamageSource damageSource, float amount, int[] slots, CallbackInfo cbi) {
        if (amount <= 0.0f) {
            cbi.cancel();
        }
        for (int i : slots) {
            ItemStack itemStack = ((PlayerInventory)(Object)this).armor.get(i);
            if ((damageSource.isIn(DamageTypeTags.IS_FIRE) && itemStack.getItem().isFireproof()) || !(itemStack.getItem() instanceof ArmorItem || (itemStack.getItem() instanceof ElytraItem && ElytraItem.isUsable(itemStack)))) continue;
            EquipmentSlot currentSlot = EquipmentSlot.fromTypeIndex(EquipmentSlot.Type.ARMOR, i);
            float currentAmount = amount;
            if (itemStack.getItem() instanceof ArmorItem) {
                float toughness = 1.0f + ((ArmorItem)itemStack.getItem()).getToughness();
                currentAmount /= toughness;
                itemStack.damage(Math.round(currentAmount), ((PlayerInventory)(Object)this).player, player -> player.sendEquipmentBreakStatus(currentSlot));
            }
            else if (itemStack.getItem() instanceof ElytraItem && ElytraItem.isUsable(itemStack)) {
                Optional<ArmorTrim> trimOptional = ArmorTrim.getTrim(((PlayerInventory)(Object)this).player.getWorld().getRegistryManager(), itemStack, false);
                if (trimOptional.isPresent()) {
                    RegistryEntry<ArmorTrimMaterial> trimMaterial = trimOptional.get().getMaterial();
                    float toughness = 1.0f;
                    if (trimMaterial.matchesKey(ArmorTrimMaterials.COPPER) || trimMaterial.matchesKey(ArmorTrimMaterials.GOLD)) {
                        toughness += 0.25f;
                    }
                    else if (trimMaterial.matchesKey(ArmorTrimMaterials.IRON)) {
                        toughness += 0.5f;
                    }
                    else if (trimMaterial.matchesKey(ArmorTrimMaterials.DIAMOND) || trimMaterial.matchesKey(ArmorTrimMaterials.NETHERITE)) {
                        toughness += 1.0f;
                    }
                    currentAmount /= toughness;
                    itemStack.damage(Math.round(currentAmount), ((PlayerInventory)(Object)this).player, player -> player.sendEquipmentBreakStatus(currentSlot));
                    if (!ElytraItem.isUsable(itemStack)) {
                        itemStack.removeSubNbt("Trim");
                        if (!((PlayerInventory)(Object)this).player.isSilent()) {
                            ((PlayerInventory)(Object)this).player.getWorld().playSound(((PlayerInventory)(Object)this).player.getX(), ((PlayerInventory)(Object)this).player.getY(), ((PlayerInventory)(Object)this).player.getZ(), 
                                SoundEvents.ENTITY_ITEM_BREAK, ((PlayerInventory)(Object)this).player.getSoundCategory(), 0.8f, 0.8f + 0.4f*((PlayerInventory)(Object)this).player.getRandom().nextFloat(), false);
                        }
                    }
                }
            }
        }
        cbi.cancel();
    }


}
