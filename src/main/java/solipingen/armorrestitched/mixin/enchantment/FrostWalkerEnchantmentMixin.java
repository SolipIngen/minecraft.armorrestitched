package solipingen.armorrestitched.mixin.enchantment;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FrostedIceBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.FrostWalkerEnchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;


@Mixin(FrostWalkerEnchantment.class)
public abstract class FrostWalkerEnchantmentMixin extends Enchantment {


    protected FrostWalkerEnchantmentMixin(Enchantment.Properties properties) {
        super(properties);
    }

    @Inject(method = "freezeWater", at = @At("HEAD"), cancellable = true)
    private static void injectedFreezeWater(LivingEntity entity, World world, BlockPos blockPos, int level, CallbackInfo cbi) {
        if (!(entity.isOnGround() || entity.hasVehicle())) {
            cbi.cancel();
        }
        if (entity.isOnFire() || entity.isInLava()) {
            boolean fireBl = world.getFluidState(blockPos).isIn(FluidTags.LAVA) || world.getBlockState(blockPos).isIn(BlockTags.FIRE);
            BlockPos currentBlockPos = fireBl ? blockPos : blockPos.down();
            fireBl |= world.getFluidState(currentBlockPos).isIn(FluidTags.LAVA) || world.getBlockState(currentBlockPos).isIn(BlockTags.FIRE);
            if (fireBl && entity.getRandom().nextInt(13) <= level*(world.getDimension().ultrawarm() ? 1 : 2)) {
                if (entity.isOnFire() && !entity.isFireImmune()) {
                    entity.extinguishWithSound();
                }
                if (world.getBlockState(currentBlockPos).isIn(BlockTags.FIRE)) {
                    world.removeBlock(currentBlockPos, false);
                    world.syncWorldEvent(WorldEvents.FIRE_EXTINGUISHED, currentBlockPos, 0);
                }
                else if (world.getFluidState(currentBlockPos).isIn(FluidTags.LAVA)) {
                    BlockState cooledState = world.getFluidState(currentBlockPos).isStill() ? Blocks.OBSIDIAN.getDefaultState() : (world.getDimension().ultrawarm() ? Blocks.BASALT.getDefaultState() : Blocks.COBBLESTONE.getDefaultState());
                    world.setBlockState(currentBlockPos, cooledState);
                    world.syncWorldEvent(WorldEvents.LAVA_EXTINGUISHED, currentBlockPos, 0);
                }
            }
        }
        BlockState blockState = Blocks.FROSTED_ICE.getDefaultState();
        int i = Math.min(16, 2 + level);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        int j = entity.hasVehicle() ? MathHelper.floor(entity.getVehicle().getHeight()) : 1;
        for (BlockPos blockPos2 : BlockPos.iterate(blockPos.add(-i, -j, -i), blockPos.add(i, -j, i))) {
            BlockState blockState3 = world.getBlockState(blockPos2);
            if (!blockPos2.isWithinDistance(entity.getPos(), (double)i)) continue;
            mutable.set(blockPos2.getX(), blockPos2.getY() + 1, blockPos2.getZ());
            BlockState blockState2 = world.getBlockState(mutable);
            if (!blockState2.isAir() || blockState3 != FrostedIceBlock.getMeltedState() || !blockState.canPlaceAt(world, blockPos2) || !world.canPlace(blockState, blockPos2, ShapeContext.absent())) continue;
            world.setBlockState(blockPos2, blockState);
            world.scheduleBlockTick(blockPos2, Blocks.FROSTED_ICE, MathHelper.nextInt(entity.getRandom(), 60, 120));
        }
        cbi.cancel();
    }


    
}
