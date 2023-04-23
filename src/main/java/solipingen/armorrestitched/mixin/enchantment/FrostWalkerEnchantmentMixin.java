package solipingen.armorrestitched.mixin.enchantment;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.FrostWalkerEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;


@Mixin(FrostWalkerEnchantment.class)
public abstract class FrostWalkerEnchantmentMixin extends Enchantment {


    protected FrostWalkerEnchantmentMixin(Rarity weight, EnchantmentTarget target, EquipmentSlot[] slotTypes) {
        super(weight, target, slotTypes);
    }

    @Inject(method = "freezeWater", at = @At("HEAD"))
    private static void injectedFreezeWater(LivingEntity entity, World world, BlockPos blockPos, int level, CallbackInfo cbi) {
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
    }

    @Redirect(method = "freezeWater", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;isOnGround()Z"))
    private static boolean redirectedIsOnGround(LivingEntity entity) {
        return entity.isOnGround() || (entity.hasVehicle() && entity.getVehicle() instanceof LivingEntity);
    }


    
}
