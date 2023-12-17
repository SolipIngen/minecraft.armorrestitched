package solipingen.armorrestitched.mixin.world.poi;

import java.util.Set;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.poi.PointOfInterestTypes;


@Mixin(PointOfInterestTypes.class)
public abstract class PointOfInterestTypesMixin {
    private static final Set<BlockState> MOD_CAULDRONS = ImmutableList.of(Blocks.CAULDRON, Blocks.WATER_CAULDRON).stream().flatMap(block -> block.getStateManager().getStates().stream()).collect(ImmutableSet.toImmutableSet());

    
    @Redirect(method = "registerAndGetDefault", at = @At(value = "FIELD", target = "Lnet/minecraft/world/poi/PointOfInterestTypes;CAULDRONS:Ljava/util/Set;", opcode = Opcodes.GETSTATIC))
    private static Set<BlockState> redirectedCauldrons() {
        return MOD_CAULDRONS;
    }

    
}
