package solipingen.armorrestitched.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import solipingen.armorrestitched.ArmorRestitched;
import solipingen.armorrestitched.client.render.entity.model.ModEntityModelLayers;
import solipingen.armorrestitched.client.render.entity.model.SilkMothEntityModel;
import solipingen.armorrestitched.entity.SilkMothEntity;


@Environment(value = EnvType.CLIENT)
public class SilkMothEntityRenderer extends MobEntityRenderer<SilkMothEntity, SilkMothEntityModel> {
    private static final Identifier TEXTURE = Identifier.of(ArmorRestitched.MOD_ID, "textures/entity/silk_moth.png");

    
    public SilkMothEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new SilkMothEntityModel(context.getPart(ModEntityModelLayers.SILK_MOTH_LAYER)), 0.25f);
    }

    @Override
    public Identifier getTexture(SilkMothEntity silkMothEntity) {
        return TEXTURE;
    }

    @Override
    protected void scale(SilkMothEntity silkMothEntity, MatrixStack matrixStack, float f) {
        matrixStack.scale(0.2f, 0.2f, 0.2f);
    }

}
