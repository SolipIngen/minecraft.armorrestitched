package solipingen.armorrestitched.mixin.client.render.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.mob.MobEntity;
import solipingen.armorrestitched.client.render.entity.feature.ElytraTrimFeatureRenderer;


@Mixin(BipedEntityRenderer.class)
@Environment(value = EnvType.CLIENT)
public abstract class BipedEntityRendererMixin<T extends MobEntity, M extends BipedEntityModel<T>> extends MobEntityRenderer<T, M> {


    public BipedEntityRendererMixin(Context context, M entityModel, float f) {
        super(context, entityModel, f);
    }

    @Inject(method = "<init>(Lnet/minecraft/client/render/entity/EntityRendererFactory$Context;Lnet/minecraft/client/render/entity/model/BipedEntityModel;FFFF)V", at = @At("TAIL"))
    private void injectedInit(EntityRendererFactory.Context context, M model, float shadowRadius, float scaleX, float scaleY, float scaleZ, CallbackInfo cbi) {
        this.addFeature(new ElytraTrimFeatureRenderer<>(this, context.getModelLoader(), context.getModelManager()));
    }
    

    
}
