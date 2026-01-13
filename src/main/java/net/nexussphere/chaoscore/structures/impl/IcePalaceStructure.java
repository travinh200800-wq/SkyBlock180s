package net.nexussphere.chaoscore.structures.impl;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.server.world.ServerWorld;
import net.nexussphere.chaoscore.managers.LootManager;
import net.nexussphere.chaoscore.structures.BaseStructure;
public class IcePalaceStructure extends BaseStructure {
    @Override public String getName() { return "Cung Điện Băng Giá"; }
    @Override protected LootManager.LootType getLootType() { return LootManager.LootType.ICE; }
    @Override protected Block getFloorBlock() { return Blocks.BLUE_ICE; }
    @Override protected void placeBlock(ServerWorld w, int x, int y, int z, int width, int height, int length, int ox, int oy, int oz) {
        if (y > 8) { setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR); return; }
        int cx = width / 2; int cz = length / 2; double dist = Math.sqrt(Math.pow(x - cx, 2) + Math.pow(z - cz, 2));
        double maxR = 3.5; double radiusAtY = maxR * (1.0 - (double)y / 8.0);
        if (dist <= radiusAtY) {
            if (dist > radiusAtY - 1.0) setBlock(w, x, y, z, ox, oy, oz, pickOne(Blocks.PACKED_ICE, Blocks.BLUE_ICE));
            else {
                if (y % 4 == 0) { setBlock(w, x, y, z, ox, oy, oz, Blocks.CHISELED_QUARTZ_BLOCK); if (y > 0) setBlock(w, x, y, z, ox, oy, oz, Blocks.SEA_LANTERN); }
                else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
                if (y == 1 && x == cx && z == cz) spawnMob(w, x, y, z, ox, oy, oz, EntityType.SNOW_GOLEM, "Snow Golem");
            }
        } else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
    }
}