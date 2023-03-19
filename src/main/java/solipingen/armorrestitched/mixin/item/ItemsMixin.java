package solipingen.armorrestitched.mixin.item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.item.DyeableHorseArmorItem;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.SaddleItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


@Mixin(Items.class)
public abstract class ItemsMixin {

    
    @Inject(method = "register(Lnet/minecraft/util/Identifier;Lnet/minecraft/item/Item;)Lnet/minecraft/item/Item;", at = @At("TAIL"), cancellable = true)
    private static void injectedRegister(Identifier id, Item item, CallbackInfoReturnable<Item> cbireturn) {
        String idPath = id.getPath();
        if (item instanceof HorseArmorItem) {
            if (idPath.matches("iron_horse_armor")) {
                Item newHorseArmorItem = (Item)new HorseArmorItem(9, "iron", new Item.Settings());
                int horseArmorRawId = Item.getRawId(item);
                cbireturn.setReturnValue(Registry.register(Registries.ITEM, horseArmorRawId, "iron_horse_armor", newHorseArmorItem));
            }
            else if (idPath.matches("golden_horse_armor")) {
                Item newHorseArmorItem = (Item)new HorseArmorItem(7, "gold", new Item.Settings());
                int horseArmorRawId = Item.getRawId(item);
                cbireturn.setReturnValue(Registry.register(Registries.ITEM, horseArmorRawId, "golden_horse_armor", newHorseArmorItem));
            }
            else if (idPath.matches("diamond_horse_armor")) {
                Item newHorseArmorItem = (Item)new HorseArmorItem(13, "diamond", new Item.Settings());
                int horseArmorRawId = Item.getRawId(item);
                cbireturn.setReturnValue(Registry.register(Registries.ITEM, horseArmorRawId, "diamond_horse_armor", newHorseArmorItem));
            }
            else if (idPath.matches("leather_horse_armor")) {
                Item newHorseArmorItem = (Item)new DyeableHorseArmorItem(3, "leather", new Item.Settings());
                int horseArmorRawId = Item.getRawId(item);
                cbireturn.setReturnValue(Registry.register(Registries.ITEM, horseArmorRawId, "leather_horse_armor", newHorseArmorItem));
            }
        }
        else if (item instanceof SaddleItem) {
            if (idPath.matches("saddle")) {
                Item newSaddleItem = (Item)new SaddleItem(new Item.Settings());
                int saddleRawId = Item.getRawId(item);
                cbireturn.setReturnValue(Registry.register(Registries.ITEM, saddleRawId, "saddle", newSaddleItem));
            }
        }

    }
    
}

