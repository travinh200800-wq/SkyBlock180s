package net.nexussphere.chaoscore.structures.impl;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.nexussphere.chaoscore.managers.LootManager;
import net.nexussphere.chaoscore.structures.BaseStructure;
public class AncientFountainStructure extends BaseStructure {
    @Override public String getName() { return "Đài Phun Nước Cổ"; }
    @Override protected LootManager.LootType getLootType() { return LootManager.LootType.LIGHT; }
    @Override protected Block getFloorBlock() { return Blocks.MOSSY_STONE_BRICKS; }
    @Override protected void placeBlock(ServerWorld w, int x, int y, int z, int width, int height, int length, int ox, int oy, int oz) {
        int cx = 4, cz = 4;
        if (y == 1) {
            if ((x==1 && z==1) || (x==7 && z==1) || (x==1 && z==7) || (x==7 && z==7)) {
                setBlock(w, x, y, z, ox, oy, oz, Blocks.MOSSY_STONE_BRICK_SLAB);
            }
            else if (z == 1 && x > 1 && x < 7) setStair(w, x, y, z, ox, oy, oz, Direction.SOUTH);
            else if (z == 7 && x > 1 && x < 7) setStair(w, x, y, z, ox, oy, oz, Direction.NORTH);
            else if (x == 1 && z > 1 && z < 7) setStair(w, x, y, z, ox, oy, oz, Direction.EAST);
            else if (x == 7 && z > 1 && z < 7) setStair(w, x, y, z, ox, oy, oz, Direction.WEST);
            else if (x > 1 && x < 7 && z > 1 && z < 7) setBlock(w, x, y, z, ox, oy, oz, Blocks.WATER);
            else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            return;
        }
        if (x == cx && z == cz) {
            if (y < 6) setBlock(w, x, y, z, ox, oy, oz, Blocks.MOSSY_COBBLESTONE_WALL);
            else if (y == 6) setBlock(w, x, y, z, ox, oy, oz, Blocks.WATER);
            else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
        } else {
            setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
        }
    }
    private void setStair(ServerWorld w, int x, int y, int z, int ox, int oy, int oz, Direction facing) {
        w.setBlockState(new BlockPos(ox + x, oy + y, oz + z),
                Blocks.STONE_BRICK_STAIRS.getDefaultState().with(StairsBlock.FACING, facing));
    }
}