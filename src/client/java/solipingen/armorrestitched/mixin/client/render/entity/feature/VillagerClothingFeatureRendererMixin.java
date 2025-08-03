package solipingen.armorrestitched.mixin.client.render.entity.feature;

import net.minecraft.client.render.entity.model.ModelWithHat;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.entity.state.VillagerDataRenderState;
import net.minecraft.client.render.entity.state.VillagerEntityRenderState;
import net.minecraft.client.render.entity.state.ZombieVillagerRenderState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.VillagerClothingFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerType;
import solipingen.armorrestitched.ArmorRestitched;
import solipingen.armorrestitched.util.interfaces.mixin.render.entity.model.VillagerEntityRenderStateInterface;


@Mixin(VillagerClothingFeatureRenderer.class)
@Environment(value = EnvType.CLIENT)
public abstract class VillagerClothingFeatureRendererMixin<S extends LivingEntityRenderState & VillagerDataRenderState, M extends EntityModel<S> & ModelWithHat> extends FeatureRenderer<S, M> {
    @Shadow @Final private String entityType;


    public VillagerClothingFeatureRendererMixin(FeatureRendererContext<S, M> context) {
        super(context);
    }

    @ModifyVariable(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/LivingEntityRenderState;FF)V", at = @At(value = "STORE"), ordinal = 0)
    private Identifier modifiedFindTypeTexture(Identifier originalId, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, S livingEntityRenderState, float f, float g) {
        VillagerType villagerType = livingEntityRenderState.getVillagerData().type().value();
        if (livingEntityRenderState instanceof VillagerEntityRenderState) {
            VillagerEntityRenderStateInterface iRenderState = (VillagerEntityRenderStateInterface)livingEntityRenderState;
            if (!iRenderState.getEquippedLegsStack().isEmpty() && iRenderState.getEquippedHeadStack().isEmpty()) {
                String professionPath = "textures/entity/" + this.entityType + "_armored/type/" + Registries.VILLAGER_TYPE.getId(villagerType).getPath() + "_without_main" + ".png";
                return Identifier.of(ArmorRestitched.MOD_ID, professionPath);
            }
            else if (!iRenderState.getEquippedHeadStack().isEmpty() && iRenderState.getEquippedLegsStack().isEmpty()) {
                String professionPath = "textures/entity/" + this.entityType + "_armored/type/" + Registries.VILLAGER_TYPE.getId(villagerType).getPath() + "_without_headgear" + ".png";
                return Identifier.of(ArmorRestitched.MOD_ID, professionPath);
            }
            else if (!iRenderState.getEquippedHeadStack().isEmpty()  && !iRenderState.getEquippedLegsStack().isEmpty()) {
                String professionPath = "textures/entity/" + this.entityType + "_armored/type/" + Registries.VILLAGER_TYPE.getId(villagerType).getPath() + "_without_all" + ".png";
                return Identifier.of(ArmorRestitched.MOD_ID, professionPath);
            }
        }
        else if (livingEntityRenderState instanceof ZombieVillagerRenderState zombieVillagerRenderState) {
            if (!zombieVillagerRenderState.equippedLegsStack.isEmpty() && zombieVillagerRenderState.equippedHeadStack.isEmpty()) {
                String professionPath = "textures/entity/" + this.entityType + "_armored/type/" + Registries.VILLAGER_TYPE.getId(villagerType).getPath() + "_without_main" + ".png";
                return Identifier.of(ArmorRestitched.MOD_ID, professionPath);
            }
            else if (!zombieVillagerRenderState.equippedHeadStack.isEmpty() && zombieVillagerRenderState.equippedLegsStack.isEmpty()) {
                String professionPath = "textures/entity/" + this.entityType + "_armored/type/" + Registries.VILLAGER_TYPE.getId(villagerType).getPath() + "_without_headgear" + ".png";
                return Identifier.of(ArmorRestitched.MOD_ID, professionPath);
            }
            else if (!zombieVillagerRenderState.equippedHeadStack.isEmpty() && !zombieVillagerRenderState.equippedLegsStack.isEmpty()) {
                String professionPath = "textures/entity/" + this.entityType + "_armored/type/" + Registries.VILLAGER_TYPE.getId(villagerType).getPath() + "_without_all" + ".png";
                return Identifier.of(ArmorRestitched.MOD_ID, professionPath);
            }
        }
        return originalId;
    }

    
}

