package solipingen.armorrestitched.mixin.item;

import java.util.EnumMap;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;


@Mixin(ArmorMaterials.class)
public abstract class ArmorMaterialsMixin implements ArmorMaterial, StringIdentifiable {
    @Shadow @Final private static EnumMap<ArmorItem.Type, Integer> BASE_DURABILITY;
    @Shadow @Final private String name;
    @Shadow @Final private int durabilityMultiplier;
    @Shadow @Final private EnumMap<ArmorItem.Type, Integer> protectionAmounts;
    @Shadow @Final private float toughness;
    @Shadow @Final private float knockbackResistance;


    @Inject(method = "getDurability", at = @At("HEAD"), cancellable = true)
    private void injectedGetDurability(ArmorItem.Type type, CallbackInfoReturnable<Integer> cbireturn) {
        if (type == ArmorItem.Type.HELMET) {
            if (this.name.equals("iron")) {
                cbireturn.setReturnValue(MathHelper.ceil(1.75f*14)*2*this.durabilityMultiplier);
            }
            cbireturn.setReturnValue(MathHelper.ceil(1.75f*14)*this.durabilityMultiplier);
        }
        else if (type == ArmorItem.Type.HELMET && this.name.equals("iron")) {
            cbireturn.setReturnValue(MathHelper.ceil(1.75f*BASE_DURABILITY.get((Object)type))*2*this.durabilityMultiplier);
        }
        else {
            cbireturn.setReturnValue(MathHelper.ceil(1.75f*BASE_DURABILITY.get((Object)type))*this.durabilityMultiplier);
        }
    }

    @Inject(method = "getProtection", at = @At("HEAD"), cancellable = true)
    private void injectedGetProtection(ArmorItem.Type type, CallbackInfoReturnable<Integer> cbireturn) {
        EnumMap<ArmorItem.Type, Integer> newProtectionAmounts = Util.make(new EnumMap<ArmorItem.Type, Integer>(ArmorItem.Type.class), map -> {
            map.put(ArmorItem.Type.BOOTS, 0);
            map.put(ArmorItem.Type.LEGGINGS, 0);
            map.put(ArmorItem.Type.CHESTPLATE, 0);
            map.put(ArmorItem.Type.HELMET, 0);
        });
        if (this.name.equals("leather")) {
            newProtectionAmounts = Util.make(new EnumMap<ArmorItem.Type, Integer>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 1);
                map.put(ArmorItem.Type.LEGGINGS, 2);
                map.put(ArmorItem.Type.CHESTPLATE, 2);
                map.put(ArmorItem.Type.HELMET, 1);
            });
        }
        else if (this.name.equals("chainmail")) {
            newProtectionAmounts = Util.make(new EnumMap<ArmorItem.Type, Integer>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 3);
                map.put(ArmorItem.Type.LEGGINGS, 4);
                map.put(ArmorItem.Type.CHESTPLATE, 4);
                map.put(ArmorItem.Type.HELMET, 3);
            });
        }
        else if (this.name.equals("gold")) {
            newProtectionAmounts = Util.make(new EnumMap<ArmorItem.Type, Integer>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 2);
                map.put(ArmorItem.Type.LEGGINGS, 4);
                map.put(ArmorItem.Type.CHESTPLATE, 4);
                map.put(ArmorItem.Type.HELMET, 2);
            });
        }
        else if (this.name.equals("iron")) {
            newProtectionAmounts = Util.make(new EnumMap<ArmorItem.Type, Integer>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 3);
                map.put(ArmorItem.Type.LEGGINGS, 5);
                map.put(ArmorItem.Type.CHESTPLATE, 5);
                map.put(ArmorItem.Type.HELMET, 3);
            });
        }
        else if (this.name.equals("diamond")) {
            newProtectionAmounts = Util.make(new EnumMap<ArmorItem.Type, Integer>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 4);
                map.put(ArmorItem.Type.LEGGINGS, 6);
                map.put(ArmorItem.Type.CHESTPLATE, 6);
                map.put(ArmorItem.Type.HELMET, 4);
            });
        }
        else if (this.name.equals("netherite")) {
            newProtectionAmounts = Util.make(new EnumMap<ArmorItem.Type, Integer>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 4);
                map.put(ArmorItem.Type.LEGGINGS, 6);
                map.put(ArmorItem.Type.CHESTPLATE, 6);
                map.put(ArmorItem.Type.HELMET, 4);
            });
        }
        else if (this.name.equals("turtle")) {
            newProtectionAmounts = Util.make(new EnumMap<ArmorItem.Type, Integer>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 2);
                map.put(ArmorItem.Type.LEGGINGS, 3);
                map.put(ArmorItem.Type.CHESTPLATE, 3);
                map.put(ArmorItem.Type.HELMET, 2);
            });
        }
        cbireturn.setReturnValue(newProtectionAmounts.get((Object)type));
    }

    @Inject(method = "getToughness", at = @At("HEAD"), cancellable = true)
    private void injectedGetToughness(CallbackInfoReturnable<Float> cbireturn) {
        if (this.name.equals("gold")) {
            cbireturn.setReturnValue(1.0f);
        }
        else if (this.name.equals("chainmail")) {
            cbireturn.setReturnValue(2.0f);
        }
        else if (this.name.equals("iron")) {
            cbireturn.setReturnValue(3.0f);
        }
        else if (this.name.equals("diamond")) {
            cbireturn.setReturnValue(6.0f);
        }
        else if (this.name.equals("netherite")) {
            cbireturn.setReturnValue(8.0f);
        }
    }

    @Inject(method = "getKnockbackResistance", at = @At("HEAD"), cancellable = true)
    private void injectedGetKnockbackResistance(CallbackInfoReturnable<Float> cbireturn) {
        if (this.name.equals("iron")) {
            cbireturn.setReturnValue(0.05f);
        }
        else if (this.name.equals("diamond")) {
            cbireturn.setReturnValue(0.12f);
        }
        else if (this.name.equals("netherite")) {
            cbireturn.setReturnValue(0.15f);
        }
        else if (this.name.equals("turtle")) {
            cbireturn.setReturnValue(0.02f);
        }
    }
    



}

