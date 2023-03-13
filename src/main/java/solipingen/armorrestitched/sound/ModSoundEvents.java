package solipingen.armorrestitched.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.ArmorRestitched;


public class ModSoundEvents {

    public static final SoundEvent COPPER_ARMOR_EQUIP = ModSoundEvents.registerSoundEvent("copper_armor_equip");


    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(ArmorRestitched.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerModSoundEvents() {
        ArmorRestitched.LOGGER.debug("Registering Mod Sounds for " + ArmorRestitched.MOD_ID);
    }

}
