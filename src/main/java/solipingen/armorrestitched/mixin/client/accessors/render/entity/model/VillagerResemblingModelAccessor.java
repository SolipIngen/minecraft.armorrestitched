package solipingen.armorrestitched.mixin.client.accessors.render.entity.model;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.VillagerResemblingModel;


@Mixin(VillagerResemblingModel.class)
@Environment(value = EnvType.CLIENT)
public interface VillagerResemblingModelAccessor {
    
    @Accessor("rightLeg") public ModelPart getRightLeg();
    @Accessor("leftLeg") public ModelPart getLeftLeg();

}

