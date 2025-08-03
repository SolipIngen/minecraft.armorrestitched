package solipingen.armorrestitched.mixin.item;

import net.minecraft.component.type.ToolComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ShearsItem;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import solipingen.armorrestitched.registry.tag.ModBlockTags;

import java.util.List;


@Mixin(ShearsItem.class)
public abstract class ShearsItemMixin extends Item {


    public ShearsItemMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "createToolComponent", at = @At("HEAD"), cancellable = true)
    private static void injectedCreateToolComponent(CallbackInfoReturnable<ToolComponent> cbireturn) {
        RegistryEntryLookup<Block> registryEntryLookup = Registries.createEntryLookup(Registries.BLOCK);
        new ToolComponent(List.of(ToolComponent.Rule.ofAlwaysDropping(RegistryEntryList.of(Blocks.COBWEB.getRegistryEntry()), 15.0f),
                ToolComponent.Rule.of(registryEntryLookup.getOrThrow(BlockTags.LEAVES), 15.0f),
                ToolComponent.Rule.of(registryEntryLookup.getOrThrow(BlockTags.WOOL), 10.0f), ToolComponent.Rule.of(registryEntryLookup.getOrThrow(BlockTags.WOOL_CARPETS), 10.0f),
                ToolComponent.Rule.of(registryEntryLookup.getOrThrow(ModBlockTags.COTTON), 10.0f), ToolComponent.Rule.of(registryEntryLookup.getOrThrow(ModBlockTags.COTTON_CARPETS), 10.0f),
                ToolComponent.Rule.of(registryEntryLookup.getOrThrow(ModBlockTags.FUR), 10.0f), ToolComponent.Rule.of(registryEntryLookup.getOrThrow(ModBlockTags.FUR_CARPETS), 10.0f),
                ToolComponent.Rule.of(registryEntryLookup.getOrThrow(ModBlockTags.LINEN), 10.0f), ToolComponent.Rule.of(registryEntryLookup.getOrThrow(ModBlockTags.LINEN_CARPETS), 10.0f),
                ToolComponent.Rule.of(registryEntryLookup.getOrThrow(ModBlockTags.SILK), 10.0f), ToolComponent.Rule.of(registryEntryLookup.getOrThrow(ModBlockTags.SILK_CARPETS), 10.0f),
                ToolComponent.Rule.of(registryEntryLookup.getOrThrow(ModBlockTags.COTTON), 10.0f), ToolComponent.Rule.of(registryEntryLookup.getOrThrow(ModBlockTags.COTTON_CARPETS), 10.0f),
                ToolComponent.Rule.of(RegistryEntryList.of(new RegistryEntry[]{Blocks.VINE.getRegistryEntry(), Blocks.GLOW_LICHEN.getRegistryEntry()}), 15.0f)), 1.0f, 1, true);
    }

    @Inject(method = "postMine", at = @At("HEAD"), cancellable = true)
    private void injectedPostMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner, CallbackInfoReturnable<Boolean> cbireturn) {
        if (!world.isClient() && !state.isIn(BlockTags.FIRE)) {
            boolean bl = state.isIn(BlockTags.LEAVES) || state.isOf(Blocks.COBWEB) || state.isOf(Blocks.SHORT_GRASS) || state.isOf(Blocks.FERN) || state.isOf(Blocks.TALL_GRASS) || state.isOf(Blocks.SEAGRASS) || state.isOf(Blocks.TALL_SEAGRASS)
                    || state.isOf(Blocks.DEAD_BUSH) || state.isOf(Blocks.HANGING_ROOTS) || state.isOf(Blocks.VINE) || state.isOf(Blocks.GLOW_LICHEN)
                    || state.isIn(BlockTags.WOOL) || state.isIn(BlockTags.WOOL_CARPETS) || state.isIn(ModBlockTags.COTTON) || state.isIn(ModBlockTags.COTTON_CARPETS) || state.isIn(ModBlockTags.FUR) || state.isIn(ModBlockTags.FUR_CARPETS)
                    || state.isIn(ModBlockTags.LINEN) || state.isIn(ModBlockTags.LINEN_CARPETS) || state.isIn(ModBlockTags.SILK) || state.isIn(ModBlockTags.SILK_CARPETS) || state.isOf(Blocks.TRIPWIRE);
            if (bl) {
                stack.damage(1, miner, EquipmentSlot.MAINHAND);
            }
            cbireturn.setReturnValue(bl);
        }
    }

    
}
