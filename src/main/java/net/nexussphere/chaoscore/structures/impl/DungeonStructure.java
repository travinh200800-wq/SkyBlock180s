package net.nexussphere.chaoscore.structures.impl;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.server.world.ServerWorld;
import net.nexussphere.chaoscore.managers.LootManager;
import net.nexussphere.chaoscore.structures.BaseStructure;
public class DungeonStructure extends BaseStructure {
    @Override public String getName() { return "Hầm Ngục Tối"; }
    @Override protected LootManager.LootType getLootType() { return LootManager.LootType.DUNGEON; }
    @Override protected Block getFloorBlock() { return Blocks.COBBLESTONE; }
    @Override protected void placeBlock(ServerWorld w, int x, int y, int z, int width, int height, int length, int ox, int oy, int oz) {
        if (y > 5) { setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR); return; }
        boolean wall = x == 0 || x == width - 1 || z == 0 || z == length - 1;
        if (wall) {
            if (y < 5) setBlock(w, x, y, z, ox, oy, oz, pickOne(Blocks.MOSSY_COBBLESTONE, Blocks.COBBLESTONE, Blocks.STONE_BRICKS));
            else { if ((x + z) % 2 == 0) setBlock(w, x, y, z, ox, oy, oz, Blocks.COBBLESTONE_WALL); else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR); }
        } else {
            if (y == 0) return;
            if (x % 3 == 0 && z % 3 == 0) setBlock(w, x, y, z, ox, oy, oz, Blocks.CHISELED_STONE_BRICKS);
            else if (y == 1) {
                if (chance(30)) spawnMob(w, x, y, z, ox, oy, oz, EntityType.ZOMBIE, "Zombie");
                else if (chance(10)) spawnMob(w, x, y, z, ox, oy, oz, EntityType.SKELETON, "Skeleton");
                setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            } else if (y == 4) {
                if (chance(10)) { setBlock(w, x, y, z, ox, oy, oz, Blocks.IRON_BARS); setBlock(w, x, y-1, z, ox, oy, oz, Blocks.LANTERN); }
                else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            } else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
        }
    }
}