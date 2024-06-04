package solipingen.armorrestitched.mixin.block;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


@Mixin(LeavesBlock.class)
public abstract class LeavesBlockMixin extends Block implements Waterloggable {


    public LeavesBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (state.get(LeavesBlock.WATERLOGGED)) {
            entity.handleFallDamage(fallDistance, 0.0f, world.getDamageSources().fall());
        }
        else {
            entity.handleFallDamage(fallDistance, 1.0f/3.0f, world.getDamageSources().fall());
        }
    }

    
}
