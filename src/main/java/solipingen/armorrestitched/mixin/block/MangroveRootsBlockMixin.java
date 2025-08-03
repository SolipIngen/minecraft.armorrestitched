package solipingen.armorrestitched.mixin.block;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MangroveRootsBlock;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


@Mixin(MangroveRootsBlock.class)
public abstract class MangroveRootsBlockMixin extends Block implements Waterloggable {


    public MangroveRootsBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, double fallDistance) {
        if (state.get(MangroveRootsBlock.WATERLOGGED)) {
            entity.handleFallDamage(fallDistance, 0.0f, world.getDamageSources().fall());
        }
        else {
            super.onLandedUpon(world, state, pos, entity, fallDistance);
        }
    }


    
}
