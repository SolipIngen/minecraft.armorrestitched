package solipingen.armorrestitched.client.render.entity.feature;

import net.minecraft.client.render.entity.equipment.EquipmentModel;
import net.minecraft.client.render.entity.equipment.EquipmentRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.state.IllagerEntityRenderState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import solipingen.armorrestitched.client.render.entity.model.IllagerArmorEntityModel;
import solipingen.armorrestitched.client.resource.ModClientResourcePacks;
import solipingen.armorrestitched.mixin.client.accessors.render.entity.model.IllagerEntityModelAccessor;
import solipingen.armorrestitched.util.interfaces.mixin.render.entity.model.IllagerEntityModelInterface;
import solipingen.armorrestitched.util.interfaces.mixin.render.entity.model.IllagerEntityRenderStateInterface;


@Environment(value = EnvType.CLIENT)
public class IllagerArmorFeatureRenderer<S extends IllagerEntityRenderState, M extends IllagerEntityModel<S>, A extends IllagerArmorEntityModel> extends FeatureRenderer<S, M> {
    private final A innerModel;
    private final A outerModel;
    private final EquipmentRenderer equipmentRenderer;
//    private final SpriteAtlasTexture armorTrimsAtlas;

    
    public IllagerArmorFeatureRenderer(FeatureRendererContext<S, M> context, A innerModel, A outerModel, EquipmentRenderer equipmentRenderer) {
        super(context);
        this.innerModel = innerModel;
        this.outerModel = outerModel;
        this.equipmentRenderer = equipmentRenderer;
//        this.armorTrimsAtlas = bakery.getAtlas(TexturedRenderLayers.ARMOR_TRIMS_ATLAS_TEXTURE);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, S illagerEntityRenderState, float f, float g) {
        IllagerEntityRenderStateInterface iRenderState = (IllagerEntityRenderStateInterface)illagerEntityRenderState;
        this.renderArmor(matrixStack, vertexConsumerProvider, iRenderState.getEquippedChestStack(), EquipmentSlot.CHEST, i, this.getModel(EquipmentSlot.CHEST));
        this.renderArmor(matrixStack, vertexConsumerProvider, iRenderState.getEquippedLegsStack(), EquipmentSlot.LEGS, i, this.getModel(EquipmentSlot.LEGS));
        this.renderArmor(matrixStack, vertexConsumerProvider, iRenderState.getEquippedFeetStack(), EquipmentSlot.FEET, i, this.getModel(EquipmentSlot.FEET));
        this.renderArmor(matrixStack, vertexConsumerProvider, iRenderState.getEquippedHeadStack(), EquipmentSlot.HEAD, i, this.getModel(EquipmentSlot.HEAD));
    }

    private void renderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, EquipmentSlot slot, int light, A armorModel) {
        if (ArmorFeatureRenderer.hasModel(stack, slot)) {
            EquippableComponent equippableComponent = stack.get(DataComponentTypes.EQUIPPABLE);
            IllagerEntityModel<S> entityModel = this.getContextModel();
            this.setVisible(armorModel, slot);
            boolean freshBl = ModClientResourcePacks.isFreshAnimationsEnabled();
            armorModel.getHead().copyTransform(entityModel.getHead());
            armorModel.getBody().copyTransform(((IllagerEntityModelInterface)entityModel).getBody());
            armorModel.getLeftLeg().copyTransform(((IllagerEntityModelAccessor)entityModel).getLeftLeg());
            armorModel.getRightLeg().copyTransform(((IllagerEntityModelAccessor)entityModel).getRightLeg());
            EquipmentModel.LayerType layerType = slot == EquipmentSlot.LEGS ? EquipmentModel.LayerType.HUMANOID_LEGGINGS : EquipmentModel.LayerType.HUMANOID;
            this.equipmentRenderer.render(layerType, equippableComponent.assetId().orElseThrow(), armorModel, stack, matrices, vertexConsumers, light);
        }
//        ItemStack itemStack = entity.getEquippedStack(armorSlot);
//        if (!(itemStack.getItem() instanceof ArmorItem)) {
//            return;
//        }
//        ArmorItem armorItem = (ArmorItem)itemStack.getItem();
//        if (armorItem.getSlotType() != armorSlot) {
//            return;
//        }
//        IllagerEntityModel<IllagerEntity> entityModel = this.getContextModel();
//        this.setVisible(model, entity, armorSlot);
//        boolean freshBl = ModClientResourcePacks.isFreshAnimationsEnabled();
//        model.getHead().copyTransform(entityModel.getHead());
//        model.getBody().copyTransform(((IllagerEntityModelInterface)entityModel).getBody());
//        model.getLeftLeg().copyTransform(((IllagerEntityModelAccessor)entityModel).getLeftLeg());
//        model.getRightLeg().copyTransform(((IllagerEntityModelAccessor)entityModel).getRightLeg());
//        boolean legsBl = armorSlot == EquipmentSlot.LEGS;
//        int i = itemStack.isIn(ItemTags.DYEABLE) ? DyedColorComponent.getColor(itemStack, ModItemColorProvider.getDyeableArmorDefaultColor(itemStack)) : Colors.WHITE;
//        for (ArmorMaterial.Layer layer : armorItem.getMaterial().value().layers()) {
//            this.renderArmorParts(matrices, vertexConsumers, light, model, i, layer.getTexture(legsBl));
//        }
//        ArmorTrim armorTrim = itemStack.get(DataComponentTypes.TRIM);
//        if (armorTrim != null) {
//            this.renderTrim(armorItem.getMaterial(), matrices, vertexConsumers, light, armorTrim, model, legsBl);
//        }
//        if (itemStack.hasGlint()) {
//            this.renderGlint(matrices, vertexConsumers, light, model);
//        }
    }

    @SuppressWarnings("incomplete-switch")
    protected void setVisible(IllagerArmorEntityModel illagerArmorModel, EquipmentSlot slot) {
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

//    private void renderArmorParts(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, IllagerArmorEntityModel<IllagerEntity> model, int color, Identifier overlay) {
//        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getArmorCutoutNoCull(overlay));
//        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, color);
//    }
//
//    private void renderTrim(RegistryEntry<ArmorMaterial> armorMaterial, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ArmorTrim trim, IllagerArmorEntityModel<IllagerEntity> model, boolean leggings) {
//        Sprite sprite = this.armorTrimsAtlas.getSprite(leggings ? trim.getLeggingsModelId(armorMaterial) : trim.getGenericModelId(armorMaterial));
//        VertexConsumer vertexConsumer = sprite.getTextureSpecificVertexConsumer(vertexConsumers.getBuffer(TexturedRenderLayers.getArmorTrims((trim.getPattern().value()).decal())));
//        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
//    }

    private A getModel(EquipmentSlot slot) {
        if (slot == EquipmentSlot.LEGS) {
            return this.innerModel;
        }
        return this.outerModel;
    }

//    private void renderGlint(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, IllagerArmorEntityModel<IllagerEntity> model) {
//        model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getArmorEntityGlint()), light, OverlayTexture.DEFAULT_UV);
//    }

    
}

