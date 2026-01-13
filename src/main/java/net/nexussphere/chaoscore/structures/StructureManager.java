package net.nexussphere.chaoscore.structures;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.nexussphere.chaoscore.structures.impl.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class StructureManager {
    private final List<ChaosStructure> structures = new ArrayList<>();
    private final Random random = new Random();
    public StructureManager() {
        structures.add(new DungeonStructure());
        structures.add(new WatchTowerStructure());
        structures.add(new IceTowerStructure());
        structures.add(new GreenHouseStructure());
        structures.add(new BeeNestStructure());
        structures.add(new CrystalGeodeStructure());
        structures.add(new MeteoriteStructure());
        structures.add(new MagmaCoreStructure());
        structures.add(new RuinedPortalStructure());
        structures.add(new EndPortalStructure());
        structures.add(new VoidLibraryStructure());
        structures.add(new IcePalaceStructure());
        structures.add(new GraveyardStructure());
        structures.add(new AncientLibraryStructure());
        structures.add(new VillageHouseStructure());
        structures.add(new BlacksmithStructure());
        structures.add(new VillageCuringStructure());
        structures.add(new DesertTombStructure());
        structures.add(new GiantSwordStructure());
        structures.add(new SwampAltarStructure());
        structures.add(new WitchTowerStructure());
        structures.add(new AncientFountainStructure());
        structures.add(new GuardianOutpostStructure());
        structures.add(new AbandonedMineStructure());
        structures.add(new LightShrineStructure());
        structures.add(new CactusGardenStructure());
        structures.add(new FloatingMushroomStructure());
        structures.add(new TrialForgeStructure());
    }
    public void generate(ServerWorld world, int x1, int y1, int z1, int x2, int y2, int z2) {
        if (structures.isEmpty()) return;
        ChaosStructure s = structures.get(random.nextInt(structures.size()));
        s.build(world, x1, y1, z1, x2, y1 + 30, z2);
    }
    public void testAll(ServerWorld world, BlockPos startPos) {
        int currentX = startPos.getX();
        int y = startPos.getY();
        int z = startPos.getZ();
        int size = 9;
        int gap = 20;
        for (ChaosStructure s : structures) {
            world.getServer().getPlayerManager().broadcast(Text.literal("§eCreating: §f" + s.getName()), false);
            s.build(world, currentX, y, z, currentX + size - 1, y + 40, z + size - 1);
            currentX += gap;
        }
    }
}