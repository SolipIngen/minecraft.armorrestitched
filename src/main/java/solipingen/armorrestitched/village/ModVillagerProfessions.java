package solipingen.armorrestitched.village;

import java.util.ArrayList;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;
import net.minecraft.village.TradeOffers.BuyForOneEmeraldFactory;
import net.minecraft.village.TradeOffers.Factory;
import net.minecraft.village.TradeOffers.SellEnchantedToolFactory;
import net.minecraft.village.TradeOffers.SellItemFactory;
import net.minecraft.world.poi.PointOfInterestType;
import solipingen.armorrestitched.ArmorRestitched;
import solipingen.armorrestitched.block.ModBlocks;
import solipingen.armorrestitched.item.ModItems;

import org.jetbrains.annotations.Nullable;


public class ModVillagerProfessions {

    // public static final VillagerProfession SWORDSMAN = ModVillagerProfessions.registerVillagerProfession("swordsman",
    //     RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, new Identifier(ArmorRestitched.MOD_ID, "swordsman_marker_poi")), null, null, null);
    // public static final VillagerProfession SPEARMAN = ModVillagerProfessions.registerVillagerProfession("spearman",
    //     RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, new Identifier((ArmorRestitched.MOD_ID, "spearman_marker_poi")), null, null, null);

    // public static final PointOfInterestType SWORDSMAN_MARKER_POI = ModVillagerProfessions.registerPOI("swordsman_marker_poi", ModBlocks.SWORDSMAN_MARKER);
    // public static final PointOfInterestType SPEARMAN_MARKER_POI = ModVillagerProfessions.registerPOI("spearman_marker_poi", ModBlocks.SPEARMAN_MARKER);


    // Trade Offer Replacements
    public static void replaceArmorerProfessionToLeveledTrade(Map<VillagerProfession, Int2ObjectMap<Factory[]>> originalTradeOffers) {
        originalTradeOffers.replace(VillagerProfession.ARMORER, ModVillagerProfessions.copyToFastUtilMap(
            ImmutableMap.of(
                1, new Factory[]{new BuyForOneEmeraldFactory(Items.COAL, 15, 16, 2), new BuyForOneEmeraldFactory(Items.CHARCOAL, 15, 16, 2), 
                    new BuyForOneEmeraldFactory(Items.STICK, 64, 16, 2), new BuyForOneEmeraldFactory(Items.TINTED_GLASS, 2, 16, 2), 
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

    public static void replaceLeatherworkerProfessionToLeveledTrade(Map<VillagerProfession, Int2ObjectMap<Factory[]>> originalTradeOffers, MerchantEntity villager) {
        ArrayList<DyeItem> dyeItemList = new ArrayList<DyeItem>();
        for (Item item : Registries.ITEM) {
            if (item instanceof DyeItem) {
                dyeItemList.add(((DyeItem)item));
            }
        }
        originalTradeOffers.replace(VillagerProfession.LEATHERWORKER, ModVillagerProfessions.copyToFastUtilMap(
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


    private static VillagerProfession registerVillagerProfession(String name, RegistryKey<PointOfInterestType> poiType, @Nullable ImmutableSet<Item> gatherableItems, @Nullable ImmutableSet<Block> secondaryJobSites, @Nullable SoundEvent workSound) {
        ImmutableSet<Item> gatherableItemsSet = ImmutableSet.of();
        if (gatherableItems != null) {
            gatherableItemsSet = gatherableItems;
        }
        ImmutableSet<Block> secondaryJobSitesSet = ImmutableSet.of();
        if (secondaryJobSites != null) {
            secondaryJobSitesSet = secondaryJobSites;
        }
        return Registry.register(Registries.VILLAGER_PROFESSION, new Identifier(ArmorRestitched.MOD_ID, name),
            new VillagerProfession(name, entry -> entry.matchesKey(poiType), entry -> entry.matchesKey(poiType), gatherableItemsSet, secondaryJobSitesSet, workSound));
    }

    private static PointOfInterestType registerPOI(String name, Block block) {
        return PointOfInterestHelper.register(new Identifier(ArmorRestitched.MOD_ID, name),
            1, 1, ImmutableSet.copyOf(block.getStateManager().getStates()));
    }

    public static void registerModVillagerProfessions() {
        ArmorRestitched.LOGGER.debug("Registering Villagers for " + ArmorRestitched.MOD_ID);
    }

    private static Int2ObjectMap<Factory[]> copyToFastUtilMap(ImmutableMap<Integer, Factory[]> map) {
        return new Int2ObjectOpenHashMap<Factory[]>(map);
    }

    
    public static class SellDyedItemFactory implements Factory {
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
    
        public static DyeItem getDye(Random random) {
            return DyeItem.byColor(DyeColor.byId(random.nextInt(16)));
        }

    }



}

