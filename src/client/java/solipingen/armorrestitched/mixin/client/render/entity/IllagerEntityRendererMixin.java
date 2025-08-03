package solipingen.armorrestitched.mixin.client.render.entity;

import net.minecraft.client.render.entity.state.IllagerEntityRenderState;
import net.minecraft.entity.EquipmentSlot;
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
import solipingen.armorrestitched.util.interfaces.mixin.render.entity.model.IllagerEntityRenderStateInterface;


@Mixin(IllagerEntityRenderer.class)
@Environment(value = EnvType.CLIENT)
public abstract class IllagerEntityRendererMixin<T extends IllagerEntity, S extends IllagerEntityRenderState> extends MobEntityRenderer<T, S, IllagerEntityModel<S>> {


    public IllagerEntityRendererMixin(Context context, IllagerEntityModel<S> entityModel, float shadowRadius) {
        super(context, entityModel, shadowRadius);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectedInit(EntityRendererFactory.Context context, IllagerEntityModel<S> model, float shadowRadius, CallbackInfo cbi) {
        this.addFeature(new IllagerArmorFeatureRenderer((IllagerEntityRenderer)(Object)this,
            new IllagerArmorEntityModel(context.getPart(ModEntityModelLayers.ILLAGER_INNER_ARMOR_LAYER)),
            new IllagerArmorEntityModel(context.getPart(ModEntityModelLayers.ILLAGER_OUTER_ARMOR_LAYER)), context.getEquipmentRenderer()));
    }

    @Inject(method = "updateRenderState(Lnet/minecraft/entity/mob/IllagerEntity;Lnet/minecraft/client/render/entity/state/IllagerEntityRenderState;F)V", at = @At("TAIL"))
    private void injectedUpdateRenderState(T illagerEntity, S illagerEntityRenderState, float f, CallbackInfo cbi) {
        ((IllagerEntityRenderStateInterface)illagerEntityRenderState).setEquippedHeadStack(illagerEntity.getEquippedStack(EquipmentSlot.HEAD));
        ((IllagerEntityRenderStateInterface)illagerEntityRenderState).setEquippedChestStack(illagerEntity.getEquippedStack(EquipmentSlot.CHEST));
        ((IllagerEntityRenderStateInterface)illagerEntityRenderState).setEquippedLegsStack(illagerEntity.getEquippedStack(EquipmentSlot.LEGS));
        ((IllagerEntityRenderStateInterface)illagerEntityRenderState).setEquippedFeetStack(illagerEntity.getEquippedStack(EquipmentSlot.FEET));
    }

    
}

