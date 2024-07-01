package solipingen.armorrestitched.mixin.client.render.entity.model;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.mob.IllagerEntity;
import solipingen.armorrestitched.util.interfaces.mixin.render.entity.model.IllagerEntityModelInterface;


@Mixin(IllagerEntityModel.class)
@Environment(value = EnvType.CLIENT)
public abstract class IllagerEntityModelMixin<T extends IllagerEntity> extends SinglePartEntityModel<T> implements ModelWithHead, ModelWithArms, IllagerEntityModelInterface {
    @Unique ModelPart body;

    
    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectedInit(ModelPart root, CallbackInfo cbi) {
        this.body = root.getChild(EntityModelPartNames.BODY);
    }

    @Override
    public ModelPart getBody() {
        return this.body;
    }


}
