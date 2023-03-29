package solipingen.armorrestitched.mixin.item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.item.DyeableHorseArmorItem;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.SaddleItem;
import net.minecraft.item.ShearsItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;


@Mixin(Items.class)
public abstract class ItemsMixin {

    
    @Inject(method = "register(Lnet/minecraft/util/Identifier;Lnet/minecraft/item/Item;)Lnet/minecraft/item/Item;", at = @At("RETURN"), cancellable = true)
    private static void injectedRegister(Identifier id, Item item, CallbackInfoReturnable<Item> cbireturn) {
        String name = id.getPath();
        int rawId = Item.getRawId(item);
        if (item instanceof HorseArmorItem) {
            if (name.matches("leather_horse_armor")) {
                Item newHorseArmorItem = (Item)new DyeableHorseArmorItem(3, "leather", new Item.Settings());
                cbireturn.setReturnValue(Registry.register(Registries.ITEM, rawId, name, newHorseArmorItem));
            }
            else if (name.matches("iron_horse_armor")) {
                Item newHorseArmorItem = (Item)new HorseArmorItem(9, "iron", new Item.Settings());
                cbireturn.setReturnValue(Registry.register(Registries.ITEM, rawId, name, newHorseArmorItem));
            }
            else if (name.matches("golden_horse_armor")) {
                Item newHorseArmorItem = (Item)new HorseArmorItem(7, "gold", new Item.Settings());
                cbireturn.setReturnValue(Registry.register(Registries.ITEM, rawId, name, newHorseArmorItem));
            }
            else if (name.matches("diamond_horse_armor")) {
                Item newHorseArmorItem = (Item)new HorseArmorItem(12, "diamond", new Item.Settings());
                cbireturn.setReturnValue(Registry.register(Registries.ITEM, rawId, name, newHorseArmorItem));
            }
        }
        else if (item instanceof SaddleItem) {
            Item newSaddleItem = (Item)new SaddleItem(new Item.Settings());
            cbireturn.setReturnValue(Registry.register(Registries.ITEM, rawId, name, newSaddleItem));
        }
        else if (item instanceof ShearsItem) {
            Item newShearsItem = (Item)new ShearsItem(new Item.Settings().maxDamage(517));
            cbireturn.setReturnValue(Registry.register(Registries.ITEM, rawId, name, newShearsItem));
        }
        else if (item instanceof ElytraItem) {
            Item newElytraItem = (Item)new ElytraItem(new Item.Settings().maxDamage(864).rarity(Rarity.UNCOMMON));
            cbireturn.setReturnValue(Registry.register(Registries.ITEM, rawId, name, newElytraItem));
        }
    }
    
}

