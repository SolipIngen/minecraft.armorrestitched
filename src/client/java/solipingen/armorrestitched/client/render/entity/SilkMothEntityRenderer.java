package solipingen.armorrestitched.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.ArmorRestitched;
import solipingen.armorrestitched.client.render.entity.model.ModEntityModelLayers;
import solipingen.armorrestitched.client.render.entity.model.SilkMothEntityModel;
import solipingen.armorrestitched.client.render.entity.state.SilkMothEntityRenderState;
import solipingen.armorrestitched.entity.SilkMothEntity;


@Environment(value = EnvType.CLIENT)
public class SilkMothEntityRenderer extends MobEntityRenderer<SilkMothEntity, SilkMothEntityRenderState, SilkMothEntityModel> {
    private static final Identifier TEXTURE = Identifier.of(ArmorRestitched.MOD_ID, "textures/entity/silk_moth.png");

    
    public SilkMothEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new SilkMothEntityModel(context.getPart(ModEntityModelLayers.SILK_MOTH_LAYER)), 0.25f);
    }

    @Override
    public Identifier getTexture(SilkMothEntityRenderState renderState) {
        return TEXTURE;
    }

    @Override
    public SilkMothEntityRenderState createRenderState() {
        return new SilkMothEntityRenderState();
    }

    @Override
    public void updateRenderState(SilkMothEntity silkMothEntity, SilkMothEntityRenderState renderState, float f) {
        super.updateRenderState(silkMothEntity, renderState, f);
        renderState.bodyPitch = silkMothEntity.getBodyPitch(f);
        renderState.stoppedOnGround = silkMothEntity.isOnGround() && silkMothEntity.getVelocity().lengthSquared() < 1.0E-7;
    }

}
