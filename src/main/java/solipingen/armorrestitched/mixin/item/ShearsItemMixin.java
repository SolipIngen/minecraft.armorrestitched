package solipingen.armorrestitched.mixin.item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ShearsItem;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import solipingen.armorrestitched.registry.tag.ModBlockTags;


@Mixin(ShearsItem.class)
public abstract class ShearsItemMixin extends Item {


    public ShearsItemMixin(Settings settings) {
        super(settings);
    }

    @Redirect(method = "postMine", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"))
    private boolean redirectedPostMineIsIn(BlockState state, TagKey<Block> blockTag) {
        return state.isIn(blockTag) || !this.isSuitableFor(state);
    }

    @Redirect(method = "getMiningSpeedMultiplier", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"))
    private boolean redirectedMiningSpeedIsIn(BlockState state, TagKey<Block> blockTag) {
        if (blockTag == BlockTags.WOOL) {
            return state.isIn(blockTag) || state.isIn(BlockTags.WOOL_CARPETS) || state.isIn(ModBlockTags.COTTON) || state.isIn(ModBlockTags.COTTON_CARPETS) || state.isIn(ModBlockTags.FUR) || state.isIn(ModBlockTags.FUR_CARPETS)
                || state.isIn(ModBlockTags.LINEN) || state.isIn(ModBlockTags.LINEN_CARPETS) || state.isIn(ModBlockTags.SILK) || state.isIn(ModBlockTags.SILK_CARPETS);
        }
        return state.isIn(blockTag);
    }

    @Inject(method = "isSuitableFor", at = @At("HEAD"), cancellable = true)
    private void injectedIsSuitableFor(BlockState state, CallbackInfoReturnable<Boolean> cbireturn) {
        boolean bl = state.isIn(BlockTags.LEAVES) || state.isOf(Blocks.COBWEB) || state.isOf(Blocks.GRASS) || state.isOf(Blocks.FERN) || state.isOf(Blocks.TALL_GRASS) || state.isOf(Blocks.SEAGRASS) || state.isOf(Blocks.TALL_SEAGRASS)
            || state.isOf(Blocks.DEAD_BUSH) || state.isOf(Blocks.HANGING_ROOTS) || state.isOf(Blocks.VINE) || state.isOf(Blocks.GLOW_LICHEN) 
            || state.isIn(BlockTags.WOOL) || state.isIn(BlockTags.WOOL_CARPETS) || state.isIn(ModBlockTags.COTTON) || state.isIn(ModBlockTags.COTTON_CARPETS) || state.isIn(ModBlockTags.FUR) || state.isIn(ModBlockTags.FUR_CARPETS)
            || state.isIn(ModBlockTags.LINEN) || state.isIn(ModBlockTags.LINEN_CARPETS) || state.isIn(ModBlockTags.SILK) || state.isIn(ModBlockTags.SILK_CARPETS)
            || state.isOf(Blocks.REDSTONE_WIRE) || state.isOf(Blocks.TRIPWIRE);
        cbireturn.setReturnValue(bl);
    }

    @ModifyConstant(method = "getMiningSpeedMultiplier", constant = @Constant(floatValue = 5.0f))
    private float modifiedTextileMiningSpeed(float originalf) {
        return 10.0f;
    }

    @ModifyConstant(method = "getMiningSpeedMultiplier", constant = @Constant(floatValue = 2.0f))
    private float modifiedVineMiningSpeed(float originalf) {
        return 15.0f;
    }


    
}
