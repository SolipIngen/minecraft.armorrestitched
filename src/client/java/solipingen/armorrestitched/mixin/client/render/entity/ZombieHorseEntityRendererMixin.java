package solipingen.armorrestitched.mixin.client.render.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.AbstractHorseEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ZombieHorseEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.entity.passive.AbstractHorseEntity;
import solipingen.armorrestitched.client.render.entity.feature.ZombieHorseArmorFeatureRenderer; 


@Mixin(ZombieHorseEntityRenderer.class)
@Environment(value = EnvType.CLIENT)
public abstract class ZombieHorseEntityRendererMixin extends AbstractHorseEntityRenderer<AbstractHorseEntity, HorseEntityModel<AbstractHorseEntity>> {


    public ZombieHorseEntityRendererMixin(Context context, HorseEntityModel<AbstractHorseEntity> model, float scale) {
        super(context, model, scale);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectedInit(EntityRendererFactory.Context context, EntityModelLayer layer, CallbackInfo cbi) {
        this.addFeature(new ZombieHorseArmorFeatureRenderer(((ZombieHorseEntityRenderer)(Object)this), context.getModelLoader()));
    }

    
}

