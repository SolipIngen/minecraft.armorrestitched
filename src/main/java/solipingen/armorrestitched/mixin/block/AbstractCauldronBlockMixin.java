package solipingen.armorrestitched.mixin.block;

import java.util.Map;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.CarpetBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import solipingen.armorrestitched.block.ModBlocks;
import solipingen.armorrestitched.block.WoollikeBlock;
import solipingen.armorrestitched.item.ModItems;
import solipingen.armorrestitched.registry.tag.ModItemTags;
import solipingen.armorrestitched.sound.ModSoundEvents;


@Mixin(AbstractCauldronBlock.class)
public abstract class AbstractCauldronBlockMixin extends Block {
    private static final CauldronBehavior CLEAN_WOOLLIKE_BLOCK = (state, world, pos, player, hand, stack) -> {
        if (!world.isClient && state.isOf(Blocks.WATER_CAULDRON)) {
            Block block = Block.getBlockFromItem(stack.getItem());
            if (block instanceof WoollikeBlock && !(block == ModBlocks.WHITE_COTTON || block == ModBlocks.WHITE_FUR || block == ModBlocks.WHITE_LINEN || block == ModBlocks.WHITE_SILK || block == Blocks.WHITE_WOOL)) {
                ItemStack itemStack2 = stack.copyWithCount(1);
                if (stack.isIn(ModItemTags.COTTON_BLOCKS)) {
                    itemStack2 = new ItemStack(ModBlocks.WHITE_COTTON);
                }
                else if (stack.isIn(ModItemTags.FUR_BLOCKS)) {
                    itemStack2 = new ItemStack(ModBlocks.WHITE_FUR);
                }
                else if (stack.isIn(ModItemTags.LINEN_BLOCKS)) {
                    itemStack2 = new ItemStack(ModBlocks.WHITE_LINEN);
                }
                else if (stack.isIn(ModItemTags.SILK_BLOCKS)) {
                    itemStack2 = new ItemStack(ModBlocks.WHITE_SILK);
                }
                else if (stack.isIn(ItemTags.WOOL)) {
                    itemStack2 = new ItemStack(Blocks.WHITE_WOOL);
                }
                if (!player.getAbilities().creativeMode) {
                    stack.decrement(1);
                }
                if (stack.isEmpty()) {
                    player.setStackInHand(hand, itemStack2);
                } 
                else if (player.getInventory().insertStack(itemStack2)) {
                    player.playerScreenHandler.syncState();
                } 
                else {
                    player.dropItem(itemStack2, false);
                }
                world.playSound(null, pos, ModSoundEvents.CAULDRON_USED, SoundCategory.BLOCKS, 1.0f, 1.0f);
                player.incrementStat(Stats.USE_CAULDRON);
                LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
                return ActionResult.SUCCESS;
            }
            if (block instanceof CarpetBlock && !(block == ModBlocks.WHITE_COTTON_CARPET || block == ModBlocks.WHITE_FUR_CARPET || block == ModBlocks.WHITE_LINEN_CARPET || block == ModBlocks.WHITE_SILK_CARPET || block == Blocks.WHITE_CARPET)) {
                ItemStack itemStack2 = stack.copyWithCount(1);
                if (stack.isIn(ModItemTags.COTTON_CARPETS)) {
                    itemStack2 = new ItemStack(ModBlocks.WHITE_COTTON_CARPET);
                }
                else if (stack.isIn(ModItemTags.FUR_CARPETS)) {
                    itemStack2 = new ItemStack(ModBlocks.WHITE_FUR_CARPET);
                }
                else if (stack.isIn(ModItemTags.LINEN_CARPETS)) {
                    itemStack2 = new ItemStack(ModBlocks.WHITE_LINEN_CARPET);
                }
                else if (stack.isIn(ModItemTags.SILK_CARPETS)) {
                    itemStack2 = new ItemStack(ModBlocks.WHITE_SILK_CARPET);
                }
                else if (stack.isIn(ItemTags.WOOL_CARPETS)) {
                    itemStack2 = new ItemStack(Blocks.WHITE_CARPET);
                }
                if (!player.getAbilities().creativeMode) {
                    stack.decrement(1);
                }
                if (stack.isEmpty()) {
                    player.setStackInHand(hand, itemStack2);
                } 
                else if (player.getInventory().insertStack(itemStack2)) {
                    player.playerScreenHandler.syncState();
                } 
                else {
                    player.dropItem(itemStack2, false);
                }
                world.playSound(null, pos, ModSoundEvents.CAULDRON_USED, SoundCategory.BLOCKS, 1.0f, 1.0f);
                player.incrementStat(Stats.USE_CAULDRON);
                LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    };
    private static final CauldronBehavior SILK_EXTRACTION = (state, world, pos, player, hand, stack) -> {
        if (!world.isClient && stack.isOf(ModItems.SILKWORM_COCOON)) {
            if (state.isOf(Blocks.WATER_CAULDRON) && (world.getBlockState(pos.down()).getLuminance() >= 15 || world.getBlockState(pos.down()).getBlock() instanceof CampfireBlock || world.getBlockState(pos.down()).getBlock() instanceof FireBlock)) {
                ItemStack itemStack = new ItemStack(ModItems.SILK, stack.getCount());
                ItemStack itemStack2 = new ItemStack(ModItems.COOKED_SILKWORM_PUPA, stack.getCount());
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
                player.incrementStat(Stats.USE_CAULDRON);
                LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    };
    @Shadow @Final private Map<Item, CauldronBehavior> behaviorMap;


    public AbstractCauldronBlockMixin(Settings settings) {
        super(settings);
    }

    @Redirect(method = "onUse", at = @At(value = "FIELD", target = "Lnet/minecraft/block/AbstractCauldronBlock;behaviorMap:Ljava/util/Map;", opcode = Opcodes.GETFIELD))
    private Map<Item, CauldronBehavior> redirectedCauldronBehaviorMap(AbstractCauldronBlock abstractCauldronBlock, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        Map<Item, CauldronBehavior> behaviorMap2 = this.behaviorMap;
        if (!world.isClient && behaviorMap2 == CauldronBehavior.WATER_CAULDRON_BEHAVIOR) {
            behaviorMap2.putIfAbsent(ModItems.SILKWORM_COCOON, SILK_EXTRACTION);

            behaviorMap2.putIfAbsent(ModBlocks.BLACK_COTTON.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.BLUE_COTTON.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.BROWN_COTTON.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.CYAN_COTTON.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.GRAY_COTTON.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.GREEN_COTTON.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIGHT_BLUE_COTTON.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIGHT_GRAY_COTTON.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIME_COTTON.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.MAGENTA_COTTON.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.ORANGE_COTTON.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.PINK_COTTON.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.PURPLE_COTTON.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.RED_COTTON.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.YELLOW_COTTON.asItem(), CLEAN_WOOLLIKE_BLOCK);

            behaviorMap2.putIfAbsent(ModBlocks.BLACK_COTTON_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.BLUE_COTTON_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.BROWN_COTTON_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.CYAN_COTTON_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.GRAY_COTTON_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.GREEN_COTTON_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIGHT_BLUE_COTTON_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIGHT_GRAY_COTTON_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIME_COTTON_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.MAGENTA_COTTON_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.ORANGE_COTTON_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.PINK_COTTON_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.PURPLE_COTTON_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.RED_COTTON_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.YELLOW_COTTON_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            
            behaviorMap2.putIfAbsent(ModBlocks.BLACK_FUR.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.BLUE_FUR.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.BROWN_FUR.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.CYAN_FUR.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.GRAY_FUR.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.GREEN_FUR.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIGHT_BLUE_FUR.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIGHT_GRAY_FUR.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIME_FUR.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.MAGENTA_FUR.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.ORANGE_FUR.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.PINK_FUR.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.PURPLE_FUR.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.RED_FUR.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.YELLOW_FUR.asItem(), CLEAN_WOOLLIKE_BLOCK);

            behaviorMap2.putIfAbsent(ModBlocks.BLACK_FUR_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.BLUE_FUR_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.BROWN_FUR_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.CYAN_FUR_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.GRAY_FUR_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.GREEN_FUR_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIGHT_BLUE_FUR_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIGHT_GRAY_FUR_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIME_FUR_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.MAGENTA_FUR_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.ORANGE_FUR_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.PINK_FUR_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.PURPLE_FUR_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.RED_FUR_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.YELLOW_FUR_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
    
            behaviorMap2.putIfAbsent(ModBlocks.BLACK_LINEN.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.BLUE_LINEN.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.BROWN_LINEN.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.CYAN_LINEN.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.GRAY_LINEN.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.GREEN_LINEN.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIGHT_BLUE_LINEN.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIGHT_GRAY_LINEN.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIME_LINEN.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.MAGENTA_LINEN.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.ORANGE_LINEN.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.PINK_LINEN.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.PURPLE_LINEN.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.RED_LINEN.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.YELLOW_LINEN.asItem(), CLEAN_WOOLLIKE_BLOCK);

            behaviorMap2.putIfAbsent(ModBlocks.BLACK_LINEN_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.BLUE_LINEN_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.BROWN_LINEN_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.CYAN_LINEN_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.GRAY_LINEN_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.GREEN_LINEN_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIGHT_BLUE_LINEN_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIGHT_GRAY_LINEN_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIME_LINEN_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.MAGENTA_LINEN_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.ORANGE_LINEN_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.PINK_LINEN_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.PURPLE_LINEN_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.RED_LINEN_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.YELLOW_LINEN_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
    
            behaviorMap2.putIfAbsent(ModBlocks.BLACK_SILK.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.BLUE_SILK.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.BROWN_SILK.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.CYAN_SILK.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.GRAY_SILK.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.GREEN_SILK.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIGHT_BLUE_SILK.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIGHT_GRAY_SILK.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIME_SILK.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.MAGENTA_SILK.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.ORANGE_SILK.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.PINK_SILK.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.PURPLE_SILK.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.RED_SILK.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.YELLOW_SILK.asItem(), CLEAN_WOOLLIKE_BLOCK);

            behaviorMap2.putIfAbsent(ModBlocks.BLACK_SILK_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.BLUE_SILK_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.BROWN_SILK_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.CYAN_SILK_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.GRAY_SILK_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.GREEN_SILK_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIGHT_BLUE_SILK_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIGHT_GRAY_SILK_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.LIME_SILK_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.MAGENTA_SILK_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.ORANGE_SILK_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.PINK_SILK_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.PURPLE_SILK_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.RED_SILK_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(ModBlocks.YELLOW_SILK_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
    
            behaviorMap2.putIfAbsent(Blocks.BLACK_WOOL.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.BLUE_WOOL.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.BROWN_WOOL.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.CYAN_WOOL.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.GRAY_WOOL.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.GREEN_WOOL.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.LIGHT_BLUE_WOOL.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.LIGHT_GRAY_WOOL.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.LIME_WOOL.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.MAGENTA_WOOL.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.ORANGE_WOOL.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.PINK_WOOL.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.PURPLE_WOOL.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.RED_WOOL.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.YELLOW_WOOL.asItem(), CLEAN_WOOLLIKE_BLOCK);

            behaviorMap2.putIfAbsent(Blocks.BLACK_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.BLUE_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.BROWN_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.CYAN_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.GRAY_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.GREEN_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.LIGHT_BLUE_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.LIGHT_GRAY_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.LIME_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.MAGENTA_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.ORANGE_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.PINK_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.PURPLE_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.RED_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
            behaviorMap2.putIfAbsent(Blocks.YELLOW_CARPET.asItem(), CLEAN_WOOLLIKE_BLOCK);
    
        }
        return behaviorMap2;
    }


    
}
