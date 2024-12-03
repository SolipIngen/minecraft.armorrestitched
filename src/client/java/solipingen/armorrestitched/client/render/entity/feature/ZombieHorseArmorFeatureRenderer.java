package solipingen.armorrestitched.client.render.entity.feature;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.item.AnimalArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.math.ColorHelper;


@SuppressWarnings({"unchecked", "rawtypes"})
@Environment(value = EnvType.CLIENT)
public class ZombieHorseArmorFeatureRenderer extends FeatureRenderer<AbstractHorseEntity, HorseEntityModel<AbstractHorseEntity>> {
    private final HorseEntityModel<AbstractHorseEntity> model;


    public ZombieHorseArmorFeatureRenderer(FeatureRendererContext<AbstractHorseEntity, HorseEntityModel<AbstractHorseEntity>> context, EntityModelLoader loader) {
        super(context);
        this.model = new HorseEntityModel(loader.getModelPart(EntityModelLayers.HORSE_ARMOR));
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, AbstractHorseEntity horseEntity, float f, float g, float h, float j, float k, float l) {
        AnimalArmorItem animalArmorItem;
        ItemStack itemStack = horseEntity.getBodyArmor();
        Item item = itemStack.getItem();
        if (!(item instanceof AnimalArmorItem) || (animalArmorItem = (AnimalArmorItem)item).getType() != AnimalArmorItem.Type.EQUESTRIAN) {
            return;
        }
        this.getContextModel().copyStateTo(this.model);
        this.model.animateModel(horseEntity, f, g, h);
        this.model.setAngles(horseEntity, f, g, j, k, l);
        int m;
        if (itemStack.isIn(ItemTags.DYEABLE)) {
            m = DyedColorComponent.getColor(itemStack, -6265536);
        }
        else {
            m = -1;
        }
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutoutNoCull(animalArmorItem.getEntityTexture()));
        this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, m);
    }


}

