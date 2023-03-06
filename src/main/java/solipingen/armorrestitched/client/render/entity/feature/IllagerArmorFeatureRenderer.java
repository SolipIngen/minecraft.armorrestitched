package solipingen.armorrestitched.client.render.entity.feature;
import org.jetbrains.annotations.Nullable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.ArmorRestitched;
import solipingen.armorrestitched.client.render.entity.model.IllagerArmorEntityModel;
import solipingen.armorrestitched.mixin.client.accessors.render.entity.model.IllagerEntityModelAccessor;


@Environment(value=EnvType.CLIENT)
public class IllagerArmorFeatureRenderer<T extends IllagerEntity, M extends IllagerEntityModel<IllagerEntity>> extends FeatureRenderer<IllagerEntity, IllagerEntityModel<IllagerEntity>> {
    private final IllagerArmorEntityModel<IllagerEntity> chestModel;
    private final IllagerArmorEntityModel<IllagerEntity> legsModel;
    private final IllagerArmorEntityModel<IllagerEntity> headModel;
    private final IllagerArmorEntityModel<IllagerEntity> feetModel;

    
    public IllagerArmorFeatureRenderer(FeatureRendererContext<IllagerEntity, IllagerEntityModel<IllagerEntity>> context, IllagerArmorEntityModel<IllagerEntity> chestModel, IllagerArmorEntityModel<IllagerEntity> legsModel, IllagerArmorEntityModel<IllagerEntity> headModel, IllagerArmorEntityModel<IllagerEntity> feetModel) {
        super(context);
        this.chestModel = chestModel;
        this.legsModel = legsModel;
        this.headModel = headModel;
        this.feetModel = feetModel;
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, IllagerEntity illagerEntity, float f, float g, float h, float j, float k, float l) {
        this.renderArmor(matrixStack, vertexConsumerProvider, illagerEntity, EquipmentSlot.CHEST, i, this.getModel(EquipmentSlot.CHEST));
        this.renderArmor(matrixStack, vertexConsumerProvider, illagerEntity, EquipmentSlot.LEGS, i, this.getModel(EquipmentSlot.LEGS));
        this.renderArmor(matrixStack, vertexConsumerProvider, illagerEntity, EquipmentSlot.FEET, i, this.getModel(EquipmentSlot.FEET));
        this.renderArmor(matrixStack, vertexConsumerProvider, illagerEntity, EquipmentSlot.HEAD, i, this.getModel(EquipmentSlot.HEAD));
    }

    private void renderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, IllagerEntity entity, EquipmentSlot armorSlot, int light, IllagerArmorEntityModel<IllagerEntity> model) {
        ItemStack itemStack = entity.getEquippedStack(armorSlot);
        if (!(itemStack.getItem() instanceof ArmorItem)) {
            return;
        }
        ArmorItem armorItem = (ArmorItem)itemStack.getItem();
        if (armorItem.getSlotType() != armorSlot) {
            return;
        }
        IllagerEntityModel<IllagerEntity> entityModel = this.getContextModel();
        model.getHead().copyTransform(entityModel.getHead());
        model.getLeftLeg().copyTransform(((IllagerEntityModelAccessor)entityModel).getLeftLeg());
        model.getRightLeg().copyTransform(((IllagerEntityModelAccessor)entityModel).getRightLeg());
        this.setVisible(model, armorSlot);
        boolean legsBl = armorSlot == EquipmentSlot.LEGS;
        boolean headBl = armorSlot == EquipmentSlot.HEAD;
        boolean feetBl = armorSlot == EquipmentSlot.FEET;
        boolean bl2 = itemStack.hasGlint();
        if (armorItem instanceof DyeableArmorItem) {
            int i = ((DyeableArmorItem)armorItem).getColor(itemStack);
            float f = (float)(i >> 16 & 0xFF) / 255.0f;
            float g = (float)(i >> 8 & 0xFF) / 255.0f;
            float h = (float)(i & 0xFF) / 255.0f;
            this.renderArmorParts(matrices, vertexConsumers, light, armorItem, bl2, model, legsBl, headBl, feetBl, f, g, h, null);
            this.renderArmorParts(matrices, vertexConsumers, light, armorItem, bl2, model, legsBl, headBl, feetBl, 1.0f, 1.0f, 1.0f, "overlay");
        } else {
            this.renderArmorParts(matrices, vertexConsumers, light, armorItem, bl2, model, legsBl, headBl, feetBl, 1.0f, 1.0f, 1.0f, null);
        }
    }

    @SuppressWarnings("incomplete-switch")
    protected void setVisible(IllagerArmorEntityModel<IllagerEntity> illagerModel, EquipmentSlot slot) {
        switch (slot) {
            case HEAD: {
                illagerModel.getHead().visible = true;
                illagerModel.getHat().visible = true;
                break;
            }
            case LEGS: {
                illagerModel.getRightLeg().visible = true;
                illagerModel.getLeftLeg().visible = true;
                break;
            }
            case FEET: {
                illagerModel.getRightLeg().visible = true;
                illagerModel.getLeftLeg().visible = true;
            }
        }
    }

    private void renderArmorParts(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ArmorItem item, boolean glint, IllagerArmorEntityModel<IllagerEntity> model, boolean legsTextureLayer, boolean headTextureLayer, boolean feetTextureLayer, float red, float green, float blue, @Nullable String overlay) {
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(this.getArmorTexture(item, legsTextureLayer, headTextureLayer, feetTextureLayer, overlay)), false, glint);
        ((SinglePartEntityModel<IllagerEntity>)model).render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, red, green, blue, 1.0f);
    }

    private IllagerArmorEntityModel<IllagerEntity> getModel(EquipmentSlot slot) {
        if (slot == EquipmentSlot.LEGS) {
            return this.legsModel;
        }
        else if (slot == EquipmentSlot.HEAD) {
            return this.headModel;
        }
        else if (slot == EquipmentSlot.FEET) {
            return this.feetModel;
        }
        return this.chestModel;
    }

    private Identifier getArmorTexture(ArmorItem item, boolean legsLayer, boolean headLayer, boolean feetLayer, @Nullable String overlay) {
        String string = "textures/models/armor/illager_armor/" + item.getMaterial().getName() + "_layer_" + (feetLayer ? 4 : (headLayer ? 3 : (legsLayer ? 2 : 1))) + (String)(overlay == null ? "" : "_" + overlay) + ".png";
        return new Identifier(ArmorRestitched.MOD_ID, string);
    }

    
}

