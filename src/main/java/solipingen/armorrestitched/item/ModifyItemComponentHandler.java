package solipingen.armorrestitched.item;

import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;

import java.util.ArrayList;


public class ModifyItemComponentHandler implements DefaultItemComponentEvents.ModifyCallback {


    @Override
    public void modify(DefaultItemComponentEvents.ModifyContext context) {
        ArrayList<ArmorItem> armorItemList = new ArrayList<>();
        for (Item item : Registries.ITEM) {
            if (!(item instanceof ArmorItem armorItem)) continue;
            armorItemList.add(armorItem);
        }
        for (ArmorItem armorItem : armorItemList) {
            ComponentMap componentMap = armorItem.getComponents();
            context.modify(armorItem, builder -> {
                if (componentMap.contains(DataComponentTypes.MAX_STACK_SIZE)) {
                    if (armorItem instanceof AnimalArmorItem animalArmorItem && animalArmorItem.getType() == AnimalArmorItem.Type.EQUESTRIAN) {
                        builder.add(DataComponentTypes.MAX_STACK_SIZE, 16);
                    }
                }
            });
        }
        context.modify(Items.ELYTRA, builder -> builder.add(DataComponentTypes.MAX_DAMAGE, 888));
        context.modify(Items.SADDLE, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 16));
        context.modify(Items.SHEARS, builder -> builder.add(DataComponentTypes.MAX_DAMAGE, 357));
    }


}
