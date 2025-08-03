package solipingen.armorrestitched.mixin.enchantment.effect.entity;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.enchantment.effect.entity.ReplaceDiskEnchantmentEffect;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import solipingen.armorrestitched.registry.tag.ModBlockTags;


@Mixin(ReplaceDiskEnchantmentEffect.class)
public abstract class ReplaceDiskEnchantmentEffectMixin implements EnchantmentEntityEffect {
    @Shadow @Final private BlockStateProvider blockState;


    @ModifyVariable(method = "apply", at = @At("HEAD"), index = 5)
    private Vec3d modifiedPos(Vec3d pos, ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos2) {
        if (user.hasVehicle()) {
            return user.getVehicle().getPos();
        }
        return pos;
    }

    @Redirect(method = "apply", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z"))
    private boolean redirectedSetBlockState(ServerWorld world, BlockPos blockPos2, BlockState blockState) {
        if (blockState.isIn(ModBlockTags.FROST_WALKER_LAVA_COOLED)) {
            world.syncWorldEvent(null, WorldEvents.LAVA_EXTINGUISHED, blockPos2, 0);
        }
        return world.setBlockState(blockPos2, blockState);
    }


}
