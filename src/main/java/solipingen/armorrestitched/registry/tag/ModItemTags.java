package solipingen.armorrestitched.registry.tag;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.ArmorRestitched;


public class ModItemTags {

    public static final TagKey<Item> CLOTHING_HELMETS = TagKey.of(RegistryKeys.ITEM, new Identifier(ArmorRestitched.MOD_ID, "clothing_helmets"));
    public static final TagKey<Item> CLOTHING_CHESTPLATES = TagKey.of(RegistryKeys.ITEM, new Identifier(ArmorRestitched.MOD_ID, "clothing_chestplates"));
    public static final TagKey<Item> CLOTHING_LEGGINGS = TagKey.of(RegistryKeys.ITEM, new Identifier(ArmorRestitched.MOD_ID, "clothing_leggings"));
    public static final TagKey<Item> CLOTHING_BOOTS = TagKey.of(RegistryKeys.ITEM, new Identifier(ArmorRestitched.MOD_ID, "clothing_boots"));

    
}
