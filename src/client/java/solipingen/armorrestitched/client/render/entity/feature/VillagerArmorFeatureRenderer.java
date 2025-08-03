package solipingen.armorrestitched.client.render.entity.feature;

import net.minecraft.client.render.entity.equipment.EquipmentModel;
import net.minecraft.client.render.entity.equipment.EquipmentRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.state.VillagerEntityRenderState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.VillagerResemblingModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import solipingen.armorrestitched.client.render.entity.model.VillagerArmorEntityModel;
import solipingen.armorrestitched.client.resource.ModClientResourcePacks;
import solipingen.armorrestitched.mixin.client.accessors.render.entity.model.VillagerResemblingModelAccessor;
import solipingen.armorrestitched.util.interfaces.mixin.render.entity.model.VillagerEntityRenderStateInterface;
import solipingen.armorrestitched.util.interfaces.mixin.render.entity.model.VillagerResemblingModelInterface;


@Environment(value = EnvType.CLIENT)
public class VillagerArmorFeatureRenderer<S extends VillagerEntityRenderState, M extends VillagerResemblingModel, A extends VillagerArmorEntityModel> extends FeatureRenderer<S, M> {
    private final A innerModel;
    private final A outerModel;
    private final EquipmentRenderer equipmentRenderer;
//    private final SpriteAtlasTexture armorTrimsAtlas;

    
    public VillagerArmorFeatureRenderer(FeatureRendererContext<S, M> context, A innerModel, A outerModel, EquipmentRenderer equipmentRenderer) {
        super(context);
        this.innerModel = innerModel;
        this.outerModel = outerModel;
        this.equipmentRenderer = equipmentRenderer;
//        this.armorTrimsAtlas = bakery.getAtlas(TexturedRenderLayers.ARMOR_TRIMS_ATLAS_TEXTURE);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, S villagerEntityRenderState, float f, float g) {
        VillagerEntityRenderStateInterface iRenderState = (VillagerEntityRenderStateInterface)villagerEntityRenderState;
        this.renderArmor(matrixStack, vertexConsumerProvider, iRenderState.getEquippedChestStack(), EquipmentSlot.CHEST, i, this.getModel(EquipmentSlot.CHEST));
        this.renderArmor(matrixStack, vertexConsumerProvider, iRenderState.getEquippedLegsStack(), EquipmentSlot.LEGS, i, this.getModel(EquipmentSlot.LEGS));
        this.renderArmor(matrixStack, vertexConsumerProvider, iRenderState.getEquippedFeetStack(), EquipmentSlot.FEET, i, this.getModel(EquipmentSlot.FEET));
        this.renderArmor(matrixStack, vertexConsumerProvider, iRenderState.getEquippedHeadStack(), EquipmentSlot.HEAD, i, this.getModel(EquipmentSlot.HEAD));
    }

    private void renderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, EquipmentSlot slot, int light, A armorModel) {
        if (ArmorFeatureRenderer.hasModel(stack, slot)) {
            EquippableComponent equippableComponent = stack.get(DataComponentTypes.EQUIPPABLE);
            VillagerResemblingModel entityModel = this.getContextModel();
            this.setVisible(armorModel, slot);
            boolean freshBl = ModClientResourcePacks.isFreshAnimationsEnabled();
            VillagerArmorFeatureRenderer.copyFreshHelmetTransform(freshBl, armorModel, entityModel);
            armorModel.getBody().copyTransform(((VillagerResemblingModelInterface)entityModel).getBody());
            armorModel.getLeftLeg().copyTransform(((VillagerResemblingModelAccessor)entityModel).getLeftLeg());
            armorModel.getRightLeg().copyTransform(((VillagerResemblingModelAccessor)entityModel).getRightLeg());
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
//        VillagerResemblingModel<VillagerEntity> entityModel = this.getContextModel();
//        this.setVisible(model, armorSlot);
//        boolean freshBl = ModClientResourcePacks.isFreshAnimationsEnabled();
//        VillagerArmorFeatureRenderer.copyFreshHelmetTransform(freshBl, model, entityModel);
//        model.getBody().copyTransform(((VillagerResemblingModelInterface)entityModel).getBody());
//        model.getLeftLeg().copyTransform(((VillagerResemblingModelAccessor)entityModel).getLeftLeg());
//        model.getRightLeg().copyTransform(((VillagerResemblingModelAccessor)entityModel).getRightLeg());
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
    protected void setVisible(A villagerArmorModel, EquipmentSlot slot) {
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

//    private void renderArmorParts(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, VillagerArmorEntityModel model, int color, Identifier overlay) {
//        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getArmorCutoutNoCull(overlay));
//        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, color);
//    }
//
//    private void renderTrim(RegistryEntry<ArmorMaterial> armorMaterial, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ArmorTrim trim, VillagerArmorEntityModel<VillagerEntity> model, boolean leggings) {
//        Sprite sprite = this.armorTrimsAtlas.getSprite(leggings ? trim.getTextureId() : trim.getGenericModelId(armorMaterial));
//        VertexConsumer vertexConsumer = sprite.getTextureSpecificVertexConsumer(vertexConsumers.getBuffer(TexturedRenderLayers.getArmorTrims((trim.getPattern().value()).decal())));
//        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
//    }

    private A getModel(EquipmentSlot slot) {
        if (slot == EquipmentSlot.LEGS) {
            return this.innerModel;
        }
        return this.outerModel;
    }

//    private void renderGlint(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, VillagerArmorEntityModel model) {
//        model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getArmorEntityGlint()), light, OverlayTexture.DEFAULT_UV);
//    }

    private static void copyFreshHelmetTransform(boolean freshBl, VillagerArmorEntityModel model, VillagerResemblingModel entityModel) {
        if (freshBl) {
            model.getHead().copyTransform(((VillagerResemblingModelInterface)entityModel).getHat());
        }
        else {
            model.getHead().copyTransform(entityModel.getHead());
        }
    }

    
}

