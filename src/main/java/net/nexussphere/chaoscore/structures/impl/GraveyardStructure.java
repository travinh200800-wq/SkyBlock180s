package net.nexussphere.chaoscore.structures.impl;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.server.world.ServerWorld;
import net.nexussphere.chaoscore.managers.LootManager;
import net.nexussphere.chaoscore.structures.BaseStructure;
public class GraveyardStructure extends BaseStructure {
    @Override public String getName() { return "Nghĩa Địa Bị Nguyền"; }
    @Override protected LootManager.LootType getLootType() { return LootManager.LootType.GRAVEYARD; }
    @Override protected Block getFloorBlock() { return Blocks.PODZOL; }
    @Override protected void placeBlock(ServerWorld w, int x, int y, int z, int width, int height, int length, int ox, int oy, int oz) {
        if (x == 0 || x == width - 1 || z == 0 || z == length - 1) {
            if (y == 1) setBlock(w, x, y, z, ox, oy, oz, Blocks.IRON_BARS);
            else if (y == 2 && (x+z)%2==0) setBlock(w, x, y, z, ox, oy, oz, Blocks.IRON_BARS);
            else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            return;
        }
        if (y == 1) {
            if (chance(20)) setBlock(w, x, y, z, ox, oy, oz, Blocks.COBBLESTONE);
            else if (chance(10)) setBlock(w, x, y, z, ox, oy, oz, Blocks.COARSE_DIRT);
            else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            if (chance(3)) spawnMob(w, x, y, z, ox, oy, oz, EntityType.ZOMBIE, "Zombie");
            else if (chance(2)) spawnMob(w, x, y, z, ox, oy, oz, EntityType.SKELETON, "Skeleton");
        } else if (y > 1) {
            if (x % 3 == 0 && z % 3 == 0 && y == 2) { setBlock(w, x, y, z, ox, oy, oz, pickOne(Blocks.MOSSY_COBBLESTONE_WALL, Blocks.POLISHED_BLACKSTONE_WALL)); setBlock(w, x, y+1, z, ox, oy, oz, Blocks.STONE_BRICK_SLAB); }
            else if (x == 1 && z == 1 && y < 5) { setBlock(w, x, y, z, ox, oy, oz, Blocks.ACACIA_LOG); if (y == 4) setBlock(w, x, y, z, ox, oy, oz, Blocks.COBWEB); }
            else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
        }
    }
}