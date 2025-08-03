package solipingen.armorrestitched.mixin.component;

import net.minecraft.component.ComponentHolder;
import net.minecraft.component.ComponentType;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.trim.ArmorTrim;
import net.minecraft.item.equipment.trim.ArmorTrimMaterial;
import net.minecraft.item.equipment.trim.ArmorTrimMaterials;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.ItemTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import solipingen.armorrestitched.registry.tag.ModItemTags;


@Mixin(ComponentHolder.class)
public interface ComponentHolderMixin extends ComponentsAccess {


    @Inject(method = "get", at = @At("HEAD"), cancellable = true)
    private <T> void injectedGet(ComponentType<? extends T> type, CallbackInfoReturnable<T> cbireturn) {
        if (((ComponentHolder)(Object)this) instanceof ItemStack stack) {
            if (type == DataComponentTypes.ATTRIBUTE_MODIFIERS && stack.isIn(ItemTags.TRIMMABLE_ARMOR)) {
                ArmorTrim trim = stack.getComponents().get(DataComponentTypes.TRIM);
                if (trim != null) {
                    AttributeModifiersComponent attributeModifiersComponent = stack.getComponents().get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
                    RegistryEntry<ArmorTrimMaterial> material = trim.material();
                    AttributeModifiersComponent.Builder builder = AttributeModifiersComponent.builder();
                    float armorAddition = stack.isIn(ModItemTags.TRIMMABLE_ELYTRA) ? 1.0f : 0.0f;
                    float toughnessAddition = !stack.isIn(ModItemTags.TRIMMABLE_ELYTRA) && material.matchesKey(ArmorTrimMaterials.DIAMOND) ? 1.0f : 0.0f;
                    float knockbackResistanceAddition = 0.0f;
                    if (stack.isIn(ModItemTags.TRIMMABLE_ELYTRA) && attributeModifiersComponent != null) {
                        if (material.matchesKey(ArmorTrimMaterials.COPPER) || material.matchesKey(ArmorTrimMaterials.GOLD) || material.matchesKey(ArmorTrimMaterials.EMERALD)) {
                            toughnessAddition = 0.5f;
                        }
                        else if (material.matchesKey(ArmorTrimMaterials.IRON)) {
                            toughnessAddition = 1.0f;
                        }
                        else if (material.matchesKey(ArmorTrimMaterials.DIAMOND)) {
                            toughnessAddition = 2.0f;
                        }
                        else if (material.matchesKey(ArmorTrimMaterials.NETHERITE)) {
                            toughnessAddition = 3.0f;
                            knockbackResistanceAddition = 0.04f;
                        }
                    }
                    for (AttributeModifiersComponent.Entry entry : attributeModifiersComponent.modifiers()) {
                        double addition = (entry.attribute() == EntityAttributes.ARMOR) ? armorAddition
                                : ((entry.attribute() == EntityAttributes.ARMOR_TOUGHNESS) ? toughnessAddition
                                : ((entry.attribute() == EntityAttributes.KNOCKBACK_RESISTANCE) ? knockbackResistanceAddition
                                : 0.0f));
                        builder.add(entry.attribute(),
                                new EntityAttributeModifier(entry.modifier().id(), entry.modifier().value() + addition, entry.modifier().operation()),
                                entry.slot());
                    }
                    cbireturn.setReturnValue((T)builder.build());
                }
            }
        }
    }

    @Inject(method = "getOrDefault", at = @At("HEAD"), cancellable = true)
    private <T> void injectedGetOrDefault(ComponentType<? extends T> type, T fallback, CallbackInfoReturnable<T> cbireturn) {
        if (((ComponentHolder)(Object)this) instanceof ItemStack stack) {
            ArmorTrim trim = stack.getComponents().get(DataComponentTypes.TRIM);
            if (type == DataComponentTypes.ATTRIBUTE_MODIFIERS && trim != null) {
                AttributeModifiersComponent attributeModifiersComponent = stack.getComponents().getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.DEFAULT);
                RegistryEntry<ArmorTrimMaterial> material = trim.material();
                AttributeModifiersComponent.Builder builder = AttributeModifiersComponent.builder();
                float armorAddition = stack.isIn(ModItemTags.TRIMMABLE_ELYTRA) ? 1.0f : 0.0f;
                float toughnessAddition = !stack.isIn(ModItemTags.TRIMMABLE_ELYTRA) && material.matchesKey(ArmorTrimMaterials.DIAMOND) ? 1.0f : 0.0f;
                float knockbackResistanceAddition = 0.0f;
                if (stack.isIn(ModItemTags.TRIMMABLE_ELYTRA)) {
                    if (material.matchesKey(ArmorTrimMaterials.COPPER) || material.matchesKey(ArmorTrimMaterials.GOLD) || material.matchesKey(ArmorTrimMaterials.EMERALD)) {
                        toughnessAddition = 0.5f;
                    }
                    else if (material.matchesKey(ArmorTrimMaterials.IRON)) {
                        toughnessAddition = 1.0f;
                    }
                    else if (material.matchesKey(ArmorTrimMaterials.DIAMOND)) {
                        toughnessAddition = 2.0f;
                    }
                    else if (material.matchesKey(ArmorTrimMaterials.NETHERITE)) {
                        toughnessAddition = 3.0f;
                        knockbackResistanceAddition = 0.04f;
                    }
                }
                for (AttributeModifiersComponent.Entry entry : attributeModifiersComponent.modifiers()) {
                    double addition = entry.attribute() == EntityAttributes.ARMOR ? armorAddition
                            : entry.attribute() == EntityAttributes.ARMOR_TOUGHNESS ? toughnessAddition
                                : entry.attribute() == EntityAttributes.KNOCKBACK_RESISTANCE ? knockbackResistanceAddition
                                    : 0.0f;
                    builder.add(entry.attribute(),
                            new EntityAttributeModifier(entry.modifier().id(), entry.modifier().value() + addition, entry.modifier().operation()),
                            entry.slot());
                }
                cbireturn.setReturnValue((T)builder.build());
            }
        }
    }


}
