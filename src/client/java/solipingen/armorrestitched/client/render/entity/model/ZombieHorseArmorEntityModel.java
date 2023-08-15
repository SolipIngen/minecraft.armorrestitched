package solipingen.armorrestitched.client.render.entity.model;

import com.google.common.collect.ImmutableList;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.entity.passive.AbstractHorseEntity;
import solipingen.armorrestitched.client.resource.ModClientResourcePacks;


@Environment(value = EnvType.CLIENT)
public class ZombieHorseArmorEntityModel<T extends AbstractHorseEntity> extends AnimalModel<T> {
   protected static final String HEAD_PARTS = "head_parts";
   protected final ModelPart body;
   protected final ModelPart head;
   private final ModelPart rightHindLeg;
   private final ModelPart leftHindLeg;
   private final ModelPart rightFrontLeg;
   private final ModelPart leftFrontLeg;
   private final ModelPart[] saddle;
   private final ModelPart[] straps;


   public ZombieHorseArmorEntityModel(ModelPart root) {
      super(true, 16.2f, 1.36f, 2.7272f, 2.0f, 20.0f);
      this.body = root.getChild("body");
      this.head = root.getChild("head_parts");
      this.rightHindLeg = root.getChild("right_hind_leg");
      this.leftHindLeg = root.getChild("left_hind_leg");
      this.rightFrontLeg = root.getChild("right_front_leg");
      this.leftFrontLeg = root.getChild("left_front_leg");
      ModelPart modelPart = this.body.getChild("saddle");
      ModelPart modelPart2 = this.head.getChild("left_saddle_mouth");
      ModelPart modelPart3 = this.head.getChild("right_saddle_mouth");
      ModelPart modelPart4 = this.head.getChild("left_saddle_line");
      ModelPart modelPart5 = this.head.getChild("right_saddle_line");
      ModelPart modelPart6 = this.head.getChild("head_saddle");
      ModelPart modelPart7 = this.head.getChild("mouth_saddle_wrap");
      this.saddle = new ModelPart[]{modelPart, modelPart2, modelPart3, modelPart6, modelPart7};
      this.straps = new ModelPart[]{modelPart4, modelPart5};
   }

   public static TexturedModelData getTexturedModelData() {
      ModelData modelData = new ModelData();
      ModelPartData modelPartData = modelData.getRoot();
      ModelPartData modelPartData2 = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 32).cuboid(-5.0f, -8.0f, -17.0f, 10.0f, 10.0f, 22.0f, new Dilation(0.05f)), ModelTransform.pivot(0.0f, 11.0f, 5.0f));
      ModelPartData modelPartData3 = modelPartData.addChild("head_parts", ModelPartBuilder.create().uv(0, 35).cuboid(-2.05f, -6.0f, -2.0f, 4.0f, 12.0f, 7.0f), ModelTransform.of(0.0f, 4.0f, -12.0f, 0.5235988f, 0.0f, 0.0f));
      ModelPartData modelPartData4 = modelPartData3.addChild("head", ModelPartBuilder.create().uv(0, 13).cuboid(-3.0f, -11.0f, -2.0f, 6.0f, 5.0f, 7.0f, new Dilation(0.1f)), ModelTransform.NONE);
      modelPartData3.addChild("mane", ModelPartBuilder.create().uv(56, 36).cuboid(-1.0f, -11.0f, 5.01f, 2.0f, 16.0f, 2.0f, new Dilation(0.1f)), ModelTransform.NONE);
      modelPartData3.addChild("upper_mouth", ModelPartBuilder.create().uv(0, 25).cuboid(-2.0f, -11.0f, -7.0f, 4.0f, 5.0f, 5.0f, new Dilation(0.1f)), ModelTransform.NONE);
      modelPartData.addChild("left_hind_leg", ModelPartBuilder.create().uv(48, 21).mirrored().cuboid(-3.0f + ZombieHorseArmorEntityModel.getFreshLegsXCorrection(), -1.01f + ZombieHorseArmorEntityModel.getFreshLegsYCorrection(), -1.0f + ZombieHorseArmorEntityModel.getFreshHindLegsZCorrection(), 4.0f, 11.0f, 4.0f, new Dilation(0.101f)), ModelTransform.pivot(4.0f, 14.0f, 7.0f));
      modelPartData.addChild("right_hind_leg", ModelPartBuilder.create().uv(48, 21).cuboid(-1.0f - ZombieHorseArmorEntityModel.getFreshLegsXCorrection(), -1.01f + ZombieHorseArmorEntityModel.getFreshLegsYCorrection(), -1.0f + ZombieHorseArmorEntityModel.getFreshHindLegsZCorrection(), 4.0f, 11.0f, 4.0f, new Dilation(0.101f)), ModelTransform.pivot(-4.0f, 14.0f, 7.0f));
      modelPartData.addChild("left_front_leg", ModelPartBuilder.create().uv(48, 21).mirrored().cuboid(-3.0f + ZombieHorseArmorEntityModel.getFreshLegsXCorrection(), -1.01f + ZombieHorseArmorEntityModel.getFreshLegsYCorrection(), -1.9f + ZombieHorseArmorEntityModel.getFreshFrontLegsZCorrection(), 4.0f, 11.0f, 4.0f, new Dilation(0.101f)), ModelTransform.pivot(4.0f, 14.0f, -12.0f));
      modelPartData.addChild("right_front_leg", ModelPartBuilder.create().uv(48, 21).cuboid(-1.0f - ZombieHorseArmorEntityModel.getFreshLegsXCorrection(), -1.01f + ZombieHorseArmorEntityModel.getFreshLegsYCorrection(), -1.9f + ZombieHorseArmorEntityModel.getFreshFrontLegsZCorrection(), 4.0f, 11.0f, 4.0f, new Dilation(0.101f)), ModelTransform.pivot(-4.0f, 14.0f, -12.0f));
      modelPartData2.addChild("saddle", ModelPartBuilder.create().uv(26, 0).cuboid(-5.0f, -8.0f, -9.0f, 10.0f, 9.0f, 9.0f, new Dilation(0.5f)), ModelTransform.NONE);
      modelPartData3.addChild("left_saddle_mouth", ModelPartBuilder.create().uv(29, 5).cuboid(2.0f, -9.0f, -6.0f, 1.0f, 2.0f, 2.0f, new Dilation(0.1f)), ModelTransform.NONE);
      modelPartData3.addChild("right_saddle_mouth", ModelPartBuilder.create().uv(29, 5).cuboid(-3.0f, -9.0f, -6.0f, 1.0f, 2.0f, 2.0f, new Dilation(0.1f)), ModelTransform.NONE);
      modelPartData3.addChild("left_saddle_line", ModelPartBuilder.create().uv(32, 2).cuboid(3.1f, -6.0f, -8.0f, 0.0f, 3.0f, 16.0f), ModelTransform.rotation(-0.5235988f, 0.0f, 0.0f));
      modelPartData3.addChild("right_saddle_line", ModelPartBuilder.create().uv(32, 2).cuboid(-3.1f, -6.0f, -8.0f, 0.0f, 3.0f, 16.0f), ModelTransform.rotation(-0.5235988f, 0.0f, 0.0f));
      modelPartData3.addChild("head_saddle", ModelPartBuilder.create().uv(1, 1).cuboid(-3.0f, -11.0f, -1.9f, 6.0f, 5.0f, 6.0f, new Dilation(0.22f)), ModelTransform.NONE);
      modelPartData3.addChild("mouth_saddle_wrap", ModelPartBuilder.create().uv(19, 0).cuboid(-2.0f, -11.0f, -4.0f, 4.0f, 5.0f, 2.0f, new Dilation(0.2f)), ModelTransform.NONE);
      modelPartData4.addChild("left_ear", ModelPartBuilder.create().uv(19, 16).cuboid(0.55f, -13.0f, 4.0f, 2.0f, 3.0f, 1.0f, new Dilation(-0.001f)), ModelTransform.NONE);
      modelPartData4.addChild("right_ear", ModelPartBuilder.create().uv(19, 16).cuboid(-2.55f, -13.0f, 4.0f, 2.0f, 3.0f, 1.0f, new Dilation(-0.001f)), ModelTransform.NONE);
      return TexturedModelData.of(modelData, 64, 64);
   }

   @Override
   public void setAngles(T abstractHorseEntity, float f, float g, float h, float i, float j) {
      boolean bl = abstractHorseEntity.isSaddled();
      boolean bl2 = abstractHorseEntity.hasPassengers();
      ModelPart[] modelParts = this.saddle;
      for(int index = 0; index < modelParts.length; index++) {
         ModelPart modelPart = modelParts[index];
         modelPart.visible = bl;
      }
      modelParts = this.straps;
      for(int index = 0; index < modelParts.length; index++) {
         ModelPart modelPart = modelParts[index];
         modelPart.visible = bl2 && bl;
      }
      this.body.pivotY = 11.0f;
   }

   @Override
   public Iterable<ModelPart> getHeadParts() {
      return ImmutableList.of(this.head);
   }

   @Override
   public Iterable<ModelPart> getBodyParts() {
      return ImmutableList.of(this.body, this.rightHindLeg, this.leftHindLeg, this.rightFrontLeg, this.leftFrontLeg);
   }

   public ModelPart getHead() {
      return this.head;
   }

   public ModelPart getBody() {
      return this.body;
   }

   public ModelPart getLeftFrontLeg() {
      return this.leftFrontLeg;
   }

   public ModelPart getLeftHindLeg() {
      return this.leftHindLeg;
   }

   public ModelPart getRightFrontLeg() {
      return this.rightFrontLeg;
   }

   public ModelPart getRightHindLeg() {
      return this.rightHindLeg;
   }
   

   private static float getFreshLegsXCorrection() {
      return ModClientResourcePacks.isFreshAnimationsEnabled() ? 1.0f : 0.0f;
   }

   private static float getFreshLegsYCorrection() {
      return ModClientResourcePacks.isFreshAnimationsEnabled() ? -9.0f : 0.0f;
   }

   private static float getFreshFrontLegsZCorrection() {
      return ModClientResourcePacks.isFreshAnimationsEnabled() ? -0.1f : 0.0f;
   }

   private static float getFreshHindLegsZCorrection() {
      return ModClientResourcePacks.isFreshAnimationsEnabled() ? -1.1f : 0.0f;
   }
    

    
}
