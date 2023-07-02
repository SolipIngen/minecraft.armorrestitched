package solipingen.armorrestitched.client.render.entity.model;

import java.util.Collection;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.util.math.MathHelper;


@Environment(value = EnvType.CLIENT)
public class VillagerArmorEntityModel<T extends VillagerEntity> extends SinglePartEntityModel<T> {
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart arms;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    private static final Dilation OUTER_DILATION = new Dilation(1.0f);
    private static final Dilation INNER_DILATION = new Dilation(0.5f);


    public VillagerArmorEntityModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild(EntityModelPartNames.HEAD);
        this.body = root.getChild(EntityModelPartNames.BODY);
        this.arms = root.getChild(EntityModelPartNames.ARMS);
        this.leftLeg = root.getChild(EntityModelPartNames.LEFT_LEG);
        this.rightLeg = root.getChild(EntityModelPartNames.RIGHT_LEG);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        boolean bl = ((MerchantEntity)entity).getHeadRollingTimeLeft() > 0;
        this.head.yaw = headYaw*((float)Math.PI/180);
        this.head.pitch = headPitch*((float)Math.PI/180);
        if (bl) {
            this.head.roll = 0.3f*MathHelper.sin(0.45f*animationProgress);
            this.head.pitch = 0.4f;
        } else {
            this.head.roll = 0.0f;
        }
        this.rightLeg.pitch = MathHelper.cos(limbAngle*0.6662f)*1.4f*limbDistance * 0.5f;
        this.leftLeg.pitch = MathHelper.cos(limbAngle*0.6662f + (float)Math.PI)*1.4f*limbDistance*0.5f;
        this.rightLeg.yaw = 0.0f;
        this.leftLeg.yaw = 0.0f;
    }

    public static TexturedModelData getLegsArmorTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-4.0f, -10.0f, -4.0f, 8.0f, 8.0f, 8.0f, INNER_DILATION), ModelTransform.NONE);
        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(16, 16).cuboid(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, OUTER_DILATION.add(0.2f)), ModelTransform.NONE);
        modelPartData.addChild(EntityModelPartNames.ARMS, ModelPartBuilder.create().uv(40, 16).cuboid(-8.0f, -2.0f, -2.0f, 4.0f, 8.0f, 4.0f, OUTER_DILATION.add(0.15f)).uv(40, 16).mirrored().cuboid(4.0f, -2.0f, -2.0f, 4.0f, 8.0f, 4.0f, OUTER_DILATION.add(0.2f)), ModelTransform.of(0.0f, 3.0f, -1.0f, -0.75f, 0.0f, 0.0f));
        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(0, 16).cuboid(-2.0f, VillagerArmorEntityModel.getFreshLegsYCorrection(), -2.0f, 4.0f, 12.0f, 4.0f, INNER_DILATION.add(0.1f)), ModelTransform.pivot(-1.9f, 12.0f, 0.0f));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(0, 16).mirrored().cuboid(-2.0f, VillagerArmorEntityModel.getFreshLegsYCorrection(), -2.0f, 4.0f, 12.0f, 4.0f, INNER_DILATION.add(0.0997f)), ModelTransform.pivot(1.9f, 12.0f, 0.0f));
        return TexturedModelData.of(modelData, 64, 32);
    }

    public static TexturedModelData getHeadArmorTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-4.0f, -10.0f, -4.0f, 8.0f, 8.0f, 8.0f, OUTER_DILATION), ModelTransform.NONE);
        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(16, 16).cuboid(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, OUTER_DILATION.add(0.2f)), ModelTransform.NONE);
        modelPartData.addChild(EntityModelPartNames.ARMS, ModelPartBuilder.create().uv(40, 16).cuboid(-8.0f, -2.0f, -2.0f, 4.0f, 8.0f, 4.0f, OUTER_DILATION.add(0.15f)).uv(40, 16).mirrored().cuboid(4.0f, -2.0f, -2.0f, 4.0f, 8.0f, 4.0f, OUTER_DILATION.add(0.2f)), ModelTransform.of(0.0f, 3.0f, -1.0f, -0.75f, 0.0f, 0.0f));
        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(0, 16).cuboid(-2.0f, VillagerArmorEntityModel.getFreshLegsYCorrection(), -2.0f, 4.0f, 12.0f, 4.0f, OUTER_DILATION.add(0.1f)), ModelTransform.pivot(-1.9f, 12.0f, 0.0f));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(0, 16).mirrored().cuboid(-2.0f, VillagerArmorEntityModel.getFreshLegsYCorrection(), -2.0f, 4.0f, 12.0f, 4.0f, OUTER_DILATION.add(0.0997f)), ModelTransform.pivot(1.9f, 12.0f, 0.0f));
        return TexturedModelData.of(modelData, 64, 32);
    }

    public static TexturedModelData getFeetArmorTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-4.0f, -10.0f, -4.0f, 8.0f, 8.0f, 8.0f, OUTER_DILATION), ModelTransform.NONE);
        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(16, 16).cuboid(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, OUTER_DILATION.add(0.2f)), ModelTransform.NONE);
        modelPartData.addChild(EntityModelPartNames.ARMS, ModelPartBuilder.create().uv(40, 16).cuboid(-8.0f, -2.0f, -2.0f, 4.0f, 8.0f, 4.0f, OUTER_DILATION.add(0.15f)).uv(40, 16).mirrored().cuboid(4.0f, -2.0f, -2.0f, 4.0f, 8.0f, 4.0f, OUTER_DILATION.add(0.2f)), ModelTransform.of(0.0f, 3.0f, -1.0f, -0.75f, 0.0f, 0.0f));
        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(0, 16).cuboid(-2.0f, VillagerArmorEntityModel.getFreshLegsYCorrection(), -2.0f, 4.0f, 12.0f, 4.0f, OUTER_DILATION.add(0.1f)), ModelTransform.pivot(-1.9f, VillagerArmorEntityModel.getFreshLegsYCorrection(), 0.0f));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(0, 16).mirrored().cuboid(-2.0f, VillagerArmorEntityModel.getFreshLegsYCorrection(), -2.0f, 4.0f, 12.0f, 4.0f, OUTER_DILATION.add(0.0997f)), ModelTransform.pivot(1.9f, 12.0f, 0.0f));
        return TexturedModelData.of(modelData, 64, 32);
    }

    public static TexturedModelData getChestArmorTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-4.0f, -10.0f, -4.0f, 8.0f, 8.0f, 8.0f, OUTER_DILATION), ModelTransform.NONE);
        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(16, 16).cuboid(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, OUTER_DILATION.add(0.2f)), ModelTransform.NONE);
        modelPartData.addChild(EntityModelPartNames.ARMS, ModelPartBuilder.create().uv(40, 16).cuboid(-8.0f, -2.0f, -2.0f, 4.0f, 8.0f, 4.0f, OUTER_DILATION.add(0.15f)).uv(40, 16).mirrored().cuboid(4.0f, -2.0f, -2.0f, 4.0f, 8.0f, 4.0f, OUTER_DILATION.add(0.2f)), ModelTransform.of(0.0f, 3.0f, -1.0f, -0.75f, 0.0f, 0.0f));
        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(0, 16).cuboid(-2.0f, VillagerArmorEntityModel.getFreshLegsYCorrection(), -2.0f, 4.0f, 12.0f, 4.0f, OUTER_DILATION.add(0.1f)), ModelTransform.pivot(-1.9f, VillagerArmorEntityModel.getFreshLegsYCorrection(), 0.0f));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(0, 16).mirrored().cuboid(-2.0f, VillagerArmorEntityModel.getFreshLegsYCorrection(), -2.0f, 4.0f, 12.0f, 4.0f, OUTER_DILATION.add(0.0997f)), ModelTransform.pivot(1.9f, 12.0f, 0.0f));
        return TexturedModelData.of(modelData, 64, 32);
    }

    public ModelPart getHead() {
        return this.head;
    }

    public ModelPart getBody() {
        return this.body;
    }

    public ModelPart getArms() {
        return this.arms;
    }

    public ModelPart getRightLeg() {
        return this.rightLeg;
    }

    public ModelPart getLeftLeg() {
        return this.leftLeg;
    }

    private static float getFreshLegsYCorrection() {
        boolean freshAnimationsEnabled = false;
        ResourcePackManager resourcePackManager = MinecraftClient.getInstance().getResourcePackManager();
        Collection<String> enabledResourcePackNames = resourcePackManager.getEnabledNames();
        for (String enabledPackName : enabledResourcePackNames) {
            if (enabledPackName.contains("FreshAnimations")) {
                freshAnimationsEnabled |= true;
            }
        }
        return freshAnimationsEnabled ? -12.0f : 0.0f;
    }

}

