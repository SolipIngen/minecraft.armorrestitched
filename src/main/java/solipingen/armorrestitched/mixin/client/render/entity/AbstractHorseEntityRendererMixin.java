package solipingen.armorrestitched.mixin.client.render.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.AbstractHorseEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.entity.passive.AbstractHorseEntity;
import solipingen.armorrestitched.client.render.entity.feature.AbstractHorseArmorFeatureRenderer; 


@Mixin(AbstractHorseEntityRenderer.class)
@Environment(value = EnvType.CLIENT)
public abstract class AbstractHorseEntityRendererMixin<T extends AbstractHorseEntity, M extends HorseEntityModel<T>> extends MobEntityRenderer<T, M> {


    public AbstractHorseEntityRendererMixin(Context context, M entityModel, float f) {
        super(context, entityModel, f);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectedInit(EntityRendererFactory.Context context, M model, float scale, CallbackInfo cbi) {
        if (context.getPart(EntityModelLayers.SKELETON_HORSE) != null) {
            this.addFeature((FeatureRenderer<T, M>)new AbstractHorseArmorFeatureRenderer(((AbstractHorseEntityRenderer)(Object)this), context.getModelLoader()));
        }
    }

    
}

