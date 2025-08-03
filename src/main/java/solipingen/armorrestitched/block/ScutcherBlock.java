package solipingen.armorrestitched.block;

import com.mojang.serialization.MapCodec;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
import solipingen.armorrestitched.item.ModItems;
import solipingen.armorrestitched.sound.ModSoundEvents;

@SuppressWarnings("deprecation")
public class ScutcherBlock extends HorizontalFacingBlock implements Waterloggable {
    public static final MapCodec<ScutcherBlock> CODEC = ScutcherBlock.createCodec(ScutcherBlock::new);
    public static final VoxelShape LEG_NW = Block.createCuboidShape(3, 0, 3, 5, 6, 5);
    public static final VoxelShape LEG_NE = Block.createCuboidShape(11, 0, 3, 13, 6, 5);
    public static final VoxelShape LEG_SW = Block.createCuboidShape(3, 0, 11, 5, 6, 13);
    public static final VoxelShape LEG_SE = Block.createCuboidShape(11, 0, 11, 13, 6, 13);
    public static final VoxelShape TABLE = Block.createCuboidShape(3, 6, 3, 13, 7, 13);
    public static final VoxelShape WHEELS = Block.createCuboidShape(3, 7, 6, 13, 15, 10);
    public static final VoxelShape WHEELS_HORIZONTAL = Block.createCuboidShape(6, 7, 3, 10, 15, 13);
    public static final VoxelShape SHAPE = VoxelShapes.union(LEG_NW, LEG_NE, LEG_SW, LEG_SE, TABLE, WHEELS);
    public static final VoxelShape SHAPE_HORIZONTAL = VoxelShapes.union(LEG_NW, LEG_NE, LEG_SW, LEG_SE, TABLE, WHEELS_HORIZONTAL);
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;


    public ScutcherBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false));
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
    }

    @Override
    public ActionResult onUseWithItem(ItemStack itemStack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            if (itemStack.isOf(ModItems.COTTON) && itemStack.getCount() >= 4) {
                ItemStack itemStack2 = new ItemStack(ModBlocks.WHITE_COTTON);
                if (!player.isCreative()) {
                    itemStack.decrement(4);
                    player.setStackInHand(hand, itemStack);
                }
                if (!player.getInventory().insertStack(itemStack2)) {
                    player.dropItem(itemStack2, false);
                }
                else {
                    player.playerScreenHandler.syncState();
                }
                world.playSound(null, pos, ModSoundEvents.SCUTCHER_USED, SoundCategory.PLAYERS);
                player.incrementStat(Stats.USED.getOrCreateStat(this.asItem()));
                return ActionResult.SUCCESS;
            }
            if (itemStack.isOf(ModItems.FLAX_STRAW)) {
                ItemStack itemStack2 = new ItemStack(ModItems.LINEN, itemStack.getCount());
                if (player.isCreative()) {
                    if (!player.getInventory().insertStack(itemStack2)) {
                        player.dropItem(itemStack2, false);
                    }
                    else {
                        player.playerScreenHandler.syncState();
                    }
                }
                else {
                    player.setStackInHand(hand, itemStack2);
                }
                world.playSound(null, pos, ModSoundEvents.SCUTCHER_USED, SoundCategory.PLAYERS);
                player.incrementStat(Stats.USED.getOrCreateStat(this.asItem()));
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return (BlockState)this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite()).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @SuppressWarnings("incomplete-switch")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING)) {
            case NORTH, SOUTH: {
                return SHAPE;
            }
            case EAST, WEST: {
                return SHAPE_HORIZONTAL;
            }
        }
        return SHAPE;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (state.get(WATERLOGGED)) {
            tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return state;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(WATERLOGGED)) {
            return Fluids.WATER.getStill(false);
        }
        return super.getFluidState(state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }




    
}
