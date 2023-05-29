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
import net.minecraft.client.render.entity.IllusionerEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.IllusionerEntity;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.ArmorRestitched;


@Mixin(IllusionerEntityRenderer.class)
@Environment(value = EnvType.CLIENT)
public abstract class IllusionerEntityRendererMixin extends IllagerEntityRenderer<IllusionerEntity> {
    @Shadow @Final private static Identifier TEXTURE;


    protected IllusionerEntityRendererMixin(Context ctx, IllagerEntityModel<IllusionerEntity> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Redirect(method = "getTexture", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/entity/IllusionerEntityRenderer;TEXTURE:Lnet/minecraft/util/Identifier;", opcode = Opcodes.GETSTATIC))
    private Identifier redirectedGetTexture(IllusionerEntity illusionerEntity) {
        if (!illusionerEntity.getEquippedStack(EquipmentSlot.CHEST).isEmpty() || !illusionerEntity.getEquippedStack(EquipmentSlot.LEGS).isEmpty()) {
            return new Identifier(ArmorRestitched.MOD_ID, "textures/entity/illager_armored/illusioner.png");
        }
        return TEXTURE;
    }
    
    
}

