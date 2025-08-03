package solipingen.armorrestitched.item;

import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.EnchantableComponent;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.*;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;


public class ModifyItemComponentHandler implements DefaultItemComponentEvents.ModifyCallback {


    @Override
    public void modify(DefaultItemComponentEvents.ModifyContext context) {
        for (Item item : Registries.ITEM) {
            String name = Registries.ITEM.getId(item).getPath();
            if (name.endsWith("horse_armor")) {
                ComponentMap componentMap = item.getComponents();
                context.modify(item, builder -> {
                    if (componentMap.contains(DataComponentTypes.MAX_STACK_SIZE)) {
                        builder.add(DataComponentTypes.MAX_STACK_SIZE, 16);
                    }
                });
            }
        }
        context.modify(Items.ELYTRA, builder -> {
            AttributeModifiersComponent.Builder modifierBuilder = AttributeModifiersComponent.builder();
            AttributeModifierSlot attributeModifierSlot = AttributeModifierSlot.forEquipmentSlot(EquipmentSlot.CHEST);
            Identifier identifier = Identifier.ofVanilla("armor." + EquipmentType.CHESTPLATE.getName());
            modifierBuilder.add(EntityAttributes.ARMOR, new EntityAttributeModifier(identifier, 1.0, EntityAttributeModifier.Operation.ADD_VALUE), attributeModifierSlot);
            modifierBuilder.add(EntityAttributes.ARMOR_TOUGHNESS, new EntityAttributeModifier(identifier, 0.0, EntityAttributeModifier.Operation.ADD_VALUE), attributeModifierSlot);
            modifierBuilder.add(EntityAttributes.KNOCKBACK_RESISTANCE, new EntityAttributeModifier(identifier, 0.0, EntityAttributeModifier.Operation.ADD_VALUE), attributeModifierSlot);
            builder.add(DataComponentTypes.ATTRIBUTE_MODIFIERS, modifierBuilder.build());

            builder.add(DataComponentTypes.ENCHANTABLE, new EnchantableComponent(8));
            builder.add(DataComponentTypes.MAX_DAMAGE, 888);
        });
        context.modify(Items.SADDLE, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 16));
        context.modify(Items.SHEARS, builder -> builder.add(DataComponentTypes.MAX_DAMAGE, 357));
    }


}
