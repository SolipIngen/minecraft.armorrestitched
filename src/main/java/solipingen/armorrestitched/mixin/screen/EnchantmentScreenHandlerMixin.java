package solipingen.armorrestitched.mixin.screen;

import java.util.List;
import java.util.Optional;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.item.trim.ArmorTrimMaterials;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;


@Mixin(EnchantmentScreenHandler.class)
public abstract class EnchantmentScreenHandlerMixin extends ScreenHandler {
    @Final private PlayerInventory playerInventory;
    @Shadow @Final private Random random;


    protected EnchantmentScreenHandlerMixin(ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }

    @Inject(method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/screen/ScreenHandlerContext;)V", at = @At("TAIL"))
    private void injectedInit(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context, CallbackInfo cbi) {
        this.playerInventory = playerInventory;
    }

    @ModifyVariable(method = "generateEnchantments", at = @At("STORE"), ordinal = 0)
    private List<EnchantmentLevelEntry> modifiedEnchantability(List<EnchantmentLevelEntry> originalList, ItemStack stack, int slot, int level) {
        List<EnchantmentLevelEntry> list = originalList;
        Item item = stack.getItem();
        if (item instanceof ArmorItem) {
            Optional<ArmorTrim> trimOptional = ArmorTrim.getTrim(this.playerInventory.player.getWorld().getRegistryManager(), stack, false);
            if (trimOptional.isPresent() && trimOptional.get().getMaterial().matchesKey(ArmorTrimMaterials.GOLD)) {
                list = EnchantmentHelper.generateEnchantments(this.random, stack, MathHelper.ceil(1.5f*level), false);
            }
        }
        return list;
    }


}
