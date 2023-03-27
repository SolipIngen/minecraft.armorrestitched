package solipingen.armorrestitched.registry.tag;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.ArmorRestitched;

public class ModBlockTags {
    
    public static final TagKey<Block> COTTON = TagKey.of(RegistryKeys.BLOCK, new Identifier(ArmorRestitched.MOD_ID, "cotton"));
    public static final TagKey<Block> FUR = TagKey.of(RegistryKeys.BLOCK, new Identifier(ArmorRestitched.MOD_ID, "fur"));
    public static final TagKey<Block> LINEN = TagKey.of(RegistryKeys.BLOCK, new Identifier(ArmorRestitched.MOD_ID, "linen"));
    public static final TagKey<Block> SILK = TagKey.of(RegistryKeys.BLOCK, new Identifier(ArmorRestitched.MOD_ID, "silk"));


}
