package net.nexussphere.chaoscore.structures.impl;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.nexussphere.chaoscore.managers.LootManager;
import net.nexussphere.chaoscore.structures.BaseStructure;
public class GuardianOutpostStructure extends BaseStructure {
    @Override public String getName() { return "Pháo Đài Thủ Vệ"; }
    @Override protected LootManager.LootType getLootType() { return LootManager.LootType.WAR; }
    @Override protected Block getFloorBlock() { return Blocks.POLISHED_ANDESITE; }
    @Override protected void placeBlock(ServerWorld w, int x, int y, int z, int width, int height, int length, int ox, int oy, int oz) {
        if (y > 10) { setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR); return; }
        boolean isCorner = (x == 0 || x == 8) && (z == 0 || z == 8);
        if (isCorner && y <= 8) {
            setBlock(w, x, y, z, ox, oy, oz, Blocks.STONE_BRICKS);
            return;
        }
        boolean isWall = x == 0 || x == 8 || z == 0 || z == 8;
        if (isWall && y <= 4) {
            if (y == 2 && (x == 4 || z == 4)) {
                setBlock(w, x, y, z, ox, oy, oz, Blocks.CRACKED_STONE_BRICKS);
            } else {
                setBlock(w, x, y, z, ox, oy, oz, Blocks.STONE_BRICKS);
            }
            return;
        }
        if (y == 5) {
            setBlock(w, x, y, z, ox, oy, oz, Blocks.POLISHED_ANDESITE);
            return;
        }
        if (y >= 6 && y <= 9) {
            if (x >= 2 && x <= 6 && z >= 2 && z <= 6) {
                boolean innerWall = x == 2 || x == 6 || z == 2 || z == 6;
                if (innerWall) {
                    if (y == 7) setBlock(w, x, y, z, ox, oy, oz, Blocks.IRON_BARS);
                    else setBlock(w, x, y, z, ox, oy, oz, Blocks.COBBLESTONE_WALL);
                } else {
                    setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
                    if (y == 6 && x == 4 && z == 4) spawnMob(w, x, y, z, ox, oy, oz, EntityType.PILLAGER, "Commander");
                }
            } else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            return;
        }
        if (y == 10) {
            if ((x == 2 || x == 6 || z == 2 || z == 6) && (x >= 2 && x <= 6 && z >= 2 && z <= 6)) {
                if ((x+z)%2 == 0) setBlock(w, x, y, z, ox, oy, oz, Blocks.STONE_BRICK_SLAB);
                else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            } else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
        } else {
            setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
        }
    }
}