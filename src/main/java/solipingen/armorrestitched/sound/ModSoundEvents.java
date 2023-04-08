package solipingen.armorrestitched.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.ArmorRestitched;


public class ModSoundEvents {

    public static final SoundEvent COTTON_CLOTHING_EQUIP = ModSoundEvents.registerSoundEvent("cotton_clothing_equip");
    public static final SoundEvent FUR_CLOTHING_EQUIP = ModSoundEvents.registerSoundEvent("fur_clothing_equip");
    public static final SoundEvent LINEN_CLOTHING_EQUIP = ModSoundEvents.registerSoundEvent("linen_clothing_equip");
    public static final SoundEvent SILK_CLOTHING_EQUIP = ModSoundEvents.registerSoundEvent("silk_clothing_equip");
    public static final SoundEvent WOOL_CLOTHING_EQUIP = ModSoundEvents.registerSoundEvent("wool_clothing_equip");
    public static final SoundEvent PAPER_CLOTHING_EQUIP = ModSoundEvents.registerSoundEvent("paper_clothing_equip");
    public static final SoundEvent COPPER_ARMOR_EQUIP = ModSoundEvents.registerSoundEvent("copper_armor_equip");

    public static final SoundEvent COTTON_PICK = ModSoundEvents.registerSoundEvent("cotton_pick");
    public static final SoundEvent SCUTCHER_USED = ModSoundEvents.registerSoundEvent("scutcher_used");
    public static final SoundEvent LLAMA_SHEARED = ModSoundEvents.registerSoundEvent("llama_sheared");
    public static final SoundEvent WEAVER_WORKS = ModSoundEvents.registerSoundEvent("weaver_works");


    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(ArmorRestitched.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerModSoundEvents() {
        ArmorRestitched.LOGGER.debug("Registering Mod Sounds for " + ArmorRestitched.MOD_ID);
    }

}
