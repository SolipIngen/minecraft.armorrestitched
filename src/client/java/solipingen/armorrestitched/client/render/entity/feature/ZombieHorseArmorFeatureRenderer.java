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
        if (!(horseEntity instanceof SkeletonHorseEntity || horseEntity instanceof ZombieHorseEntity)) {
            return;
        }
        ItemStack itemStack = ((ZombieHorseEntityInterface)horseEntity).getArmorType();
        if (!(itemStack.getItem() instanceof HorseArmorItem)) {
            return;
        }
        HorseArmorItem horseArmorItem = (HorseArmorItem)itemStack.getItem();
        HorseEntityModel<AbstractHorseEntity> entityModel = this.getContextModel();
        this.model.child = entityModel.child;
        Iterable<ModelPart> headParts = this.model.getHeadParts();
        Iterator<ModelPart> entityHeadParts = entityModel.getHeadParts().iterator();
        Iterable<ModelPart> bodyParts = this.model.getBodyParts();
        Iterator<ModelPart> entityBodyParts = ((HorseEntityModelAccessor)entityModel).invokeGetBodyParts().iterator();
        // this.model.setAngles(horseEntity, f, g, j, k, l);
        headParts.forEach((headPart) -> headPart.copyTransform(entityHeadParts.next()));
        bodyParts.forEach((bodyPart) -> bodyPart.copyTransform(entityBodyParts.next()));
        if (ZombieHorseArmorFeatureRenderer.isFreshAnimationsEnabled() && ((HorseEntityModelAccessor)entityModel).getBody().hasChild("neck2")) {
            ModelPart freshHeadModel = ((HorseEntityModelAccessor)entityModel).getBody().getChild("neck2");
            this.model.getHead().setAngles(freshHeadModel.pitch, freshHeadModel.yaw, freshHeadModel.roll);
            this.model.getHead().getChild("head").setAngles(freshHeadModel.getChild("head2").pitch, 
                freshHeadModel.getChild("head2").yaw, 
                freshHeadModel.getChild("head2").roll);
            this.model.getHead().getChild("mane").setAngles(freshHeadModel.getChild("head2").getChild("mane3").pitch, 
                freshHeadModel.getChild("head2").getChild("mane3").yaw, 
                freshHeadModel.getChild("head2").getChild("mane3").roll);
            this.model.getHead().getChild("upper_mouth").setAngles(freshHeadModel.getChild("head2").getChild("snout2").pitch, 
                freshHeadModel.getChild("head2").getChild("snout2").yaw, 
                freshHeadModel.getChild("head2").getChild("snout2").roll);
            // this.model.getHead().getChild("head").getChild("left_ear").setAngles(freshHeadModel.getChild("head2").getChild("left_ear2").pitch, freshHeadModel.getChild("head2").getChild("left_ear2").yaw, freshHeadModel.getChild("head2").getChild("left_ear2").roll);
            // this.model.getHead().getChild("head").getChild("right_ear").setAngles(freshHeadModel.getChild("head2").getChild("right_ear2").pitch, freshHeadModel.getChild("head2").getChild("right_ear2").yaw, freshHeadModel.getChild("head2").getChild("right_ear2").roll);
        }
        float n = 1.0f;
        float o = 1.0f;
        float p = 1.0f;
        if (horseArmorItem instanceof DyeableHorseArmorItem) {
            int m = ((DyeableHorseArmorItem)horseArmorItem).getColor(itemStack);
            n = (float)(m >> 16 & 0xFF) / 255.0f;
            o = (float)(m >> 8 & 0xFF) / 255.0f;
            p = (float)(m & 0xFF) / 255.0f;
        }
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutoutNoCull(horseArmorItem.getEntityTexture()));
        this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, n, o, p, 1.0f);
    }

    private static boolean isFreshAnimationsEnabled() {
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

