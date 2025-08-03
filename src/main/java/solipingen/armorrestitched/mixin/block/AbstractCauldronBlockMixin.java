package solipingen.armorrestitched.mixin.block;

import java.util.Map;

import net.minecraft.block.*;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import solipingen.armorrestitched.block.ModBlocks;
import solipingen.armorrestitched.item.ModItems;
import solipingen.armorrestitched.registry.tag.ModBlockTags;
import solipingen.armorrestitched.registry.tag.ModItemTags;
import solipingen.armorrestitched.sound.ModSoundEvents;


@Mixin(AbstractCauldronBlock.class)
public abstract class AbstractCauldronBlockMixin extends Block {
    @Unique private static final CauldronBehavior CLEAN_WOLLIKE_BLOCK = (state, world, pos, player, hand, stack) -> {
        if (state.isOf(Blocks.WATER_CAULDRON) && !(stack.isIn(ModItemTags.WHITE_WOOLLIKE_BLOCKS) || stack.isIn(ModItemTags.WHITE_WOOLLIKE_CARPETS)) && !player.isInSneakingPose()) {
            ItemStack itemStack = stack.copy();
            if (stack.isIn(ModItemTags.COTTON_BLOCKS)) {
                itemStack = new ItemStack(ModBlocks.WHITE_COTTON);
            }
            else if (stack.isIn(ModItemTags.COTTON_CARPETS)) {
                itemStack = new ItemStack(ModBlocks.WHITE_COTTON_CARPET);
            }
            else if (stack.isIn(ModItemTags.FUR_BLOCKS)) {
                itemStack = new ItemStack(ModBlocks.WHITE_FUR);
            }
            else if (stack.isIn(ModItemTags.FUR_CARPETS)) {
                itemStack = new ItemStack(ModBlocks.WHITE_FUR_CARPET);
            }
            else if (stack.isIn(ModItemTags.LINEN_BLOCKS)) {
                itemStack = new ItemStack(ModBlocks.WHITE_LINEN);
            }
            else if (stack.isIn(ModItemTags.LINEN_CARPETS)) {
                itemStack = new ItemStack(ModBlocks.WHITE_LINEN_CARPET);
            }
            else if (stack.isIn(ModItemTags.SILK_BLOCKS)) {
                itemStack = new ItemStack(ModBlocks.WHITE_SILK);
            }
            else if (stack.isIn(ModItemTags.SILK_CARPETS)) {
                itemStack = new ItemStack(ModBlocks.WHITE_SILK_CARPET);
            }
            else if (stack.isIn(ItemTags.WOOL)) {
                itemStack = new ItemStack(Blocks.WHITE_WOOL);
            }
            else if (stack.isIn(ItemTags.WOOL_CARPETS)) {
                itemStack = new ItemStack(Blocks.WHITE_CARPET);
            }
            itemStack.setCount(stack.getCount());
            if (!world.isClient) {
                player.setStackInHand(hand, itemStack);
                LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
                world.playSound(null, pos, ModSoundEvents.CAULDRON_USED, SoundCategory.BLOCKS, 1.0f, 0.8f + 0.1f*player.getRandom().nextBetween(0, 4));
                world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player));
                player.incrementStat(Stats.USE_CAULDRON);
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
    };
    @Unique private static final CauldronBehavior SILK_EXTRACTION = (state, world, pos, player, hand, stack) -> {
        if (stack.isOf(ModItems.SILKWORM_COCOON)) {
            int count = stack.getCount();
            BlockState downState = world.getBlockState(pos.down());
            if (state.isOf(Blocks.WATER_CAULDRON) && (downState.getLuminance() >= 15
                    || (downState.getBlock() instanceof CampfireBlock && downState.get(Properties.LIT))
                    || (!(downState.getBlock() instanceof CampfireBlock) && downState.isIn(ModBlockTags.SILK_EXTRACTION_HEAT_SOURCE)))) {
                ItemStack itemStack = new ItemStack(ModItems.SILK, count);
                ItemStack itemStack2 = new ItemStack(ModItems.COOKED_SILKWORM_PUPA, count);
                if (!world.isClient) {
                    if (!player.getAbilities().creativeMode) {
                        player.setStackInHand(hand, itemStack);
                    }
                    else {
                        if (player.getInventory().insertStack(itemStack)) {
                            player.playerScreenHandler.syncState();
                        }
                        else {
                            player.dropItem(itemStack, false);
                        }
                    }
                    if (player.getInventory().insertStack(itemStack2)) {
                        player.playerScreenHandler.syncState();
                    } 
                    else {
                        player.dropItem(itemStack2, false);
                    }
                    world.playSound(null, pos, ModSoundEvents.CAULDRON_USED, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player));
                    player.incrementStat(Stats.USE_CAULDRON);
                }
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
    };
    @Shadow @Final protected CauldronBehavior.CauldronBehaviorMap behaviorMap;


    public AbstractCauldronBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "onUseWithItem", at = @At("HEAD"), cancellable = true)
    private void injectedOnUse(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cbireturn) {
        Map<Item, CauldronBehavior> behaviorMap2 = this.behaviorMap.map();
        if (behaviorMap2 == CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map()) {
            behaviorMap2.putIfAbsent(ModItems.SILKWORM_COCOON, SILK_EXTRACTION);

            behaviorMap2.putIfAbsent(ModBlocks.BLACK_COTTON.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.BLUE_COTTON.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.BROWN_COTTON.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.CYAN_COTTON.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.GRAY_COTTON.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.GREEN_COTTON.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIGHT_BLUE_COTTON.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIGHT_GRAY_COTTON.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIME_COTTON.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.MAGENTA_COTTON.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.ORANGE_COTTON.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.PINK_COTTON.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.PURPLE_COTTON.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.RED_COTTON.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.WHITE_COTTON.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.YELLOW_COTTON.asItem(), CLEAN_WOLLIKE_BLOCK);

            behaviorMap2.putIfAbsent(ModBlocks.BLACK_COTTON_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.BLUE_COTTON_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.BROWN_COTTON_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.CYAN_COTTON_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.GRAY_COTTON_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.GREEN_COTTON_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIGHT_BLUE_COTTON_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIGHT_GRAY_COTTON_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIME_COTTON_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.MAGENTA_COTTON_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.ORANGE_COTTON_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.PINK_COTTON_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.PURPLE_COTTON_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.RED_COTTON_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.WHITE_COTTON_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.YELLOW_COTTON_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);

            behaviorMap2.putIfAbsent(ModBlocks.BLACK_FUR.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.BLUE_FUR.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.BROWN_FUR.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.CYAN_FUR.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.GRAY_FUR.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.GREEN_FUR.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIGHT_BLUE_FUR.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIGHT_GRAY_FUR.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIME_FUR.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.MAGENTA_FUR.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.ORANGE_FUR.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.PINK_FUR.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.PURPLE_FUR.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.RED_FUR.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.WHITE_FUR.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.YELLOW_FUR.asItem(), CLEAN_WOLLIKE_BLOCK);

            behaviorMap2.putIfAbsent(ModBlocks.BLACK_FUR_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.BLUE_FUR_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.BROWN_FUR_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.CYAN_FUR_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.GRAY_FUR_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.GREEN_FUR_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIGHT_BLUE_FUR_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIGHT_GRAY_FUR_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIME_FUR_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.MAGENTA_FUR_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.ORANGE_FUR_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.PINK_FUR_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.PURPLE_FUR_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.RED_FUR_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.WHITE_FUR_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.YELLOW_FUR_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);

            behaviorMap2.putIfAbsent(ModBlocks.BLACK_LINEN.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.BLUE_LINEN.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.BROWN_LINEN.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.CYAN_LINEN.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.GRAY_LINEN.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.GREEN_LINEN.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIGHT_BLUE_LINEN.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIGHT_GRAY_LINEN.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIME_LINEN.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.MAGENTA_LINEN.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.ORANGE_LINEN.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.PINK_LINEN.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.PURPLE_LINEN.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.RED_LINEN.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.WHITE_LINEN.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.YELLOW_LINEN.asItem(), CLEAN_WOLLIKE_BLOCK);

            behaviorMap2.putIfAbsent(ModBlocks.BLACK_LINEN_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.BLUE_LINEN_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.BROWN_LINEN_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.CYAN_LINEN_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.GRAY_LINEN_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.GREEN_LINEN_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIGHT_BLUE_LINEN_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIGHT_GRAY_LINEN_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIME_LINEN_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.MAGENTA_LINEN_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.ORANGE_LINEN_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.PINK_LINEN_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.PURPLE_LINEN_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.RED_LINEN_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.WHITE_LINEN_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.YELLOW_LINEN_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);

            behaviorMap2.putIfAbsent(ModBlocks.BLACK_SILK.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.BLUE_SILK.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.BROWN_SILK.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.CYAN_SILK.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.GRAY_SILK.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.GREEN_SILK.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIGHT_BLUE_SILK.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIGHT_GRAY_SILK.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIME_SILK.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.MAGENTA_SILK.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.ORANGE_SILK.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.PINK_SILK.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.PURPLE_SILK.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.RED_SILK.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.WHITE_SILK.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.YELLOW_SILK.asItem(), CLEAN_WOLLIKE_BLOCK);

            behaviorMap2.putIfAbsent(ModBlocks.BLACK_SILK_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.BLUE_SILK_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.BROWN_SILK_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.CYAN_SILK_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.GRAY_SILK_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.GREEN_SILK_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIGHT_BLUE_SILK_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIGHT_GRAY_SILK_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIME_SILK_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.MAGENTA_SILK_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.ORANGE_SILK_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.PINK_SILK_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.PURPLE_SILK_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.RED_SILK_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.WHITE_SILK_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.YELLOW_SILK_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);

            behaviorMap2.putIfAbsent(Blocks.BLACK_WOOL.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.BLUE_WOOL.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.BROWN_WOOL.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.CYAN_WOOL.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.GRAY_WOOL.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.GREEN_WOOL.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.LIGHT_BLUE_WOOL.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.LIGHT_GRAY_WOOL.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.LIME_WOOL.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.MAGENTA_WOOL.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.ORANGE_WOOL.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.PINK_WOOL.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.PURPLE_WOOL.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.RED_WOOL.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.WHITE_WOOL.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.YELLOW_WOOL.asItem(), CLEAN_WOLLIKE_BLOCK);

            behaviorMap2.putIfAbsent(Blocks.BLACK_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.BLUE_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.BROWN_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.CYAN_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.GRAY_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.GREEN_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.LIGHT_BLUE_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.LIGHT_GRAY_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.LIME_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.MAGENTA_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.ORANGE_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.PINK_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.PURPLE_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.RED_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.WHITE_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.YELLOW_CARPET.asItem(), CLEAN_WOLLIKE_BLOCK);

        }
        CauldronBehavior cauldronBehavior = behaviorMap2.get(stack.getItem());
        cbireturn.setReturnValue(cauldronBehavior.interact(state, world, pos, player, hand, stack));
    }


    
}
