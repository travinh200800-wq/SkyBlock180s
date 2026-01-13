package net.nexussphere.chaoscore.structures.impl;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.EndRodBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Direction;
import net.nexussphere.chaoscore.managers.LootManager;
import net.nexussphere.chaoscore.structures.BaseStructure;
public class VoidLibraryStructure extends BaseStructure {
    @Override public String getName() { return "Thư Viện Hư Không"; }
    @Override protected LootManager.LootType getLootType() { return LootManager.LootType.END; }
    @Override protected Block getFloorBlock() { return Blocks.END_STONE_BRICKS; }
    @Override protected void placeBlock(ServerWorld w, int x, int y, int z, int width, int height, int length, int ox, int oy, int oz) {
        if (y > 9) { setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR); return; }
        if ((x == 1 || x == width - 2) && (z == 1 || z == length - 2)) {
            setBlock(w, x, y, z, ox, oy, oz, Blocks.PURPUR_PILLAR);
            return;
        }
        if (y < 5 && y > 0) {
            boolean isWall = x == 0 || x == width - 1 || z == 0 || z == length - 1;
            if (isWall) {
                if (x == width/2 || z == length/2) setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
                else setBlock(w, x, y, z, ox, oy, oz, Blocks.BOOKSHELF);
            } else {
                setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
                if (y == 1 && x == width/2 && z == length/2) {
                    setBlock(w, x, y, z, ox, oy, oz, Blocks.ENCHANTING_TABLE);
                    spawnMob(w, x, y, z, ox, oy, oz, EntityType.ENDERMAN, "Guardian");
                }
            }
            return;
        }
        if (y == 5) {
            if (x > 2 && x < width - 3 && z > 2 && z < length - 3) setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            else setBlock(w, x, y, z, ox, oy, oz, Blocks.PURPUR_BLOCK);
            return;
        }
        if (y > 5 && y < 9) {
            if (x == 2 || x == width - 3 || z == 2 || z == length - 3) {
                if (y == 6) {
                    if ((x==2||x==width-3) && (z==2||z==length-3)) setBlock(w, x, y, z, ox, oy, oz, Blocks.SEA_LANTERN);
                    else setBlock(w, x, y, z, ox, oy, oz, Blocks.PURPUR_SLAB);
                }
                else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            }
            else if ((x == 1 || x == width - 2) && (z == 1 || z == length - 2)) {
                if (y == 6) w.setBlockState(new net.minecraft.util.math.BlockPos(ox+x, oy+y, oz+z),
                        Blocks.END_ROD.getDefaultState().with(EndRodBlock.FACING, Direction.UP));
                else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            }
            else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
        }
        if (y == 9) setBlock(w, x, y, z, ox, oy, oz, Blocks.PURPUR_SLAB);
    }
}