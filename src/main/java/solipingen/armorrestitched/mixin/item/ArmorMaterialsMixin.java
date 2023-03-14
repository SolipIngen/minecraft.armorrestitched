package solipingen.armorrestitched.mixin.item;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.util.math.MathHelper;


@Mixin(ArmorMaterials.class)
public abstract class ArmorMaterialsMixin implements ArmorMaterial {
    @Shadow @Final private static int[] BASE_DURABILITY;
    @Shadow @Final private String name;
    @Shadow @Final private int durabilityMultiplier;
    @Shadow @Final private int[] protectionAmounts;
    @Shadow @Final private float toughness;
    @Shadow @Final private float knockbackResistance;


    @Inject(method = "getDurability", at = @At("HEAD"), cancellable = true)
    private void injectedGetDurability(EquipmentSlot slot, CallbackInfoReturnable<Integer> cbireturn) {
        if (slot == EquipmentSlot.HEAD) {
            if (this.name.equals("iron")) {
                cbireturn.setReturnValue(MathHelper.ceil(1.75f*14)*2*this.durabilityMultiplier);
            }
            cbireturn.setReturnValue(MathHelper.ceil(1.75f*14)*this.durabilityMultiplier);
        }
        else if (slot != EquipmentSlot.HEAD && this.name.equals("iron")) {
            cbireturn.setReturnValue(MathHelper.ceil(1.75f*BASE_DURABILITY[slot.getEntitySlotId()])*2*this.durabilityMultiplier);
        }
        else {
            cbireturn.setReturnValue(MathHelper.ceil(1.75f*BASE_DURABILITY[slot.getEntitySlotId()])*this.durabilityMultiplier);
        }
    }

    @Inject(method = "getProtectionAmount", at = @At("HEAD"), cancellable = true)
    private void injectedGetProtectionAmount(EquipmentSlot slot, CallbackInfoReturnable<Integer> cbireturn) {
        int[] newProtectionAmounts = new int[]{0, 0, 0, 0};
        if (this.name.equals("leather")) {
            newProtectionAmounts = new int[]{1, 3, 3, 1};
        }
        else if (this.name.equals("chainmail")) {
            newProtectionAmounts = new int[]{2, 4, 4, 2};
        }
        else if (this.name.equals("gold")) {
            newProtectionAmounts = new int[]{2, 4, 4, 2};
        }
        else if (this.name.equals("iron")) {
            newProtectionAmounts = new int[]{3, 5, 5, 3};
        }
        else if (this.name.equals("diamond")) {
            newProtectionAmounts = new int[]{4, 6, 6, 4};
        }
        else if (this.name.equals("netherite")) {
            newProtectionAmounts = new int[]{4, 6, 6, 4};
        }
        else if (this.name.equals("turtle")) {
            newProtectionAmounts = new int[]{2, 3, 3, 2};
        }
        cbireturn.setReturnValue(newProtectionAmounts[slot.getEntitySlotId()]);
    }

    @Inject(method = "getToughness", at = @At("HEAD"), cancellable = true)
    private void injectedGetToughness(CallbackInfoReturnable<Float> cbireturn) {
        if (this.name.equals("iron")) {
            cbireturn.setReturnValue(2.0f);
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
            cbireturn.setReturnValue(0.08f);
        }
        else if (this.name.equals("diamond")) {
            cbireturn.setReturnValue(0.15f);
        }
        else if (this.name.equals("netherite")) {
            cbireturn.setReturnValue(0.2f);
        }
        else if (this.name.equals("turtle")) {
            cbireturn.setReturnValue(0.03f);
        }
    }
    



}

