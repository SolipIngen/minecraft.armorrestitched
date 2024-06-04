package solipingen.armorrestitched.client.render.entity.feature;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Colors;
import net.minecraft.util.math.ColorHelper;

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
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.client.color.ModItemColorProvider;
import solipingen.armorrestitched.client.render.entity.model.VillagerArmorEntityModel;
import solipingen.armorrestitched.client.resource.ModClientResourcePacks;
import solipingen.armorrestitched.mixin.client.accessors.render.entity.model.VillagerResemblingModelAccessor;
import solipingen.armorrestitched.util.interfaces.mixin.render.entity.model.VillagerResemblingModelInterface;


@Environment(value = EnvType.CLIENT)
public class VillagerArmorFeatureRenderer<T extends VillagerEntity, M extends VillagerResemblingModel<VillagerEntity>> extends FeatureRenderer<VillagerEntity, VillagerResemblingModel<VillagerEntity>> {
    private final VillagerArmorEntityModel<VillagerEntity> innerModel;
    private final VillagerArmorEntityModel<VillagerEntity> outerModel;
    private final SpriteAtlasTexture armorTrimsAtlas;

    
    public VillagerArmorFeatureRenderer(FeatureRendererContext<VillagerEntity, VillagerResemblingModel<VillagerEntity>> context, VillagerArmorEntityModel<VillagerEntity> innerModel, VillagerArmorEntityModel<VillagerEntity> outerModel, BakedModelManager bakery) {
        super(context);
        this.innerModel = innerModel;
        this.outerModel = outerModel;
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
        this.setVisible(model, armorSlot);
        boolean freshBl = ModClientResourcePacks.isFreshAnimationsEnabled();
        VillagerArmorFeatureRenderer.copyFreshHelmetTransform(freshBl, model, entityModel);
        model.getBody().copyTransform(((VillagerResemblingModelInterface)entityModel).getBody());
        model.getLeftLeg().copyTransform(((VillagerResemblingModelAccessor)entityModel).getLeftLeg());
        model.getRightLeg().copyTransform(((VillagerResemblingModelAccessor)entityModel).getRightLeg());
        boolean legsBl = armorSlot == EquipmentSlot.LEGS;
        int i = itemStack.isIn(ItemTags.DYEABLE) ? DyedColorComponent.getColor(itemStack, ModItemColorProvider.getDyeableArmorDefaultColor(itemStack)) : Colors.WHITE;
        for (ArmorMaterial.Layer layer : armorItem.getMaterial().value().layers()) {
            float h;
            float g;
            float f;
            if (layer.isDyeable() && i != Colors.WHITE) {
                f = (float) ColorHelper.Argb.getRed(i) / 255.0f;
                g = (float)ColorHelper.Argb.getGreen(i) / 255.0f;
                h = (float)ColorHelper.Argb.getBlue(i) / 255.0f;
            }
            else {
                f = 1.0f;
                g = 1.0f;
                h = 1.0f;
            }
            this.renderArmorParts(matrices, vertexConsumers, light, model, f, g, h, layer.getTexture(legsBl));
        }
        ArmorTrim armorTrim = itemStack.get(DataComponentTypes.TRIM);
        if (armorTrim != null) {
            this.renderTrim(armorItem.getMaterial(), matrices, vertexConsumers, light, armorTrim, model, legsBl);
        }
        if (itemStack.hasGlint()) {
            this.renderGlint(matrices, vertexConsumers, light, model);
        }
    }

    @SuppressWarnings("incomplete-switch")
    protected void setVisible(VillagerArmorEntityModel<VillagerEntity> villagerArmorModel, EquipmentSlot slot) {
        villagerArmorModel.getHead().visible = false;
        villagerArmorModel.getBody().visible = false;
        villagerArmorModel.getRightLeg().visible = false;
        villagerArmorModel.getLeftLeg().visible = false;
        switch (slot) {
            case HEAD: {
                villagerArmorModel.getHead().visible = true;
                break;
            }
            case CHEST: {
                villagerArmorModel.getBody().visible = true;
                break;
            }
            case LEGS: {
                villagerArmorModel.getRightLeg().visible = true;
                villagerArmorModel.getLeftLeg().visible = true;
                break;
            }
            case FEET: {
                villagerArmorModel.getRightLeg().visible = true;
                villagerArmorModel.getLeftLeg().visible = true;
                break;
            }
        }
    }

    private void renderArmorParts(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, VillagerArmorEntityModel<VillagerEntity> model, float red, float green, float blue, Identifier overlay) {
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getArmorCutoutNoCull(overlay));
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, red, green, blue, 1.0f);
    }

    private void renderTrim(RegistryEntry<ArmorMaterial> armorMaterial, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ArmorTrim trim, VillagerArmorEntityModel<VillagerEntity> model, boolean leggings) {
        Sprite sprite = this.armorTrimsAtlas.getSprite(leggings ? trim.getLeggingsModelId(armorMaterial) : trim.getGenericModelId(armorMaterial));
        VertexConsumer vertexConsumer = sprite.getTextureSpecificVertexConsumer(vertexConsumers.getBuffer(TexturedRenderLayers.getArmorTrims((trim.getPattern().value()).decal())));
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
    }

    private VillagerArmorEntityModel<VillagerEntity> getModel(EquipmentSlot slot) {
        if (slot == EquipmentSlot.LEGS) {
            return this.innerModel;
        }
        return this.outerModel;
    }

    private void renderGlint(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, VillagerArmorEntityModel<VillagerEntity> model) {
        model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getArmorEntityGlint()), light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
    }

    private static void copyFreshHelmetTransform(boolean freshBl, VillagerArmorEntityModel<VillagerEntity> model, VillagerResemblingModel<VillagerEntity> entityModel) {
        if (freshBl) {
            model.getHead().copyTransform(((VillagerResemblingModelInterface)entityModel).getHat());
        }
        else {
            model.getHead().copyTransform(entityModel.getHead());
        }
    }

    
}

