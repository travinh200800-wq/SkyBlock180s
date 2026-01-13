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
public class WitchTowerStructure extends BaseStructure {
    @Override public String getName() { return "Nhà Chòi Phù Thủy"; }
    @Override protected LootManager.LootType getLootType() { return LootManager.LootType.WITCH; }
    @Override protected Block getFloorBlock() { return Blocks.MUD; }
    @Override protected void placeBlock(ServerWorld w, int x, int y, int z, int width, int height, int length, int ox, int oy, int oz) {
        if (y > 10) { setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR); return; }
        boolean isSupport = (x == 1 || x == 7) && (z == 1 || z == 7);
        if (isSupport && y < 4) {
            setBlock(w, x, y, z, ox, oy, oz, Blocks.STRIPPED_SPRUCE_LOG);
            return;
        }
        if (y == 4) {
            if (x >= 1 && x <= 7 && z >= 1 && z <= 7) setBlock(w, x, y, z, ox, oy, oz, Blocks.SPRUCE_PLANKS);
            else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            if (x == 4 && z == 0) w.setBlockState(new BlockPos(ox+x, oy+y, oz+z), Blocks.SPRUCE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.NORTH));
            return;
        }
        if (y >= 5 && y <= 7) {
            boolean wall = x == 1 || x == 7 || z == 1 || z == 7;
            if (wall) {
                if (y == 6 && (x==4 || z==4)) setBlock(w, x, y, z, ox, oy, oz, Blocks.OAK_FENCE);
                else if (y==5 && x==4 && z==1) setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
                else setBlock(w, x, y, z, ox, oy, oz, Blocks.STRIPPED_DARK_OAK_LOG);
            } else {
                setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
                if (y == 5) {
                    if (x==6 && z==6) setBlock(w, x, y, z, ox, oy, oz, Blocks.CAULDRON);
                    if (x==2 && z==6) setBlock(w, x, y, z, ox, oy, oz, Blocks.CRAFTING_TABLE);
                    if (x==4 && z==4) spawnMob(w, x, y, z, ox, oy, oz, EntityType.WITCH, "Swamp Witch");
                }
            }
            return;
        }
        if (y >= 8) {
            int roofLayer = y - 8;
            int min = 0 + roofLayer;
            int max = 8 - roofLayer;
            if (x >= min && x <= max && z >= min && z <= max) {
                if (x == min || x == max || z == min || z == max) setBlock(w, x, y, z, ox, oy, oz, Blocks.DARK_OAK_PLANKS);
                else setBlock(w, x, y, z, ox, oy, oz, Blocks.SPRUCE_SLAB);
            } else {
                setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            }
        }
    }
}