package solipingen.armorrestitched.mixin.item;

import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Supplier;


@Mixin(ArmorItem.class)
public abstract class ArmorItemMixin extends Item implements Equipment {
    @Shadow @Final private Supplier<AttributeModifiersComponent> attributeModifiers;
    @Shadow @Final protected ArmorItem.Type type;
    @Shadow @Final protected RegistryEntry<ArmorMaterial> material;


    public ArmorItemMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "getAttributeModifiers", at = @At("HEAD"), cancellable = true)
    private void injectedAttributeModifiers(CallbackInfoReturnable<AttributeModifiersComponent> cbireturn) {
        Identifier id = Identifier.ofVanilla("armor." + this.type.getName());
        AttributeModifiersComponent.Builder builder = AttributeModifiersComponent.builder();
        for (AttributeModifiersComponent.Entry entry : this.attributeModifiers.get().modifiers()) {
            builder.add(entry.attribute(), entry.modifier(), entry.slot());
        }
        float burnTimeReduction = Math.min(this.material.value().getProtection(this.type)/50.0f + (this.material == ArmorMaterials.NETHERITE ? 0.1f : 0.0f), 1.0f);
        builder.add(EntityAttributes.GENERIC_BURNING_TIME,
                new EntityAttributeModifier(id, -burnTimeReduction, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE), AttributeModifierSlot.forEquipmentSlot(this.type.getEquipmentSlot()));
        builder.add(EntityAttributes.GENERIC_EXPLOSION_KNOCKBACK_RESISTANCE,
                new EntityAttributeModifier(id, this.material.value().knockbackResistance(), EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.forEquipmentSlot(this.type.getEquipmentSlot()));
        cbireturn.setReturnValue(builder.build());
    }


}
