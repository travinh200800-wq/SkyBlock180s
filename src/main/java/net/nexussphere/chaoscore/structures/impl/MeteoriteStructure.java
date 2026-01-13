package net.nexussphere.chaoscore.structures.impl;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.server.world.ServerWorld;
import net.nexussphere.chaoscore.managers.LootManager;
import net.nexussphere.chaoscore.structures.BaseStructure;
public class MeteoriteStructure extends BaseStructure {
    @Override public String getName() { return "Thiên Thạch Rơi"; }
    @Override protected LootManager.LootType getLootType() { return LootManager.LootType.NETHER; }
    @Override protected Block getFloorBlock() { return Blocks.BLACKSTONE; }
    @Override protected void placeBlock(ServerWorld w, int x, int y, int z, int width, int height, int length, int ox, int oy, int oz) {
        double cx = width / 2.0; double cy = 2.5;
        double cz = length / 2.0; double radius = 3.5;
        double dist = Math.pow(x - cx, 2) + Math.pow(y - cy, 2) + Math.pow(z - cz, 2);
        if (dist <= radius * radius) setBlock(w, x, y, z, ox, oy, oz, pickOne(Blocks.MAGMA_BLOCK, Blocks.OBSIDIAN, Blocks.COAL_BLOCK, Blocks.ANCIENT_DEBRIS));
        else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
    }
}