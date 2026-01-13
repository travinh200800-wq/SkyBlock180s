package net.nexussphere.chaoscore.state;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.WorldSavePath;
import net.minecraft.util.math.BlockPos;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
public class ChaosState {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static ChaosState INSTANCE;
    public boolean isRunning = false;
    public int timer = 180 * 20;
    public int x1_1, y1_1, z1_1;
    public int x2_1, y2_1, z2_1;
    public String world1_id = "minecraft:overworld";
    public int x1_2, y1_2, z1_2;
    public int x2_2, y2_2, z2_2;
    public String world2_id = "minecraft:overworld";
    public static ChaosState getServerState(MinecraftServer server) {
        if (INSTANCE == null) {
            load(server);
        }
        return INSTANCE;
    }
    public void markDirty(MinecraftServer server) {
        save(server);
    }
    public void setPos1_1(BlockPos pos) { this.x1_1 = pos.getX(); this.y1_1 = pos.getY(); this.z1_1 = pos.getZ(); }
    public void setPos2_1(BlockPos pos) { this.x2_1 = pos.getX(); this.y2_1 = pos.getY(); this.z2_1 = pos.getZ(); }
    public BlockPos getPos1_1() { return new BlockPos(x1_1, y1_1, z1_1); }
    public BlockPos getPos2_1() { return new BlockPos(x2_1, y2_1, z2_1); }
    public void setPos1_2(BlockPos pos) { this.x1_2 = pos.getX(); this.y1_2 = pos.getY(); this.z1_2 = pos.getZ(); }
    public void setPos2_2(BlockPos pos) { this.x2_2 = pos.getX(); this.y2_2 = pos.getY(); this.z2_2 = pos.getZ(); }
    public BlockPos getPos1_2() { return new BlockPos(x1_2, y1_2, z1_2); }
    public BlockPos getPos2_2() { return new BlockPos(x2_2, y2_2, z2_2); }
    private static File getConfigFile(MinecraftServer server) {
        Path savePath = server.getSavePath(WorldSavePath.ROOT);
        return savePath.resolve("chaos_state.json").toFile();
    }
    private static void load(MinecraftServer server) {
        File file = getConfigFile(server);
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                INSTANCE = GSON.fromJson(reader, ChaosState.class);
            } catch (IOException e) {
                INSTANCE = new ChaosState();
            }
        }
        if (INSTANCE == null) INSTANCE = new ChaosState();
    }
    private void save(MinecraftServer server) {
        File file = getConfigFile(server);
        try (FileWriter writer = new FileWriter(file)) {
            GSON.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}