package solipingen.armorrestitched.mixin.client.render.entity;

import net.minecraft.client.render.entity.AgeableMobEntityRenderer;
import net.minecraft.client.render.entity.state.VillagerEntityRenderState;
import net.minecraft.entity.EquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.VillagerEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.model.VillagerResemblingModel;
import net.minecraft.entity.passive.VillagerEntity;
import solipingen.armorrestitched.client.render.entity.feature.VillagerArmorFeatureRenderer;
import solipingen.armorrestitched.client.render.entity.model.ModEntityModelLayers;
import solipingen.armorrestitched.client.render.entity.model.VillagerArmorEntityModel;
import solipingen.armorrestitched.util.interfaces.mixin.render.entity.model.VillagerEntityRenderStateInterface;


@Mixin(VillagerEntityRenderer.class)
@Environment(value = EnvType.CLIENT)
public abstract class VillagerEntityRendererMixin extends AgeableMobEntityRenderer<VillagerEntity, VillagerEntityRenderState, VillagerResemblingModel> {


    public VillagerEntityRendererMixin(Context context, VillagerResemblingModel model, VillagerResemblingModel babyModel, float shadowRadius) {
        super(context, model, babyModel, shadowRadius);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectedInit(EntityRendererFactory.Context context, CallbackInfo cbi) {
        this.addFeature(new VillagerArmorFeatureRenderer((VillagerEntityRenderer)(Object)this,
            new VillagerArmorEntityModel(context.getPart(ModEntityModelLayers.VILLAGER_INNER_ARMOR_LAYER)),
            new VillagerArmorEntityModel(context.getPart(ModEntityModelLayers.VILLAGER_OUTER_ARMOR_LAYER)), context.getEquipmentRenderer()));
    }

    @Inject(method = "updateRenderState(Lnet/minecraft/entity/passive/VillagerEntity;Lnet/minecraft/client/render/entity/state/VillagerEntityRenderState;F)V", at = @At("TAIL"))
    private void injectedUpdateRenderState(VillagerEntity villagerEntity, VillagerEntityRenderState villagerEntityRenderState, float f, CallbackInfo cbi) {
        ((VillagerEntityRenderStateInterface)villagerEntityRenderState).setEquippedHeadStack(villagerEntity.getEquippedStack(EquipmentSlot.HEAD));
        ((VillagerEntityRenderStateInterface)villagerEntityRenderState).setEquippedChestStack(villagerEntity.getEquippedStack(EquipmentSlot.CHEST));
        ((VillagerEntityRenderStateInterface)villagerEntityRenderState).setEquippedLegsStack(villagerEntity.getEquippedStack(EquipmentSlot.LEGS));
        ((VillagerEntityRenderStateInterface)villagerEntityRenderState).setEquippedFeetStack(villagerEntity.getEquippedStack(EquipmentSlot.FEET));
    }


    
}

