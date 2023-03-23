package solipingen.armorrestitched.mixin.client.render.entity.feature;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.ArmorRestitched;
import solipingen.armorrestitched.item.armor.ModArmorMaterials;


@Mixin(ArmorFeatureRenderer.class)
@Environment(value=EnvType.CLIENT)
public abstract class ArmorFeatureRendererMixin<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> extends FeatureRenderer<T, M>  {


    public ArmorFeatureRendererMixin(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @Inject(method = "getArmorTexture", at = @At("TAIL"), cancellable = true)
    private void injectedGetArmorTexture(ArmorItem item, boolean secondLayer, @Nullable String overlay, CallbackInfoReturnable<Identifier> cbireturn) {
        if (item.getMaterial() instanceof ModArmorMaterials) {
            Identifier modArmorId = new Identifier(ArmorRestitched.MOD_ID, "textures/models/armor/" + item.getMaterial().getName() + "_layer_" + (secondLayer ? 2 : 1) + (String)(overlay == null ? "" : "_" + overlay) + ".png");
            cbireturn.setReturnValue(modArmorId);
        }
    }

    
}

