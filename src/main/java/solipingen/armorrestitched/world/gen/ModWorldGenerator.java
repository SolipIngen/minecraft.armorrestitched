package solipingen.armorrestitched.world.gen;

import solipingen.armorrestitched.ArmorRestitched;


public class ModWorldGenerator {
    

    public static void generateModWorldGen() {

        ModEntityGenerator.addSpawns();
        ModPlantGenerator.generatePlants();


        ArmorRestitched.LOGGER.debug("Generating Mod World Generation for " + ArmorRestitched.MOD_ID);

    }

}
