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
    private static final VoxelShape[] AGE_TO_SHAPE = new VoxelShape[]{Block.createCuboidShape(0.0, 0.0, 0.0, 10.0, 2.0, 10.0), 
        Block.createCuboidShape(0.0, 0.0, 0.0, 10.0, 4.0, 10.0), 
        Block.createCuboidShape(0.0, 0.0, 0.0, 11.0, 6.0, 11.0), 
        Block.createCuboidShape(0.0, 0.0, 0.0, 11.0, 8.0, 11.0), 
        Block.createCuboidShape(0.0, 0.0, 0.0, 12.0, 10.0, 12.0),
        Block.createCuboidShape(0.0, 0.0, 0.0, 12.0, 12.0, 12.0), 
        Block.createCuboidShape(0.0, 0.0, 0.0, 12.0, 14.0, 12.0), 
        Block.createCuboidShape(0.0, 0.0, 0.0, 12.0, 16.0, 12.0)};


    public FlaxCropBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return ModItems.FLAXSEEDS;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AGE_TO_SHAPE[state.get(this.getAgeProperty())];
    }
    


}
