package net.nexussphere.chaoscore.structures.impl;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.nexussphere.chaoscore.managers.LootManager;
import net.nexussphere.chaoscore.structures.BaseStructure;
public class CactusGardenStructure extends BaseStructure {
    @Override public String getName() { return "Ốc Đảo Xanh"; }
    @Override protected LootManager.LootType getLootType() { return LootManager.LootType.DESERT; }
    @Override protected Block getFloorBlock() { return Blocks.SANDSTONE; }
    @Override protected void placeBlock(ServerWorld w, int x, int y, int z, int width, int height, int length, int ox, int oy, int oz) {
        int cx = 4, cz = 4;
        double dist = Math.sqrt(Math.pow(x - cx, 2) + Math.pow(z - cz, 2));
        if (y == 1) {
            if (dist < 2.0) setBlock(w, x, y, z, ox, oy, oz, Blocks.WATER);
            else if (dist < 2.8) setBlock(w, x, y, z, ox, oy, oz, Blocks.GRASS_BLOCK);
            else setBlock(w, x, y, z, ox, oy, oz, Blocks.SAND);
            return;
        }
        if (y >= 2) {
            if (x == 6 && z == 6) {
                if (y < 6) setBlock(w, x, y, z, ox, oy, oz, Blocks.JUNGLE_LOG);
                else if (y == 6) {
                    w.setBlockState(new BlockPos(ox+x, oy+y, oz+z), Blocks.JUNGLE_LEAVES.getDefaultState());
                    w.setBlockState(new BlockPos(ox+x+1, oy+y-1, oz+z), Blocks.JUNGLE_LEAVES.getDefaultState());
                    w.setBlockState(new BlockPos(ox+x-1, oy+y-1, oz+z), Blocks.JUNGLE_LEAVES.getDefaultState());
                    w.setBlockState(new BlockPos(ox+x, oy+y-1, oz+z+1), Blocks.JUNGLE_LEAVES.getDefaultState());
                    w.setBlockState(new BlockPos(ox+x, oy+y-1, oz+z-1), Blocks.JUNGLE_LEAVES.getDefaultState());
                }
                return;
            }
            Block blockBelow = w.getBlockState(new BlockPos(ox+x, oy+y-1, oz+z)).getBlock();
            if (blockBelow == Blocks.SAND) {
                if (chance(15)) setBlock(w, x, y, z, ox, oy, oz, Blocks.CACTUS);
                else if (chance(10) && y == 2) {
                    setBlock(w, x, y, z, ox, oy, oz, Blocks.CACTUS);
                    setBlock(w, x, y+1, z, ox, oy, oz, Blocks.CACTUS);
                }
                else if (chance(10)) setBlock(w, x, y, z, ox, oy, oz, Blocks.DEAD_BUSH);
                else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            } else {
                setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            }
            if (y == 2 && chance(5)) spawnMob(w, x, y, z, ox, oy, oz, EntityType.RABBIT, "Desert Rabbit");
        }
    }
}