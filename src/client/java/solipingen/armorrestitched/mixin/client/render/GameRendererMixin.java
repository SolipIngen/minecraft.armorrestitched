package solipingen.armorrestitched.mixin.client.render;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.equipment.trim.ArmorTrim;
import net.minecraft.item.equipment.trim.ArmorTrimMaterials;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;


@Mixin(GameRenderer.class)
@Environment(value = EnvType.CLIENT)
public abstract class GameRendererMixin implements AutoCloseable {
    

    @Redirect(method = "getNightVisionStrength", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/effect/StatusEffectInstance;isDurationBelow(I)Z"))
    private static boolean redirectedFlickeringCondition(StatusEffectInstance statusEffectInstance, int duration, LivingEntity entity, float tickDelta) {
        ItemStack equippedStack = entity.getEquippedStack(EquipmentSlot.HEAD);
        ArmorTrim trim = equippedStack.get(DataComponentTypes.TRIM);
        if (trim != null && trim.material().matchesKey(ArmorTrimMaterials.QUARTZ)) {
            return false;
        }
        return statusEffectInstance.isDurationBelow(duration);
    }


}
