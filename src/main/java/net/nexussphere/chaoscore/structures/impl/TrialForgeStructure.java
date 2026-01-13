package net.nexussphere.chaoscore.structures.impl;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.nexussphere.chaoscore.managers.LootManager;
import net.nexussphere.chaoscore.structures.BaseStructure;
public class TrialForgeStructure extends BaseStructure {
    @Override public String getName() { return "Lò Luyện Ngục"; }
    @Override protected LootManager.LootType getLootType() { return LootManager.LootType.FORGE; }
    @Override protected Block getFloorBlock() { return Blocks.POLISHED_TUFF; }
    @Override protected void placeBlock(ServerWorld w, int x, int y, int z, int width, int height, int length, int ox, int oy, int oz) {
        if (y > 8) { setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR); return; }
        if (x <= 2 && z <= 2) {
            boolean isWall = x == 0 || z == 0 || x == 2 || z == 2;
            if (y >= 1 && y <= 3) {
                if (isWall) setBlock(w, x, y, z, ox, oy, oz, Blocks.TINTED_GLASS);
                else setBlock(w, x, y, z, ox, oy, oz, Blocks.LAVA);
            }
            else if (y == 4) setBlock(w, x, y, z, ox, oy, oz, Blocks.CHISELED_COPPER);
            return;
        }
        if (x >= 6 && z >= 6) {
            if (y <= 6) setBlock(w, x, y, z, ox, oy, oz, Blocks.BRICKS);
            else if (y == 7) setBlock(w, x, y, z, ox, oy, oz, Blocks.CAMPFIRE);
            else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            return;
        }
        if (y == 1) {
            if (x == 4 && z == 4) {
                setBlock(w, x, y, z, ox, oy, oz, Blocks.VAULT);
            }
            else if (x == 4 && z == 2) {
                setBlock(w, x, y, z, ox, oy, oz, Blocks.TRIAL_SPAWNER);
                LootManager.createLootChest(w, new BlockPos(ox+x, oy+y+1, oz+z), LootManager.LootType.TRIAL_OMINOUS);
            }
            else if (x == 5 && z == 4) setBlock(w, x, y, z, ox, oy, oz, Blocks.GRINDSTONE);
            else if (x == 3 && z == 4) setBlock(w, x, y, z, ox, oy, oz, Blocks.ANVIL);
            else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            if (x == 5 && z == 2) spawnMob(w, x, y, z, ox, oy, oz, EntityType.BREEZE, "Forge Guardian");
        }
        else if (y >= 4 && y <= 5 && x == 4 && z == 4) {
            setBlock(w, x, y, z, ox, oy, oz, Blocks.IRON_BARS);
        }
        else if (y == 3 && x == 4 && z == 4) {
            setBlock(w, x, y, z, ox, oy, oz, Blocks.HEAVY_CORE);
        }
        else {
            setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
        }
    }
}