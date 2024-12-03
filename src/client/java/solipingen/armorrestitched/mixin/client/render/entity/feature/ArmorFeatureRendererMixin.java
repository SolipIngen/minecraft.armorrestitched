package solipingen.armorrestitched.mixin.client.render.entity.feature;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import solipingen.armorrestitched.item.ModArmorMaterials;


@Mixin(ArmorFeatureRenderer.class)
@Environment(EnvType.CLIENT)
public abstract class ArmorFeatureRendererMixin<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> {


    @ModifyConstant(method = "renderArmor", constant = @Constant(intValue = -6265536))
    private int modifiedDefaultColor(int leatherColor, MatrixStack matrices, VertexConsumerProvider vertexConsumers, T entity, EquipmentSlot armorSlot, int light, A model) {
        ItemStack itemStack = entity.getEquippedStack(armorSlot);
        if (itemStack.getItem() instanceof ArmorItem) {
            ArmorMaterial material = ((ArmorItem)itemStack.getItem()).getMaterial().value();
            if (material == ModArmorMaterials.COTTON.value()) {
                return 0xFDFFF6;
            }
            else if (material == ModArmorMaterials.FUR.value()) {
                return 0xFFD1B3;
            }
            else if (material == ModArmorMaterials.LINEN.value()) {
                return 0x9B8866;
            }
            else if (material == ModArmorMaterials.SILK.value()) {
                return 0xFFF6D4;
            }
            else if (material == ModArmorMaterials.WOOL.value()) {
                return 0xD5CDA8;
            }
            else if (material == ModArmorMaterials.PAPER.value()) {
                return 0xFFFCF0;
            }
        }
        return leatherColor;
    }

}
