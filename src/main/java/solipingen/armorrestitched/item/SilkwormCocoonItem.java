package solipingen.armorrestitched.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import solipingen.armorrestitched.block.ModBlocks;


public class SilkwormCocoonItem extends Item {


    public SilkwormCocoonItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        PlayerEntity playerEntity = context.getPlayer();
        BlockState blockState = world.getBlockState(blockPos);
        ItemStack itemStack = context.getStack();
        if (blockState.isOf(ModBlocks.MULBERRY_LEAVES) && !blockState.get(Properties.WATERLOGGED) && !itemStack.isEmpty()) {
            world.setBlockState(blockPos, ModBlocks.MULBERRY_SILKWORM_LEAVES.getDefaultState(), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
            world.playSound(null, blockPos, SoundEvents.BLOCK_CAVE_VINES_PLACE, SoundCategory.BLOCKS);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(playerEntity));
            if (!playerEntity.isCreative()) {
                itemStack.decrement(1);
            }
            return ActionResult.success(world.isClient);
        }
        return super.useOnBlock(context);
    }

    
}
