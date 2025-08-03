package solipingen.armorrestitched.client.render.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;


@Environment(EnvType.CLIENT)
public class SilkMothEntityRenderState extends LivingEntityRenderState {
    public float bodyPitch;
    public boolean stoppedOnGround;


    public SilkMothEntityRenderState() {
    }

}
