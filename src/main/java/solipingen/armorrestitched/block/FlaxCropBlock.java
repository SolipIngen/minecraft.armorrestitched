package solipingen.armorrestitched.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import solipingen.armorrestitched.item.ModItems;


public class FlaxCropBlock extends CropBlock {
    private static final VoxelShape[] AGE_TO_SHAPE = new VoxelShape[]{Block.createCuboidShape(4.0, 0.0, 4.0, 12.0, 2.0, 12.0), 
        Block.createCuboidShape(4.0, 0.0, 4.0, 12.0, 4.0, 12.0), 
        Block.createCuboidShape(3.0, 0.0, 3.0, 13.0, 6.0, 13.0), 
        Block.createCuboidShape(3.0, 0.0, 3.0, 13.0, 8.0, 13.0), 
        Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 12.0, 14.0), 
        Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 14.0, 14.0), 
        Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 14.0, 14.0), 
        Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 14.0, 14.0)};


    public FlaxCropBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return ModItems.FLAXSEED;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AGE_TO_SHAPE[state.get(this.getAgeProperty())];
    }
    


}
