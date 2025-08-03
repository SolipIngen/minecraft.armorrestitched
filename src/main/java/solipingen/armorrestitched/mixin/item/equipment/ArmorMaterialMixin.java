package solipingen.armorrestitched.mixin.item.equipment;

import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.ArmorMaterials;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import solipingen.armorrestitched.item.equipment.ModArmorMaterials;

import java.util.Map;


@Mixin(ArmorMaterial.class)
public abstract class ArmorMaterialMixin {
    @Shadow @Final private Map<EquipmentType, Integer> defense;
    @Shadow @Final private float toughness;
    @Shadow @Final private float knockbackResistance;


    @Inject(method = "createAttributeModifiers", at = @At("HEAD"), cancellable = true)
    private void injectedAttributeModifiers(EquipmentType equipmentType, CallbackInfoReturnable<AttributeModifiersComponent> cbireturn) {
        int armor = this.defense.getOrDefault(equipmentType, 0);
        AttributeModifiersComponent.Builder builder = AttributeModifiersComponent.builder();
        AttributeModifierSlot attributeModifierSlot = AttributeModifierSlot.forEquipmentSlot(equipmentType.getEquipmentSlot());
        Identifier identifier = Identifier.ofVanilla("armor." + equipmentType.getName());
        builder.add(EntityAttributes.ARMOR,
                new EntityAttributeModifier(identifier, armor, EntityAttributeModifier.Operation.ADD_VALUE), attributeModifierSlot);
        builder.add(EntityAttributes.ARMOR_TOUGHNESS,
                new EntityAttributeModifier(identifier, this.toughness, EntityAttributeModifier.Operation.ADD_VALUE), attributeModifierSlot);
        builder.add(EntityAttributes.KNOCKBACK_RESISTANCE,
                new EntityAttributeModifier(identifier, this.knockbackResistance, EntityAttributeModifier.Operation.ADD_VALUE), attributeModifierSlot);
        builder.add(EntityAttributes.EXPLOSION_KNOCKBACK_RESISTANCE,
                new EntityAttributeModifier(identifier, this.knockbackResistance, EntityAttributeModifier.Operation.ADD_VALUE), attributeModifierSlot);
        float burnTimeReduction = Math.min(armor/50.0f + (((ArmorMaterial)(Object)this) == ArmorMaterials.NETHERITE ? 0.1f : 0.0f), 1.0f);
        builder.add(EntityAttributes.BURNING_TIME,
                new EntityAttributeModifier(identifier, -burnTimeReduction, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE), attributeModifierSlot);
        cbireturn.setReturnValue(builder.build());
    }

    @Inject(method = "durability", at = @At("HEAD"), cancellable = true)
    private void injectedDurability(CallbackInfoReturnable<Integer> cbireturn) {
        ArmorMaterial material = (ArmorMaterial)(Object)this;
        if (material == ArmorMaterials.LEATHER) {
            cbireturn.setReturnValue(9);
        }
        else if (material == ArmorMaterials.CHAIN) {
            cbireturn.setReturnValue(15);
        }
        else if (material == ArmorMaterials.IRON) {
            cbireturn.setReturnValue(20);
        }
        else if (material == ArmorMaterials.DIAMOND) {
            cbireturn.setReturnValue(33);
        }
        else if (material == ArmorMaterials.GOLD) {
            cbireturn.setReturnValue(7);
        }
        else if (material == ArmorMaterials.NETHERITE) {
            cbireturn.setReturnValue(37);
        }
        else if (material == ArmorMaterials.ARMADILLO_SCUTE) {
            cbireturn.setReturnValue(20);
        }
    }

    @Inject(method = "defense", at = @At("HEAD"), cancellable = true)
    private void injectedDefense(CallbackInfoReturnable<Map<EquipmentType, Integer>> cbireturn) {
        ArmorMaterial material = (ArmorMaterial)(Object)this;
        Map<EquipmentType, Integer> protectionAmounts = this.defense;
        if (material == ArmorMaterials.LEATHER) {
            protectionAmounts = ModArmorMaterials.createDefenseMap(1, 3, 3, 1, 3);
        }
        else if (material == ArmorMaterials.CHAIN) {
            protectionAmounts = ModArmorMaterials.createDefenseMap(3, 5, 5, 3, 10);
        }
        else if (material == ArmorMaterials.GOLD) {
            protectionAmounts = ModArmorMaterials.createDefenseMap(2, 4, 4, 2, 7);
        }
        else if (material == ArmorMaterials.IRON) {
            protectionAmounts = ModArmorMaterials.createDefenseMap(3,5,5, 3, 10);
        }
        else if (material == ArmorMaterials.DIAMOND || material == ArmorMaterials.ARMADILLO_SCUTE) {
            protectionAmounts = ModArmorMaterials.createDefenseMap(4,6, 6, 4, 14);
        }
        else if (material == ArmorMaterials.NETHERITE) {
            protectionAmounts = ModArmorMaterials.createDefenseMap(4, 6, 6, 4, 14);
        }
        else if (material == ArmorMaterials.TURTLE_SCUTE) {
            protectionAmounts = ModArmorMaterials.createDefenseMap(2, 3, 3, 2, 10);
        }
        cbireturn.setReturnValue(protectionAmounts);
    }

    @Inject(method = "toughness", at = @At("HEAD"), cancellable = true)
    private void injectedGetToughness(CallbackInfoReturnable<Float> cbireturn) {
        ArmorMaterial material = (ArmorMaterial)(Object)this;
        if (material == ArmorMaterials.GOLD) {
            cbireturn.setReturnValue(1.0f);
        }
        else if (material == ArmorMaterials.CHAIN) {
            cbireturn.setReturnValue(2.0f);
        }
        else if (material == ArmorMaterials.IRON) {
            cbireturn.setReturnValue(3.0f);
        }
        else if (material == ArmorMaterials.DIAMOND) {
            cbireturn.setReturnValue(6.0f);
        }
        else if (material == ArmorMaterials.NETHERITE) {
            cbireturn.setReturnValue(8.0f);
        }
    }

    @Inject(method = "knockbackResistance", at = @At("HEAD"), cancellable = true)
    private void injectedGetKnockbackResistance(CallbackInfoReturnable<Float> cbireturn) {
        ArmorMaterial material = (ArmorMaterial)(Object)this;
        if (material == ArmorMaterials.IRON) {
            cbireturn.setReturnValue(0.05f);
        }
        else if (material == ArmorMaterials.DIAMOND) {
            cbireturn.setReturnValue(0.07f);
        }
        else if (material == ArmorMaterials.NETHERITE) {
            cbireturn.setReturnValue(0.12f);
        }
        else if (material == ArmorMaterials.TURTLE_SCUTE || material == ArmorMaterials.ARMADILLO_SCUTE) {
            cbireturn.setReturnValue(0.02f);
        }
    }



}

