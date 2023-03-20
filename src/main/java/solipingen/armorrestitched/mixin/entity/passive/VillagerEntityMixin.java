package solipingen.armorrestitched.mixin.entity.passive;

import java.util.ArrayList;
import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.InteractionObserver;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.DyeItem;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerData;
import net.minecraft.village.VillagerDataContainer;
import net.minecraft.village.VillagerProfession;
import net.minecraft.village.TradeOffers.BuyForOneEmeraldFactory;
import net.minecraft.village.TradeOffers.Factory;
import net.minecraft.village.TradeOffers.SellEnchantedToolFactory;
import net.minecraft.village.TradeOffers.SellItemFactory;
import net.minecraft.world.World;
import solipingen.armorrestitched.item.ModItems;


@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends MerchantEntity implements InteractionObserver, VillagerDataContainer {

    @Invoker("sayNo")
    public abstract void invokeSayNo();


    public VillagerEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }
    

    @Inject(method = "interactMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/VillagerEntity;isBaby()Z"), cancellable = true)
    private void injectedInteractMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cbireturn) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.getItem() instanceof ArmorItem && !this.isSleeping()) {
            EquipmentSlot slot = MobEntity.getPreferredEquipmentSlot(itemStack);
            ItemStack itemStack2 = this.getEquippedStack(slot);
            if (itemStack2.isEmpty() || (itemStack2.getItem() instanceof ArmorItem && ((ArmorItem)itemStack2.getItem()).getProtection() <= ((ArmorItem)itemStack.getItem()).getProtection())) {
                this.equipStack(slot, itemStack);
                player.setStackInHand(hand, ItemUsage.exchangeStack(itemStack, player, itemStack2));
                this.playSound(SoundEvents.ENTITY_VILLAGER_YES, this.getSoundVolume(), this.getSoundPitch());
            }
            else {
                this.invokeSayNo();
            }
            player.incrementStat(Stats.TALKED_TO_VILLAGER);
            cbireturn.setReturnValue(ActionResult.success(this.world.isClient));
        }
    }

    @ModifyVariable(method = "fillRecipes", at = @At("STORE"), ordinal = 0)
    private Int2ObjectMap<TradeOffers.Factory[]> modifiedFilledRecipes(Int2ObjectMap<TradeOffers.Factory[]> int2ObjectMap) {
        VillagerData villagerData = this.getVillagerData();
        Map<VillagerProfession, Int2ObjectMap<Factory[]>> tradeOffers = TradeOffers.PROFESSION_TO_LEVELED_TRADE;
        if (villagerData.getProfession() == VillagerProfession.ARMORER) {
            VillagerEntityMixin.replaceArmorerProfessionToLeveledTrade(tradeOffers);
        }
        else if (villagerData.getProfession() == VillagerProfession.LEATHERWORKER) {
            VillagerEntityMixin.replaceLeatherworkerProfessionToLeveledTrade(tradeOffers, this);
        }
        return tradeOffers.get(villagerData.getProfession());
    }

    private static void replaceArmorerProfessionToLeveledTrade(Map<VillagerProfession, Int2ObjectMap<Factory[]>> originalTradeOffers) {
        originalTradeOffers.replace(VillagerProfession.ARMORER, VillagerEntityMixin.copyToFastUtilMap(
            ImmutableMap.of(
                1, new Factory[]{new BuyForOneEmeraldFactory(Items.COAL, 15, 16, 2), new BuyForOneEmeraldFactory(Items.CHARCOAL, 15, 16, 2), 
                    new BuyForOneEmeraldFactory(Items.STICK, 60, 16, 2), new BuyForOneEmeraldFactory(Items.TINTED_GLASS, 2, 16, 2), 
                    new BuyForOneEmeraldFactory(Items.SMOOTH_STONE_SLAB, 8, 16, 2)}, 
                2, new Factory[]{new BuyForOneEmeraldFactory(Items.COPPER_INGOT, 8, 12, 10), new BuyForOneEmeraldFactory(Items.LAVA_BUCKET, 1, 12, 10), 
                    new SellItemFactory(ModItems.COPPER_HELMET, 2, 1, 5), new SellItemFactory(ModItems.COPPER_CHESTPLATE, 4, 1, 5), 
                    new SellItemFactory(ModItems.COPPER_LEGGINGS, 3, 1, 5), new SellItemFactory(ModItems.COPPER_BOOTS, 2, 1, 5), 
                    new SellItemFactory(ModItems.COPPER_HORSE_ARMOR, 6, 1, 3, 5)}, 
                3, new Factory[]{new BuyForOneEmeraldFactory(Items.GOLD_INGOT, 2, 12, 20), 
                    new SellItemFactory(Items.GOLDEN_HELMET, 5, 1, 10), new SellItemFactory(Items.GOLDEN_CHESTPLATE, 9, 1, 10), 
                    new SellItemFactory(Items.GOLDEN_LEGGINGS, 7, 1, 10), new SellItemFactory(Items.GOLDEN_BOOTS, 4, 1, 10), 
                    new SellItemFactory(Items.GOLDEN_HORSE_ARMOR, 12, 1, 3, 10), 
                    new SellItemFactory(Items.CHAINMAIL_HELMET, 2, 1, 10), new SellItemFactory(Items.CHAINMAIL_CHESTPLATE, 4, 1, 10), 
                    new SellItemFactory(Items.CHAINMAIL_LEGGINGS, 3, 1, 10), new SellItemFactory(Items.CHAINMAIL_BOOTS, 2, 1, 10)}, 
                4, new Factory[]{new BuyForOneEmeraldFactory(Items.IRON_INGOT, 4, 12, 30), 
                    new SellItemFactory(Items.IRON_HELMET, 5, 1, 15), new SellItemFactory(Items.IRON_CHESTPLATE, 9, 1, 15), 
                    new SellItemFactory(Items.IRON_LEGGINGS, 7, 1, 15), new SellItemFactory(Items.IRON_BOOTS, 4, 1, 15), 
                    new SellItemFactory(Items.IRON_HORSE_ARMOR, 10, 1, 3, 15)}, 
                5, new Factory[]{new BuyForOneEmeraldFactory(Items.DIAMOND, 1, 12, 30), 
                    new SellEnchantedToolFactory(Items.DIAMOND_HELMET, 13, 3, 15), new SellEnchantedToolFactory(Items.DIAMOND_CHESTPLATE, 21, 3, 15), 
                    new SellEnchantedToolFactory(Items.DIAMOND_LEGGINGS, 19, 3, 15), new SellEnchantedToolFactory(Items.DIAMOND_BOOTS, 13, 3, 15), 
                    new SellItemFactory(Items.DIAMOND_HORSE_ARMOR, 18, 1, 3, 15)}
                )
            )
	    );
    }

    private static void replaceLeatherworkerProfessionToLeveledTrade(Map<VillagerProfession, Int2ObjectMap<Factory[]>> originalTradeOffers, LivingEntity villager) {
        ArrayList<DyeItem> dyeItemList = new ArrayList<DyeItem>();
        for (Item item : Registries.ITEM) {
            if (item instanceof DyeItem) {
                dyeItemList.add(((DyeItem)item));
            }
        }
        originalTradeOffers.replace(VillagerProfession.LEATHERWORKER, VillagerEntityMixin.copyToFastUtilMap(
            ImmutableMap.of(
                1, new Factory[]{new BuyForOneEmeraldFactory(Items.LEATHER, 6, 16, 2), new BuyForOneEmeraldFactory(Items.RABBIT_HIDE, 9, 16, 2), 
                    new SellItemFactory(Items.LEATHER_HELMET, 2, 1, 1), new SellItemFactory(Items.LEATHER_CHESTPLATE, 4, 1, 1), 
                    new SellItemFactory(Items.LEATHER_LEGGINGS, 3, 1, 1), new SellItemFactory(Items.LEATHER_BOOTS, 2, 1, 1)}, 
                2, new Factory[]{new BuyForOneEmeraldFactory(dyeItemList.get(villager.getRandom().nextInt(dyeItemList.size())), 4, 12, 10), 
                    new SellDyedItemFactory(Items.LEATHER_HELMET, 3, 12, 5, false), new SellDyedItemFactory(Items.LEATHER_CHESTPLATE, 5, 12, 5, false), 
                    new SellDyedItemFactory(Items.LEATHER_LEGGINGS, 4, 12, 5, false), new SellDyedItemFactory(Items.LEATHER_BOOTS, 3, 12, 5, false), 
                    new SellItemFactory(Items.ITEM_FRAME, 1, 8, 12, 5)}, 
                3, new Factory[]{new BuyForOneEmeraldFactory(Items.WATER_BUCKET, 1, 12, 20), new BuyForOneEmeraldFactory(Items.TRIPWIRE_HOOK, 2, 12, 20), 
                    new SellItemFactory(Items.SADDLE, 6, 1, 10)}, 
                4, new Factory[]{new BuyForOneEmeraldFactory(Items.SCUTE, 1, 12, 30), new BuyForOneEmeraldFactory(Items.FLINT, 13, 12, 30), 
                    new SellItemFactory(Items.TURTLE_HELMET, 9, 3, 15), new SellDyedItemFactory(Items.LEATHER_HORSE_ARMOR, 6, 12, 15, false)}, 
                5, new Factory[]{new SellDyedItemFactory(Items.LEATHER_HELMET, 4, 3, 15, true), 
                    new SellDyedItemFactory(Items.LEATHER_CHESTPLATE, 8, 3, 15, true), 
                    new SellDyedItemFactory(Items.LEATHER_HELMET, 6, 3, 15, true), 
                    new SellDyedItemFactory(Items.LEATHER_BOOTS, 4, 3, 15, true), 
                    new SellEnchantedToolFactory(Items.TURTLE_HELMET, 9, 3, 15)}
                )
            )
	    );
    }

    @Inject(method = "talkWithVillager", at = @At("TAIL"))
    private void injectedTalkWithVillager(ServerWorld world, VillagerEntity villager, long time, CallbackInfo cbi) {
        if (villager.getVillagerData().getProfession() == VillagerProfession.LEATHERWORKER && !this.isBaby()) {
            int leatherworkerLevel = villager.getVillagerData().getLevel();
            if (leatherworkerLevel >= 1) {
                if (this.getEquippedStack(EquipmentSlot.FEET).isEmpty()) {
                    this.equipStack(EquipmentSlot.FEET, this.getRandomlyDyedClothing(Items.LEATHER_BOOTS, false));
                }
            }
            if (leatherworkerLevel >= 2) {
                if (this.getEquippedStack(EquipmentSlot.HEAD).isEmpty()) {
                    this.equipStack(EquipmentSlot.HEAD, this.getRandomlyDyedClothing(Items.LEATHER_HELMET, false));
                }
            }
            if (leatherworkerLevel >= 3) {
                if (this.getEquippedStack(EquipmentSlot.LEGS).isEmpty()) {
                    this.equipStack(EquipmentSlot.LEGS, this.getRandomlyDyedClothing(Items.LEATHER_LEGGINGS, false));
                }
            }
            if (leatherworkerLevel >= 4) {
                if (this.getEquippedStack(EquipmentSlot.CHEST).isEmpty()) {
                    this.equipStack(EquipmentSlot.CHEST, this.getRandomlyDyedClothing(Items.LEATHER_CHESTPLATE, false));
                }
            }
            if (leatherworkerLevel == 5) {
                for (EquipmentSlot slot : EquipmentSlot.values()) {
                    if (slot.getType() != EquipmentSlot.Type.ARMOR) continue;
                    if (this.getEquippedStack(slot).isEmpty() || !this.getEquippedStack(slot).hasEnchantments()) {
                        if (slot == EquipmentSlot.HEAD) {
                            this.equipStack(EquipmentSlot.HEAD, this.getRandomlyDyedClothing(Items.LEATHER_HELMET, true));
                        }
                        else if (slot == EquipmentSlot.CHEST) {
                            this.equipStack(EquipmentSlot.CHEST, this.getRandomlyDyedClothing(Items.LEATHER_CHESTPLATE, true));
                        }
                        else if (slot == EquipmentSlot.LEGS) {
                            this.equipStack(EquipmentSlot.LEGS, this.getRandomlyDyedClothing(Items.LEATHER_LEGGINGS, true));
                        }
                        else if (slot == EquipmentSlot.FEET) {
                            this.equipStack(EquipmentSlot.FEET, this.getRandomlyDyedClothing(Items.LEATHER_BOOTS, true));
                        }
                    }
                    this.setEquipmentDropChance(slot, 0.0f);
                }
            }
        }
        else if (villager.getVillagerData().getProfession() == VillagerProfession.ARMORER && !this.isBaby()) {
            int armorerLevel = villager.getVillagerData().getLevel();
            if (armorerLevel >= 2) {
                for (EquipmentSlot slot : EquipmentSlot.values()) {
                    if (slot.getType() != EquipmentSlot.Type.ARMOR) continue;
                    if (this.random.nextFloat() >= 0.2f*this.world.getDifficulty().getId()) continue;
                    if (slot == EquipmentSlot.HEAD) {
                        ItemStack itemStack = new ItemStack(ModItems.COPPER_HELMET);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getItem().getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.HEAD, itemStack);
                    }
                    else if (slot == EquipmentSlot.CHEST) {
                        ItemStack itemStack = new ItemStack(ModItems.COPPER_CHESTPLATE);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getItem().getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.CHEST, itemStack);
                    }
                    else if (slot == EquipmentSlot.LEGS) {
                        ItemStack itemStack = new ItemStack(ModItems.COPPER_LEGGINGS);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getItem().getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.LEGS, itemStack);
                    }
                    else if (slot == EquipmentSlot.FEET) {
                        ItemStack itemStack = new ItemStack(ModItems.COPPER_BOOTS);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getItem().getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.FEET, itemStack);
                    }
                    this.setEquipmentDropChance(slot, 0.0f);
                }
            }
            if (armorerLevel >= 3) {
                for (EquipmentSlot slot : EquipmentSlot.values()) {
                    if (slot.getType() != EquipmentSlot.Type.ARMOR) continue;
                    if (this.random.nextFloat() >= 0.15f*this.world.getDifficulty().getId()) continue;
                    if (slot == EquipmentSlot.HEAD) {
                        ItemStack itemStack = new ItemStack(Items.CHAINMAIL_HELMET);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getItem().getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.HEAD, itemStack);
                    }
                    else if (slot == EquipmentSlot.CHEST) {
                        ItemStack itemStack = new ItemStack(Items.CHAINMAIL_CHESTPLATE);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getItem().getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.CHEST, itemStack);
                    }
                    else if (slot == EquipmentSlot.LEGS) {
                        ItemStack itemStack = new ItemStack(Items.CHAINMAIL_LEGGINGS);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getItem().getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.LEGS, itemStack);
                    }
                    else if (slot == EquipmentSlot.FEET) {
                        ItemStack itemStack = new ItemStack(Items.CHAINMAIL_BOOTS);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getItem().getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.FEET, itemStack);
                    }
                    this.setEquipmentDropChance(slot, 0.0f);
                }
            }
            if (armorerLevel >= 4) {
                for (EquipmentSlot slot : EquipmentSlot.values()) {
                    if (slot.getType() != EquipmentSlot.Type.ARMOR) continue;
                    if (this.random.nextFloat() >= 0.1f*this.world.getDifficulty().getId()) continue;
                    if (slot == EquipmentSlot.HEAD) {
                        ItemStack itemStack = new ItemStack(Items.IRON_HELMET);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getItem().getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.HEAD, itemStack);
                    }
                    else if (slot == EquipmentSlot.CHEST) {
                        ItemStack itemStack = new ItemStack(Items.IRON_CHESTPLATE);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getItem().getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.CHEST, itemStack);
                    }
                    else if (slot == EquipmentSlot.LEGS) {
                        ItemStack itemStack = new ItemStack(Items.IRON_LEGGINGS);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getItem().getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.LEGS, itemStack);
                    }
                    else if (slot == EquipmentSlot.FEET) {
                        ItemStack itemStack = new ItemStack(Items.IRON_BOOTS);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getItem().getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.FEET, itemStack);
                    }
                    this.setEquipmentDropChance(slot, 0.0f);
                }
            }
            if (armorerLevel >= 5) {
                for (EquipmentSlot slot : EquipmentSlot.values()) {
                    if (slot.getType() != EquipmentSlot.Type.ARMOR) continue;
                    if (this.random.nextFloat() >= 0.05f*this.world.getDifficulty().getId()) continue;
                    if (slot == EquipmentSlot.HEAD) {
                        ItemStack itemStack = new ItemStack(Items.DIAMOND_HELMET);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getItem().getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.HEAD, itemStack);
                    }
                    else if (slot == EquipmentSlot.CHEST) {
                        ItemStack itemStack = new ItemStack(Items.DIAMOND_CHESTPLATE);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getItem().getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.CHEST, itemStack);
                    }
                    else if (slot == EquipmentSlot.LEGS) {
                        ItemStack itemStack = new ItemStack(Items.DIAMOND_LEGGINGS);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getItem().getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.LEGS, itemStack);
                    }
                    else if (slot == EquipmentSlot.FEET) {
                        ItemStack itemStack = new ItemStack(Items.DIAMOND_BOOTS);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getItem().getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.FEET, itemStack);
                    }
                    this.setEquipmentDropChance(slot, 0.0f);
                }
            }
        }
    }

    private ItemStack getRandomlyDyedClothing(Item dyeableArmorItem, boolean enchanted) {
        if (dyeableArmorItem instanceof DyeableArmorItem) {
            int i = 5 + random.nextInt(15);
            ItemStack itemStack = new ItemStack(dyeableArmorItem);
            ArrayList<DyeItem> list = Lists.newArrayList();
            list.add(SellDyedItemFactory.getDye(this.random));
            if (random.nextFloat() > 0.7f) {
                list.add(SellDyedItemFactory.getDye(this.random));
            }
            if (random.nextFloat() > 0.8f) {
                list.add(SellDyedItemFactory.getDye(this.random));
            }
            itemStack = DyeableItem.blendAndSetColor(itemStack, list);
            if (enchanted) {
                itemStack = EnchantmentHelper.enchant(this.random, itemStack, i, false);
            }
            return itemStack;
        }
        return ItemStack.EMPTY;
    }

    private static Int2ObjectMap<Factory[]> copyToFastUtilMap(ImmutableMap<Integer, Factory[]> map) {
        return new Int2ObjectOpenHashMap<Factory[]>(map);
    }


    public static class SellDyedItemFactory
    implements Factory {
        private final ItemStack armor;
        private final int basePrice;
        private final int maxUses;
        private final int experience;
        private final float multiplier;
        private final boolean enchanted;


        public SellDyedItemFactory(Item item, int basePrice, int maxUses, int experience, boolean enchanted) {
            this(item, basePrice, maxUses, experience, 0.05f, enchanted);
        }

        public SellDyedItemFactory(Item item, int basePrice, int maxUses, int experience, float multiplier, boolean enchanted) {
            this.armor = new ItemStack(item);
            this.basePrice = basePrice;
            this.maxUses = maxUses;
            this.experience = experience;
            this.multiplier = multiplier;
            this.enchanted = enchanted;
        }

        @Override
        public TradeOffer create(Entity entity, Random random) {
            int i = 5 + random.nextInt(15);
            ItemStack itemStack = this.armor;
            if (itemStack.getItem() instanceof DyeableItem) {
                ArrayList<DyeItem> list = Lists.newArrayList();
                list.add(SellDyedItemFactory.getDye(random));
                if (random.nextFloat() > 0.7f) {
                    list.add(SellDyedItemFactory.getDye(random));
                }
                if (random.nextFloat() > 0.8f) {
                    list.add(SellDyedItemFactory.getDye(random));
                }
                itemStack = DyeableItem.blendAndSetColor(itemStack, list);
            }
            if (this.enchanted) {
                itemStack = EnchantmentHelper.enchant(random, itemStack, i, false);
            }
            int j = this.basePrice;
            if (this.enchanted) {
                j = Math.min(this.basePrice + i, 64);
            }
            ItemStack itemStack2 = new ItemStack(Items.EMERALD, j);
            return new TradeOffer(itemStack2, itemStack, this.maxUses, this.experience, this.multiplier);
        }
    
        private static DyeItem getDye(Random random) {
            return DyeItem.byColor(DyeColor.byId(random.nextInt(16)));
        }

    }

    
}

