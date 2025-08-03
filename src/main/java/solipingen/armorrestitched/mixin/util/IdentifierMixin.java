package solipingen.armorrestitched.mixin.util;

import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import solipingen.armorrestitched.ArmorRestitched;


@Mixin(Identifier.class)
public abstract class IdentifierMixin implements Comparable<Identifier> {

    // One-off re-mapper for renamed items.
    @Inject(method = "validatePath", at = @At("HEAD"), cancellable = true)
    private static void injectedValidatePath(String namespace, String path, CallbackInfoReturnable<String> cbireturn) {
        if (namespace.matches(ArmorRestitched.MOD_ID)) {
            if (path.matches("flaxseed")) {
                cbireturn.setReturnValue("flaxseeds");
            }
            else if (path.matches("flax_stem")) {
                cbireturn.setReturnValue("flax_straw");
            }
        }
    }


}
