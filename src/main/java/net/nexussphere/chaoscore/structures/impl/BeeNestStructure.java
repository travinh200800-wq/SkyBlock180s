package net.nexussphere.chaoscore.structures.impl;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.server.world.ServerWorld;
import net.nexussphere.chaoscore.managers.LootManager;
import net.nexussphere.chaoscore.structures.BaseStructure;
public class BeeNestStructure extends BaseStructure {
    @Override public String getName() { return "Đại Thụ Mật Ong"; }
    @Override protected LootManager.LootType getLootType() { return LootManager.LootType.GARDEN; }
    @Override protected Block getFloorBlock() { return Blocks.GRASS_BLOCK; }
    @Override protected void placeBlock(ServerWorld w, int x, int y, int z, int width, int height, int length, int ox, int oy, int oz) {
        int cx = width / 2; int cz = length / 2;
        int treeHeight = 10;
        if (y > treeHeight + 3) { setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR); return; }
        double dist = Math.sqrt(Math.pow(x - cx, 2) + Math.pow(z - cz, 2));
        boolean isTrunk = (Math.abs(x - cx) <= 1 && z == cz) || (Math.abs(z - cz) <= 1 && x == cx);
        if (isTrunk && y < treeHeight - 2) {
            setBlock(w, x, y, z, ox, oy, oz, Blocks.OAK_LOG);
            return;
        }
        if (y >= treeHeight - 4) {
            double radius = 4.0;
            if (y >= treeHeight) radius = 2.5;
            if (dist <= radius) {
                setBlock(w, x, y, z, ox, oy, oz, Blocks.OAK_LEAVES);
                if (chance(8)) setBlock(w, x, y, z, ox, oy, oz, Blocks.SHROOMLIGHT);
            } else {
                setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            }
            return;
        }
        if (y == treeHeight - 5) {
            if (dist <= 3.0 && dist > 1.5) {
                if (chance(20)) {
                    w.setBlockState(new net.minecraft.util.math.BlockPos(ox+x, oy+y, oz+z),
                            Blocks.BEE_NEST.getDefaultState().with(BeehiveBlock.HONEY_LEVEL, 5));
                    if (chance(60)) spawnMob(w, x, y, z, ox, oy, oz, EntityType.BEE, "Worker Bee");
                } else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            } else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            return;
        }
        setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
    }
}