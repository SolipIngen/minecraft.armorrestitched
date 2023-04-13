package solipingen.armorrestitched.mixin.entity.ai.brain.task;

import java.util.List;
import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.GiveGiftsToHeroTask;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext.*;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;
import solipingen.armorrestitched.ArmorRestitched;
import solipingen.armorrestitched.village.ModVillagerProfessions;


@Mixin(GiveGiftsToHeroTask.class)
public abstract class GiveGiftsToHeroTaskMixin extends MultiTickTask<VillagerEntity> {
    private static final Identifier HERO_OF_THE_VILLAGE_DRESSER_GIFT = new Identifier(ArmorRestitched.MOD_ID, "gameplay/hero_of_the_village/dresser_gift");
    private static final Identifier HERO_OF_THE_VILLAGE_LEATHERWORKER_GIFT = new Identifier(ArmorRestitched.MOD_ID, "gameplay/hero_of_the_village/leatherworker_gift");
    private static final Identifier HERO_OF_THE_VILLAGE_SHEPHERD_GIFT = new Identifier(ArmorRestitched.MOD_ID, "gameplay/hero_of_the_village/shepherd_gift");

    
    public GiveGiftsToHeroTaskMixin(Map<MemoryModuleType<?>, MemoryModuleState> requiredMemoryState) {
        super(requiredMemoryState);
    }

    @Inject(method = "getGifts", at = @At("HEAD"), cancellable = true)
    private void injectedModProfessionGifts(VillagerEntity villager, CallbackInfoReturnable<List<ItemStack>> cbireturn) {
        VillagerProfession villagerProfession = villager.getVillagerData().getProfession();
        if (villagerProfession == ModVillagerProfessions.DRESSER) {
            LootTable lootTable = villager.world.getServer().getLootManager().getTable(HERO_OF_THE_VILLAGE_DRESSER_GIFT);
            Builder builder = new Builder((ServerWorld)villager.world).parameter(LootContextParameters.ORIGIN, villager.getPos()).parameter(LootContextParameters.THIS_ENTITY, villager).random(villager.getRandom());
            cbireturn.setReturnValue(lootTable.generateLoot(builder.build(LootContextTypes.GIFT)));
        }
        else if (villagerProfession == VillagerProfession.LEATHERWORKER) {
            LootTable lootTable = villager.world.getServer().getLootManager().getTable(HERO_OF_THE_VILLAGE_LEATHERWORKER_GIFT);
            Builder builder = new Builder((ServerWorld)villager.world).parameter(LootContextParameters.ORIGIN, villager.getPos()).parameter(LootContextParameters.THIS_ENTITY, villager).random(villager.getRandom());
            cbireturn.setReturnValue(lootTable.generateLoot(builder.build(LootContextTypes.GIFT)));
        }
        else if (villagerProfession == VillagerProfession.SHEPHERD) {
            LootTable lootTable = villager.world.getServer().getLootManager().getTable(HERO_OF_THE_VILLAGE_SHEPHERD_GIFT);
            Builder builder = new Builder((ServerWorld)villager.world).parameter(LootContextParameters.ORIGIN, villager.getPos()).parameter(LootContextParameters.THIS_ENTITY, villager).random(villager.getRandom());
            cbireturn.setReturnValue(lootTable.generateLoot(builder.build(LootContextTypes.GIFT)));
        }
    }    

}
