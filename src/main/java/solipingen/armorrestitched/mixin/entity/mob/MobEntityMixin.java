package solipingen.armorrestitched.mixin.entity.mob;

import java.util.ArrayList;
import java.util.Optional;

import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.passive.*;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.common.collect.Lists;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import solipingen.armorrestitched.ArmorRestitched;
import solipingen.armorrestitched.item.ModItems;
import solipingen.armorrestitched.mixin.accessors.WolfEntityAccessor;
import solipingen.armorrestitched.util.interfaces.mixin.entity.mob.MobEntityInterface;
import solipingen.armorrestitched.village.ModVillagerProfessions;


@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity implements MobEntityInterface {
    @Unique private boolean entranced;
    @Unique private int entrancedTime;


    protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectedInit(CallbackInfo cbi) {
        this.entranced = false;
        this.entrancedTime = 0;
    }

    @Inject(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;baseTick()V", shift = At.Shift.AFTER), cancellable = true)
    private void injectedBaseTick(CallbackInfo cbi) {
        if (this.getEntranced()) {
            this.entrancedTime -= this.entrancedTime > 0 ? 1 : 0;
            if (this.entrancedTime <= 0) {
                this.setEntranced(false, 0);
            }
        }
    }

    @Inject(method = "getLootTableKey", at = @At("HEAD"), cancellable = true)
    private void injectedGetLootTableKey(CallbackInfoReturnable<Optional<RegistryKey<LootTable>>> cbireturn) {
        if (((MobEntity)(Object)this) instanceof FoxEntity foxEntity) {
            cbireturn.setReturnValue(switch (foxEntity.getVariant()) {
                case FoxEntity.Variant.RED -> Optional.of(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(ArmorRestitched.MOD_ID, "entities/fox/red")));
                case FoxEntity.Variant.SNOW -> Optional.of(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(ArmorRestitched.MOD_ID, "entities/fox/snow")));
            });
        }
        else if (((MobEntity)(Object)this) instanceof LlamaEntity llamaEntity) {
            cbireturn.setReturnValue(switch (llamaEntity.getVariant()) {
                case LlamaEntity.Variant.CREAMY ->
                        Optional.of(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(ArmorRestitched.MOD_ID, "entities/llama/yellow")));
                case LlamaEntity.Variant.WHITE ->
                        Optional.of(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(ArmorRestitched.MOD_ID, "entities/llama/white")));
                case LlamaEntity.Variant.BROWN ->
                        Optional.of(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(ArmorRestitched.MOD_ID, "entities/llama/brown")));
                case LlamaEntity.Variant.GRAY ->
                        Optional.of(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(ArmorRestitched.MOD_ID, "entities/llama/light_gray")));
            });
        }
        else if (((MobEntity)(Object)this) instanceof PandaEntity pandaEntity && pandaEntity.isBrown()) {
            cbireturn.setReturnValue(Optional.of(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(ArmorRestitched.MOD_ID, "entities/panda_brown"))));
        }
        else if (((MobEntity)(Object)this) instanceof WolfEntity wolfEntity) {
            RegistryEntry<WolfVariant> variant = ((WolfEntityAccessor)wolfEntity).invokeGetVariant();
            String name = "";
            if (variant.matchesKey(WolfVariants.ASHEN)) {
                name = "ashen";
            }
            else if (variant.matchesKey(WolfVariants.BLACK)) {
                name = "black";
            }
            else if (variant.matchesKey(WolfVariants.CHESTNUT)) {
                name = "chestnut";
            }
            else if (variant.matchesKey(WolfVariants.PALE)) {
                name = "pale";
            }
            else if (variant.matchesKey(WolfVariants.RUSTY)) {
                name = "rusty";
            }
            else if (variant.matchesKey(WolfVariants.SNOWY)) {
                name = "snowy";
            }
            else if (variant.matchesKey(WolfVariants.SPOTTED)) {
                name = "spotted";
            }
            else if (variant.matchesKey(WolfVariants.STRIPED)) {
                name = "striped";
            }
            else if (variant.matchesKey(WolfVariants.WOODS)) {
                name = "woods";
            }
            if (!name.matches("")) {
                cbireturn.setReturnValue(Optional.of(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(ArmorRestitched.MOD_ID, "entities/wolf/" + name))));
            }
            }
    }

    @SuppressWarnings("incomplete-switch")
    @Inject(method = "initEquipment", at = @At("TAIL"))
    private void injectedInitEquipment(Random random, LocalDifficulty localDifficulty, CallbackInfo cbi) {
        float equipThreshold = ((MobEntity)(Object)this) instanceof IllagerEntity ? 0.2f*localDifficulty.getGlobalDifficulty().getId() + 0.25f*localDifficulty.getClampedLocalDifficulty() : 0.12f*localDifficulty.getGlobalDifficulty().getId() + 0.2f*localDifficulty.getClampedLocalDifficulty();
        float armorTypeThreshold = 0.03f*localDifficulty.getGlobalDifficulty().getId() + 0.15f*localDifficulty.getClampedLocalDifficulty();
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (!slot.isArmorSlot()) continue;
            this.equipStack(slot, ItemStack.EMPTY);
            if (random.nextFloat() <= equipThreshold) {
                int level = 0;
                for (int j = 0; j <= 4; j++) {
                    if (random.nextFloat() <= armorTypeThreshold) {
                        level++;
                    }
                }
                Item item = MobEntityMixin.getModEquipmentForSlot(slot, level);
                if (item != null && item.getDefaultStack().isIn(ItemTags.DYEABLE)) {
                    int randomInt = random.nextInt(6);
                    if (randomInt == 0) {
                        switch (slot) {
                            case HEAD: item = ModItems.COTTON_HELMET; break;
                            case CHEST: item = ModItems.COTTON_CHESTPLATE; break;
                            case LEGS: item = ModItems.COTTON_LEGGINGS; break;
                            case FEET: item = ModItems.COTTON_BOOTS; break;
                        }
                    }
                    else if (randomInt == 1) {
                        switch (slot) {
                            case HEAD: item = ModItems.FUR_HELMET; break;
                            case CHEST: item = ModItems.FUR_CHESTPLATE; break;
                            case LEGS: item = ModItems.FUR_LEGGINGS; break;
                            case FEET: item = ModItems.FUR_BOOTS; break;
                        }
                    }
                    else if (randomInt == 2) {
                        switch (slot) {
                            case HEAD: item = ModItems.LINEN_HELMET; break;
                            case CHEST: item = ModItems.LINEN_CHESTPLATE; break;
                            case LEGS: item = ModItems.LINEN_LEGGINGS; break;
                            case FEET: item = ModItems.LINEN_BOOTS; break;
                        }
                    }
                    else if (randomInt == 3) {
                        switch (slot) {
                            case HEAD: item = ModItems.SILK_HELMET; break;
                            case CHEST: item = ModItems.SILK_CHESTPLATE; break;
                            case LEGS: item = ModItems.SILK_LEGGINGS; break;
                            case FEET: item = ModItems.SILK_BOOTS; break;
                        }
                    }
                    else if (randomInt == 4) {
                        switch (slot) {
                            case HEAD: item = ModItems.PAPER_HELMET; break;
                            case CHEST: item = ModItems.PAPER_CHESTPLATE; break;
                            case LEGS: item = ModItems.PAPER_LEGGINGS; break;
                            case FEET: item = ModItems.PAPER_BOOTS; break;
                        }
                    }
                }
                if (item != null) {
                    ItemStack itemStack = item.getDefaultStack().isIn(ItemTags.DYEABLE) ? this.getRandomlyDyedClothing(item) : new ItemStack(item);
                    this.equipStack(slot, itemStack);
                }
            }
        }
    }

    @Unique
    @Nullable
    private static Item getModEquipmentForSlot(EquipmentSlot equipmentSlot, int equipmentLevel) {
        switch (equipmentSlot) {
            case HEAD: {
                if (equipmentLevel == 0) {
                    return Items.LEATHER_HELMET;
                }
                else if (equipmentLevel == 1) {
                    return ModItems.COPPER_HELMET;
                }
                else if (equipmentLevel == 2) {
                    return Items.IRON_HELMET;
                }
                else if (equipmentLevel == 3) {
                    return Items.GOLDEN_HELMET;
                }
                else if (equipmentLevel == 4) {
                    return Items.DIAMOND_HELMET;
                }
            }
            case CHEST: {
                if (equipmentLevel == 0) {
                    return Items.LEATHER_CHESTPLATE;
                }
                else if (equipmentLevel == 1) {
                    return ModItems.COPPER_CHESTPLATE;
                }
                else if (equipmentLevel == 2) {
                    return Items.IRON_CHESTPLATE;
                }
                else if (equipmentLevel == 3) {
                    return Items.GOLDEN_CHESTPLATE;
                }
                else if (equipmentLevel == 4) {
                    return Items.DIAMOND_CHESTPLATE;
                }
            }
            case LEGS: {
                if (equipmentLevel == 0) {
                    return Items.LEATHER_LEGGINGS;
                }
                else if (equipmentLevel == 1) {
                    return ModItems.COPPER_LEGGINGS;
                }
                else if (equipmentLevel == 2) {
                    return Items.IRON_LEGGINGS;
                }
                else if (equipmentLevel == 3) {
                    return Items.GOLDEN_LEGGINGS;
                }
                else if (equipmentLevel == 4) {
                    return Items.DIAMOND_LEGGINGS;
                }
            }
            case FEET: {
                if (equipmentLevel == 0) {
                    return Items.LEATHER_BOOTS;
                }
                else if (equipmentLevel == 1) {
                    return ModItems.COPPER_BOOTS;
                }
                else if (equipmentLevel == 2) {
                    return Items.IRON_BOOTS;
                }
                else if (equipmentLevel == 3) {
                    return Items.GOLDEN_BOOTS;
                }
                else if (equipmentLevel == 4) {
                    return Items.DIAMOND_BOOTS;
                }
            }
            default: {
                return null;
            }
        }
    }

    @Inject(method = "writeCustomData", at = @At("TAIL"))
    private void injectedWriteCustomDataToNbt(WriteView view, CallbackInfo cbi) {
        view.putBoolean("Entranced", this.entranced);
        view.putInt("EntrancedTime", this.entrancedTime);
    }
    
    @Inject(method = "readCustomData", at = @At("TAIL"))
    private void injectedReadCustomDataFromNbt(ReadView view, CallbackInfo cbi) {
        this.entranced = view.getBoolean("Entranced", false);
        this.entrancedTime = view.getInt("EntrancedTime", 0);
        ((MobEntity)(Object)this).setAiDisabled(this.entranced);
    }

    @Override
    public boolean getEntranced() {
        return this.entranced;
    }

    @Override
    public void setEntranced(boolean entranced, int duration) {
        this.entranced = entranced;
        this.entrancedTime = duration;
        ((MobEntity)(Object)this).setAiDisabled(entranced);
        if (((MobEntity)(Object)this) instanceof Angerable && entranced) {
            ((Angerable)this).stopAnger();
        }
    }

    @Override
    public int getEntrancedTime() {
        return this.entrancedTime;
    }

    @Unique
    private ItemStack getRandomlyDyedClothing(Item dyeableArmorItem) {
        if (dyeableArmorItem.getDefaultStack().isIn(ItemTags.DYEABLE)) {
            ItemStack itemStack = new ItemStack(dyeableArmorItem);
            ArrayList<DyeItem> list = Lists.newArrayList();
            list.add(ModVillagerProfessions.SellDyedItemFactory.getDye(this.random));
            if (this.random.nextFloat() > 0.7f) {
                list.add(ModVillagerProfessions.SellDyedItemFactory.getDye(this.random));
            }
            if (this.random.nextFloat() > 0.8f) {
                list.add(ModVillagerProfessions.SellDyedItemFactory.getDye(this.random));
            }
            itemStack = DyedColorComponent.setColor(itemStack, list);
            return itemStack;
        }
        return ItemStack.EMPTY;
    }



    
}

