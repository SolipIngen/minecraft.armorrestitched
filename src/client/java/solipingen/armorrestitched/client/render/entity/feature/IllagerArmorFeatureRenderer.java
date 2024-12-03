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
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.client.color.ModItemColorProvider;
import solipingen.armorrestitched.client.render.entity.model.IllagerArmorEntityModel;
import solipingen.armorrestitched.client.resource.ModClientResourcePacks;
import solipingen.armorrestitched.mixin.client.accessors.render.entity.model.IllagerEntityModelAccessor;
import solipingen.armorrestitched.util.interfaces.mixin.render.entity.model.IllagerEntityModelInterface;


@Environment(value = EnvType.CLIENT)
public class IllagerArmorFeatureRenderer<T extends IllagerEntity, M extends IllagerEntityModel<IllagerEntity>> extends FeatureRenderer<IllagerEntity, IllagerEntityModel<IllagerEntity>> {
    private final IllagerArmorEntityModel<IllagerEntity> innerModel;
    private final IllagerArmorEntityModel<IllagerEntity> outerModel;
    private final SpriteAtlasTexture armorTrimsAtlas;

    
    public IllagerArmorFeatureRenderer(FeatureRendererContext<IllagerEntity, IllagerEntityModel<IllagerEntity>> context, IllagerArmorEntityModel<IllagerEntity> innerModel, IllagerArmorEntityModel<IllagerEntity> outerModel, BakedModelManager bakery) {
        super(context);
        this.innerModel = innerModel;
        this.outerModel = outerModel;
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
        this.setVisible(model, entity, armorSlot);
        boolean freshBl = ModClientResourcePacks.isFreshAnimationsEnabled();
        model.getHead().copyTransform(entityModel.getHead());
        model.getBody().copyTransform(((IllagerEntityModelInterface)entityModel).getBody());
        model.getLeftLeg().copyTransform(((IllagerEntityModelAccessor)entityModel).getLeftLeg());
        model.getRightLeg().copyTransform(((IllagerEntityModelAccessor)entityModel).getRightLeg());
        boolean legsBl = armorSlot == EquipmentSlot.LEGS;
        int i = itemStack.isIn(ItemTags.DYEABLE) ? DyedColorComponent.getColor(itemStack, ModItemColorProvider.getDyeableArmorDefaultColor(itemStack)) : Colors.WHITE;
        for (ArmorMaterial.Layer layer : armorItem.getMaterial().value().layers()) {
            this.renderArmorParts(matrices, vertexConsumers, light, model, i, layer.getTexture(legsBl));
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
    protected void setVisible(IllagerArmorEntityModel<IllagerEntity> illagerArmorModel, IllagerEntity entity, EquipmentSlot slot) {
        boolean crossedBl = entity.getState() == IllagerEntity.State.CROSSED;
        illagerArmorModel.getHead().visible = false;
        illagerArmorModel.getBody().visible = false;
        illagerArmorModel.getRightLeg().visible = false;
        illagerArmorModel.getLeftLeg().visible = false;
        switch (slot) {
            case HEAD: {
                illagerArmorModel.getHead().visible = true;
                break;
            }
            case CHEST: {
                illagerArmorModel.getBody().visible = true;
                break;
            }
            case LEGS: {
                illagerArmorModel.getRightLeg().visible = true;
                illagerArmorModel.getLeftLeg().visible = true;
                break;
            }
            case FEET: {
                illagerArmorModel.getRightLeg().visible = true;
                illagerArmorModel.getLeftLeg().visible = true;
                break;
            }
        }
    }

    private void renderArmorParts(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, IllagerArmorEntityModel<IllagerEntity> model, int color, Identifier overlay) {
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getArmorCutoutNoCull(overlay));
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, color);
    }

    private void renderTrim(RegistryEntry<ArmorMaterial> armorMaterial, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ArmorTrim trim, IllagerArmorEntityModel<IllagerEntity> model, boolean leggings) {
        Sprite sprite = this.armorTrimsAtlas.getSprite(leggings ? trim.getLeggingsModelId(armorMaterial) : trim.getGenericModelId(armorMaterial));
        VertexConsumer vertexConsumer = sprite.getTextureSpecificVertexConsumer(vertexConsumers.getBuffer(TexturedRenderLayers.getArmorTrims((trim.getPattern().value()).decal())));
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
    }

    private IllagerArmorEntityModel<IllagerEntity> getModel(EquipmentSlot slot) {
        if (slot == EquipmentSlot.LEGS) {
            return this.innerModel;
        }
        return this.outerModel;
    }

    private void renderGlint(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, IllagerArmorEntityModel<IllagerEntity> model) {
        model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getArmorEntityGlint()), light, OverlayTexture.DEFAULT_UV);
    }

    
}

