package net.nexussphere.chaoscore.structures;
import net.minecraft.server.world.ServerWorld;
public interface ChaosStructure {
    void build(ServerWorld world, int x1, int y1, int z1, int width, int height, int length);
    String getName();
}