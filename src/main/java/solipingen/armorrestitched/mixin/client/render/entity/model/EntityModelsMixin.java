package solipingen.armorrestitched.mixin.client.render.entity.model;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.google.common.collect.ImmutableMap;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.ArmorEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModels;


@Mixin(EntityModels.class)
@Environment(value = EnvType.CLIENT)
public abstract class EntityModelsMixin {
    

    @ModifyVariable(method = "getModels", at = @At("STORE"), ordinal = 0)
    private static ImmutableMap<EntityModelLayer, TexturedModelData> modifiedDrownedOuterArmorModel(ImmutableMap<EntityModelLayer, TexturedModelData> originalMap) {
        ImmutableMap.Builder<EntityModelLayer, TexturedModelData> mapBuilder = ImmutableMap.builder();
        originalMap.forEach((entityModelLayer, texturedModelData) -> {
            if (entityModelLayer == EntityModelLayers.DROWNED_OUTER_ARMOR) return;
            mapBuilder.put(entityModelLayer, texturedModelData);
        });
        TexturedModelData texturedModelData2 = TexturedModelData.of(ArmorEntityModel.getModelData(new Dilation(1.0f)), 64, 32);
        mapBuilder.put(EntityModelLayers.DROWNED_OUTER_ARMOR, texturedModelData2);
        return mapBuilder.build();
    }



}
