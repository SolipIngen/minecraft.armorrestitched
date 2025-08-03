package solipingen.armorrestitched.mixin.client.render.entity.model;

import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.render.entity.state.IllagerEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import solipingen.armorrestitched.util.interfaces.mixin.render.entity.model.IllagerEntityModelInterface;


@Mixin(IllagerEntityModel.class)
@Environment(value = EnvType.CLIENT)
public abstract class IllagerEntityModelMixin<S extends IllagerEntityRenderState> extends EntityModel<S> implements ModelWithHead, ModelWithArms, IllagerEntityModelInterface {
    @Unique ModelPart body;


    protected IllagerEntityModelMixin(ModelPart root) {
        super(root);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectedInit(ModelPart root, CallbackInfo cbi) {
        this.body = root.getChild(EntityModelPartNames.BODY);
    }

    @Override
    public ModelPart getBody() {
        return this.body;
    }


}
