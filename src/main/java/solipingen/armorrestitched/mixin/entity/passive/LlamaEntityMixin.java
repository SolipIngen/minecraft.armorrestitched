package solipingen.armorrestitched.mixin.entity.passive;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.common.collect.Maps;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.ItemTags;
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
import solipingen.armorrestitched.registry.tag.ModItemTags;
import solipingen.armorrestitched.sound.ModSoundEvents;


@Mixin(LlamaEntity.class)
public abstract class LlamaEntityMixin extends AbstractDonkeyEntity {
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

    @Inject(method = "initGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/recipe/Ingredient;ofItems([Lnet/minecraft/item/ItemConvertible;)Lnet/minecraft/recipe/Ingredient;"))
    private void injectedTemptGoal(CallbackInfo cbi) {
        this.goalSelector.add(5, new TemptGoal(this, 1.25, Ingredient.ofItems(ModBlocks.FLAX_BLOCK), false));
    }

    @Inject(method = "isBreedingItem", at = @At("HEAD"), cancellable = true)
    private void injectedBreedingIngredient(ItemStack stack, CallbackInfoReturnable<Boolean> cbireturn) {
        if (stack.isOf(ModBlocks.FLAX_BLOCK.asItem())) {
            cbireturn.setReturnValue(true);
        }
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

    @Inject(method = "receiveFood", at = @At("HEAD"), cancellable = true)
    private void injectedReceiveFood(PlayerEntity player, ItemStack item, CallbackInfoReturnable<Boolean> cbireturn) {
        int i = 0;
        int j = 0;
        float f = 0.0f;
        boolean bl = false;
        if (item.isOf(ModItems.FLAX_STEM)) {
            i = 10;
            j = 3;
            f = 2.0f;
            this.shearedCooldown = MathHelper.ceil(0.9f*this.shearedCooldown);
        } 
        else if (item.isOf(ModBlocks.FLAX_BLOCK.asItem())) {
            i = 90;
            j = 6;
            f = 10.0f;
            this.shearedCooldown = MathHelper.ceil(0.67f*this.shearedCooldown);
            if (this.isTame() && this.getBreedingAge() == 0 && this.canEat()) {
                bl = true;
                this.lovePlayer(player);
            }
        }
        if (this.getHealth() < this.getMaxHealth() && f > 0.0f) {
            this.heal(f);
            bl = true;
        }
        if (this.isBaby() && i > 0) {
            this.world.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getParticleX(1.0), this.getRandomBodyY() + 0.5, this.getParticleZ(1.0), 0.0, 0.0, 0.0);
            if (!this.world.isClient) {
                this.growUp(i);
            }
            bl = true;
        }
        if (j > 0 && (bl || !this.isTame()) && this.getTemper() < this.getMaxTemper()) {
            bl = true;
            if (!this.world.isClient) {
                this.addTemper(j);
            }
        }
        if (bl && !this.isSilent() && this.getEatSound() != null) {
            this.world.playSound(null, this.getX(), this.getY(), this.getZ(), this.getEatSound(), this.getSoundCategory(), 1.0f, 1.0f + (this.random.nextFloat() - this.random.nextFloat()) * 0.2f);
        }
        if (bl) {
            cbireturn.setReturnValue(bl);
        }
    }

    @Inject(method = "receiveFood", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", shift = At.Shift.AFTER), cancellable = true)
    private void injectedReceiveVanillaFood(PlayerEntity player, ItemStack item, CallbackInfoReturnable<Boolean> cbireturn) {
        if (item.isOf(Items.WHEAT)) {
            this.shearedCooldown = MathHelper.ceil(0.9f*this.shearedCooldown);
        }
        else if (item.isOf(Blocks.HAY_BLOCK.asItem())) {
            this.shearedCooldown = MathHelper.ceil(0.67f*this.shearedCooldown);
        }
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

    @Inject(method = "isHorseArmor", at = @At("HEAD"), cancellable = true)
    private void injectedIsHorseArmor(ItemStack item, CallbackInfoReturnable<Boolean> cbireturn) {
        boolean bl = item.isIn(ItemTags.WOOL_CARPETS) || item.isIn(ModItemTags.COTTON_CARPETS) || item.isIn(ModItemTags.FUR_CARPETS) || item.isIn(ModItemTags.LINEN_CARPETS) || item.isIn(ModItemTags.SILK_CARPETS);
        cbireturn.setReturnValue(bl);
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
