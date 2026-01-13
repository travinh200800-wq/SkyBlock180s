package net.nexussphere.chaoscore.structures.impl;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.nexussphere.chaoscore.managers.LootManager;
import net.nexussphere.chaoscore.structures.BaseStructure;
public class DesertTombStructure extends BaseStructure {
    @Override public String getName() { return "Lăng Mộ Pharaoh"; }
    @Override protected LootManager.LootType getLootType() { return LootManager.LootType.DESERT; }
    @Override protected Block getFloorBlock() { return Blocks.CUT_SANDSTONE; }
    @Override protected void placeBlock(ServerWorld w, int x, int y, int z, int width, int height, int length, int ox, int oy, int oz) {
        if (y > 6) { setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR); return; }
        int cx = width / 2;
        int cz = length / 2;
        int layerSize = 4 - y;
        if (layerSize < 0) return;
        boolean isEdge = Math.abs(x - cx) == layerSize || Math.abs(z - cz) == layerSize;
        boolean isInside = Math.abs(x - cx) < layerSize && Math.abs(z - cz) < layerSize;
        if (isEdge) {
            setBlock(w, x, y, z, ox, oy, oz, pickOne(Blocks.CUT_SANDSTONE, Blocks.SANDSTONE, Blocks.CHISELED_SANDSTONE));
        } else if (isInside) {
            if (y == 1) {
                if (x == cx && z == cz) {
                    setBlock(w, x, y, z, ox, oy, oz, Blocks.GOLD_BLOCK);
                    LootManager.createLootChest(w, new BlockPos(ox+x, oy+y+1, oz+z), LootManager.LootType.DESERT);
                } else {
                    setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
                    if (chance(5)) spawnMob(w, x, y, z, ox, oy, oz, EntityType.HUSK, "Tomb Guardian");
                }
            } else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
        } else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
    }
}