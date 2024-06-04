package solipingen.armorrestitched.client.resource;

import java.util.Collection;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ResourcePackManager;


@Environment(value = EnvType.CLIENT)
public class ModClientResourcePacks {


    public static boolean isFreshAnimationsEnabled() {
        boolean freshAnimationsEnabled = false;
        ResourcePackManager resourcePackManager = MinecraftClient.getInstance().getResourcePackManager();
        Collection<String> enabledResourcePackNames = resourcePackManager.getEnabledIds();
        for (String enabledPackName : enabledResourcePackNames) {
            if (enabledPackName.contains("FreshAnimations")) {
                freshAnimationsEnabled |= true;
            }
        }
        return freshAnimationsEnabled;
    }


}
