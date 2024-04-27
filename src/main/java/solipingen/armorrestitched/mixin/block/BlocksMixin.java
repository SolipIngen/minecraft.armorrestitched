package solipingen.armorrestitched.mixin.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.serialization.Lifecycle;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.block.WoollikeBlock;


@Mixin(Blocks.class)
public abstract class BlocksMixin {
    
    @SuppressWarnings("all")
    @Inject(method = "register", at = @At("HEAD"), cancellable = true)
    private static void injectedRegister(String name, Block entry, CallbackInfoReturnable<Block> cbireturn) {
        if (name.endsWith("wool")) {
            Registry.register(Registries.BLOCK, name, entry);
            int rawId = Registries.BLOCK.getRawId(entry);
            Block newEntry = (Block)new WoollikeBlock(AbstractBlock.Settings.copy(entry));
            cbireturn.setReturnValue(((SimpleRegistry<Block>)Registries.BLOCK).set(rawId, RegistryKey.of(Registries.BLOCK.getKey(), new Identifier(name)), newEntry, Lifecycle.stable()).value());
        }
    }


}
