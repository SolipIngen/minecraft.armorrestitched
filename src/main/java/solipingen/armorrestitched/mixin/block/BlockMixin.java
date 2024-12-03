package solipingen.armorrestitched.mixin.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import solipingen.armorrestitched.block.WoollikeBlock;


@Mixin(Block.class)
public abstract class BlockMixin {

    @Shadow
    public abstract BlockState getDefaultState();


    @Inject(method = "onLandedUpon", at = @At("HEAD"), cancellable = true)
    private void injectedOnLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance, CallbackInfo cbi) {
        if (state.isIn(BlockTags.WOOL)) {
            entity.handleFallDamage(0.67f*fallDistance, 1.0f, entity.getDamageSources().fall());
            cbi.cancel();
        }
    }

    @Inject(method = "onEntityLand", at = @At("HEAD"), cancellable = true)
    private void injectedOnEntityLand(BlockView world, Entity entity, CallbackInfo cbi) {
        if (this.getDefaultState().isIn(BlockTags.WOOL)) {
            if (entity.bypassesLandingEffects()) {
                entity.setVelocity(entity.getVelocity().multiply(1.0, 0.0, 1.0));
            }
            else {
                WoollikeBlock.bounceEntity(entity);
            }
            cbi.cancel();
        }
    }


}
