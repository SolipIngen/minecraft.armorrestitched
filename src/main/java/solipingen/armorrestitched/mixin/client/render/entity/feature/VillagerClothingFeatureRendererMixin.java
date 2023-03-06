package solipingen.armorrestitched.mixin.client.render.entity.feature;

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
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerDataContainer;
import net.minecraft.village.VillagerProfession;
import net.minecraft.village.VillagerType;
import solipingen.armorrestitched.ArmorRestitched;


@Mixin(VillagerClothingFeatureRenderer.class)
@Environment(value=EnvType.CLIENT)
public abstract class VillagerClothingFeatureRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
    @Shadow @Final private String entityType;


    public VillagerClothingFeatureRendererMixin(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @ModifyVariable(method = "render", at = @At(value = "STORE"), ordinal = 0)
    private Identifier modifiedFindTypeTexture(Identifier originalId, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l) {
        VillagerType villagerType = ((VillagerDataContainer)livingEntity).getVillagerData().getType();
        if (livingEntity.getEquippedStack(EquipmentSlot.LEGS).getItem() instanceof ArmorItem && !(livingEntity.getEquippedStack(EquipmentSlot.HEAD).getItem() instanceof ArmorItem)) {
            String professionPath = "textures/entity/" + this.entityType + "_armored/type/" + Registries.VILLAGER_TYPE.getId(villagerType).getPath() + "_without_main" + ".png";
            return new Identifier(ArmorRestitched.MOD_ID, professionPath);
        }
        else if (livingEntity.getEquippedStack(EquipmentSlot.HEAD).getItem() instanceof ArmorItem && !(livingEntity.getEquippedStack(EquipmentSlot.LEGS).getItem() instanceof ArmorItem)) {
            String professionPath = "textures/entity/" + this.entityType + "_armored/type/" + Registries.VILLAGER_TYPE.getId(villagerType).getPath() + "_without_headgear" + ".png";
            return new Identifier(ArmorRestitched.MOD_ID, professionPath);
        }
        else if (livingEntity.getEquippedStack(EquipmentSlot.HEAD).getItem() instanceof ArmorItem && livingEntity.getEquippedStack(EquipmentSlot.LEGS).getItem() instanceof ArmorItem) {
            String professionPath = "textures/entity/" + this.entityType + "_armored/type/" + Registries.VILLAGER_TYPE.getId(villagerType).getPath() + "_without_all" + ".png";
            return new Identifier(ArmorRestitched.MOD_ID, professionPath);
        }
        return originalId;
    }

    @ModifyVariable(method = "render", at = @At(value = "STORE"), ordinal = 1)
    private Identifier modifiedFindProfessionTexture(Identifier originalId, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l) {
        VillagerProfession villagerProfession = ((VillagerDataContainer)livingEntity).getVillagerData().getProfession();
        if ((livingEntity.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof ArmorItem || livingEntity.getEquippedStack(EquipmentSlot.LEGS).getItem() instanceof ArmorItem) && !(livingEntity.getEquippedStack(EquipmentSlot.HEAD).getItem() instanceof ArmorItem)) {
            String professionPath = "textures/entity/" + this.entityType + "_armored/profession/" + Registries.VILLAGER_PROFESSION.getId(villagerProfession).getPath() + "_without_main" + ".png";
            return new Identifier(ArmorRestitched.MOD_ID, professionPath);
        }
        else if (livingEntity.getEquippedStack(EquipmentSlot.HEAD).getItem() instanceof ArmorItem && (!(livingEntity.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof ArmorItem) && !(livingEntity.getEquippedStack(EquipmentSlot.LEGS).getItem() instanceof ArmorItem))) {
            String professionPath = "textures/entity/" + this.entityType + "_armored/profession/" + Registries.VILLAGER_PROFESSION.getId(villagerProfession).getPath() + "_without_headgear" + ".png";
            return new Identifier(ArmorRestitched.MOD_ID, professionPath);
        }
        else if (livingEntity.getEquippedStack(EquipmentSlot.HEAD).getItem() instanceof ArmorItem && (livingEntity.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof ArmorItem || livingEntity.getEquippedStack(EquipmentSlot.LEGS).getItem() instanceof ArmorItem)) {
            String professionPath = "textures/entity/" + this.entityType + "_armored/profession/" + Registries.VILLAGER_PROFESSION.getId(villagerProfession).getPath() + "_without_all" + ".png";
            return new Identifier(ArmorRestitched.MOD_ID, professionPath);
        }
        return originalId;
    }

    
}

