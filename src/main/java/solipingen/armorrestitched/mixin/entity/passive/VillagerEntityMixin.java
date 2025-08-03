package solipingen.armorrestitched.mixin.entity.passive;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.equipment.trim.ArmorTrim;
import net.minecraft.item.equipment.trim.ArmorTrimMaterials;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.registry.tag.ItemTags;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.InteractionObserver;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerData;
import net.minecraft.village.VillagerDataContainer;
import net.minecraft.village.VillagerGossips;
import net.minecraft.village.VillagerProfession;
import net.minecraft.village.TradeOffers.Factory;
import net.minecraft.world.World;
import solipingen.armorrestitched.item.ModItems;
import solipingen.armorrestitched.item.equipment.ModEquipmentAssetKeys;
import solipingen.armorrestitched.village.ModVillagerProfessions;


@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends MerchantEntity implements InteractionObserver, VillagerDataContainer {
    @Shadow @Final private VillagerGossips gossip;


    public VillagerEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "interactMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/VillagerEntity;isBaby()Z"), cancellable = true)
    private void injectedInteractMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cbireturn) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.get(DataComponentTypes.EQUIPPABLE) != null && itemStack.get(DataComponentTypes.ATTRIBUTE_MODIFIERS) != null
                && itemStack.get(DataComponentTypes.ATTRIBUTE_MODIFIERS).modifiers().stream().anyMatch(entry -> entry.attribute() == EntityAttributes.ARMOR) && !this.isSleeping()) {
            EquipmentSlot slot = this.getPreferredEquipmentSlot(itemStack);
            ItemStack itemStack2 = this.getEquippedStack(slot);
            this.equipStack(slot, itemStack.copy());
            player.setStackInHand(hand, ItemUsage.exchangeStack(itemStack, player, itemStack2));
            this.playSound(SoundEvents.ENTITY_VILLAGER_YES, this.getSoundVolume(), this.getSoundPitch());
            player.incrementStat(Stats.TALKED_TO_VILLAGER);
            cbireturn.setReturnValue(ActionResult.SUCCESS);
        }
    }

    @ModifyVariable(method = "fillRecipes", at = @At("STORE"), ordinal = 0)
    private Int2ObjectMap<TradeOffers.Factory[]> modifiedFilledRecipes(Int2ObjectMap<TradeOffers.Factory[]> int2ObjectMap) {
        VillagerData villagerData = this.getVillagerData();
        Map<RegistryKey<VillagerProfession>, Int2ObjectMap<Factory[]>> tradeOffers = TradeOffers.PROFESSION_TO_LEVELED_TRADE;
        if (villagerData.profession().matchesKey(VillagerProfession.ARMORER)) {
            ModVillagerProfessions.replaceArmorerProfessionToLeveledTrade(tradeOffers);
            if (this.getWorld().getEnabledFeatures().contains(FeatureFlags.TRADE_REBALANCE)) {
                ModVillagerProfessions.replaceRebalancedArmorerProfessionToLeveledTrade(tradeOffers);
            }
        }
        else if (villagerData.profession().matchesKey(VillagerProfession.LEATHERWORKER)) {
            ModVillagerProfessions.replaceLeatherworkerProfessionToLeveledTrade(tradeOffers, this);
        }
        else if (villagerData.profession().matchesKey(VillagerProfession.SHEPHERD)) {
            ModVillagerProfessions.replaceShepherdProfessionToLeveledTrade(tradeOffers, this);
        }
        else if (villagerData.profession().value() == ModVillagerProfessions.DRESSER) {
            ModVillagerProfessions.putDresserTradeOffers(tradeOffers, this);
        }
        return tradeOffers.get(villagerData.profession().getKey().orElseThrow());
    }

    @Inject(method = "talkWithVillager", at = @At("TAIL"))
    private void injectedTalkWithVillager(ServerWorld world, VillagerEntity villager, long time, CallbackInfo cbi) {
        if (villager.getVillagerData().profession().matchesKey(VillagerProfession.LEATHERWORKER) && !this.isBaby()) {
            int leatherworkerLevel = villager.getVillagerData().level();
            if (leatherworkerLevel >= 1) {
                if (this.getEquippedStack(EquipmentSlot.FEET).isEmpty()) {
                    this.equipStack(EquipmentSlot.FEET, this.getRandomlyDyedClothing(this.random.nextBoolean() ? Items.LEATHER_BOOTS : ModItems.FUR_BOOTS, false));
                }
            }
            if (leatherworkerLevel >= 2) {
                if (this.getEquippedStack(EquipmentSlot.HEAD).isEmpty()) {
                    this.equipStack(EquipmentSlot.HEAD, this.getRandomlyDyedClothing(this.random.nextBoolean() ? Items.LEATHER_HELMET : ModItems.FUR_HELMET, false));
                }
            }
            if (leatherworkerLevel >= 3) {
                if (this.getEquippedStack(EquipmentSlot.LEGS).isEmpty()) {
                    this.equipStack(EquipmentSlot.LEGS, this.getRandomlyDyedClothing(this.random.nextBoolean() ? Items.LEATHER_LEGGINGS : ModItems.FUR_LEGGINGS, false));
                }
            }
            if (leatherworkerLevel >= 4) {
                if (this.getEquippedStack(EquipmentSlot.CHEST).isEmpty()) {
                    this.equipStack(EquipmentSlot.CHEST, this.getRandomlyDyedClothing(this.random.nextBoolean() ? Items.LEATHER_CHESTPLATE : ModItems.FUR_CHESTPLATE, false));
                }
            }
            if (leatherworkerLevel == 5) {
                for (EquipmentSlot slot : EquipmentSlot.values()) {
                    if (!slot.isArmorSlot()) continue;
                    if (this.getEquippedStack(slot).isEmpty() || !this.getEquippedStack(slot).hasEnchantments()) {
                        if (slot == EquipmentSlot.HEAD) {
                            this.equipStack(EquipmentSlot.HEAD, this.getRandomlyDyedClothing(this.random.nextBoolean() ? Items.LEATHER_HELMET : ModItems.FUR_HELMET, true));
                        }
                        else if (slot == EquipmentSlot.CHEST) {
                            this.equipStack(EquipmentSlot.CHEST, this.getRandomlyDyedClothing(this.random.nextBoolean() ? Items.LEATHER_CHESTPLATE : ModItems.FUR_CHESTPLATE, true));
                        }
                        else if (slot == EquipmentSlot.LEGS) {
                            this.equipStack(EquipmentSlot.LEGS, this.getRandomlyDyedClothing(this.random.nextBoolean() ? Items.LEATHER_LEGGINGS : ModItems.FUR_LEGGINGS, true));
                        }
                        else if (slot == EquipmentSlot.FEET) {
                            this.equipStack(EquipmentSlot.FEET, this.getRandomlyDyedClothing(this.random.nextBoolean() ? Items.LEATHER_BOOTS : ModItems.FUR_BOOTS, true));
                        }
                    }
                    this.setEquipmentDropChance(slot, 0.0f);
                }
            }
        }
        else if (villager.getVillagerData().profession().matchesKey(VillagerProfession.SHEPHERD) && !this.isBaby()) {
            int shepherdLevel = villager.getVillagerData().level();
            if (shepherdLevel >= 1) {
                if (this.getEquippedStack(EquipmentSlot.FEET).isEmpty()) {
                    this.equipStack(EquipmentSlot.FEET, this.getRandomlyDyedClothing(this.random.nextBoolean() ? ModItems.WOOL_BOOTS : ModItems.SILK_BOOTS, false));
                }
            }
            if (shepherdLevel >= 2) {
                if (this.getEquippedStack(EquipmentSlot.HEAD).isEmpty()) {
                    this.equipStack(EquipmentSlot.HEAD, this.getRandomlyDyedClothing(this.random.nextBoolean() ? ModItems.WOOL_HELMET : ModItems.SILK_HELMET, false));
                }
            }
            if (shepherdLevel >= 3) {
                if (this.getEquippedStack(EquipmentSlot.LEGS).isEmpty()) {
                    this.equipStack(EquipmentSlot.LEGS, this.getRandomlyDyedClothing(this.random.nextBoolean() ? ModItems.WOOL_LEGGINGS : ModItems.SILK_LEGGINGS, false));
                }
            }
            if (shepherdLevel >= 4) {
                if (this.getEquippedStack(EquipmentSlot.CHEST).isEmpty()) {
                    this.equipStack(EquipmentSlot.CHEST, this.getRandomlyDyedClothing(this.random.nextBoolean() ? ModItems.WOOL_CHESTPLATE : ModItems.SILK_CHESTPLATE, false));
                }
            }
            if (shepherdLevel == 5) {
                for (EquipmentSlot slot : EquipmentSlot.values()) {
                    if (!slot.isArmorSlot()) continue;
                    if (this.getEquippedStack(slot).isEmpty() || !this.getEquippedStack(slot).hasEnchantments()) {
                        if (slot == EquipmentSlot.HEAD) {
                            this.equipStack(EquipmentSlot.HEAD, this.getRandomlyDyedClothing(this.random.nextBoolean() ? ModItems.WOOL_HELMET : ModItems.SILK_HELMET, true));
                        }
                        else if (slot == EquipmentSlot.CHEST) {
                            this.equipStack(EquipmentSlot.CHEST, this.getRandomlyDyedClothing(this.random.nextBoolean() ? ModItems.WOOL_CHESTPLATE : ModItems.SILK_CHESTPLATE, true));
                        }
                        else if (slot == EquipmentSlot.LEGS) {
                            this.equipStack(EquipmentSlot.LEGS, this.getRandomlyDyedClothing(this.random.nextBoolean() ? ModItems.WOOL_LEGGINGS : ModItems.SILK_LEGGINGS, true));
                        }
                        else if (slot == EquipmentSlot.FEET) {
                            this.equipStack(EquipmentSlot.FEET, this.getRandomlyDyedClothing(this.random.nextBoolean() ? ModItems.WOOL_BOOTS : ModItems.SILK_BOOTS, true));
                        }
                    }
                    this.setEquipmentDropChance(slot, 0.0f);
                }
            }
        }
        else if (villager.getVillagerData().profession().value() == ModVillagerProfessions.DRESSER && !this.isBaby()) {
            int weaverLevel = villager.getVillagerData().level();
            if (weaverLevel >= 1) {
                if (this.getEquippedStack(EquipmentSlot.FEET).isEmpty()) {
                    this.equipStack(EquipmentSlot.FEET, this.getRandomlyDyedClothing(this.random.nextBoolean() ? ModItems.COTTON_BOOTS : ModItems.LINEN_BOOTS, false));
                }
            }
            if (weaverLevel >= 2) {
                if (this.getEquippedStack(EquipmentSlot.HEAD).isEmpty()) {
                    this.equipStack(EquipmentSlot.HEAD, this.getRandomlyDyedClothing(this.random.nextBoolean() ? ModItems.COTTON_HELMET : ModItems.LINEN_HELMET, false));
                }
            }
            if (weaverLevel >= 3) {
                if (this.getEquippedStack(EquipmentSlot.LEGS).isEmpty()) {
                    this.equipStack(EquipmentSlot.LEGS, this.getRandomlyDyedClothing(this.random.nextBoolean() ? ModItems.COTTON_LEGGINGS : ModItems.LINEN_LEGGINGS, false));
                }
            }
            if (weaverLevel >= 4) {
                if (this.getEquippedStack(EquipmentSlot.CHEST).isEmpty()) {
                    this.equipStack(EquipmentSlot.CHEST, this.getRandomlyDyedClothing(this.random.nextBoolean() ? ModItems.COTTON_CHESTPLATE : ModItems.LINEN_CHESTPLATE, false));
                }
            }
            if (weaverLevel == 5) {
                for (EquipmentSlot slot : EquipmentSlot.values()) {
                    if (!slot.isArmorSlot()) continue;
                    if (this.getEquippedStack(slot).isEmpty() || !this.getEquippedStack(slot).hasEnchantments()) {
                        if (slot == EquipmentSlot.HEAD) {
                            this.equipStack(EquipmentSlot.HEAD, this.getRandomlyDyedClothing(this.random.nextBoolean() ? ModItems.COTTON_HELMET : ModItems.LINEN_HELMET, true));
                        }
                        else if (slot == EquipmentSlot.CHEST) {
                            this.equipStack(EquipmentSlot.CHEST, this.getRandomlyDyedClothing(this.random.nextBoolean() ? ModItems.COTTON_CHESTPLATE : ModItems.LINEN_CHESTPLATE, true));
                        }
                        else if (slot == EquipmentSlot.LEGS) {
                            this.equipStack(EquipmentSlot.LEGS, this.getRandomlyDyedClothing(this.random.nextBoolean() ? ModItems.COTTON_LEGGINGS : ModItems.LINEN_LEGGINGS, true));
                        }
                        else if (slot == EquipmentSlot.FEET) {
                            this.equipStack(EquipmentSlot.FEET, this.getRandomlyDyedClothing(this.random.nextBoolean() ? ModItems.COTTON_BOOTS : ModItems.LINEN_BOOTS, true));
                        }
                    }
                    this.setEquipmentDropChance(slot, 0.0f);
                }
            }
        }
        else if (villager.getVillagerData().profession().matchesKey(VillagerProfession.ARMORER) && !this.isBaby()) {
            int armorerLevel = villager.getVillagerData().level();
            if (armorerLevel >= 2) {
                for (EquipmentSlot slot : EquipmentSlot.values()) {
                    if (!slot.isArmorSlot()) continue;
                    if (this.random.nextFloat() >= 0.2f*this.getWorld().getDifficulty().getId()) continue;
                    if (slot == EquipmentSlot.HEAD) {
                        ItemStack itemStack = new ItemStack(ModItems.COPPER_HELMET);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.HEAD, itemStack);
                    }
                    else if (slot == EquipmentSlot.CHEST) {
                        ItemStack itemStack = new ItemStack(ModItems.COPPER_CHESTPLATE);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.CHEST, itemStack);
                    }
                    else if (slot == EquipmentSlot.LEGS) {
                        ItemStack itemStack = new ItemStack(ModItems.COPPER_LEGGINGS);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.LEGS, itemStack);
                    }
                    else if (slot == EquipmentSlot.FEET) {
                        ItemStack itemStack = new ItemStack(ModItems.COPPER_BOOTS);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.FEET, itemStack);
                    }
                    this.setEquipmentDropChance(slot, 0.0f);
                }
            }
            if (armorerLevel >= 3) {
                for (EquipmentSlot slot : EquipmentSlot.values()) {
                    if (!slot.isArmorSlot()) continue;
                    if (this.random.nextFloat() >= 0.15f*this.getWorld().getDifficulty().getId()) continue;
                    if (slot == EquipmentSlot.HEAD) {
                        ItemStack itemStack = new ItemStack(Items.CHAINMAIL_HELMET);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.HEAD, itemStack);
                    }
                    else if (slot == EquipmentSlot.CHEST) {
                        ItemStack itemStack = new ItemStack(Items.CHAINMAIL_CHESTPLATE);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.CHEST, itemStack);
                    }
                    else if (slot == EquipmentSlot.LEGS) {
                        ItemStack itemStack = new ItemStack(Items.CHAINMAIL_LEGGINGS);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.LEGS, itemStack);
                    }
                    else if (slot == EquipmentSlot.FEET) {
                        ItemStack itemStack = new ItemStack(Items.CHAINMAIL_BOOTS);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.FEET, itemStack);
                    }
                    this.setEquipmentDropChance(slot, 0.0f);
                }
            }
            if (armorerLevel >= 4) {
                for (EquipmentSlot slot : EquipmentSlot.values()) {
                    if (!slot.isArmorSlot()) continue;
                    if (this.random.nextFloat() >= 0.1f*this.getWorld().getDifficulty().getId()) continue;
                    if (slot == EquipmentSlot.HEAD) {
                        ItemStack itemStack = new ItemStack(Items.IRON_HELMET);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.HEAD, itemStack);
                    }
                    else if (slot == EquipmentSlot.CHEST) {
                        ItemStack itemStack = new ItemStack(Items.IRON_CHESTPLATE);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.CHEST, itemStack);
                    }
                    else if (slot == EquipmentSlot.LEGS) {
                        ItemStack itemStack = new ItemStack(Items.IRON_LEGGINGS);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.LEGS, itemStack);
                    }
                    else if (slot == EquipmentSlot.FEET) {
                        ItemStack itemStack = new ItemStack(Items.IRON_BOOTS);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.FEET, itemStack);
                    }
                    this.setEquipmentDropChance(slot, 0.0f);
                }
            }
            if (armorerLevel >= 5) {
                for (EquipmentSlot slot : EquipmentSlot.values()) {
                    if (!slot.isArmorSlot()) continue;
                    if (this.random.nextFloat() >= 0.05f*this.getWorld().getDifficulty().getId()) continue;
                    if (slot == EquipmentSlot.HEAD) {
                        ItemStack itemStack = new ItemStack(Items.DIAMOND_HELMET);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.HEAD, itemStack);
                    }
                    else if (slot == EquipmentSlot.CHEST) {
                        ItemStack itemStack = new ItemStack(Items.DIAMOND_CHESTPLATE);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.CHEST, itemStack);
                    }
                    else if (slot == EquipmentSlot.LEGS) {
                        ItemStack itemStack = new ItemStack(Items.DIAMOND_LEGGINGS);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.LEGS, itemStack);
                    }
                    else if (slot == EquipmentSlot.FEET) {
                        ItemStack itemStack = new ItemStack(Items.DIAMOND_BOOTS);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.FEET, itemStack);
                    }
                    this.setEquipmentDropChance(slot, 0.0f);
                }
            }
        }
    }

    @Inject(method = "getReputation", at = @At("HEAD"), cancellable = true)
    private void injectedGetReputation(PlayerEntity player, CallbackInfoReturnable<Integer> cbireturn) {
        int reputation = this.gossip.getReputationFor(player.getUuid(), gossipType -> true);
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (!slot.isArmorSlot()) continue;
            ItemStack equippedStack = player.getEquippedStack(slot);
            ArmorTrim trim = equippedStack.get(DataComponentTypes.TRIM);
            if (trim != null && trim.material().matchesKey(ArmorTrimMaterials.EMERALD)) {
                reputation += 1;
                reputation = MathHelper.ceil(1.25f*reputation);
            }
        }
        cbireturn.setReturnValue(reputation);
    }

    @Inject(method = "onStruckByLightning", at = @At("HEAD"), cancellable = true)
    private void injectedOnStruckByLightning(ServerWorld world, LightningEntity lightning, CallbackInfo cbi) {
        boolean bl = false;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (!slot.isArmorSlot()) continue;
            ItemStack equippedStack = this.getEquippedStack(slot);
            if (equippedStack.get(DataComponentTypes.EQUIPPABLE) != null && equippedStack.get(DataComponentTypes.EQUIPPABLE).assetId().get() == ModEquipmentAssetKeys.COPPER) {
                super.onStruckByLightning(world, lightning);
                bl = true;
            }
            ArmorTrim trim = equippedStack.get(DataComponentTypes.TRIM);
            if (trim == null) continue;
            if (trim.material().matchesKey(ArmorTrimMaterials.COPPER)) {
                super.onStruckByLightning(world, lightning);
                bl = true;
            }
        }
        if (bl) {
            cbi.cancel();
        }
    }

    @Unique
    private ItemStack getRandomlyDyedClothing(Item dyeableArmorItem, boolean enchanted) {
        if (dyeableArmorItem.getDefaultStack().isIn(ItemTags.DYEABLE)) {
            int i = 5 + this.random.nextInt(15);
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
            if (enchanted) {
                DynamicRegistryManager dynamicRegistryManager = this.getWorld().getRegistryManager();
                Optional<RegistryEntryList.Named<Enchantment>> optional = dynamicRegistryManager.getOrThrow(RegistryKeys.ENCHANTMENT).getOptional(EnchantmentTags.ON_TRADED_EQUIPMENT);
                itemStack = EnchantmentHelper.enchant(this.random, itemStack, i, dynamicRegistryManager, optional);
            }
            return itemStack;
        }
        return ItemStack.EMPTY;
    }

    
}

