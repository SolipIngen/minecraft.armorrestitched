package solipingen.armorrestitched.mixin.client.render.entity;

import net.minecraft.client.render.entity.state.EvokerEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EvokerEntityRenderer;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.entity.mob.SpellcastingIllagerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import solipingen.armorrestitched.ArmorRestitched;
import solipingen.armorrestitched.util.interfaces.mixin.render.entity.model.IllagerEntityRenderStateInterface;


@Mixin(EvokerEntityRenderer.class)
@Environment(value = EnvType.CLIENT)
public abstract class EvokerEntityRendererMixin<T extends SpellcastingIllagerEntity> extends IllagerEntityRenderer<T, EvokerEntityRenderState> {


    protected EvokerEntityRendererMixin(Context ctx, IllagerEntityModel<EvokerEntityRenderState> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Inject(method = "getTexture(Lnet/minecraft/client/render/entity/state/EvokerEntityRenderState;)Lnet/minecraft/util/Identifier;", at = @At("HEAD"), cancellable = true)
    private void injectedGetTexture(EvokerEntityRenderState evokerEntityRenderState, CallbackInfoReturnable<Identifier> cbireturn) {
        IllagerEntityRenderStateInterface iRenderState = (IllagerEntityRenderStateInterface)evokerEntityRenderState;
        if (!iRenderState.getEquippedLegsStack().isEmpty()) {
            cbireturn.setReturnValue(Identifier.of(ArmorRestitched.MOD_ID, "textures/entity/illager_armored/evoker.png"));
        }
    }
    
}

