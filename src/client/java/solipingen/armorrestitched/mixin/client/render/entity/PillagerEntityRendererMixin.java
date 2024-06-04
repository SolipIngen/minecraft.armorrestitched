package solipingen.armorrestitched.mixin.client.render.entity;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.client.render.entity.PillagerEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.ArmorRestitched;


@Mixin(PillagerEntityRenderer.class)
@Environment(value = EnvType.CLIENT)
public abstract class PillagerEntityRendererMixin extends IllagerEntityRenderer<PillagerEntity>{
    @Shadow @Final private static Identifier TEXTURE;


    protected PillagerEntityRendererMixin(Context ctx, IllagerEntityModel<PillagerEntity> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Redirect(method = "getTexture", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/entity/PillagerEntityRenderer;TEXTURE:Lnet/minecraft/util/Identifier;", opcode = Opcodes.GETSTATIC))
    private Identifier redirectedGetTexture(PillagerEntity pillagerEntity) {
        if (!pillagerEntity.getEquippedStack(EquipmentSlot.LEGS).isEmpty()) {
            return new Identifier(ArmorRestitched.MOD_ID, "textures/entity/illager_armored/pillager.png");
        }
        return TEXTURE;
    }

    
}

