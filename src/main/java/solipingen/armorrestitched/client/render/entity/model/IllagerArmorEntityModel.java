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
import net.minecraft.client.render.entity.model.CrossbowPosing;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;


@Environment(value = EnvType.CLIENT)
public class IllagerArmorEntityModel<T extends IllagerEntity> extends SinglePartEntityModel<T> implements ModelWithArms, ModelWithHead {
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart arms;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private static final Dilation OUTER_DILATION = new Dilation(1.0f);
    private static final Dilation INNER_DILATION = new Dilation(0.5f);


    public IllagerArmorEntityModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild(EntityModelPartNames.HEAD);
        this.arms = root.getChild(EntityModelPartNames.ARMS);
        this.leftLeg = root.getChild(EntityModelPartNames.LEFT_LEG);
        this.rightLeg = root.getChild(EntityModelPartNames.RIGHT_LEG);
        this.leftArm = root.getChild(EntityModelPartNames.LEFT_ARM);
        this.rightArm = root.getChild(EntityModelPartNames.RIGHT_ARM);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    @Override
    public void setAngles(T illagerEntity, float f, float g, float h, float i, float j) {
        this.head.yaw = i * ((float)Math.PI / 180);
        this.head.pitch = j * ((float)Math.PI / 180);
        if (this.riding) {
            this.rightArm.pitch = -0.62831855f;
            this.rightArm.yaw = 0.0f;
            this.rightArm.roll = 0.0f;
            this.leftArm.pitch = -0.62831855f;
            this.leftArm.yaw = 0.0f;
            this.leftArm.roll = 0.0f;
            this.rightLeg.pitch = -1.4137167f;
            this.rightLeg.yaw = 0.31415927f;
            this.rightLeg.roll = 0.07853982f;
            this.leftLeg.pitch = -1.4137167f;
            this.leftLeg.yaw = -0.31415927f;
            this.leftLeg.roll = -0.07853982f;
        } 
        else {
            this.rightArm.pitch = MathHelper.cos(f * 0.6662f + (float)Math.PI) * 2.0f * g * 0.5f;
            this.rightArm.yaw = 0.0f;
            this.rightArm.roll = 0.0f;
            this.leftArm.pitch = MathHelper.cos(f * 0.6662f) * 2.0f * g * 0.5f;
            this.leftArm.yaw = 0.0f;
            this.leftArm.roll = 0.0f;
            this.rightLeg.pitch = MathHelper.cos(f * 0.6662f) * 1.4f * g * 0.5f;
            this.rightLeg.yaw = 0.0f;
            this.rightLeg.roll = 0.0f;
            this.leftLeg.pitch = MathHelper.cos(f * 0.6662f + (float)Math.PI) * 1.4f * g * 0.5f;
            this.leftLeg.yaw = 0.0f;
            this.leftLeg.roll = 0.0f;
        }
        IllagerEntity.State state = ((IllagerEntity)illagerEntity).getState();
        if (state == IllagerEntity.State.ATTACKING) {
            if (((LivingEntity)illagerEntity).getMainHandStack().isEmpty()) {
                CrossbowPosing.meleeAttack(this.leftArm, this.rightArm, true, this.handSwingProgress, h);
            }
            else {
                CrossbowPosing.meleeAttack(this.rightArm, this.leftArm, illagerEntity, this.handSwingProgress, h);
            }
        }
        else if (state == IllagerEntity.State.SPELLCASTING) {
            this.rightArm.pivotZ = 0.0f;
            this.rightArm.pivotX = -5.0f;
            this.leftArm.pivotZ = 0.0f;
            this.leftArm.pivotX = 5.0f;
            this.rightArm.pitch = MathHelper.cos(h * 0.6662f) * 0.25f;
            this.leftArm.pitch = MathHelper.cos(h * 0.6662f) * 0.25f;
            this.rightArm.roll = 2.3561945f;
            this.leftArm.roll = -2.3561945f;
            this.rightArm.yaw = 0.0f;
            this.leftArm.yaw = 0.0f;
        }
        else if (state == IllagerEntity.State.BOW_AND_ARROW) {
            this.rightArm.yaw = -0.1f + this.head.yaw;
            this.rightArm.pitch = -1.5707964f + this.head.pitch;
            this.leftArm.pitch = -0.9424779f + this.head.pitch;
            this.leftArm.yaw = this.head.yaw - 0.4f;
            this.leftArm.roll = 1.5707964f;
        }
        else if (state == IllagerEntity.State.CROSSBOW_HOLD) {
            CrossbowPosing.hold(this.rightArm, this.leftArm, this.head, true);
        }
        else if (state == IllagerEntity.State.CROSSBOW_CHARGE) {
            CrossbowPosing.charge(this.rightArm, this.leftArm, illagerEntity, true);
        }
        else if (state == IllagerEntity.State.CELEBRATING) {
            this.rightArm.pivotZ = 0.0f;
            this.rightArm.pivotX = -5.0f;
            this.rightArm.pitch = MathHelper.cos(h * 0.6662f) * 0.05f;
            this.rightArm.roll = 2.670354f;
            this.rightArm.yaw = 0.0f;
            this.leftArm.pivotZ = 0.0f;
            this.leftArm.pivotX = 5.0f;
            this.leftArm.pitch = MathHelper.cos(h * 0.6662f) * 0.05f;
            this.leftArm.roll = -2.3561945f;
            this.leftArm.yaw = 0.0f;
        }
        boolean bl = state == IllagerEntity.State.CROSSED;
        this.arms.visible = bl;
        this.leftArm.visible = !bl;
        this.rightArm.visible = !bl;
    }

    public static TexturedModelData getLegsArmorTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-4.0f, -10.0f, -4.0f, 8.0f, 8.0f, 8.0f, INNER_DILATION), ModelTransform.NONE);
        modelPartData.addChild(EntityModelPartNames.ARMS, ModelPartBuilder.create().uv(40, 16).cuboid(-8.0f, -2.0f, -2.0f, 0.0f, 0.0f, 0.0f).uv(40, 16).mirrored().cuboid(4.0f, -2.0f, -2.0f, 0.0f, 0.0f, 0.0f), ModelTransform.of(0.0f, 3.0f, -1.0f, -0.75f, 0.0f, 0.0f));
        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(0, 16).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, INNER_DILATION.add(0.1f)), ModelTransform.pivot(-1.9f, 12.0f, 0.0f));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(0, 16).mirrored().cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, INNER_DILATION.add(0.0997f)), ModelTransform.pivot(1.9f, 12.0f, 0.0f));
        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(40, 16).cuboid(-3.0f, -2.0f, -2.0f, 0.0f, 0.0f, 0.0f, INNER_DILATION.add(0.15f)), ModelTransform.pivot(-5.0f, 2.0f, 0.0f));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(40, 16).mirrored().cuboid(-1.0f, -2.0f, -2.0f, 0.0f, 0.0f, 0.0f, INNER_DILATION.add(0.15f)), ModelTransform.pivot(5.0f, 2.0f, 0.0f));
        return TexturedModelData.of(modelData, 64, 32);
    }

    public static TexturedModelData getHeadArmorTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-4.0f, -10.0f, -4.0f, 8.0f, 8.0f, 8.0f, OUTER_DILATION), ModelTransform.NONE);
        modelPartData.addChild(EntityModelPartNames.ARMS, ModelPartBuilder.create().uv(40, 16).cuboid(-8.0f, -2.0f, -2.0f, 0.0f, 0.0f, 0.0f).uv(40, 16).mirrored().cuboid(4.0f, -2.0f, -2.0f, 0.0f, 0.0f, 0.0f), ModelTransform.of(0.0f, 3.0f, -1.0f, -0.75f, 0.0f, 0.0f));
        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(0, 16).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, OUTER_DILATION.add(0.1f)), ModelTransform.pivot(-1.9f, 12.0f, 0.0f));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(0, 16).mirrored().cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, OUTER_DILATION.add(0.0997f)), ModelTransform.pivot(1.9f, 12.0f, 0.0f));
        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(40, 16).cuboid(-3.0f, -2.0f, -2.0f, 0.0f, 0.0f, 0.0f, OUTER_DILATION.add(0.15f)), ModelTransform.pivot(-5.0f, 2.0f, 0.0f));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(40, 16).mirrored().cuboid(-1.0f, -2.0f, -2.0f, 0.0f, 0.0f, 0.0f, OUTER_DILATION.add(0.15f)), ModelTransform.pivot(5.0f, 2.0f, 0.0f));
        return TexturedModelData.of(modelData, 64, 32);
    }

    public static TexturedModelData getFeetArmorTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-4.0f, -10.0f, -4.0f, 8.0f, 8.0f, 8.0f, OUTER_DILATION), ModelTransform.NONE);
        modelPartData.addChild(EntityModelPartNames.ARMS, ModelPartBuilder.create().uv(40, 16).cuboid(-8.0f, -2.0f, -2.0f, 0.0f, 0.0f, 0.0f).uv(40, 16).mirrored().cuboid(4.0f, -2.0f, -2.0f, 0.0f, 0.0f, 0.0f), ModelTransform.of(0.0f, 3.0f, -1.0f, -0.75f, 0.0f, 0.0f));
        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(0, 16).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, OUTER_DILATION.add(0.1f)), ModelTransform.pivot(-1.9f, 12.0f, 0.0f));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(0, 16).mirrored().cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, OUTER_DILATION.add(0.0997f)), ModelTransform.pivot(1.9f, 12.0f, 0.0f));
        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(40, 16).cuboid(-3.0f, -2.0f, -2.0f, 0.0f, 0.0f, 0.0f, OUTER_DILATION.add(0.15f)), ModelTransform.pivot(-5.0f, 2.0f, 0.0f));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(40, 16).mirrored().cuboid(-1.0f, -2.0f, -2.0f, 0.0f, 0.0f, 0.0f, OUTER_DILATION.add(0.15f)), ModelTransform.pivot(5.0f, 2.0f, 0.0f));
        return TexturedModelData.of(modelData, 64, 32);
    }

    public static TexturedModelData getChestArmorTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-4.0f, -10.0f, -4.0f, 8.0f, 8.0f, 8.0f, OUTER_DILATION), ModelTransform.NONE);
        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(16, 16).cuboid(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, OUTER_DILATION.add(0.1f)), ModelTransform.NONE);
        modelPartData.addChild(EntityModelPartNames.ARMS, ModelPartBuilder.create().uv(40, 16).cuboid(-8.0f, -2.0f, -2.0f, 4.0f, 8.0f, 4.0f, OUTER_DILATION.add(0.15f)).uv(40, 16).mirrored().cuboid(4.0f, -2.0f, -2.0f, 4.0f, 8.0f, 4.0f, OUTER_DILATION.add(0.2f)), ModelTransform.of(0.0f, 3.0f, -1.0f, -0.75f, 0.0f, 0.0f));
        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(0, 16).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, OUTER_DILATION.add(0.1f)), ModelTransform.pivot(-1.9f, 12.0f, 0.0f));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(0, 16).mirrored().cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, OUTER_DILATION.add(0.0997f)), ModelTransform.pivot(1.9f, 12.0f, 0.0f));
        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(40, 16).cuboid(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, OUTER_DILATION.add(0.15f)), ModelTransform.pivot(-5.0f, 2.0f, 0.0f));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(40, 16).mirrored().cuboid(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, OUTER_DILATION.add(0.15f)), ModelTransform.pivot(5.0f, 2.0f, 0.0f));
        return TexturedModelData.of(modelData, 64, 32);
    }

    @Override
    public ModelPart getHead() {
        return this.head;
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

    public ModelPart getRightArm() {
        return this.rightArm;
    }

    public ModelPart getLeftArm() {
        return this.leftArm;
    }

    @Override
    public void setArmAngle(Arm arm, MatrixStack matrices) {
        this.getAttackingArm(arm).rotate(matrices);
    }

    private ModelPart getAttackingArm(Arm arm) {
        if (arm == Arm.LEFT) {
            return this.leftArm;
        }
        return this.rightArm;
    }

}

