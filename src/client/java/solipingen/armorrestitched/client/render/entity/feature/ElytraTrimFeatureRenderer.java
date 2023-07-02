package solipingen.armorrestitched.client.render.entity.feature;

import java.util.Optional;
import org.jetbrains.annotations.Nullable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.item.trim.ArmorTrimMaterial;
import net.minecraft.item.trim.ArmorTrimPattern;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.ArmorRestitched;
import solipingen.armorrestitched.client.render.entity.model.ElytraTrimEntityModel;
import solipingen.armorrestitched.client.render.entity.model.ModEntityModelLayers;


@Environment(value = EnvType.CLIENT)
public class ElytraTrimFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
    private final ElytraTrimEntityModel<T> elytraTrim;
    private final SpriteAtlasTexture elytraTrimsAtlas;


    public ElytraTrimFeatureRenderer(FeatureRendererContext<T, M> context, EntityModelLoader loader, BakedModelManager bakery) {
        super(context);
        this.elytraTrim = new ElytraTrimEntityModel<T>(loader.getModelPart(ModEntityModelLayers.ELYTA_TRIM_LAYER));
        this.elytraTrimsAtlas = bakery.getAtlas(TexturedRenderLayers.ARMOR_TRIMS_ATLAS_TEXTURE);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l) {
        ItemStack itemStack = ((LivingEntity)livingEntity).getEquippedStack(EquipmentSlot.CHEST);
        Optional<ArmorTrim> trimOptional = ArmorTrim.getTrim(livingEntity.getWorld().getRegistryManager(), itemStack);
        if (!(itemStack.isOf(Items.ELYTRA) && trimOptional.isPresent())) {
            return;
        }
        boolean isPlayerBl = livingEntity instanceof AbstractClientPlayerEntity;
        boolean elytraRenderBl = isPlayerBl ? ((AbstractClientPlayerEntity)livingEntity).canRenderElytraTexture() : true;
        boolean capeRenderBl = isPlayerBl ? ((AbstractClientPlayerEntity)livingEntity).canRenderCapeTexture() && ((AbstractClientPlayerEntity)livingEntity).getCapeTexture() != null && ((AbstractClientPlayerEntity)livingEntity).isPartVisible(PlayerModelPart.CAPE) : false;
        Identifier identifier = this.getTrimTexture(livingEntity.getWorld().getRegistryManager(), itemStack, elytraRenderBl || capeRenderBl);
        if (identifier == null) return;
        Sprite sprite = this.elytraTrimsAtlas.getSprite(identifier);
        matrixStack.push();
        matrixStack.translate(0.0f, 0.0f, 0.125f);
        ((EntityModel<T>)this.getContextModel()).copyStateTo(this.elytraTrim);
        this.elytraTrim.setAngles(livingEntity, f, g, j, k, l);
        VertexConsumer vertexConsumer = sprite.getTextureSpecificVertexConsumer(ItemRenderer.getDirectItemGlintConsumer(vertexConsumerProvider, TexturedRenderLayers.getArmorTrims(), true, itemStack.hasGlint()));
        this.elytraTrim.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        matrixStack.pop();
    }

    @Nullable
    private Identifier getTrimTexture(DynamicRegistryManager registryManager, ItemStack stack, boolean renderBl) {
        Optional<ArmorTrim> trimOptional = ArmorTrim.getTrim(registryManager, stack);
        if (trimOptional.isPresent() && renderBl) {
            RegistryEntry<ArmorTrimMaterial> trimMaterial = trimOptional.get().getMaterial();
            RegistryEntry<ArmorTrimPattern> trimPattern = trimOptional.get().getPattern();
            for (ArmorTrimMaterial material : registryManager.get(RegistryKeys.TRIM_MATERIAL)) {
                if (trimMaterial.value() != material) continue;
                for (ArmorTrimPattern pattern : registryManager.get(RegistryKeys.TRIM_PATTERN)) {
                    if (trimPattern.value() != pattern) continue;
                    String path = "trims/models/elytra/" + pattern.assetId().getPath() + "_" + material.assetName();
                    return new Identifier(ArmorRestitched.MOD_ID, path);
                }
            }
        }
        return null;
    }

    
}
