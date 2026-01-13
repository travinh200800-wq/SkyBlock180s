package net.nexussphere.chaoscore.managers;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.nexussphere.chaoscore.state.ChaosState;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
public class RegionManager {
    private final Map<UUID, BlockPos[]> selections = new HashMap<>();
    public void setPos1(UUID uuid, BlockPos pos) {
        selections.computeIfAbsent(uuid, k -> new BlockPos[2])[0] = pos;
    }
    public void setPos2(UUID uuid, BlockPos pos) {
        selections.computeIfAbsent(uuid, k -> new BlockPos[2])[1] = pos;
    }
    public boolean saveRegion(ServerPlayerEntity player, MinecraftServer server, int id) {
        BlockPos[] locs = selections.get(player.getUuid());
        if (locs == null || locs[0] == null || locs[1] == null) return false;
        ChaosState state = ChaosState.getServerState(server);
        if (id == 1) {
            state.setPos1_1(locs[0]);
            state.setPos2_1(locs[1]);
        } else {
            state.setPos1_2(locs[0]);
            state.setPos2_2(locs[1]);
        }
        state.markDirty(server);
        return true;
    }
}