package net.nexussphere.chaoscore.structures.impl;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.server.world.ServerWorld;
import net.nexussphere.chaoscore.managers.LootManager;
import net.nexussphere.chaoscore.structures.BaseStructure;
public class RuinedPortalStructure extends BaseStructure {
    @Override public String getName() { return "Cổng Địa Ngục Đổ Nát"; }
    @Override protected LootManager.LootType getLootType() { return LootManager.LootType.NETHER; }
    @Override protected Block getFloorBlock() { return Blocks.NETHERRACK; }
    @Override protected void placeBlock(ServerWorld w, int x, int y, int z, int width, int height, int length, int ox, int oy, int oz) {
        if (y > 6) { setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR); return; }
        int cx = width / 2;
        boolean isPortalFrame = (x == cx - 2 || x == cx + 2) && (y > 0 && y < 5);
        boolean isPortalTop = (y == 5 || y == 1) && (x > cx - 2 && x < cx + 2);
        if (z == length / 2) {
            if (isPortalFrame || isPortalTop) {
                if (chance(20)) setBlock(w, x, y, z, ox, oy, oz, Blocks.CRYING_OBSIDIAN);
                else if (chance(10)) setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
                else { setBlock(w, x, y, z, ox, oy, oz, Blocks.OBSIDIAN); if (chance(10)) setBlock(w, x, y, z, ox, oy, oz, Blocks.GOLD_BLOCK); }
            } else if (x > cx - 2 && x < cx + 2 && y > 1 && y < 5) {
                if (chance(20)) setBlock(w, x, y, z, ox, oy, oz, Blocks.FIRE); else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            } else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
        } else {
            double dist = Math.sqrt(Math.pow(x-cx, 2) + Math.pow(z-length/2, 2));
            if (y == 1 && dist < 3) {
                setBlock(w, x, y, z, ox, oy, oz, pickOne(Blocks.NETHERRACK, Blocks.MAGMA_BLOCK));
                if (chance(2)) spawnMob(w, x, y, z, ox, oy, oz, EntityType.ZOMBIFIED_PIGLIN, "Zombified Piglin");
            } else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
        }
    }
}