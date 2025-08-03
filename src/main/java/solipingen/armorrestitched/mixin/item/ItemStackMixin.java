package solipingen.armorrestitched.mixin.item;

import net.minecraft.component.ComponentHolder;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.*;
import net.minecraft.item.equipment.trim.ArmorTrim;
import net.minecraft.item.equipment.trim.ArmorTrimMaterial;
import net.minecraft.item.equipment.trim.ArmorTrimMaterials;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.fabricmc.fabric.api.item.v1.FabricItemStack;
import net.minecraft.entity.ItemEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import solipingen.armorrestitched.registry.tag.ModItemTags;

import java.util.function.Consumer;


@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements ComponentHolder, FabricItemStack {

    @Shadow
    public abstract Item getItem();

    @Shadow
    public abstract boolean isOf(Item item);

    @Shadow
    public abstract boolean isIn(TagKey<Item> tag);


    @Inject(method = "getMaxDamage", at = @At("HEAD"), cancellable = true)
    private void injectedGetMaxDamage(CallbackInfoReturnable<Integer> cbireturn) {
        if (this.isOf(Items.ELYTRA)) {
            int durability = Math.max(this.getOrDefault(DataComponentTypes.MAX_DAMAGE, 432), 888);
            if (this.get(DataComponentTypes.TRIM) != null) {
                RegistryEntry<ArmorTrimMaterial> material = this.get(DataComponentTypes.TRIM).material();
                if (material.matchesKey(ArmorTrimMaterials.LAPIS) || material.matchesKey(ArmorTrimMaterials.REDSTONE)) {
                    durability += 40;
                }
                else if (material.matchesKey(ArmorTrimMaterials.QUARTZ) || material.matchesKey(ArmorTrimMaterials.AMETHYST)) {
                    durability += 60;
                }
                else if (material.matchesKey(ArmorTrimMaterials.COPPER) || material.matchesKey(ArmorTrimMaterials.GOLD) || material.matchesKey(ArmorTrimMaterials.EMERALD)) {
                    durability += 100;
                }
                else if (material.matchesKey(ArmorTrimMaterials.IRON)) {
                    durability += 200;
                }
                else if (material.matchesKey(ArmorTrimMaterials.DIAMOND)) {
                    durability += 240;
                }
                else if (material.matchesKey(ArmorTrimMaterials.NETHERITE)) {
                    durability += 300;
                }
            }
            cbireturn.setReturnValue(durability);
        }
    }

    @Inject(method = "damage(ILnet/minecraft/server/world/ServerWorld;Lnet/minecraft/server/network/ServerPlayerEntity;Ljava/util/function/Consumer;)V", at = @At("HEAD"), cancellable = true)
    private void injectedDamage(int amount, ServerWorld world, ServerPlayerEntity player, Consumer<Item> breakCallback, CallbackInfo cbi) {
        if (player != null) {
            ArmorTrim trim = this.get(DataComponentTypes.TRIM);
            if (trim != null) {
                if (trim.material().matchesKey(ArmorTrimMaterials.IRON) && player.getRandom().nextInt(4) == 0) {
                    cbi.cancel();
                }
                if (((ItemStack)(Object)this).isIn(ModItemTags.TRIMMABLE_ELYTRA) && ((ItemStack)(Object)this).getDamage() + amount >= ((ItemStack)(Object)this).getMaxDamage() - 1) {
                    ((ItemStack)(Object)this).remove(DataComponentTypes.TRIM);
                    if (!player.isSilent()) {
                        player.playSound(SoundEvents.ENTITY_ITEM_BREAK.value(), 0.8f, 0.8f + 0.4f*player.getRandom().nextFloat());
                    }
                    cbi.cancel();
                }
            }
        }
    }

    @Inject(method = "onItemEntityDestroyed", at = @At("HEAD"))
    private void injectedOnItemEntityDestroyed(ItemEntity entity, CallbackInfo cbi) {
        ArmorTrim trim = this.get(DataComponentTypes.TRIM);
        if (entity.getWorld() instanceof ServerWorld && trim != null && trim.material().matchesKey(ArmorTrimMaterials.NETHERITE)) {
            ItemEntity itemEntity = new ItemEntity(entity.getWorld(), entity.getX(), entity.getY(), entity.getZ(), new ItemStack(Items.NETHERITE_SCRAP, 4), 0.0, 0.0, 0.0);
            entity.getWorld().spawnEntity(itemEntity);
        }
    }



//    // Re-mapper for Flax Stem/Straw & Flaxseed/Flaxseeds.
//    @ModifyVariable(method = "", at = @At("HEAD"), index = 1)
//    private static NbtElement remappingNbtElement(NbtElement nbt, RegistryWrapper.WrapperLookup registries) {
//        if (nbt.asString().contains("armorrestitched:flax_stem")) {
//            NbtCompound nbtCompound = ((NbtCompound)nbt).copy();
//            nbtCompound.remove("id");
//            nbtCompound.putString("id", "armorrestitched:flax_straw");
//            return nbtCompound;
//        }
//        else if (nbt.asString().contains("armorrestitched:flaxseed")) {
//            NbtCompound nbtCompound = ((NbtCompound)nbt).copy();
//            nbtCompound.remove("id");
//            nbtCompound.putString("id", "armorrestitched:flaxseeds");
//            return nbtCompound;
//        }
//        return nbt;
//    }


}
