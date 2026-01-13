package net.nexussphere.chaoscore.structures.impl;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.server.world.ServerWorld;
import net.nexussphere.chaoscore.managers.LootManager;
import net.nexussphere.chaoscore.structures.BaseStructure;
public class IceTowerStructure extends BaseStructure {
    @Override public String getName() { return "Ngọn Giáo Băng"; }
    @Override protected LootManager.LootType getLootType() { return LootManager.LootType.ICE; }
    @Override protected Block getFloorBlock() { return Blocks.POWDER_SNOW; }
    @Override protected void placeBlock(ServerWorld w, int x, int y, int z, int width, int height, int length, int ox, int oy, int oz) {
        if (y > 8) { setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR); return; }
        double centerX = width / 2.0; double centerZ = length / 2.0;
        double maxRadius = 3.0; double currentRadius = maxRadius * (1.0 - ((double)y / 8.0));
        double dist = Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(z - centerZ, 2));
        if (dist <= currentRadius) setBlock(w, x, y, z, ox, oy, oz, pickOne(Blocks.PACKED_ICE, Blocks.BLUE_ICE));
        else if (dist <= currentRadius + 1.2 && y < 3) { setBlock(w, x, y, z, ox, oy, oz, Blocks.SNOW_BLOCK); if (chance(3) && y == 1) spawnMob(w, x, y, z, ox, oy, oz, EntityType.STRAY, "Stray"); }
        else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
    }
}