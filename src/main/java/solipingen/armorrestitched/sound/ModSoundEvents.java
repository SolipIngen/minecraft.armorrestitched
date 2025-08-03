package solipingen.armorrestitched.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.ArmorRestitched;


public class ModSoundEvents {

    public static final RegistryEntry<SoundEvent> COTTON_CLOTHING_EQUIP = ModSoundEvents.registerReference("item.armor.equip_cotton");
    public static final RegistryEntry<SoundEvent> FUR_CLOTHING_EQUIP = ModSoundEvents.registerReference("item.armor.equip_fur");
    public static final RegistryEntry<SoundEvent> LINEN_CLOTHING_EQUIP = ModSoundEvents.registerReference("item.armor.equip_linen");
    public static final RegistryEntry<SoundEvent> SILK_CLOTHING_EQUIP = ModSoundEvents.registerReference("item.armor.equip_silk");
    public static final RegistryEntry<SoundEvent> WOOL_CLOTHING_EQUIP = ModSoundEvents.registerReference("item.armor.equip_wool");
    public static final RegistryEntry<SoundEvent> PAPER_CLOTHING_EQUIP = ModSoundEvents.registerReference("item.armor.equip_paper");
    public static final RegistryEntry<SoundEvent> COPPER_ARMOR_EQUIP = ModSoundEvents.registerReference("item.armor.equip_copper");

    public static final SoundEvent CAULDRON_USED = ModSoundEvents.registerSoundEvent("cauldron_used");
    public static final SoundEvent COTTON_PICK = ModSoundEvents.registerSoundEvent("cotton_pick");
    public static final SoundEvent SILKWORM_HARVEST = ModSoundEvents.registerSoundEvent("silkworm_harvest");
    public static final SoundEvent SCUTCHER_USED = ModSoundEvents.registerSoundEvent("scutcher_used");
    public static final SoundEvent LLAMA_SHEARED = ModSoundEvents.registerSoundEvent("llama_sheared");
    public static final SoundEvent DRESSER_WORKS = ModSoundEvents.registerSoundEvent("dresser_works");

    public static final SoundEvent SILK_MOTH_HURT = ModSoundEvents.registerSoundEvent("silk_moth_hurt");
    public static final SoundEvent SILK_MOTH_DEATH = ModSoundEvents.registerSoundEvent("silk_moth_death");


    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(ArmorRestitched.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    private static RegistryEntry.Reference<SoundEvent> registerReference(String name) {
        Identifier id = Identifier.of(ArmorRestitched.MOD_ID, name);
        return Registry.registerReference(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerModSoundEvents() {
        ArmorRestitched.LOGGER.debug("Registering Mod Sounds for " + ArmorRestitched.MOD_ID);
    }

}
