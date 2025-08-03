package solipingen.armorrestitched.client.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.ModelTransformer;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.render.entity.state.IllagerEntityRenderState;
import net.minecraft.util.math.MathHelper;
import solipingen.armorrestitched.client.resource.ModClientResourcePacks;


@Environment(value = EnvType.CLIENT)
public class IllagerArmorEntityModel extends EntityModel<IllagerEntityRenderState> implements ModelWithHead {
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;



    public IllagerArmorEntityModel(ModelPart root) {
        super(root);
        this.root = root;
        this.head = root.getChild(EntityModelPartNames.HEAD);
        this.body = root.getChild(EntityModelPartNames.BODY);
        this.leftLeg = root.getChild(EntityModelPartNames.LEFT_LEG);
        this.rightLeg = root.getChild(EntityModelPartNames.RIGHT_LEG);
    }

//    @Override
//    public ModelPart getPart() {
//        return this.root;
//    }

    @Override
    public void setAngles(IllagerEntityRenderState illagerEntityRenderState) {
        this.head.yaw = illagerEntityRenderState.relativeHeadYaw*((float)Math.PI / 180);
        this.head.pitch = illagerEntityRenderState.pitch*((float)Math.PI / 180);
        float g = illagerEntityRenderState.limbSwingAmplitude;
        float f = illagerEntityRenderState.limbSwingAnimationProgress;
        if (illagerEntityRenderState.hasVehicle) {
            this.rightLeg.pitch = -1.4137167f;
            this.rightLeg.yaw = 0.31415927f;
            this.rightLeg.roll = 0.07853982f;
            this.leftLeg.pitch = -1.4137167f;
            this.leftLeg.yaw = -0.31415927f;
            this.leftLeg.roll = -0.07853982f;
        } 
        else {
            this.rightLeg.pitch = MathHelper.cos(0.6662f*f)*1.4f*g*0.5f;
            this.rightLeg.yaw = 0.0f;
            this.rightLeg.roll = 0.0f;
            this.leftLeg.pitch = MathHelper.cos(0.6662f*f + (float)Math.PI)*1.4f*g*0.5f;
            this.leftLeg.yaw = 0.0f;
            this.leftLeg.roll = 0.0f;
        }
    }

    public static TexturedModelData getArmorTexturedModelData(Dilation dilation) {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-4.0f, -10.0f, -4.0f, 8.0f, 8.0f, 8.0f, dilation), ModelTransform.NONE);
        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(16, 16).cuboid(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, dilation), ModelTransform.NONE);
        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(0, 16).cuboid(-2.0f, IllagerArmorEntityModel.getFreshLegsYCorrection(), -2.0f, 4.0f, 12.0f, 4.0f, dilation.add(0.1f)), ModelTransform.origin(-1.9f, 12.0f, 0.0f));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(0, 16).mirrored().cuboid(-2.0f, IllagerArmorEntityModel.getFreshLegsYCorrection(), -2.0f, 4.0f, 12.0f, 4.0f, dilation.add(0.0997f)), ModelTransform.origin(1.9f, 12.0f, 0.0f));
        return TexturedModelData.of(modelData, 64, 32).transform(ModelTransformer.scaling(0.95f));
    }

    @Override
    public ModelPart getHead() {
        return this.head;
    }

    public ModelPart getBody() {
        return this.body;
    }

    public ModelPart getRightLeg() {
        return this.rightLeg;
    }

    public ModelPart getLeftLeg() {
        return this.leftLeg;
    }

    private static float getFreshLegsYCorrection() {
        return ModClientResourcePacks.isFreshAnimationsEnabled() ? -12.0f : 0.0f;
    }

}

