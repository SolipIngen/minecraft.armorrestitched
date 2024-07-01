package solipingen.armorrestitched.mixin.item;

import java.util.function.Supplier;

import com.google.common.base.Suppliers;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.item.ArmorItem;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.Equipment;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ElytraItem.class)
public abstract class ElytraItemMixin extends Item implements Equipment {
    @Unique private final Supplier<AttributeModifiersComponent> attributeModifiers = Suppliers.memoize(() -> {
        AttributeModifiersComponent.Builder builder= AttributeModifiersComponent.builder();
        AttributeModifierSlot attributeModifierSlot = AttributeModifierSlot.forEquipmentSlot(this.getSlotType());
        Identifier identifier = Identifier.ofVanilla("armor." + ArmorItem.Type.CHESTPLATE.getName());
        builder.add(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(identifier, 1.0, EntityAttributeModifier.Operation.ADD_VALUE), attributeModifierSlot);
        builder.add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, new EntityAttributeModifier(identifier, 0.0, EntityAttributeModifier.Operation.ADD_VALUE), attributeModifierSlot);
        builder.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, new EntityAttributeModifier(identifier, 0.0, EntityAttributeModifier.Operation.ADD_VALUE), attributeModifierSlot);
        return builder.build();
    });

    @Shadow
    public abstract EquipmentSlot getSlotType();


    public ElytraItemMixin(Item.Settings settings) {
        super(settings);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectedInit(Item.Settings settings, CallbackInfo cbi) {
        settings.attributeModifiers(this.attributeModifiers.get());
    }

    @Override
    public int getEnchantability() {
        return 8;
    }

    @Override
    public AttributeModifiersComponent getAttributeModifiers() {
        return this.attributeModifiers.get();
    }


    
}
