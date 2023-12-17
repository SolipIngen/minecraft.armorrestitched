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
import net.minecraft.item.BannerItem;
import net.minecraft.item.BedItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeItem;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;
import net.minecraft.village.TradeOffers.BuyItemFactory;
import net.minecraft.village.TradeOffers.Factory;
import net.minecraft.village.TradeOffers.SellEnchantedToolFactory;
import net.minecraft.village.TradeOffers.SellItemFactory;
import net.minecraft.world.poi.PointOfInterestType;
import solipingen.armorrestitched.ArmorRestitched;
import solipingen.armorrestitched.block.ModBlocks;
import solipingen.armorrestitched.item.ModItems;
import solipingen.armorrestitched.registry.tag.ModItemTags;
import solipingen.armorrestitched.sound.ModSoundEvents;

import org.jetbrains.annotations.Nullable;


public class ModVillagerProfessions {

    public static final VillagerProfession DRESSER = ModVillagerProfessions.registerVillagerProfession("dresser",
        RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, new Identifier(ArmorRestitched.MOD_ID, "dresser_poi")), null, null, ModSoundEvents.DRESSER_WORKS);

    public static final PointOfInterestType DRESSER_MARKER_POI = ModVillagerProfessions.registerPOI("dresser_poi", ModBlocks.SCUTCHER);


    public static void putDresserTradeOffers(Map<VillagerProfession, Int2ObjectMap<Factory[]>> originalTradeOffers, MerchantEntity villager) {
        ArrayList<DyeItem> dyeItemList = new ArrayList<DyeItem>();
        ArrayList<BlockItem> blockItemList = new ArrayList<BlockItem>();
        ArrayList<BlockItem> carpetItemList = new ArrayList<BlockItem>();
        ArrayList<BannerItem> bannerItemList = new ArrayList<BannerItem>();
        ArrayList<BedItem> bedItemList = new ArrayList<BedItem>();
        for (Item item : Registries.ITEM) {
            ItemStack itemStack = new ItemStack(item);
            if (item instanceof DyeItem) {
                dyeItemList.add(((DyeItem)item));
            }
            else if ((itemStack.isIn(ModItemTags.COTTON_BLOCKS) && item != ModBlocks.WHITE_COTTON.asItem()) || (itemStack.isIn(ModItemTags.LINEN_BLOCKS) && item != ModBlocks.WHITE_LINEN.asItem())) {
                blockItemList.add((BlockItem)item);
            }
            else if (itemStack.isIn(ModItemTags.COTTON_CARPETS) || itemStack.isIn(ModItemTags.LINEN_CARPETS)) {
                carpetItemList.add((BlockItem)item);
            }
            else if (item instanceof BannerItem) {
                bannerItemList.add((BannerItem)item);
            }
            else if (item instanceof BedItem) {
                bedItemList.add((BedItem)item);
            }
        }
        DyeItem apprenticeDye = dyeItemList.get(villager.getRandom().nextInt(dyeItemList.size()));
        dyeItemList.remove(apprenticeDye);
        DyeItem journeymanDye = dyeItemList.get(villager.getRandom().nextInt(dyeItemList.size()));
        dyeItemList.remove(journeymanDye);
        BlockItem journeymanFabric = blockItemList.get(villager.getRandom().nextInt(blockItemList.size()));
        BlockItem journeymanCarpet = carpetItemList.get(villager.getRandom().nextInt(carpetItemList.size()));
        BedItem journeymanBed = bedItemList.get(villager.getRandom().nextInt(bedItemList.size()));
        BannerItem expertBanner = bannerItemList.get(villager.getRandom().nextInt(bannerItemList.size()));
        originalTradeOffers.put(DRESSER, ModVillagerProfessions.copyToFastUtilMap(
            ImmutableMap.of(
                1, new Factory[]{new BuyItemFactory(ModItems.COTTON, 24, 16, 2), new BuyItemFactory(ModItems.FLAX_STEM, 24, 16, 2), 
                    new SellItemFactory(ModItems.COTTON_HELMET, 2, 1, 1), new SellItemFactory(ModItems.COTTON_CHESTPLATE, 4, 1, 1), 
                    new SellItemFactory(ModItems.COTTON_LEGGINGS, 3, 1, 1), new SellItemFactory(ModItems.COTTON_BOOTS, 2, 1, 1), 
                    new SellItemFactory(ModItems.LINEN_HELMET, 2, 1, 1), new SellItemFactory(ModItems.LINEN_CHESTPLATE, 4, 1, 1), 
                    new SellItemFactory(ModItems.LINEN_LEGGINGS, 3, 1, 1), new SellItemFactory(ModItems.LINEN_BOOTS, 2, 1, 1)}, 
                2, new Factory[]{new BuyItemFactory(apprenticeDye, 4, 12, 10), 
                    new SellDyedItemFactory(ModItems.COTTON_HELMET, 3, 12, 5, false), new SellDyedItemFactory(ModItems.COTTON_CHESTPLATE, 5, 12, 5, false), 
                    new SellDyedItemFactory(ModItems.COTTON_LEGGINGS, 4, 12, 5, false), new SellDyedItemFactory(ModItems.COTTON_BOOTS, 3, 12, 5, false), 
                    new SellDyedItemFactory(ModItems.LINEN_HELMET, 3, 12, 5, false), new SellDyedItemFactory(ModItems.LINEN_CHESTPLATE, 5, 12, 5, false), 
                    new SellDyedItemFactory(ModItems.LINEN_LEGGINGS, 4, 12, 5, false), new SellDyedItemFactory(ModItems.LINEN_BOOTS, 3, 12, 5, false)}, 
                3, new Factory[]{new BuyItemFactory(journeymanDye, 4, 12, 20), new SellItemFactory(journeymanFabric, 1, 16, 10), 
                    new SellItemFactory(journeymanCarpet, 1, 32, 10), new SellItemFactory(journeymanBed, 1, 1, 10)}, 
                4, new Factory[]{new BuyItemFactory(Items.STONE_SLAB, 16, 16, 30), new SellItemFactory(expertBanner, 1, 3, 15), 
                    new SellItemFactory(Items.PAINTING, 2, 1, 15), new SellItemFactory(Items.FLOWER_BANNER_PATTERN, 2, 1, 15)}, 
                5, new Factory[]{new SellDyedItemFactory(ModItems.COTTON_HELMET, 4, 3, 15, true), 
                    new SellDyedItemFactory(ModItems.COTTON_CHESTPLATE, 8, 3, 15, true), 
                    new SellDyedItemFactory(ModItems.COTTON_LEGGINGS, 6, 3, 15, true), 
                    new SellDyedItemFactory(ModItems.COTTON_BOOTS, 4, 3, 15, true), 
                    new SellDyedItemFactory(ModItems.LINEN_HELMET, 4, 3, 15, true), 
                    new SellDyedItemFactory(ModItems.LINEN_CHESTPLATE, 8, 3, 15, true), 
                    new SellDyedItemFactory(ModItems.LINEN_LEGGINGS, 6, 3, 15, true), 
                    new SellDyedItemFactory(ModItems.LINEN_BOOTS, 4, 3, 15, true)}
            )
        ));
    }

    // Vanilla Trade Offer Replacements
    public static void replaceArmorerProfessionToLeveledTrade(Map<VillagerProfession, Int2ObjectMap<Factory[]>> originalTradeOffers) {
        originalTradeOffers.replace(VillagerProfession.ARMORER, ModVillagerProfessions.copyToFastUtilMap(
            ImmutableMap.of(
                1, new Factory[]{new BuyItemFactory(Items.COAL, 15, 16, 2), new BuyItemFactory(Items.CHARCOAL, 15, 16, 2), 
                    new BuyItemFactory(Items.STICK, 64, 16, 2), new BuyItemFactory(Items.TINTED_GLASS, 2, 16, 2), 
                    new BuyItemFactory(Items.SMOOTH_STONE_SLAB, 24, 16, 2)}, 
                2, new Factory[]{new BuyItemFactory(Items.COPPER_INGOT, 8, 12, 10), new BuyItemFactory(Items.LAVA_BUCKET, 1, 12, 10), 
                    new SellItemFactory(ModItems.COPPER_HELMET, 2, 1, 5), new SellItemFactory(ModItems.COPPER_CHESTPLATE, 4, 1, 5), 
                    new SellItemFactory(ModItems.COPPER_LEGGINGS, 3, 1, 5), new SellItemFactory(ModItems.COPPER_BOOTS, 2, 1, 5), 
                    new SellItemFactory(ModItems.COPPER_HORSE_ARMOR, 6, 1, 3, 5)}, 
                3, new Factory[]{new BuyItemFactory(Items.GOLD_INGOT, 2, 12, 20), 
                    new SellItemFactory(Items.GOLDEN_HELMET, 5, 1, 10), new SellItemFactory(Items.GOLDEN_CHESTPLATE, 9, 1, 10), 
                    new SellItemFactory(Items.GOLDEN_LEGGINGS, 7, 1, 10), new SellItemFactory(Items.GOLDEN_BOOTS, 4, 1, 10), 
                    new SellItemFactory(Items.GOLDEN_HORSE_ARMOR, 12, 1, 3, 10), 
                    new SellItemFactory(Items.CHAINMAIL_HELMET, 2, 1, 10), new SellItemFactory(Items.CHAINMAIL_CHESTPLATE, 4, 1, 10), 
                    new SellItemFactory(Items.CHAINMAIL_LEGGINGS, 3, 1, 10), new SellItemFactory(Items.CHAINMAIL_BOOTS, 2, 1, 10)}, 
                4, new Factory[]{new BuyItemFactory(Items.IRON_INGOT, 4, 12, 30), 
                    new SellItemFactory(Items.IRON_HELMET, 5, 1, 15), new SellItemFactory(Items.IRON_CHESTPLATE, 9, 1, 15), 
                    new SellItemFactory(Items.IRON_LEGGINGS, 7, 1, 15), new SellItemFactory(Items.IRON_BOOTS, 4, 1, 15), 
                    new SellItemFactory(Items.IRON_HORSE_ARMOR, 10, 1, 3, 15)}, 
                5, new Factory[]{new BuyItemFactory(Items.DIAMOND, 1, 12, 30), 
                    new SellEnchantedToolFactory(Items.DIAMOND_HELMET, 13, 3, 15), new SellEnchantedToolFactory(Items.DIAMOND_CHESTPLATE, 21, 3, 15), 
                    new SellEnchantedToolFactory(Items.DIAMOND_LEGGINGS, 19, 3, 15), new SellEnchantedToolFactory(Items.DIAMOND_BOOTS, 13, 3, 15), 
                    new SellItemFactory(Items.DIAMOND_HORSE_ARMOR, 18, 1, 3, 15)}
                )
            )
	    );
    }

    public static void replaceLeatherworkerProfessionToLeveledTrade(Map<VillagerProfession, Int2ObjectMap<Factory[]>> originalTradeOffers, MerchantEntity villager) {
        ArrayList<DyeItem> dyeItemList = new ArrayList<DyeItem>();
        ArrayList<BlockItem> blockItemList = new ArrayList<BlockItem>();
        ArrayList<BlockItem> carpetItemList = new ArrayList<BlockItem>();
        for (Item item : Registries.ITEM) {
            ItemStack itemStack = new ItemStack(item);
            if (item instanceof DyeItem) {
                dyeItemList.add(((DyeItem)item));
            }
            else if (itemStack.isIn(ModItemTags.FUR_BLOCKS) && item != ModBlocks.WHITE_FUR.asItem()) {
                blockItemList.add((BlockItem)item);
            }
            else if (itemStack.isIn(ModItemTags.FUR_CARPETS)) {
                carpetItemList.add((BlockItem)item);
            }
        }
        BlockItem journeymanFabric = blockItemList.get(villager.getRandom().nextInt(blockItemList.size()));
        BlockItem expertCarpet = carpetItemList.get(villager.getRandom().nextInt(carpetItemList.size()));
        originalTradeOffers.replace(VillagerProfession.LEATHERWORKER, ModVillagerProfessions.copyToFastUtilMap(
            ImmutableMap.of(
                1, new Factory[]{new BuyItemFactory(Items.LEATHER, 6, 16, 2), new BuyItemFactory(Items.RABBIT_HIDE, 9, 16, 2), 
                    new BuyItemFactory(ModBlocks.WHITE_FUR, 18, 16, 2), 
                    new SellItemFactory(Items.LEATHER_HELMET, 2, 1, 1), new SellItemFactory(Items.LEATHER_CHESTPLATE, 4, 1, 1), 
                    new SellItemFactory(Items.LEATHER_LEGGINGS, 3, 1, 1), new SellItemFactory(Items.LEATHER_BOOTS, 2, 1, 1), 
                    new SellItemFactory(ModItems.FUR_HELMET, 2, 1, 1), new SellItemFactory(ModItems.FUR_CHESTPLATE, 4, 1, 1), 
                    new SellItemFactory(ModItems.FUR_LEGGINGS, 3, 1, 1), new SellItemFactory(ModItems.FUR_BOOTS, 2, 1, 1)}, 
                2, new Factory[]{new BuyItemFactory(dyeItemList.get(villager.getRandom().nextInt(dyeItemList.size())), 4, 12, 10), 
                    new SellDyedItemFactory(Items.LEATHER_HELMET, 3, 12, 5, false), new SellDyedItemFactory(Items.LEATHER_CHESTPLATE, 5, 12, 5, false), 
                    new SellDyedItemFactory(Items.LEATHER_LEGGINGS, 4, 12, 5, false), new SellDyedItemFactory(Items.LEATHER_BOOTS, 3, 12, 5, false), 
                    new SellItemFactory(Items.ITEM_FRAME, 1, 8, 12, 5), 
                    new SellDyedItemFactory(ModItems.FUR_HELMET, 3, 12, 5, false), new SellDyedItemFactory(ModItems.FUR_CHESTPLATE, 5, 12, 5, false), 
                    new SellDyedItemFactory(ModItems.FUR_LEGGINGS, 4, 12, 5, false), new SellDyedItemFactory(ModItems.FUR_BOOTS, 3, 12, 5, false)}, 
                3, new Factory[]{new BuyItemFactory(Items.WATER_BUCKET, 1, 12, 20), new BuyItemFactory(Items.TRIPWIRE_HOOK, 2, 12, 20), 
                    new SellItemFactory(Items.SADDLE, 6, 1, 10), new SellItemFactory(journeymanFabric, 1, 16, 10)}, 
                4, new Factory[]{new BuyItemFactory(Items.SCUTE, 1, 12, 30), new BuyItemFactory(Items.FLINT, 13, 12, 30), 
                    new SellItemFactory(Items.TURTLE_HELMET, 15, 1, 15), new SellDyedItemFactory(Items.LEATHER_HORSE_ARMOR, 6, 12, 15, false), 
                    new SellItemFactory(expertCarpet, 1, 32, 10)}, 
                5, new Factory[]{new SellDyedItemFactory(Items.LEATHER_HELMET, 4, 3, 15, true), 
                    new SellDyedItemFactory(Items.LEATHER_CHESTPLATE, 8, 3, 15, true), 
                    new SellDyedItemFactory(Items.LEATHER_LEGGINGS, 6, 3, 15, true), 
                    new SellDyedItemFactory(Items.LEATHER_BOOTS, 4, 3, 15, true), 
                    new SellDyedItemFactory(ModItems.FUR_HELMET, 4, 3, 15, true), 
                    new SellDyedItemFactory(ModItems.FUR_CHESTPLATE, 8, 3, 15, true), 
                    new SellDyedItemFactory(ModItems.FUR_LEGGINGS, 6, 3, 15, true), 
                    new SellDyedItemFactory(ModItems.FUR_BOOTS, 4, 3, 15, true), 
                    new SellEnchantedToolFactory(Items.TURTLE_HELMET, 9, 3, 15)}
                )
            )
	    );
    }

    public static void replaceShepherdProfessionToLeveledTrade(Map<VillagerProfession, Int2ObjectMap<Factory[]>> originalTradeOffers, MerchantEntity villager) {
        ArrayList<DyeItem> dyeItemList = new ArrayList<DyeItem>();
        ArrayList<BlockItem> itemList = new ArrayList<BlockItem>();
        ArrayList<BlockItem> carpetItemList = new ArrayList<BlockItem>();
        ArrayList<BannerItem> woolBannerItemList = new ArrayList<BannerItem>();
        ArrayList<BedItem> bedItemList = new ArrayList<BedItem>();
        for (Item item : Registries.ITEM) {
            ItemStack itemStack = new ItemStack(item);
            if (item instanceof DyeItem) {
                dyeItemList.add(((DyeItem)item));
            }
            else if ((itemStack.isIn(ItemTags.WOOL) && item != Items.WHITE_WOOL) || (itemStack.isIn(ModItemTags.SILK_BLOCKS) && item != ModBlocks.WHITE_SILK.asItem())) {
                itemList.add((BlockItem)item);
            }
            else if (itemStack.isIn(ItemTags.WOOL_CARPETS) || itemStack.isIn(ModItemTags.SILK_CARPETS)) {
                carpetItemList.add((BlockItem)item);
            }
            else if (item instanceof BannerItem) {
                woolBannerItemList.add((BannerItem)item);
            }
            else if (item instanceof BedItem) {
                bedItemList.add((BedItem)item);
            }
        }
        DyeItem apprenticeDye = dyeItemList.get(villager.getRandom().nextInt(dyeItemList.size()));
        dyeItemList.remove(apprenticeDye);
        DyeItem journeymanDye = dyeItemList.get(villager.getRandom().nextInt(dyeItemList.size()));
        dyeItemList.remove(journeymanDye);
        BlockItem journeymanFabric = itemList.get(villager.getRandom().nextInt(itemList.size()));
        BlockItem journeymanCarpet = carpetItemList.get(villager.getRandom().nextInt(carpetItemList.size()));
        BedItem journeymanBed = bedItemList.get(villager.getRandom().nextInt(bedItemList.size()));
        BannerItem expertBanner = woolBannerItemList.get(villager.getRandom().nextInt(woolBannerItemList.size()));
        originalTradeOffers.replace(VillagerProfession.SHEPHERD, ModVillagerProfessions.copyToFastUtilMap(
            ImmutableMap.of(
                1, new Factory[]{new BuyItemFactory(Items.WHITE_WOOL, 18, 16, 2), new SellItemFactory(Items.SHEARS, 2, 1, 1), 
                    new SellItemFactory(ModItems.WOOL_HELMET, 2, 1, 1), new SellItemFactory(ModItems.WOOL_CHESTPLATE, 4, 1, 1), 
                    new SellItemFactory(ModItems.WOOL_LEGGINGS, 3, 1, 1), new SellItemFactory(ModItems.WOOL_BOOTS, 2, 1, 1), 
                    new SellItemFactory(ModItems.SILK_HELMET, 2, 1, 1), new SellItemFactory(ModItems.SILK_CHESTPLATE, 4, 1, 1), 
                    new SellItemFactory(ModItems.SILK_LEGGINGS, 3, 1, 1), new SellItemFactory(ModItems.SILK_BOOTS, 2, 1, 1)}, 
                2, new Factory[]{new BuyItemFactory(apprenticeDye, 4, 12, 10), 
                    new SellDyedItemFactory(ModItems.WOOL_HELMET, 3, 12, 5, false), new SellDyedItemFactory(ModItems.WOOL_CHESTPLATE, 5, 12, 5, false), 
                    new SellDyedItemFactory(ModItems.WOOL_LEGGINGS, 4, 12, 5, false), new SellDyedItemFactory(ModItems.WOOL_BOOTS, 3, 12, 5, false), 
                    new SellItemFactory(Items.LEAD, 2, 4, 12, 5), 
                    new SellDyedItemFactory(ModItems.SILK_HELMET, 3, 12, 5, false), new SellDyedItemFactory(ModItems.SILK_CHESTPLATE, 5, 12, 5, false), 
                    new SellDyedItemFactory(ModItems.SILK_LEGGINGS, 4, 12, 5, false), new SellDyedItemFactory(ModItems.SILK_BOOTS, 3, 12, 5, false)}, 
                3, new Factory[]{new BuyItemFactory(journeymanDye, 4, 12, 20), new SellItemFactory(journeymanFabric, 1, 16, 10), 
                    new SellItemFactory(journeymanCarpet, 1, 32, 10), new SellItemFactory(journeymanBed, 1, 1, 10)}, 
                4, new Factory[]{new BuyItemFactory(Items.WHEAT, 20, 16, 30), new BuyItemFactory(ModItems.FLAX_STEM, 20, 16, 30), new SellItemFactory(expertBanner, 1, 3, 15), 
                    new SellItemFactory(Items.PAINTING, 2, 1, 15), new SellItemFactory(Items.FLOWER_BANNER_PATTERN, 2, 1, 15)}, 
                5, new Factory[]{new SellDyedItemFactory(ModItems.WOOL_HELMET, 4, 3, 15, true), 
                    new SellDyedItemFactory(ModItems.WOOL_CHESTPLATE, 8, 3, 15, true), 
                    new SellDyedItemFactory(ModItems.WOOL_LEGGINGS, 6, 3, 15, true), 
                    new SellDyedItemFactory(ModItems.WOOL_BOOTS, 4, 3, 15, true), 
                    new SellDyedItemFactory(ModItems.SILK_HELMET, 4, 3, 15, true), 
                    new SellDyedItemFactory(ModItems.SILK_CHESTPLATE, 8, 3, 15, true), 
                    new SellDyedItemFactory(ModItems.SILK_LEGGINGS, 6, 3, 15, true), 
                    new SellDyedItemFactory(ModItems.SILK_BOOTS, 4, 3, 15, true)}
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

