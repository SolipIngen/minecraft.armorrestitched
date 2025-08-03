package solipingen.armorrestitched.mixin.client.accessors.render.entity.model;

import net.minecraft.client.render.entity.model.AbstractHorseEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.model.ModelPart;


@Mixin(AbstractHorseEntityModel.class)
public interface AbstractHorseEntityModelAccessor {
    
    @Accessor("head")
    public ModelPart getHead();

    @Accessor("body")
    public ModelPart getBody();

    @Accessor("rightHindLeg")
    public ModelPart getRightHindLeg();

    @Accessor("leftHindLeg")
    public ModelPart getLeftHindLeg();

    @Accessor("rightFrontLeg")
    public ModelPart getRightFrontLeg();

    @Accessor("leftFrontLeg")
    public ModelPart getLeftFrontLeg();
    


}
