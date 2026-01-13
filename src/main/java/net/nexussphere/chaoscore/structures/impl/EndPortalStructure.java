package net.nexussphere.chaoscore.structures.impl;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.EndPortalFrameBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Direction;
import net.nexussphere.chaoscore.managers.LootManager;
import net.nexussphere.chaoscore.structures.BaseStructure;
public class EndPortalStructure extends BaseStructure {
    @Override public String getName() { return "Phòng Cổng Kết Thúc"; }
    @Override protected LootManager.LootType getLootType() { return LootManager.LootType.STRONGHOLD; }
    @Override protected Block getFloorBlock() { return Blocks.STONE_BRICKS; }
    @Override protected void placeBlock(ServerWorld w, int x, int y, int z, int width, int height, int length, int ox, int oy, int oz) {
        int cx = width / 2; int cz = length / 2;
        boolean wall = x == 0 || x == width - 1 || z == 0 || z == length - 1;
        if (wall) {
            if (y < 4) setBlock(w, x, y, z, ox, oy, oz, Blocks.STONE_BRICKS);
            else if (y == 4) setBlock(w, x, y, z, ox, oy, oz, Blocks.STONE_BRICK_SLAB);
            return;
        }
        if (y == 0) return;
        if (y == 1) {
            if (Math.abs(x - cx) <= 1 && Math.abs(z - cz) <= 1) setBlock(w, x, y, z, ox, oy, oz, Blocks.LAVA);
            else setBlock(w, x, y, z, ox, oy, oz, Blocks.STONE_BRICKS);
            return;
        }
        if (y == 2) {
            boolean isFrame = false; Direction facing = Direction.NORTH;
            if (z == cz - 2 && Math.abs(x - cx) <= 1) { isFrame = true; facing = Direction.SOUTH; }
            else if (z == cz + 2 && Math.abs(x - cx) <= 1) { isFrame = true; facing = Direction.NORTH; }
            else if (x == cx + 2 && Math.abs(z - cz) <= 1) { isFrame = true; facing = Direction.WEST; }
            else if (x == cx - 2 && Math.abs(z - cz) <= 1) { isFrame = true; facing = Direction.EAST; }
            if (isFrame) w.setBlockState(new net.minecraft.util.math.BlockPos(ox + x, oy + y, oz + z), Blocks.END_PORTAL_FRAME.getDefaultState().with(EndPortalFrameBlock.FACING, facing).with(EndPortalFrameBlock.EYE, chance(30)));
            else if (Math.abs(x - cx) > 2 || Math.abs(z - cz) > 2) {
                if (x == cx - 3 || x == cx + 3 || z == cz - 3 || z == cz + 3) setBlock(w, x, y, z, ox, oy, oz, Blocks.STONE_BRICK_STAIRS);
                else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            } else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            if (x == cx && z == cz + 3) spawnMob(w, x, y, z, ox, oy, oz, EntityType.SILVERFISH, "Silverfish");
            return;
        }
        setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
    }
}