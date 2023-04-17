package solipingen.armorrestitched.client.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;
import solipingen.armorrestitched.entity.SilkMothEntity;


@Environment(value=EnvType.CLIENT)
public class SilkMothEntityModel extends SinglePartEntityModel<SilkMothEntity> {
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart rightEar;
    private final ModelPart leftEar;
    private final ModelPart body;
    private final ModelPart rightWing;
    private final ModelPart leftWing;


    public SilkMothEntityModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild(EntityModelPartNames.HEAD);
        this.rightEar = root.getChild(EntityModelPartNames.RIGHT_EAR);
        this.leftEar = root.getChild(EntityModelPartNames.LEFT_EAR);
        this.body = root.getChild(EntityModelPartNames.BODY);
        this.rightWing = root.getChild(EntityModelPartNames.RIGHT_WING);
        this.leftWing = root.getChild(EntityModelPartNames.LEFT_WING);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(0, 16).cuboid(-3.0f, -11.0f, -2.0f, 6.0f, 8.0f, 6.0f), ModelTransform.NONE);
        modelPartData.addChild(EntityModelPartNames.RIGHT_WING, ModelPartBuilder.create().uv(40, 2).cuboid(-15.0f, -11.0f, 2.0f, 12.0f, 8.0f, 0.0f), ModelTransform.NONE);
        modelPartData.addChild(EntityModelPartNames.LEFT_WING, ModelPartBuilder.create().uv(40, 2).mirrored().cuboid(3.0f, -11.0f, 2.0f, 12.0f, 8.0f, 0.0f), ModelTransform.NONE);
        modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-3.0f, -17.0f, -2.0f, 6.0f, 6.0f, 6.0f), ModelTransform.NONE);
        modelPartData.addChild(EntityModelPartNames.RIGHT_EAR, ModelPartBuilder.create().uv(4, 32).cuboid(-7.0f, -25.0f, 0.0f, 6.0f, 8.0f, 1.0f), ModelTransform.NONE);
        modelPartData.addChild(EntityModelPartNames.LEFT_EAR, ModelPartBuilder.create().uv(4, 32).mirrored().cuboid(1.0f, -25.0f, 0.0f, 6.0f, 8.0f, 1.0f), ModelTransform.NONE);
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    @Override
    public void setAngles(SilkMothEntity silkMothEntity, float f, float g, float h, float i, float j) {
        this.body.pitch = 1.0472f + MathHelper.cos(h * 0.1f) * 0.15f;
        this.body.yaw = 0.0f;
        this.head.pitch = this.body.pitch;
        this.head.yaw = this.body.yaw;
        this.rightEar.pitch = this.head.pitch;
        this.rightEar.yaw = this.head.yaw;
        this.leftEar.pitch = this.head.pitch;
        this.leftEar.yaw = this.head.yaw;
        this.rightWing.pitch = this.body.pitch;
        this.leftWing.pitch = this.body.pitch;
        this.rightWing.yaw = MathHelper.cos(0.75f* h * 74.48451f * ((float)Math.PI / 180)) * (float)Math.PI * 0.25f;
        this.leftWing.yaw = -this.rightWing.yaw;
    }
}
