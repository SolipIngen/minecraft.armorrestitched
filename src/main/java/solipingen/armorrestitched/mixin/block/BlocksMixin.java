package solipingen.armorrestitched.mixin.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import solipingen.armorrestitched.block.WoollikeBlock;

@Mixin(Blocks.class)
public abstract class BlocksMixin {
    

    @Inject(method = "register", at = @At("HEAD"), cancellable = true)
    private static void injectedRegister(String name, Block entry, CallbackInfoReturnable<Block> cbireturn) {
        if (name.endsWith("wool")) {
            Registry.register(Registries.BLOCK, name, entry);
            int rawId = Registries.BLOCK.getRawId(entry);
            Block newEntry = (Block)new WoollikeBlock(AbstractBlock.Settings.copy(entry));
            cbireturn.setReturnValue(Registry.register(Registries.BLOCK, rawId, name, newEntry));
        }
    }


}
