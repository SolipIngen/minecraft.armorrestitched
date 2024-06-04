package solipingen.armorrestitched.mixin.item;

import java.util.EnumMap;
import java.util.Map;

import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ArmorMaterials;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.item.ArmorItem;
import net.minecraft.util.Util;


@Mixin(ArmorMaterial.class)
public abstract class ArmorMaterialMixin {
    @Shadow @Final private Map<ArmorItem.Type, Integer> defense;


    @Inject(method = "getProtection", at = @At("HEAD"), cancellable = true)
    private void injectedGetProtection(ArmorItem.Type type, CallbackInfoReturnable<Integer> cbireturn) {
        ArmorMaterial material = (ArmorMaterial)(Object)this;
        Map<ArmorItem.Type, Integer> newProtectionAmounts = this.defense;
        if (material == ArmorMaterials.LEATHER.value()) {
            newProtectionAmounts = Util.make(new EnumMap<ArmorItem.Type, Integer>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 1);
                map.put(ArmorItem.Type.LEGGINGS, 3);
                map.put(ArmorItem.Type.CHESTPLATE, 3);
                map.put(ArmorItem.Type.HELMET, 1);
                map.put(ArmorItem.Type.BODY, 3);
            });
        }
        else if (material == ArmorMaterials.CHAIN.value()) {
            newProtectionAmounts = Util.make(new EnumMap<ArmorItem.Type, Integer>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 3);
                map.put(ArmorItem.Type.LEGGINGS, 5);
                map.put(ArmorItem.Type.CHESTPLATE, 5);
                map.put(ArmorItem.Type.HELMET, 3);
                map.put(ArmorItem.Type.BODY, 10);
            });
        }
        else if (material == ArmorMaterials.GOLD.value()) {
            newProtectionAmounts = Util.make(new EnumMap<ArmorItem.Type, Integer>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 2);
                map.put(ArmorItem.Type.LEGGINGS, 4);
                map.put(ArmorItem.Type.CHESTPLATE, 4);
                map.put(ArmorItem.Type.HELMET, 2);
                map.put(ArmorItem.Type.BODY, 7);
            });
        }
        else if (material == ArmorMaterials.IRON.value()) {
            newProtectionAmounts = Util.make(new EnumMap<ArmorItem.Type, Integer>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 3);
                map.put(ArmorItem.Type.LEGGINGS, 5);
                map.put(ArmorItem.Type.CHESTPLATE, 5);
                map.put(ArmorItem.Type.HELMET, 3);
                map.put(ArmorItem.Type.BODY, 10);
            });
        }
        else if (material == ArmorMaterials.DIAMOND.value() || material == ArmorMaterials.ARMADILLO.value()) {
            newProtectionAmounts = Util.make(new EnumMap<ArmorItem.Type, Integer>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 4);
                map.put(ArmorItem.Type.LEGGINGS, 6);
                map.put(ArmorItem.Type.CHESTPLATE, 6);
                map.put(ArmorItem.Type.HELMET, 4);
                map.put(ArmorItem.Type.BODY, 14);
            });
        }
        else if (material == ArmorMaterials.NETHERITE.value()) {
            newProtectionAmounts = Util.make(new EnumMap<ArmorItem.Type, Integer>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 4);
                map.put(ArmorItem.Type.LEGGINGS, 6);
                map.put(ArmorItem.Type.CHESTPLATE, 6);
                map.put(ArmorItem.Type.HELMET, 4);
                map.put(ArmorItem.Type.BODY, 14);
            });
        }
        else if (material == ArmorMaterials.TURTLE.value()) {
            newProtectionAmounts = Util.make(new EnumMap<ArmorItem.Type, Integer>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 2);
                map.put(ArmorItem.Type.LEGGINGS, 3);
                map.put(ArmorItem.Type.CHESTPLATE, 3);
                map.put(ArmorItem.Type.HELMET, 2);
                map.put(ArmorItem.Type.BODY, 10);
            });
        }
        cbireturn.setReturnValue(newProtectionAmounts.get(type));
    }

    @Inject(method = "toughness", at = @At("HEAD"), cancellable = true)
    private void injectedGetToughness(CallbackInfoReturnable<Float> cbireturn) {
        ArmorMaterial material = (ArmorMaterial)(Object)this;
        if (material == ArmorMaterials.GOLD.value()) {
            cbireturn.setReturnValue(1.0f);
        }
        else if (material == ArmorMaterials.CHAIN.value()) {
            cbireturn.setReturnValue(2.0f);
        }
        else if (material == ArmorMaterials.IRON.value()) {
            cbireturn.setReturnValue(3.0f);
        }
        else if (material == ArmorMaterials.DIAMOND.value()) {
            cbireturn.setReturnValue(6.0f);
        }
        else if (material == ArmorMaterials.NETHERITE.value()) {
            cbireturn.setReturnValue(8.0f);
        }
    }

    @Inject(method = "knockbackResistance", at = @At("HEAD"), cancellable = true)
    private void injectedGetKnockbackResistance(CallbackInfoReturnable<Float> cbireturn) {
        ArmorMaterial material = (ArmorMaterial)(Object)this;
        if (material == ArmorMaterials.IRON.value()) {
            cbireturn.setReturnValue(0.05f);
        }
        else if (material == ArmorMaterials.DIAMOND.value()) {
            cbireturn.setReturnValue(0.7f);
        }
        else if (material == ArmorMaterials.NETHERITE.value()) {
            cbireturn.setReturnValue(0.12f);
        }
        else if (material == ArmorMaterials.TURTLE.value() || material == ArmorMaterials.ARMADILLO.value()) {
            cbireturn.setReturnValue(0.02f);
        }
    }



}

