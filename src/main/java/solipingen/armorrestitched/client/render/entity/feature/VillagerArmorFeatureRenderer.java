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
import net.minecraft.client.render.entity.model.VillagerResemblingModel;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.ArmorRestitched;
import solipingen.armorrestitched.client.render.entity.model.VillagerArmorEntityModel;
import solipingen.armorrestitched.item.ModArmorMaterials;
import solipingen.armorrestitched.mixin.client.accessors.render.entity.model.VillagerResemblingModelAccessor;


@Environment(value = EnvType.CLIENT)
public class VillagerArmorFeatureRenderer<T extends VillagerEntity, M extends VillagerResemblingModel<VillagerEntity>> extends FeatureRenderer<VillagerEntity, VillagerResemblingModel<VillagerEntity>> {
    private final VillagerArmorEntityModel<VillagerEntity> chestModel;
    private final VillagerArmorEntityModel<VillagerEntity> legsModel;
    private final VillagerArmorEntityModel<VillagerEntity> headModel;
    private final VillagerArmorEntityModel<VillagerEntity> feetModel;
    private final SpriteAtlasTexture armorTrimsAtlas;

    
    public VillagerArmorFeatureRenderer(FeatureRendererContext<VillagerEntity, VillagerResemblingModel<VillagerEntity>> context, VillagerArmorEntityModel<VillagerEntity> chestModel, VillagerArmorEntityModel<VillagerEntity> legsModel, VillagerArmorEntityModel<VillagerEntity> headModel, VillagerArmorEntityModel<VillagerEntity> feetModel, BakedModelManager bakery) {
        super(context);
        this.chestModel = chestModel;
        this.legsModel = legsModel;
        this.headModel = headModel;
        this.feetModel = feetModel;
        this.armorTrimsAtlas = bakery.getAtlas(TexturedRenderLayers.ARMOR_TRIMS_ATLAS_TEXTURE);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, VillagerEntity villagerEntity, float f, float g, float h, float j, float k, float l) {
        this.renderArmor(matrixStack, vertexConsumerProvider, villagerEntity, EquipmentSlot.CHEST, i, this.getModel(EquipmentSlot.CHEST));
        this.renderArmor(matrixStack, vertexConsumerProvider, villagerEntity, EquipmentSlot.LEGS, i, this.getModel(EquipmentSlot.LEGS));
        this.renderArmor(matrixStack, vertexConsumerProvider, villagerEntity, EquipmentSlot.FEET, i, this.getModel(EquipmentSlot.FEET));
        this.renderArmor(matrixStack, vertexConsumerProvider, villagerEntity, EquipmentSlot.HEAD, i, this.getModel(EquipmentSlot.HEAD));
    }

    private void renderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, VillagerEntity entity, EquipmentSlot armorSlot, int light, VillagerArmorEntityModel<VillagerEntity> model) {
        ItemStack itemStack = entity.getEquippedStack(armorSlot);
        if (!(itemStack.getItem() instanceof ArmorItem)) {
            return;
        }
        ArmorItem armorItem = (ArmorItem)itemStack.getItem();
        if (armorItem.getSlotType() != armorSlot) {
            return;
        }
        VillagerResemblingModel<VillagerEntity> entityModel = this.getContextModel();
        model.getHead().copyTransform(entityModel.getHead());
        model.getLeftLeg().copyTransform(((VillagerResemblingModelAccessor)entityModel).getLeftLeg());
        model.getRightLeg().copyTransform(((VillagerResemblingModelAccessor)entityModel).getRightLeg());
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
        } 
        else {
            this.renderArmorParts(matrices, vertexConsumers, light, armorItem, bl2, model, legsBl, headBl, feetBl, 1.0f, 1.0f, 1.0f, null);
        }
        if (((LivingEntity)entity).world.getEnabledFeatures().contains(FeatureFlags.UPDATE_1_20)) {
            ArmorTrim.getTrim(((LivingEntity)entity).world.getRegistryManager(), itemStack).ifPresent(trim -> this.renderTrim(armorItem.getMaterial(), matrices, vertexConsumers, light, (ArmorTrim)trim, bl2, model, legsBl, 1.0f, 1.0f, 1.0f));
        }
    }

    @SuppressWarnings("incomplete-switch")
    protected void setVisible(VillagerArmorEntityModel<VillagerEntity> villagerModel, EquipmentSlot slot) {
        villagerModel.getHead().visible = false;
        villagerModel.getRightLeg().visible = false;
        villagerModel.getLeftLeg().visible = false;
        switch (slot) {
            case HEAD: {
                villagerModel.getHead().visible = true;
                break;
            }
            case LEGS: {
                villagerModel.getRightLeg().visible = true;
                villagerModel.getLeftLeg().visible = true;
                break;
            }
            case FEET: {
                villagerModel.getRightLeg().visible = true;
                villagerModel.getLeftLeg().visible = true;
                break;
            }
        }
    }

    private void renderArmorParts(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ArmorItem item, boolean glint, VillagerArmorEntityModel<VillagerEntity> model, boolean legsTextureLayer, boolean headTextureLayer, boolean feetTextureLayer, float red, float green, float blue, @Nullable String overlay) {
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(this.getArmorTexture(item, legsTextureLayer, overlay)), false, glint);
        ((SinglePartEntityModel<VillagerEntity>)model).render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, red, green, blue, 1.0f);
    }

    private void renderTrim(ArmorMaterial material, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ArmorTrim trim, boolean glint, VillagerArmorEntityModel<VillagerEntity> model, boolean leggings, float red, float green, float blue) {
        Sprite sprite = this.armorTrimsAtlas.getSprite(leggings ? trim.getLeggingsModelId(material) : trim.getGenericModelId(material));
        VertexConsumer vertexConsumer = sprite.getTextureSpecificVertexConsumer(ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, TexturedRenderLayers.getArmorTrims(), true, glint));
        ((SinglePartEntityModel<VillagerEntity>)model).render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, red, green, blue, 1.0f);
    }

    private VillagerArmorEntityModel<VillagerEntity> getModel(EquipmentSlot slot) {
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

