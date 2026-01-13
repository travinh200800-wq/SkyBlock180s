package net.nexussphere.chaoscore.structures.impl;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.server.world.ServerWorld;
import net.nexussphere.chaoscore.managers.LootManager;
import net.nexussphere.chaoscore.structures.BaseStructure;
public class MagmaCoreStructure extends BaseStructure {
    @Override public String getName() { return "Lõi Địa Ngục"; }
    @Override protected LootManager.LootType getLootType() { return LootManager.LootType.NETHER; }
    @Override protected Block getFloorBlock() { return Blocks.NETHER_BRICKS; }
    @Override protected void placeBlock(ServerWorld w, int x, int y, int z, int width, int height, int length, int ox, int oy, int oz) {
        double cx = width / 2.0; double cy = 2.5; double cz = length / 2.0; double radius = 3.5;
        double distSq = Math.pow(x - cx, 2) + Math.pow(y - cy, 2) + Math.pow(z - cz, 2); double rSq = radius * radius;
        if (distSq <= rSq) {
            if (distSq > rSq * 0.7) { if (chance(15)) setBlock(w, x, y, z, ox, oy, oz, Blocks.CRYING_OBSIDIAN); else if (chance(30)) setBlock(w, x, y, z, ox, oy, oz, Blocks.MAGMA_BLOCK); else setBlock(w, x, y, z, ox, oy, oz, Blocks.OBSIDIAN); }
            else if (distSq > rSq * 0.4) setBlock(w, x, y, z, ox, oy, oz, Blocks.MAGMA_BLOCK);
            else {
                if (y < 2) { setBlock(w, x, y, z, ox, oy, oz, Blocks.LAVA); if (chance(5) && y == 1) spawnMob(w, x, y, z, ox, oy, oz, EntityType.STRIDER, "Strider"); }
                else { setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR); if (chance(2) && y == 2) spawnMob(w, x, y, z, ox, oy, oz, EntityType.MAGMA_CUBE, "Magma Cube"); }
            }
        } else { if (y > 0 && distSq < rSq * 1.5 && chance(10)) setBlock(w, x, y, z, ox, oy, oz, Blocks.BASALT); else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR); }
    }
}