package solipingen.armorrestitched.mixin.client.render.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.VillagerEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.model.VillagerResemblingModel;
import net.minecraft.entity.passive.VillagerEntity;
import solipingen.armorrestitched.client.render.entity.feature.VillagerArmorFeatureRenderer;
import solipingen.armorrestitched.client.render.entity.model.ModEntityModelLayers;
import solipingen.armorrestitched.client.render.entity.model.VillagerArmorEntityModel;


@Mixin(VillagerEntityRenderer.class)
@Environment(value = EnvType.CLIENT)
public abstract class VillagerEntityRendererMixin extends MobEntityRenderer<VillagerEntity, VillagerResemblingModel<VillagerEntity>> {


    public VillagerEntityRendererMixin(Context context, VillagerResemblingModel<VillagerEntity> entityModel, float f) {
        super(context, entityModel, f);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectedInit(EntityRendererFactory.Context context, CallbackInfo cbi) {
        this.addFeature(new VillagerArmorFeatureRenderer(this, 
            new VillagerArmorEntityModel<>(context.getPart(ModEntityModelLayers.VILLAGER_CHEST_ARMOR_LAYER)), 
            new VillagerArmorEntityModel<>(context.getPart(ModEntityModelLayers.VILLAGER_LEGS_ARMOR_LAYER)), 
            new VillagerArmorEntityModel<>(context.getPart(ModEntityModelLayers.VILLAGER_HEAD_ARMOR_LAYER)), 
            new VillagerArmorEntityModel<>(context.getPart(ModEntityModelLayers.VILLAGER_FEET_ARMOR_LAYER)), context.getModelManager()));
    }

    
}

