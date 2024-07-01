package solipingen.armorrestitched.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import solipingen.armorrestitched.entity.ModEntityTypes;
import solipingen.armorrestitched.item.ModItems;
import solipingen.armorrestitched.sound.ModSoundEvents;


public class SilkwormLeavesBlock extends LeavesBlock {


    public SilkwormLeavesBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(DISTANCE, 7).with(PERSISTENT, false).with(WATERLOGGED, false));
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getLightLevel(pos) > 12) return;
        if (random.nextInt(8) == 0) {
            Direction direction = Direction.random(random);
            BlockPos spawnBlockPos = pos.offset(direction);
            if (world.getBlockState(spawnBlockPos).isAir()) {
                ModEntityTypes.SILK_MOTH_ENTITY.spawn(world, pos.offset(direction), SpawnReason.NATURAL);
                world.setBlockState(pos, ModBlocks.MULBERRY_LEAVES.getDefaultState().with(WATERLOGGED, state.get(WATERLOGGED)));
                world.emitGameEvent(null, GameEvent.BLOCK_CHANGE, pos.offset(direction));
                for (int j = 0; j < 8; j++) {
                    world.spawnParticles(ParticleTypes.HAPPY_VILLAGER, (double)pos.getX() + 0.5 + world.random.nextGaussian(), pos.getY() + 0.5 + world.random.nextGaussian(), pos.getZ() + 0.5 + world.random.nextGaussian(), 1, 0.0, 0.0, 0.0, 0.0);
                }
            }
        }
        super.randomTick(state, world, pos, random);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED) && world instanceof ServerWorld) {
            world.setBlockState(pos, ModBlocks.MULBERRY_LEAVES.getDefaultState().with(WATERLOGGED, state.get(WATERLOGGED)), Block.NOTIFY_LISTENERS);
            Block.dropStack((ServerWorld)world, pos, new ItemStack(ModItems.SILKWORM_COCOON, ((ServerWorld)world).random.nextBetween(0, 1)));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            Block.dropStack(world, pos, new ItemStack(ModItems.SILKWORM_COCOON, world.random.nextBetween(1, 2)));
            float f = MathHelper.nextBetween(world.random, 0.8f, 1.2f);
            world.playSound(null, pos, ModSoundEvents.SILKWORM_HARVEST, SoundCategory.BLOCKS, 1.0f, f);
            BlockState blockState = ModBlocks.MULBERRY_LEAVES.getDefaultState();
            world.setBlockState(pos, blockState, Block.NOTIFY_LISTENERS);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, blockState));
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }


    
}
