package solipingen.armorrestitched.registry.tag;

import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.ArmorRestitched;


public class ModEntityTypeTags {

    public static final TagKey<EntityType<?>> CAN_WEAR_CARPETS = TagKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(ArmorRestitched.MOD_ID, "can_wear_carpets"));

}
