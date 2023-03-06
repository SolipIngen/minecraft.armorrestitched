package solipingen.armorrestitched.mixin.entity.passive;

import java.util.ArrayList;
import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
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
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.DyeItem;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
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
        if (itemStack.getItem() instanceof ArmorItem) {
            if (this.isBaby()) {
                this.invokeSayNo();
                cbireturn.setReturnValue(ActionResult.success(this.world.isClient));
            }
            else {
                EquipmentSlot slot = MobEntity.getPreferredEquipmentSlot(itemStack);
                ItemStack itemStack2 = this.getEquippedStack(slot);
                this.equipStack(slot, itemStack);
                player.setStackInHand(hand, ItemUsage.exchangeStack(itemStack, player, itemStack2));
                player.incrementStat(Stats.TALKED_TO_VILLAGER);
                cbireturn.setReturnValue(ActionResult.success(this.world.isClient));
            }
        }
    }

    @ModifyVariable(method = "fillRecipes", at = @At("STORE"), ordinal = 0)
    private Int2ObjectMap<TradeOffers.Factory[]> modifiedFilledRecipes(Int2ObjectMap<TradeOffers.Factory[]> int2ObjectMap) {
        VillagerData villagerData = this.getVillagerData();
        Map<VillagerProfession, Int2ObjectMap<Factory[]>> tradeOffers = TradeOffers.PROFESSION_TO_LEVELED_TRADE;
        if (villagerData.getProfession() == VillagerProfession.ARMORER) {
            replaceArmorerProfessionToLeveledTrade(tradeOffers);
        }
        else if (villagerData.getProfession() == VillagerProfession.LEATHERWORKER) {
            int dyeRandomInt = this.random.nextInt(16);
            replaceLeatherworkerProfessionToLeveledTrade(tradeOffers, dyeRandomInt);
        }
        return tradeOffers.get(villagerData.getProfession());
    }

    private static void replaceArmorerProfessionToLeveledTrade(Map<VillagerProfession, Int2ObjectMap<Factory[]>> originalTradeOffers) {
        originalTradeOffers.replace(VillagerProfession.ARMORER, copyToFastUtilMap(
            ImmutableMap.of(
                1, new Factory[]{new BuyForOneEmeraldFactory(Items.COAL, 15, 16, 2), new BuyForOneEmeraldFactory(Items.CHARCOAL, 15, 16, 2), 
                    new BuyForOneEmeraldFactory(Items.STICK, 60, 16, 2), new BuyForOneEmeraldFactory(Items.TINTED_GLASS, 2, 16, 2)}, 
                2, new Factory[]{new BuyForOneEmeraldFactory(Items.COPPER_INGOT, 8, 12, 10), 
                    new SellItemFactory(ModItems.COPPER_HELMET, 2, 1, 5), new SellItemFactory(ModItems.COPPER_CHESTPLATE, 4, 1, 5), 
                    new SellItemFactory(ModItems.COPPER_LEGGINGS, 3, 1, 5), new SellItemFactory(ModItems.COPPER_BOOTS, 2, 1, 5)}, 
                3, new Factory[]{new BuyForOneEmeraldFactory(Items.GOLD_INGOT, 2, 12, 20),  
                    new SellItemFactory(Items.GOLDEN_HELMET, 5, 1, 10), new SellItemFactory(Items.GOLDEN_CHESTPLATE, 9, 1, 10), 
                    new SellItemFactory(Items.GOLDEN_LEGGINGS, 7, 1, 10), new SellItemFactory(Items.GOLDEN_BOOTS, 4, 1, 10), 
                    new SellItemFactory(Items.CHAINMAIL_HELMET, 2, 1, 15), new SellItemFactory(Items.CHAINMAIL_CHESTPLATE, 4, 1, 15), 
                    new SellItemFactory(Items.CHAINMAIL_LEGGINGS, 3, 1, 15), new SellItemFactory(Items.CHAINMAIL_BOOTS, 2, 1, 15)}, 
                4, new Factory[]{new BuyForOneEmeraldFactory(Items.IRON_INGOT, 4, 12, 20), new BuyForOneEmeraldFactory(Items.LAVA_BUCKET, 1, 12, 30), new BuyForOneEmeraldFactory(Items.SCUTE, 5, 12, 30), 
                    new SellItemFactory(Items.IRON_HELMET, 5, 1, 10), new SellItemFactory(Items.IRON_CHESTPLATE, 9, 1, 10), 
                    new SellItemFactory(Items.IRON_LEGGINGS, 7, 1, 10), new SellItemFactory(Items.IRON_BOOTS, 4, 1, 10)}, 
                5, new Factory[]{new BuyForOneEmeraldFactory(Items.DIAMOND, 1, 12, 30), 
                    new SellEnchantedToolFactory(Items.DIAMOND_HELMET, 13, 3, 15), new SellEnchantedToolFactory(Items.DIAMOND_CHESTPLATE, 21, 3, 15), 
                    new SellEnchantedToolFactory(Items.DIAMOND_LEGGINGS, 19, 3, 15), new SellEnchantedToolFactory(Items.DIAMOND_BOOTS, 13, 3, 15)}
                )
            )
	    );
    }

    private static void replaceLeatherworkerProfessionToLeveledTrade(Map<VillagerProfession, Int2ObjectMap<Factory[]>> originalTradeOffers, int dyeRandomInt) {
        Item[] dyeItemList = new Item[]{Items.RED_DYE, Items.PINK_DYE, Items.ORANGE_DYE, Items.LIGHT_GRAY_DYE, Items.YELLOW_DYE, Items.BROWN_DYE, Items.GREEN_DYE, Items.LIME_DYE, Items.CYAN_DYE, Items.BLUE_DYE, Items.LIGHT_BLUE_DYE, Items.PURPLE_DYE, Items.MAGENTA_DYE, Items.LIGHT_GRAY_DYE, Items.GRAY_DYE, Items.BLACK_DYE, Items.WHITE_DYE};
        originalTradeOffers.replace(VillagerProfession.LEATHERWORKER, copyToFastUtilMap(
            ImmutableMap.of(
                1, new Factory[]{new BuyForOneEmeraldFactory(Items.LEATHER, 6, 16, 2), new BuyForOneEmeraldFactory(Items.RABBIT_HIDE, 9, 16, 2), 
                    new SellItemFactory(Items.LEATHER_HELMET, 2, 1, 1), new SellItemFactory(Items.LEATHER_CHESTPLATE, 4, 1, 1), 
                    new SellItemFactory(Items.LEATHER_LEGGINGS, 3, 1, 1), new SellItemFactory(Items.LEATHER_BOOTS, 2, 1, 1)}, 
                2, new Factory[]{new BuyForOneEmeraldFactory(dyeItemList[dyeRandomInt], 4, 12, 10), 
                    new SellDyedItemFactory(Items.LEATHER_HELMET, 3, 12, 5, false), new SellDyedItemFactory(Items.LEATHER_CHESTPLATE, 5, 12, 5, false), 
                    new SellDyedItemFactory(Items.LEATHER_LEGGINGS, 4, 12, 5, false), new SellDyedItemFactory(Items.LEATHER_BOOTS, 3, 12, 5, false), 
                    new SellItemFactory(Items.ITEM_FRAME, 1, 8, 12, 5)}, 
                3, new Factory[]{new BuyForOneEmeraldFactory(Items.BUCKET, 4, 12, 20), new BuyForOneEmeraldFactory(Items.TRIPWIRE_HOOK, 2, 12, 20), 
                    new SellItemFactory(Items.SADDLE, 6, 1, 10), 
                    new SellDyedItemFactory(Items.LEATHER_HORSE_ARMOR, 6, 12, 15, false)}, 
                4, new Factory[]{
                    new SellDyedItemFactory(Items.LEATHER_HELMET, 4, 3, 15, true), 
                    new SellDyedItemFactory(Items.LEATHER_CHESTPLATE, 8, 3, 15, true), 
                    new SellDyedItemFactory(Items.LEATHER_HELMET, 6, 3, 15, true), 
                    new SellDyedItemFactory(Items.LEATHER_BOOTS, 4, 3, 15, true)}, 
                5, new Factory[]{new SellItemFactory(ModItems.COPPER_HORSE_ARMOR, 11, 1, 3, 15), 
                    new SellItemFactory(Items.GOLDEN_HORSE_ARMOR, 17, 1, 3, 15), 
                    new SellItemFactory(Items.IRON_HORSE_ARMOR, 17, 1, 3, 15), 
                    new SellItemFactory(Items.DIAMOND_HORSE_ARMOR, 32, 1, 3, 15)}
                )
            )
	    );
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
                list.add(getDye(random));
                if (random.nextFloat() > 0.7f) {
                    list.add(getDye(random));
                }
                if (random.nextFloat() > 0.8f) {
                    list.add(getDye(random));
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

