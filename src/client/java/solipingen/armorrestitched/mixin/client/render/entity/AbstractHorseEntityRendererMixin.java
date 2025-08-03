package solipingen.armorrestitched.mixin.client.render.entity;

import net.minecraft.client.render.entity.AbstractHorseEntityRenderer;
import net.minecraft.client.render.entity.AgeableMobEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingHorseEntityRenderState;
import net.minecraft.entity.passive.AbstractHorseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import solipingen.armorrestitched.util.interfaces.mixin.render.entity.model.LivingHorseEntityRenderStateInterface;


@Mixin(AbstractHorseEntityRenderer.class)
public abstract class AbstractHorseEntityRendererMixin<T extends AbstractHorseEntity, S extends LivingHorseEntityRenderState, M extends EntityModel<? super S>> extends AgeableMobEntityRenderer<T, S, M> {


    public AbstractHorseEntityRendererMixin(EntityRendererFactory.Context context, M model, M babyModel, float shadowRadius) {
        super(context, model, babyModel, shadowRadius);
    }

    @Inject(method = "updateRenderState(Lnet/minecraft/entity/passive/AbstractHorseEntity;Lnet/minecraft/client/render/entity/state/LivingHorseEntityRenderState;F)V", at = @At("TAIL"))
    private void injectedUpdateRenderState(T abstractHorseEntity, S livingHorseEntityRenderState, float f, CallbackInfo cbi) {
        ((LivingHorseEntityRenderStateInterface)livingHorseEntityRenderState).setArmor(abstractHorseEntity.getBodyArmor().copy());
    }


}
