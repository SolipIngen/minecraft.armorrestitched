package solipingen.armorrestitched.client.render.entity.feature;

import java.util.Collection;
import java.util.Iterator;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.SkeletonHorseEntity;
import net.minecraft.entity.mob.ZombieHorseEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.item.DyeableHorseArmorItem;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourcePackManager;
import solipingen.armorrestitched.client.render.entity.model.ZombieHorseArmorEntityModel;
import solipingen.armorrestitched.mixin.client.accessors.render.entity.model.HorseEntityModelAccessor;
import solipingen.armorrestitched.client.render.entity.model.ModEntityModelLayers;
import solipingen.armorrestitched.util.interfaces.mixin.entity.mob.ZombieHorseEntityInterface;


@SuppressWarnings({"unchecked", "rawtypes"})
@Environment(value = EnvType.CLIENT)
public class ZombieHorseArmorFeatureRenderer extends FeatureRenderer<AbstractHorseEntity, HorseEntityModel<AbstractHorseEntity>> {
    private final ZombieHorseArmorEntityModel<AbstractHorseEntity> model;


    public ZombieHorseArmorFeatureRenderer(FeatureRendererContext<AbstractHorseEntity, HorseEntityModel<AbstractHorseEntity>> context, EntityModelLoader loader) {
        super(context);
        this.model = new ZombieHorseArmorEntityModel(loader.getModelPart(ModEntityModelLayers.ZOMBIE_HORSE_ARMOR_LAYER));
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, AbstractHorseEntity horseEntity, float f, float g, float h, float j, float k, float l) {
        if (!(horseEntity instanceof SkeletonHorseEntity || horseEntity instanceof ZombieHorseEntity)) return;
        ItemStack itemStack = ((ZombieHorseEntityInterface)horseEntity).getArmorType();
        if (!(itemStack.getItem() instanceof HorseArmorItem)) return;
        HorseArmorItem horseArmorItem = (HorseArmorItem)itemStack.getItem();
        HorseEntityModel<AbstractHorseEntity> entityModel = this.getContextModel();
        this.model.child = entityModel.child;
        Iterator<ModelPart> headParts = this.model.getHeadParts().iterator();
        Iterator<ModelPart> entityHeadParts = entityModel.getHeadParts().iterator();
        Iterator<ModelPart> bodyParts = this.model.getBodyParts().iterator();
        Iterator<ModelPart> entityBodyParts = ((HorseEntityModelAccessor)entityModel).invokeGetBodyParts().iterator();
        float n = 1.0f;
        float o = 1.0f;
        float p = 1.0f;
        if (horseArmorItem instanceof DyeableHorseArmorItem) {
            int m = ((DyeableHorseArmorItem)horseArmorItem).getColor(itemStack);
            n = (float)(m >> 16 & 255) / 255.0f;
            o = (float)(m >> 8 & 255) / 255.0f;
            p = (float)(m & 255) / 255.0f;
        }
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutoutNoCull(horseArmorItem.getEntityTexture()));
        boolean freshBl = ZombieHorseArmorFeatureRenderer.isFreshAnimationsEnabled();
        this.model.getLeftFrontLeg().visible = freshBl;
        this.model.getLeftHindLeg().visible = freshBl;
        this.model.getRightFrontLeg().visible = freshBl;
        this.model.getRightHindLeg().visible = freshBl;
        headParts.forEachRemaining((headPart) -> headPart.copyTransform(entityHeadParts.next()));
        bodyParts.forEachRemaining((bodyPart) -> bodyPart.copyTransform(entityBodyParts.next()));
        if (freshBl && this.model.getBody().hasChild("neck2") && ((HorseEntityModelAccessor)entityModel).getBody().hasChild("neck2")) {
            ModelPart freshNeck = this.model.getBody().getChild("neck2");
            ModelPart freshEntityNeck = ((HorseEntityModelAccessor)entityModel).getBody().getChild("neck2");
            ModelPart freshHead = freshNeck.getChild("head2");
            ModelPart freshEntityHead = freshEntityNeck.getChild("head2");
            freshNeck.copyTransform(freshEntityNeck);
            freshHead.copyTransform(freshEntityHead);
            freshHead.getChild("left_ear2").copyTransform(freshEntityHead.getChild("left_ear2"));
            freshHead.getChild("right_ear2").copyTransform(freshEntityHead.getChild("right_ear2"));
        }
        this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, n, o, p, 1.0f);
    }

    public static boolean isFreshAnimationsEnabled() {
        boolean freshAnimationsEnabled = false;
        ResourcePackManager resourcePackManager = MinecraftClient.getInstance().getResourcePackManager();
        Collection<String> enabledResourcePackNames = resourcePackManager.getEnabledNames();
        for (String enabledPackName : enabledResourcePackNames) {
            if (enabledPackName.contains("FreshAnimations")) {
                freshAnimationsEnabled |= true;
            }
        }
        return freshAnimationsEnabled;
   }

}

