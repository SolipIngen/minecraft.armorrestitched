package solipingen.armorrestitched.mixin.client.accessors.render.entity.model;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.HorseEntityModel;


@Mixin(HorseEntityModel.class)
public interface HorseEntityModelAccessor {
    
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

    @Accessor("saddle")
    public ModelPart[] getSaddle();

    @Accessor("straps")
    public ModelPart[] getStraps();


    @Invoker("getBodyParts")
    public Iterable<ModelPart> invokeGetBodyParts();
    


}
