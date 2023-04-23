package solipingen.armorrestitched.mixin.client.render.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.entity.mob.IllagerEntity;
import solipingen.armorrestitched.client.render.entity.feature.IllagerArmorFeatureRenderer;
import solipingen.armorrestitched.client.render.entity.model.IllagerArmorEntityModel;
import solipingen.armorrestitched.client.render.entity.model.ModEntityModelLayers;


@Mixin(IllagerEntityRenderer.class)
@Environment(value=EnvType.CLIENT)
public abstract class IllagerEntityRendererMixin<T extends IllagerEntity> extends MobEntityRenderer<T, IllagerEntityModel<T>> {


    public IllagerEntityRendererMixin(Context context, IllagerEntityModel<T> entityModel, float f) {
        super(context, entityModel, f);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectedInit(EntityRendererFactory.Context context, IllagerEntityModel<T> model, float shadowRadius, CallbackInfo cbi) {
        this.addFeature(new IllagerArmorFeatureRenderer(this, 
            new IllagerArmorEntityModel<>(context.getPart(ModEntityModelLayers.ILLAGER_CHEST_ARMOR_LAYER)), 
            new IllagerArmorEntityModel<>(context.getPart(ModEntityModelLayers.ILLAGER_LEGS_ARMOR_LAYER)), 
            new IllagerArmorEntityModel<>(context.getPart(ModEntityModelLayers.ILLAGER_HEAD_ARMOR_LAYER)), 
            new IllagerArmorEntityModel<>(context.getPart(ModEntityModelLayers.ILLAGER_FEET_ARMOR_LAYER)), context.getModelManager()));
    }

    
}

