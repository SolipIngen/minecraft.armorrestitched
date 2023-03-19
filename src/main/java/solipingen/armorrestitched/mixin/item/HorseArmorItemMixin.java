package solipingen.armorrestitched.mixin.item;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.ArmorRestitched;


@Mixin(HorseArmorItem.class)
public abstract class HorseArmorItemMixin extends Item {
    @Shadow @Final private static String ENTITY_TEXTURE_PREFIX;
    @Shadow @Final private int bonus;
    @Shadow @Final private String entityTexture;


    public HorseArmorItemMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "getEntityTexture", at = @At("HEAD"), cancellable = true)
    private void injectedGetEntityTexture(CallbackInfoReturnable<Identifier> cbireturn) {
        if (this.entityTexture.matches("textures/entity/horse/armor/horse_armor_copper.png")) {
            cbireturn.setReturnValue(new Identifier(ArmorRestitched.MOD_ID, this.entityTexture));
        }
    }
    
}
