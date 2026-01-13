package net.nexussphere.chaoscore.structures.impl;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.nexussphere.chaoscore.managers.LootManager;
import net.nexussphere.chaoscore.structures.BaseStructure;
public class FloatingMushroomStructure extends BaseStructure {
    @Override public String getName() { return "Nấm Thần Khổng Lồ"; }
    @Override protected LootManager.LootType getLootType() { return LootManager.LootType.GARDEN; }
    @Override protected Block getFloorBlock() { return Blocks.MYCELIUM; }
    @Override protected void placeBlock(ServerWorld w, int x, int y, int z, int width, int height, int length, int ox, int oy, int oz) {
        int cx = 4, cz = 4;
        double dist = Math.sqrt(Math.pow(x - cx, 2) + Math.pow(z - cz, 2));
        if (y < 5) {
            if (dist <= 1.2) setBlock(w, x, y, z, ox, oy, oz, Blocks.MUSHROOM_STEM);
            else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            return;
        }
        if (y >= 5 && y <= 8) {
            double radius = 4.5 - (y - 5) * 0.8;
            if (dist <= radius) {
                if (chance(10)) setBlock(w, x, y, z, ox, oy, oz, Blocks.SHROOMLIGHT);
                else setBlock(w, x, y, z, ox, oy, oz, Blocks.RED_MUSHROOM_BLOCK);
            }
            else if (y == 5 && dist <= radius + 1 && chance(20)) {
                setBlock(w, x, y-1, z, ox, oy, oz, Blocks.ORANGE_STAINED_GLASS_PANE);
                setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            }
            else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
        } else {
            setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
        }
    }
}