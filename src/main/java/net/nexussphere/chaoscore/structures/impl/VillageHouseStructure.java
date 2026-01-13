package net.nexussphere.chaoscore.structures.impl;
import net.minecraft.block.*;
import net.minecraft.block.enums.BedPart;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.EntityType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.nexussphere.chaoscore.managers.LootManager;
import net.nexussphere.chaoscore.structures.BaseStructure;
public class VillageHouseStructure extends BaseStructure {
    @Override public String getName() { return "Khu Dân Cư"; }
    @Override protected LootManager.LootType getLootType() { return LootManager.LootType.GARDEN; }
    @Override protected Block getFloorBlock() { return Blocks.COBBLESTONE; }
    @Override protected void placeBlock(ServerWorld w, int x, int y, int z, int width, int height, int length, int ox, int oy, int oz) {
        if (x == 4) {
            if (y == 0 && z >= 1 && z <= 7) setBlock(w, x, y, z, ox, oy, oz, Blocks.GRAVEL);
            else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            return;
        }
        int zStart = 2;
        int zEnd = 6;
        if (z < zStart || z > zEnd) {
            setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            return;
        }
        boolean isHouse1 = x < 4;
        int xStartHouse = isHouse1 ? 0 : 5;
        int localX = x - xStartHouse;
        int maxLocalX = 3;
        if (y == 1 && localX == 1 && z == zStart + 2) return;
        if (localX == 0 || localX == maxLocalX || z == zStart || z == zEnd) {
            if (y < 4) {
                if (z == 4 && y < 3) {
                    if (localX == maxLocalX && isHouse1) return;
                    if (localX == 0 && !isHouse1) return;
                }
                if (y == 2 && localX > 0 && localX < maxLocalX) setBlock(w, x, y, z, ox, oy, oz, Blocks.GLASS_PANE);
                else setBlock(w, x, y, z, ox, oy, oz, Blocks.OAK_PLANKS);
            } else if (y == 4) {
                setBlock(w, x, y, z, ox, oy, oz, Blocks.OAK_LOG);
            } else {
                setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            }
        } else {
            if (y == 0) { setBlock(w, x, y, z, ox, oy, oz, Blocks.OAK_PLANKS); return; }
            setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            if (y == 1) {
                if (localX == 1 && z == zStart + 1) {
                    BlockPos headPos = new BlockPos(ox+x, oy+y, oz+z);
                    BlockPos footPos = new BlockPos(ox+x, oy+y, oz+z+1); // Z+1
                    w.setBlockState(headPos, (isHouse1 ? Blocks.RED_BED : Blocks.YELLOW_BED).getDefaultState().with(BedBlock.PART, BedPart.HEAD).with(BedBlock.FACING, Direction.SOUTH));
                    w.setBlockState(footPos, (isHouse1 ? Blocks.RED_BED : Blocks.YELLOW_BED).getDefaultState().with(BedBlock.PART, BedPart.FOOT).with(BedBlock.FACING, Direction.SOUTH));
                }
                else if (z == zEnd - 1 && localX == 2) setBlock(w, x, y, z, ox, oy, oz, Blocks.CRAFTING_TABLE);
                if (localX == 2 && z == 4) {
                    spawnMob(w, x, y, z, ox, oy, oz, EntityType.VILLAGER, isHouse1 ? "Chủ nhà A" : "Chủ nhà B");
                }
            }
        }
        if (z == 4) {
            if (x == 3) {
                if (y == 1) w.setBlockState(new BlockPos(ox+x, oy+y, oz+z), Blocks.OAK_DOOR.getDefaultState().with(DoorBlock.FACING, Direction.EAST).with(DoorBlock.HALF, DoubleBlockHalf.LOWER));
                if (y == 2) w.setBlockState(new BlockPos(ox+x, oy+y, oz+z), Blocks.OAK_DOOR.getDefaultState().with(DoorBlock.FACING, Direction.EAST).with(DoorBlock.HALF, DoubleBlockHalf.UPPER));
            }
            if (x == 5) {
                if (y == 1) w.setBlockState(new BlockPos(ox+x, oy+y, oz+z), Blocks.OAK_DOOR.getDefaultState().with(DoorBlock.FACING, Direction.WEST).with(DoorBlock.HALF, DoubleBlockHalf.LOWER));
                if (y == 2) w.setBlockState(new BlockPos(ox+x, oy+y, oz+z), Blocks.OAK_DOOR.getDefaultState().with(DoorBlock.FACING, Direction.WEST).with(DoorBlock.HALF, DoubleBlockHalf.UPPER));
            }
        }
    }
}