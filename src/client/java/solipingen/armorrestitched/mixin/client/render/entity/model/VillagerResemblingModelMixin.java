package solipingen.armorrestitched.mixin.client.render.entity.model;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.ModelWithHat;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.render.entity.model.VillagerResemblingModel;
import net.minecraft.entity.Entity;
import solipingen.armorrestitched.util.interfaces.mixin.render.entity.model.VillagerResemblingModelInterface;


@Mixin(VillagerResemblingModel.class)
@Environment(value = EnvType.CLIENT)
public abstract class VillagerResemblingModelMixin<T extends Entity> extends SinglePartEntityModel<T> implements ModelWithHead, ModelWithHat, VillagerResemblingModelInterface {
    @Shadow @Final private ModelPart hat;
    @Unique ModelPart body;

    
    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectedInit(ModelPart root, CallbackInfo cbi) {
        this.body = root.getChild(EntityModelPartNames.BODY);
    }

    @Override
    public ModelPart getHat() {
        return this.hat;
    }

    @Override
    public ModelPart getBody() {
        return this.body;
    }


}

