package solipingen.armorrestitched.mixin.item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.serialization.Lifecycle;

import net.minecraft.item.DyeableHorseArmorItem;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.SaddleItem;
import net.minecraft.item.ShearsItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;


@Mixin(Items.class)
public abstract class ItemsMixin {

    @SuppressWarnings("all")
    @Inject(method = "register(Lnet/minecraft/util/Identifier;Lnet/minecraft/item/Item;)Lnet/minecraft/item/Item;", at = @At("RETURN"), cancellable = true)
    private static void injectedRegister(Identifier id, Item item, CallbackInfoReturnable<Item> cbireturn) {
        String name = id.getPath();
        int rawId = Item.getRawId(item);
        if (item instanceof HorseArmorItem) {
            if (name.matches("leather_horse_armor")) {
                Item newHorseArmorItem = (Item)new DyeableHorseArmorItem(3, "leather", new Item.Settings());
                cbireturn.setReturnValue(((SimpleRegistry<Item>)Registries.ITEM).set(rawId, RegistryKey.of(Registries.ITEM.getKey(), id), newHorseArmorItem, Lifecycle.stable()).value());
            }
            else if (name.matches("iron_horse_armor")) {
                Item newHorseArmorItem = (Item)new HorseArmorItem(9, "iron", new Item.Settings());
                cbireturn.setReturnValue(((SimpleRegistry<Item>)Registries.ITEM).set(rawId, RegistryKey.of(Registries.ITEM.getKey(), id), newHorseArmorItem, Lifecycle.stable()).value());
            }
            else if (name.matches("golden_horse_armor")) {
                Item newHorseArmorItem = (Item)new HorseArmorItem(6, "gold", new Item.Settings());
                cbireturn.setReturnValue(((SimpleRegistry<Item>)Registries.ITEM).set(rawId, RegistryKey.of(Registries.ITEM.getKey(), id), newHorseArmorItem, Lifecycle.stable()).value());
            }
            else if (name.matches("diamond_horse_armor")) {
                Item newHorseArmorItem = (Item)new HorseArmorItem(14, "diamond", new Item.Settings());
                cbireturn.setReturnValue(((SimpleRegistry<Item>)Registries.ITEM).set(rawId, RegistryKey.of(Registries.ITEM.getKey(), id), newHorseArmorItem, Lifecycle.stable()).value());
            }
        }
        else if (item instanceof SaddleItem) {
            Item newSaddleItem = (Item)new SaddleItem(new Item.Settings());
            cbireturn.setReturnValue(((SimpleRegistry<Item>)Registries.ITEM).set(rawId, RegistryKey.of(Registries.ITEM.getKey(), id), newSaddleItem, Lifecycle.stable()).value());
        }
        else if (item instanceof ShearsItem) {
            Item newShearsItem = (Item)new ShearsItem(new Item.Settings().maxDamage(517));
            cbireturn.setReturnValue(((SimpleRegistry<Item>)Registries.ITEM).set(rawId, RegistryKey.of(Registries.ITEM.getKey(), id), newShearsItem, Lifecycle.stable()).value());
        }
        else if (item instanceof ElytraItem) {
            Item newElytraItem = (Item)new ElytraItem(new Item.Settings().maxDamage(888).rarity(Rarity.UNCOMMON));
            cbireturn.setReturnValue(((SimpleRegistry<Item>)Registries.ITEM).set(rawId, RegistryKey.of(Registries.ITEM.getKey(), id), newElytraItem, Lifecycle.stable()).value());
        }
    }
    
}

