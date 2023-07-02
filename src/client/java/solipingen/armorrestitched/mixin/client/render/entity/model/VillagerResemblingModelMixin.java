package solipingen.armorrestitched.mixin.client.render.entity.model;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.ModelWithHat;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.render.entity.model.VillagerResemblingModel;
import net.minecraft.entity.Entity;
import solipingen.armorrestitched.util.interfaces.mixin.render.entity.model.VillagerResemblingModelInterface;


@Mixin(VillagerResemblingModel.class)
public abstract class VillagerResemblingModelMixin<T extends Entity> extends SinglePartEntityModel<T> implements ModelWithHead, ModelWithHat, VillagerResemblingModelInterface {
    @Shadow @Final private ModelPart hat;
    private @Final ModelPart body;
    private @Final ModelPart arms;

    
    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectedInit(ModelPart root, CallbackInfo cbi) {
        this.body = root.getChild(EntityModelPartNames.BODY);
        this.arms = root.getChild(EntityModelPartNames.ARMS);
    }

    @Override
    public ModelPart getHat() {
        return this.hat;
    }

    @Override
    public ModelPart getBody() {
        return this.body;
    }

    @Override
    public ModelPart getArms() {
        return this.arms;
    }


}

