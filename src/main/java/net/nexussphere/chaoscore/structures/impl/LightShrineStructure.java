package net.nexussphere.chaoscore.structures.impl;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.nexussphere.chaoscore.managers.LootManager;
import net.nexussphere.chaoscore.structures.BaseStructure;
public class LightShrineStructure extends BaseStructure {
    @Override public String getName() { return "Đàn Tế Thần Tinh Tú"; }
    @Override protected LootManager.LootType getLootType() { return LootManager.LootType.LIGHT; }
    @Override protected Block getFloorBlock() { return Blocks.SMOOTH_QUARTZ; }
    @Override protected void placeBlock(ServerWorld w, int x, int y, int z, int width, int height, int length, int ox, int oy, int oz) {
        int cx = 4, cz = 4;
        double dist = Math.sqrt(Math.pow(x - cx, 2) + Math.pow(z - cz, 2));
        if (y == 1) {
            if (dist < 3.5) setBlock(w, x, y, z, ox, oy, oz, Blocks.QUARTZ_BRICKS);
            else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            return;
        }
        boolean isPillar = (x==2 && z==2) || (x==6 && z==2) || (x==2 && z==6) || (x==6 && z==6);
        if (isPillar) {
            if (y >= 2 && y <= 5) setBlock(w, x, y, z, ox, oy, oz, Blocks.QUARTZ_PILLAR);
            else if (y == 6) setBlock(w, x, y, z, ox, oy, oz, Blocks.SEA_LANTERN);
            else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            return;
        }
        if (x == cx && z == cz) {
            if (y == 2) setBlock(w, x, y, z, ox, oy, oz, Blocks.GOLD_BLOCK);
            else if (y == 3) setBlock(w, x, y, z, ox, oy, oz, Blocks.BEACON);
            else if (y == 4) setBlock(w, x, y, z, ox, oy, oz, Blocks.YELLOW_STAINED_GLASS);
            else if (y == 5) setBlock(w, x, y, z, ox, oy, oz, Blocks.TINTED_GLASS);
            else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
        } else {
            setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
        }
    }
}