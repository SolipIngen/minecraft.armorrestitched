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
import solipingen.armorrestitched.block.WoolBlock;

@Mixin(Blocks.class)
public abstract class BlocksMixin {
    

    @Inject(method = "register", at = @At("HEAD"), cancellable = true)
    private static void injectedRegister(String id, Block entry, CallbackInfoReturnable<Block> cbireturn) {
        if (id.endsWith("wool")) {
            Registry.register(Registries.BLOCK, id, entry);
            int rawId = Registries.BLOCK.getRawId(entry);
            Block newEntry = (Block)new WoolBlock(AbstractBlock.Settings.copy(entry));
            cbireturn.setReturnValue(Registry.register(Registries.BLOCK, rawId, id, newEntry));
        }
    }


}
