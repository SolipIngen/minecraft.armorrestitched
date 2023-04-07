package solipingen.armorrestitched.registry.tag;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.ArmorRestitched;


public class ModItemTags {

    public static final TagKey<Item> COTTON_BLOCKS = TagKey.of(RegistryKeys.ITEM, new Identifier(ArmorRestitched.MOD_ID, "cotton_blocks"));
    public static final TagKey<Item> FUR_BLOCKS = TagKey.of(RegistryKeys.ITEM, new Identifier(ArmorRestitched.MOD_ID, "fur_blocks"));
    public static final TagKey<Item> LINEN_BLOCKS = TagKey.of(RegistryKeys.ITEM, new Identifier(ArmorRestitched.MOD_ID, "linen_blocks"));
    public static final TagKey<Item> SILK_BLOCKS = TagKey.of(RegistryKeys.ITEM, new Identifier(ArmorRestitched.MOD_ID, "silk_blocks"));

    public static final TagKey<Item> COTTON_CARPETS = TagKey.of(RegistryKeys.ITEM, new Identifier(ArmorRestitched.MOD_ID, "cotton_carpets"));
    public static final TagKey<Item> FUR_CARPETS = TagKey.of(RegistryKeys.ITEM, new Identifier(ArmorRestitched.MOD_ID, "fur_carpets"));
    public static final TagKey<Item> LINEN_CARPETS = TagKey.of(RegistryKeys.ITEM, new Identifier(ArmorRestitched.MOD_ID, "linen_carpets"));
    public static final TagKey<Item> SILK_CARPETS = TagKey.of(RegistryKeys.ITEM, new Identifier(ArmorRestitched.MOD_ID, "silk_carpets"));

    public static final TagKey<Item> CLOTHING_HELMETS = TagKey.of(RegistryKeys.ITEM, new Identifier(ArmorRestitched.MOD_ID, "clothing_helmets"));
    public static final TagKey<Item> CLOTHING_CHESTPLATES = TagKey.of(RegistryKeys.ITEM, new Identifier(ArmorRestitched.MOD_ID, "clothing_chestplates"));
    public static final TagKey<Item> CLOTHING_LEGGINGS = TagKey.of(RegistryKeys.ITEM, new Identifier(ArmorRestitched.MOD_ID, "clothing_leggings"));
    public static final TagKey<Item> CLOTHING_BOOTS = TagKey.of(RegistryKeys.ITEM, new Identifier(ArmorRestitched.MOD_ID, "clothing_boots"));

    public static final TagKey<Item> STRING_INGREDIENTS = TagKey.of(RegistryKeys.ITEM, new Identifier(ArmorRestitched.MOD_ID, "string_ingredients"));

    
}
