package solipingen.armorrestitched.mixin.entity.passive;

import java.util.ArrayList;
import java.util.Map;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.common.collect.Maps;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import solipingen.armorrestitched.ArmorRestitched;
import solipingen.armorrestitched.block.ModBlocks;
import solipingen.armorrestitched.item.ModItems;
import solipingen.armorrestitched.sound.ModSoundEvents;


@Mixin(LlamaEntity.class)
public abstract class LlamaEntityMixin extends AbstractDonkeyEntity {
    @Shadow @Final private static Ingredient TAMING_INGREDIENT;
    private static final Map<LlamaEntity.Variant, ItemConvertible> DROPS = Util.make(Maps.newEnumMap(LlamaEntity.Variant.class), map -> {
        map.put(LlamaEntity.Variant.CREAMY, Blocks.YELLOW_WOOL);
        map.put(LlamaEntity.Variant.WHITE, Blocks.WHITE_WOOL);
        map.put(LlamaEntity.Variant.BROWN, Blocks.BROWN_WOOL);
        map.put(LlamaEntity.Variant.GRAY, Blocks.LIGHT_GRAY_WOOL);
    });
    private boolean isSheared;
    private int shearedCooldown;


    protected LlamaEntityMixin(EntityType<? extends AbstractDonkeyEntity> entityType, World world) {
        super(entityType, world);
        this.isSheared = false;
        this.shearedCooldown = 0;
    }

    @Redirect(method = "initGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/recipe/Ingredient;ofItems([Lnet/minecraft/item/ItemConvertible;)Lnet/minecraft/recipe/Ingredient;"))
    private Ingredient redirectedTemptIngredient(ItemConvertible... originaItemConvertibles) {
        ArrayList<ItemConvertible> itemConvertibleList = new ArrayList<ItemConvertible>();
        for (ItemConvertible itemConvertible : originaItemConvertibles) {
            itemConvertibleList.add(itemConvertible);
        }
        itemConvertibleList.add(ModBlocks.FLAX_BLOCK);
        ItemConvertible[] itemConvertibles = itemConvertibleList.toArray(new ItemConvertible[itemConvertibleList.size()]);
        return Ingredient.ofItems(itemConvertibles);
    }

    @Redirect(method = "isBreedingItem", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/passive/LlamaEntity;TAMING_INGREDIENT:Lnet/minecraft/recipe/Ingredient;", opcode = Opcodes.GETSTATIC))
    private Ingredient redirectedBreedingIngredient() {
        ItemStack[] itemStackList = TAMING_INGREDIENT.getMatchingStacks();
        ArrayList<ItemConvertible> itemList = new ArrayList<ItemConvertible>();
        for (ItemStack stack : itemStackList) {
            itemList.add(stack.getItem());
        }
        itemList.add(ModItems.FLAX_STEM);
        itemList.add(ModBlocks.FLAX_BLOCK);
        ItemConvertible[] breedingItems = itemList.toArray(new ItemConvertible[itemList.size()]);
        return Ingredient.ofItems(breedingItems);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (!this.world.isClient) {
            ItemStack itemStack = player.getStackInHand(hand);
            if (itemStack.isOf(Items.SHEARS)) {
                if (this.isTame() && !this.isSheared) {
                    ItemEntity itemEntity = this.dropItem(DROPS.get(((LlamaEntity)(Object)this).getVariant()), 1);
                    itemEntity.setVelocity(itemEntity.getVelocity().add((this.random.nextFloat() - this.random.nextFloat()) * 0.1f, this.random.nextFloat() * 0.05f, (this.random.nextFloat() - this.random.nextFloat()) * 0.1f));
                    this.world.playSoundFromEntity(null, this, ModSoundEvents.LLAMA_SHEARED, SoundCategory.PLAYERS, 1.0f, 1.0f);
                    player.emitGameEvent(GameEvent.SHEAR, player);
                    this.isSheared = true;
                    this.shearedCooldown = this.random.nextBetween(6000, 12000);
                    itemStack.damage(1, player, p -> p.sendToolBreakStatus(hand));
                    return ActionResult.SUCCESS;
                }
            }
        }
        return super.interactMob(player, hand);
    }

    @Inject(method = "receiveFood", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", shift = At.Shift.AFTER), cancellable = true)
    private void injectedReceiveFood(PlayerEntity player, ItemStack item, CallbackInfoReturnable<Boolean> cbireturn) {
        if (item.isOf(Items.WHEAT) || item.isOf(ModItems.FLAX_STEM)) {
            this.shearedCooldown = MathHelper.ceil(0.9f*this.shearedCooldown);
        }
        else if (item.isOf(Blocks.HAY_BLOCK.asItem()) || item.isOf(ModBlocks.FLAX_BLOCK.asItem())) {
            this.shearedCooldown = MathHelper.ceil(0.67f*this.shearedCooldown);
        }
    }

    @Redirect(method = "receiveFood", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private boolean redirectedReceiveFoodIsOf(ItemStack itemStack, Item item) {
        if (item == Items.WHEAT) {
            return itemStack.isOf(item) || itemStack.isOf(ModItems.FLAX_STEM);
        }
        else if (item == Blocks.HAY_BLOCK.asItem()) {
            return itemStack.isOf(item) || itemStack.isOf(ModBlocks.FLAX_BLOCK.asItem());
        }
        return itemStack.isOf(item);
    }

    @Override
    protected void mobTick() {
        if (this.shearedCooldown > 0) {
            --this.shearedCooldown;
        }
        else {
            this.isSheared = false;
        }
        super.mobTick();
    }

    @Override
    public Identifier getLootTableId() {
        return switch (((LlamaEntity)(Object)this).getVariant()) {
            default -> throw new IncompatibleClassChangeError();
            case CREAMY -> new Identifier(ArmorRestitched.MOD_ID, "entities/llama/yellow");
            case WHITE -> new Identifier(ArmorRestitched.MOD_ID, "entities/llama/white");
            case BROWN -> new Identifier(ArmorRestitched.MOD_ID, "entities/llama/brown");
            case GRAY -> new Identifier(ArmorRestitched.MOD_ID, "entities/llama/light_gray");
        };
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void injectedWriteCustomDataToNbt(NbtCompound nbt, CallbackInfo cbi) {
        nbt.putBoolean("isSheared", this.isSheared);
        nbt.putInt("ShearedCooldown", this.shearedCooldown);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void injectedReadCustomDataFromNbt(NbtCompound nbt, CallbackInfo cbi) {
        this.isSheared = nbt.getBoolean("isSheared");
        this.shearedCooldown = nbt.getInt("ShearedCooldown");
    }


    
}
