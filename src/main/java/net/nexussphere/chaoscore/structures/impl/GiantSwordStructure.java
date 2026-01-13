package net.nexussphere.chaoscore.structures.impl;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.server.world.ServerWorld;
import net.nexussphere.chaoscore.managers.LootManager;
import net.nexussphere.chaoscore.structures.BaseStructure;
public class GiantSwordStructure extends BaseStructure {
    @Override public String getName() { return "Thánh Kiếm Excalibur"; }
    @Override protected LootManager.LootType getLootType() { return LootManager.LootType.ORE; }
    @Override protected Block getFloorBlock() { return Blocks.MOSSY_COBBLESTONE; }
    @Override protected void placeBlock(ServerWorld w, int x, int y, int z, int width, int height, int length, int ox, int oy, int oz) {
        int cx = width / 2;
        int cz = length / 2;
        if (y <= 2) {
            double dist = Math.sqrt(Math.pow(x - cx, 2) + Math.pow(z - cz, 2));
            if (dist <= 3.5 - y) {
                setBlock(w, x, y, z, ox, oy, oz, pickOne(Blocks.STONE, Blocks.ANDESITE, Blocks.COBBLESTONE));
            } else {
                setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            }
            return;
        }
        if (y > 2 && y <= 9) {
            if (x == cx && z == cz) setBlock(w, x, y, z, ox, oy, oz, Blocks.IRON_BLOCK);
            else if ((x == cx && Math.abs(z-cz)==1) || (z == cz && Math.abs(x-cx)==1)) {
                if (x == cx) setBlock(w, x, y, z, ox, oy, oz, Blocks.POLISHED_DIORITE);
                else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            }
            else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            return;
        }
        if (y == 10) {
            if (x == cx && Math.abs(z-cz) <= 2) setBlock(w, x, y, z, ox, oy, oz, Blocks.GOLD_BLOCK);
            else if (x == cx && z == cz) setBlock(w, x, y, z, ox, oy, oz, Blocks.DIAMOND_BLOCK);
            else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            return;
        }
        if (y == 11 || y == 12) {
            if (x == cx && z == cz) setBlock(w, x, y, z, ox, oy, oz, Blocks.OBSIDIAN); // Tay cầm
            else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            return;
        }
        if (y == 13) {
            if (x == cx && z == cz) setBlock(w, x, y, z, ox, oy, oz, Blocks.GOLD_BLOCK);
            else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            return;
        }
        if (y == 3 && x == cx + 2 && z == cz) spawnMob(w, x, y, z, ox, oy, oz, EntityType.VINDICATOR, "Sword Keeper");
        if (y == 3 && x == cx - 2 && z == cz) LootManager.createLootChest(w, new net.minecraft.util.math.BlockPos(ox+x, oy+y, oz+z), LootManager.LootType.ORE);
    }
}