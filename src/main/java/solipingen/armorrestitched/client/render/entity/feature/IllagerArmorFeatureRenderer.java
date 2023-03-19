package solipingen.armorrestitched.client.render.entity.feature;
import org.jetbrains.annotations.Nullable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.ArmorRestitched;
import solipingen.armorrestitched.client.render.entity.model.IllagerArmorEntityModel;
import solipingen.armorrestitched.item.armor.ModArmorMaterials;
import solipingen.armorrestitched.mixin.client.accessors.render.entity.model.IllagerEntityModelAccessor;


@Environment(value=EnvType.CLIENT)
public class IllagerArmorFeatureRenderer<T extends IllagerEntity, M extends IllagerEntityModel<IllagerEntity>> extends FeatureRenderer<IllagerEntity, IllagerEntityModel<IllagerEntity>> {
    private final IllagerArmorEntityModel<IllagerEntity> chestModel;
    private final IllagerArmorEntityModel<IllagerEntity> legsModel;
    private final IllagerArmorEntityModel<IllagerEntity> headModel;
    private final IllagerArmorEntityModel<IllagerEntity> feetModel;
    private final SpriteAtlasTexture armorTrimsAtlas;

    
    public IllagerArmorFeatureRenderer(FeatureRendererContext<IllagerEntity, IllagerEntityModel<IllagerEntity>> context, IllagerArmorEntityModel<IllagerEntity> chestModel, IllagerArmorEntityModel<IllagerEntity> legsModel, IllagerArmorEntityModel<IllagerEntity> headModel, IllagerArmorEntityModel<IllagerEntity> feetModel, BakedModelManager bakery) {
        super(context);
        this.chestModel = chestModel;
        this.legsModel = legsModel;
        this.headModel = headModel;
        this.feetModel = feetModel;
        this.armorTrimsAtlas = bakery.getAtlas(TexturedRenderLayers.ARMOR_TRIMS_ATLAS_TEXTURE);
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
        model.getLeftArm().copyTransform(((IllagerEntityModelAccessor)entityModel).getLeftArm());
        model.getRightArm().copyTransform(((IllagerEntityModelAccessor)entityModel).getRightArm());
        this.setVisible(model, armorSlot);
        boolean legsBl = armorSlot == EquipmentSlot.LEGS;
        boolean bl2 = itemStack.hasGlint();
        if (armorItem instanceof DyeableArmorItem) {
            int i = ((DyeableArmorItem)armorItem).getColor(itemStack);
            float f = (float)(i >> 16 & 0xFF) / 255.0f;
            float g = (float)(i >> 8 & 0xFF) / 255.0f;
            float h = (float)(i & 0xFF) / 255.0f;
            this.renderArmorParts(matrices, vertexConsumers, light, armorItem, bl2, model, legsBl, f, g, h, null);
            this.renderArmorParts(matrices, vertexConsumers, light, armorItem, bl2, model, legsBl, 1.0f, 1.0f, 1.0f, "overlay");
        } 
        else {
            this.renderArmorParts(matrices, vertexConsumers, light, armorItem, bl2, model, legsBl, 1.0f, 1.0f, 1.0f, null);
        }
        if (((LivingEntity)entity).world.getEnabledFeatures().contains(FeatureFlags.UPDATE_1_20)) {
            ArmorTrim.getTrim(((LivingEntity)entity).world.getRegistryManager(), itemStack).ifPresent(trim -> this.renderTrim(armorItem.getMaterial(), matrices, vertexConsumers, light, (ArmorTrim)trim, bl2, model, legsBl, 1.0f, 1.0f, 1.0f));
        }
    }

    @SuppressWarnings("incomplete-switch")
    protected void setVisible(IllagerArmorEntityModel<IllagerEntity> illagerModel, EquipmentSlot slot) {
        illagerModel.getHead().visible = false;
        illagerModel.getRightArm().visible = false;
        illagerModel.getLeftArm().visible = false;
        illagerModel.getRightLeg().visible = false;
        illagerModel.getLeftLeg().visible = false;
        switch (slot) {
            case HEAD: {
                illagerModel.getHead().visible = true;
                break;
            }
            case CHEST: {
                illagerModel.getLeftArm().visible = true;
                illagerModel.getRightArm().visible = true;
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

    private void renderArmorParts(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ArmorItem item, boolean glint, IllagerArmorEntityModel<IllagerEntity> model, boolean legsTextureLayer, float red, float green, float blue, @Nullable String overlay) {
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(this.getArmorTexture(item, legsTextureLayer, overlay)), false, glint);
        ((SinglePartEntityModel<IllagerEntity>)model).render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, red, green, blue, 1.0f);
    }

    private void renderTrim(ArmorMaterial material, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ArmorTrim trim, boolean glint, IllagerArmorEntityModel<IllagerEntity> model, boolean leggings, float red, float green, float blue) {
        Sprite sprite = this.armorTrimsAtlas.getSprite(leggings ? trim.getLeggingsModelId(material) : trim.getGenericModelId(material));
        VertexConsumer vertexConsumer = sprite.getTextureSpecificVertexConsumer(ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, TexturedRenderLayers.getArmorTrims(), true, glint));
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

    private Identifier getArmorTexture(ArmorItem item, boolean legsLayer, @Nullable String overlay) {
        String string = "textures/models/armor/" + item.getMaterial().getName() + "_layer_" + (legsLayer ? "2" : "1") + (String)(overlay == null ? "" : "_" + overlay) + ".png";
        Identifier identifier = item.getMaterial() instanceof ModArmorMaterials ? new Identifier(ArmorRestitched.MOD_ID, string) : new Identifier(string);
        return identifier;
    }

    
}

