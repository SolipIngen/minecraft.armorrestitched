package solipingen.armorrestitched.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
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
import net.minecraft.world.event.GameEvent;
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
        for (Direction direction : Direction.values()) {
            BlockState neighborState = world.getBlockState(pos.offset(direction));
            if (!neighborState.isOf(ModBlocks.MULBERRY_LEAVES)) continue;
            if (random.nextInt(6) == 0) {
                world.setBlockState(pos.offset(direction), ModBlocks.MULBERRY_SILKWORM_LEAVES.getDefaultState(), Block.NOTIFY_LISTENERS);
                world.emitGameEvent(null, GameEvent.BLOCK_CHANGE, pos.offset(direction));
            }
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
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
