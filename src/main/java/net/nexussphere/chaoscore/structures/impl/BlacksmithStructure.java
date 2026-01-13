package net.nexussphere.chaoscore.structures.impl;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.nexussphere.chaoscore.managers.LootManager;
import net.nexussphere.chaoscore.structures.BaseStructure;
public class BlacksmithStructure extends BaseStructure {
    @Override public String getName() { return "Lò Rèn Chiến Tranh"; }
    @Override protected LootManager.LootType getLootType() { return LootManager.LootType.ORE; }
    @Override protected Block getFloorBlock() { return Blocks.COBBLESTONE; }
    @Override protected void placeBlock(ServerWorld w, int x, int y, int z, int width, int height, int length, int ox, int oy, int oz) {
        if (x == 7 && z == length - 1) {
            if (y >= 1 && y <= 8) {
                setBlock(w, x, y, z, ox, oy, oz, Blocks.BRICKS);
                return;
            }
            if (y == 9) {
                setBlock(w, x, y, z, ox, oy, oz, Blocks.CAMPFIRE);
                return;
            }
        }
        if (y > 6) {
            setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            return;
        }
        boolean wall = x == 0 || x == 8 || z == 0 || z == 8;
        if (wall) {
            if (y < 4) {
                boolean nearLava = (x == 0 && z <= 3) || (z == 0 && x <= 3);
                if ((x==0||x==8) && (z==0||z==8)) setBlock(w, x, y, z, ox, oy, oz, Blocks.OAK_LOG);
                else if (nearLava) setBlock(w, x, y, z, ox, oy, oz, Blocks.COBBLESTONE);
                else if (x <= 3 && z <= 3) setBlock(w, x, y, z, ox, oy, oz, Blocks.COBBLESTONE);
                else setBlock(w, x, y, z, ox, oy, oz, Blocks.OAK_PLANKS);
            }
            else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
        } else {
            if (y == 0) return;
            if (y == 4) {
                setBlock(w, x, y, z, ox, oy, oz, Blocks.STONE_BRICK_SLAB);
                return;
            }
            boolean isLavaPool = (x <= 2 && z <= 2);
            boolean isLavaBorder = (x == 3 && z <= 3) || (z == 3 && x <= 3);
            if (y == 1) {
                if (isLavaPool) {
                    setBlock(w, x, y, z, ox, oy, oz, Blocks.LAVA);
                    return;
                }
                if (isLavaBorder) {
                    setBlock(w, x, y, z, ox, oy, oz, Blocks.COBBLESTONE);
                    return;
                }
            }
            if (y == 2 && isLavaBorder) {
                setBlock(w, x, y, z, ox, oy, oz, Blocks.IRON_BARS);
                return;
            }
            if (y == 1) {
                if (x == 7 && z == 7) w.setBlockState(new BlockPos(ox+x, oy+y, oz+z), Blocks.FURNACE.getDefaultState().with(AbstractFurnaceBlock.FACING, Direction.WEST));
                else if (x == 7 && z == 6) w.setBlockState(new BlockPos(ox+x, oy+y, oz+z), Blocks.BLAST_FURNACE.getDefaultState().with(AbstractFurnaceBlock.FACING, Direction.WEST));
                else if (x == 7 && z == 2) setBlock(w, x, y, z, ox, oy, oz, Blocks.ANVIL);
                else if (x == 6 && z == 2) setBlock(w, x, y, z, ox, oy, oz, Blocks.GRINDSTONE);
                else if (x == 4 && z == 4) spawnMob(w, x, y, z, ox, oy, oz, EntityType.IRON_GOLEM, "Smith Golem");
                else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            }
            else {
                setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            }
        }
    }
}