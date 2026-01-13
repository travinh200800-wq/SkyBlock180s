package net.nexussphere.chaoscore.structures.impl;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.server.world.ServerWorld;
import net.nexussphere.chaoscore.managers.LootManager;
import net.nexussphere.chaoscore.structures.BaseStructure;
public class CrystalGeodeStructure extends BaseStructure {
    @Override public String getName() { return "Hốc Thạch Anh"; }
    @Override protected LootManager.LootType getLootType() { return LootManager.LootType.ANCIENT; }
    @Override protected Block getFloorBlock() { return Blocks.SMOOTH_BASALT; }
    @Override protected void placeBlock(ServerWorld w, int x, int y, int z, int width, int height, int length, int ox, int oy, int oz) {
        double cx = width / 2.0; double cy = 2.5; double cz = length / 2.0; double radius = 3.5;
        double dist = Math.pow(x - cx, 2) + Math.pow(y - cy, 2) + Math.pow(z - cz, 2);
        if (dist <= Math.pow(radius, 2) && dist > Math.pow(radius - 0.8, 2)) setBlock(w, x, y, z, ox, oy, oz, Blocks.SMOOTH_BASALT);
        else if (dist <= Math.pow(radius - 0.8, 2) && dist > Math.pow(radius - 1.5, 2)) setBlock(w, x, y, z, ox, oy, oz, Blocks.CALCITE);
        else if (dist <= Math.pow(radius - 1.5, 2)) {
            if (dist > Math.pow(radius - 2.2, 2)) {
                setBlock(w, x, y, z, ox, oy, oz, Blocks.AMETHYST_BLOCK);
                if (chance(40)) setBlock(w, x, y, z, ox, oy, oz, Blocks.AMETHYST_CLUSTER);
            }
            else {
                setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            }
        } else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
    }
}