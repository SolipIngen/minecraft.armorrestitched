package solipingen.armorrestitched.mixin.client.render.entity;

import net.minecraft.client.render.entity.UndeadHorseEntityRenderer;
import net.minecraft.client.render.entity.equipment.EquipmentModel;
import net.minecraft.client.render.entity.feature.SaddleFeatureRenderer;
import net.minecraft.client.render.entity.model.AbstractHorseEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.state.LivingHorseEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.AbstractHorseEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.entity.passive.AbstractHorseEntity;
import solipingen.armorrestitched.util.interfaces.mixin.render.entity.model.LivingHorseEntityRenderStateInterface;


@Mixin(UndeadHorseEntityRenderer.class)
@Environment(value = EnvType.CLIENT)
public abstract class UndeadHorseEntityRendererMixin extends AbstractHorseEntityRenderer<AbstractHorseEntity, LivingHorseEntityRenderState, AbstractHorseEntityModel<LivingHorseEntityRenderState>> {


    public UndeadHorseEntityRendererMixin(Context context, AbstractHorseEntityModel<LivingHorseEntityRenderState> model, AbstractHorseEntityModel<LivingHorseEntityRenderState> babyModel) {
        super(context, model, babyModel);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectedInit(EntityRendererFactory.Context context, UndeadHorseEntityRenderer.Type type, CallbackInfo cbi) {
        this.addFeature(new SaddleFeatureRenderer((UndeadHorseEntityRenderer)(Object)this, context.getEquipmentRenderer(),
                EquipmentModel.LayerType.HORSE_BODY,
                (horseEntityRenderState) -> ((LivingHorseEntityRenderStateInterface)horseEntityRenderState).getArmor(),
                new HorseEntityModel(context.getPart(EntityModelLayers.HORSE_ARMOR)),
                new HorseEntityModel(context.getPart(EntityModelLayers.HORSE_ARMOR_BABY))));
    }
    
}

