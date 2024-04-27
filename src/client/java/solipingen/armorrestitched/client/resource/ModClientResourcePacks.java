package solipingen.armorrestitched.client.resource;

import java.util.Collection;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.ArmorRestitched;


@Environment(value = EnvType.CLIENT)
public class ModClientResourcePacks {
    

    public static void registerModBuiltInResourcePacks() {

        FabricLoader.getInstance().getModContainer(ArmorRestitched.MOD_ID).ifPresent((container) -> {
            ResourceManagerHelper.registerBuiltinResourcePack(new Identifier(ArmorRestitched.MOD_ID, "freshrestitchedanimations"), container, ResourcePackActivationType.NORMAL);
        });
        
    }

    public static boolean isFreshAnimationsEnabled() {
        boolean freshAnimationsEnabled = false;
        ResourcePackManager resourcePackManager = MinecraftClient.getInstance().getResourcePackManager();
        Collection<String> enabledResourcePackNames = resourcePackManager.getEnabledNames();
        for (String enabledPackName : enabledResourcePackNames) {
            if (enabledPackName.contains("FreshAnimations")) {
                freshAnimationsEnabled |= true;
            }
        }
        return freshAnimationsEnabled;
    }


}
