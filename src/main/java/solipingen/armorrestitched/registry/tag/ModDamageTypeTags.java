package solipingen.armorrestitched.registry.tag;


import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.ArmorRestitched;


public class ModDamageTypeTags {

    public static final TagKey<DamageType> IMPACT_PROTECTED = TagKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(ArmorRestitched.MOD_ID, "impact_protected"));
    public static final TagKey<DamageType> MAGIC_PROTECTED = TagKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(ArmorRestitched.MOD_ID, "magic_protected"));
    public static final TagKey<DamageType> STRIKE_PROTECTED = TagKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(ArmorRestitched.MOD_ID, "strike_protected"));

}
