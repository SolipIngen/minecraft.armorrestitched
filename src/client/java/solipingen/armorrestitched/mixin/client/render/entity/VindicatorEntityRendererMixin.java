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
import net.minecraft.client.render.entity.VindicatorEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.VindicatorEntity;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.ArmorRestitched;


@Mixin(VindicatorEntityRenderer.class)
@Environment(value = EnvType.CLIENT)
public abstract class VindicatorEntityRendererMixin extends IllagerEntityRenderer<VindicatorEntity> {
    @Shadow @Final private static Identifier TEXTURE;


    protected VindicatorEntityRendererMixin(Context ctx, IllagerEntityModel<VindicatorEntity> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Redirect(method = "getTexture", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/entity/VindicatorEntityRenderer;TEXTURE:Lnet/minecraft/util/Identifier;", opcode = Opcodes.GETSTATIC))
    private Identifier redirectedGetTexture(VindicatorEntity vindicatorEntity) {
        if (!vindicatorEntity.getEquippedStack(EquipmentSlot.CHEST).isEmpty()) {
            return Identifier.of(ArmorRestitched.MOD_ID, "textures/entity/illager_armored/vindicator.png");
        }
        return TEXTURE;
    }


    
}

