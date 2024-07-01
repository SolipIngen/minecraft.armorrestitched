package solipingen.armorrestitched.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import solipingen.armorrestitched.item.ModItems;
import solipingen.armorrestitched.sound.ModSoundEvents;


public class CottonCropBlock extends CropBlock {
    public static final int MAX_AGE = 7;
    public static final IntProperty AGE = Properties.AGE_7;
    protected static final VoxelShape[] AGE_TO_SHAPE = new VoxelShape[]{Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 2.0, 11.0), 
        Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 4.0, 11.0), 
        Block.createCuboidShape(4.0, 0.0, 4.0, 12.0, 6.0, 12.0), 
        Block.createCuboidShape(4.0, 0.0, 4.0, 12.0, 8.0, 12.0), 
        Block.createCuboidShape(3.0, 0.0, 3.0, 13.0, 10.0, 13.0), 
        Block.createCuboidShape(3.0, 0.0, 3.0, 13.0, 12.0, 13.0), 
        Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 14.0, 14.0), 
        Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0)};


    public CottonCropBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(AGE, 0));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AGE_TO_SHAPE[state.get(AGE)];
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(Blocks.FARMLAND);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getBaseLightLevel(pos, 0) < 9) {
            return;
        }
        float f = CropBlock.getAvailableMoisture(this, world, pos);
        if (random.nextInt((int)(25.0f / f) + 1) == 0) {
            int i = state.get(AGE);
            if (i < 7) {
                state = (BlockState)state.with(AGE, i + 1);
                world.setBlockState(pos, state, Block.NOTIFY_LISTENERS);
                if (i >= 5) {
                    BlockState downState = world.getBlockState(pos.down());
                    if (downState.isOf(Blocks.FARMLAND) && downState.get(FarmlandBlock.MOISTURE) > 0) {
                        world.setBlockState(pos.down(), downState.with(FarmlandBlock.MOISTURE, 0), Block.NOTIFY_LISTENERS);
                    }
                }
            }
        }
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return state.get(AGE) != 7;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int i = Math.min(7, state.get(AGE) + MathHelper.nextInt(world.random, 2, 5));
        BlockState blockState = (BlockState)state.with(AGE, i);
        world.setBlockState(pos, blockState, Block.NOTIFY_LISTENERS);
        if (i == 7) {
            blockState.randomTick(world, pos, world.random);
        }
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return ModItems.COTTON_SEEDS;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            int age = state.get(AGE);
            if (age == 7) {
                Block.dropStack(world, pos, new ItemStack(ModItems.COTTON, world.random.nextBetween(1, 4)));
                float f = MathHelper.nextBetween(world.random, 0.8f, 1.2f);
                world.playSound(null, pos, ModSoundEvents.COTTON_PICK, SoundCategory.BLOCKS, 1.0f, f);
                BlockState blockState = (BlockState)state.with(AGE, age - 1);
                world.setBlockState(pos, blockState, Block.NOTIFY_LISTENERS);
                world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, blockState));
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }


}
