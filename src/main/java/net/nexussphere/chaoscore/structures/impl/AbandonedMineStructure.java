package net.nexussphere.chaoscore.structures.impl;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.server.world.ServerWorld;
import net.nexussphere.chaoscore.managers.LootManager;
import net.nexussphere.chaoscore.structures.BaseStructure;
public class AbandonedMineStructure extends BaseStructure {
    @Override public String getName() { return "Cửa Hầm Mỏ Cổ"; }
    @Override protected LootManager.LootType getLootType() { return LootManager.LootType.MINE; }
    @Override protected Block getFloorBlock() { return Blocks.COARSE_DIRT; }
    @Override protected void placeBlock(ServerWorld w, int x, int y, int z, int width, int height, int length, int ox, int oy, int oz) {
        if (y > 6) { setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR); return; }
        boolean arch = (x == 2 || x == 6) && y < 5;
        boolean roof = y == 5 && x >= 2 && x <= 6;
        if (arch || roof) {
            setBlock(w, x, y, z, ox, oy, oz, Blocks.OAK_LOG);
            return;
        }
        if (x > 2 && x < 6) {
            if (y == 1) {
                if (x == 4) setBlock(w, x, y, z, ox, oy, oz, Blocks.RAIL);
                else if (chance(20)) setBlock(w, x, y, z, ox, oy, oz, Blocks.COBBLESTONE);
                else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            }
            else if (y == 4) {
                if (chance(30)) setBlock(w, x, y, z, ox, oy, oz, Blocks.COBWEB);
                else if (x == 4 && z % 3 == 0) setBlock(w, x, y, z, ox, oy, oz, Blocks.IRON_BARS);
                else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            }
            else if (y == 3 && x == 4 && z % 3 == 0) {
                setBlock(w, x, y, z, ox, oy, oz, Blocks.LANTERN);
            }
            else {
                setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
                if (y == 1 && chance(5)) spawnMob(w, x, y, z, ox, oy, oz, EntityType.CAVE_SPIDER, "Miner Spider");
            }
        } else {
            if (y < 5) setBlock(w, x, y, z, ox, oy, oz, Blocks.STONE);
            else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
        }
    }
}