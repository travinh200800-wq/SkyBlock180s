package net.nexussphere.chaoscore.structures.impl;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.server.world.ServerWorld;
import net.nexussphere.chaoscore.managers.LootManager;
import net.nexussphere.chaoscore.structures.BaseStructure;
public class GreenHouseStructure extends BaseStructure {
    @Override public String getName() { return "Vườn Địa Đàng"; }
    @Override protected LootManager.LootType getLootType() { return LootManager.LootType.GARDEN; }
    @Override protected Block getFloorBlock() { return Blocks.MOSS_BLOCK; }
    @Override protected void placeBlock(ServerWorld w, int x, int y, int z, int width, int height, int length, int ox, int oy, int oz) {
        double cx = width / 2.0; double cz = length / 2.0;
        double radius = 4.0;
        if (y > 6) { setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR); return; }
        if (y <= 3 && y > 0) {
            boolean frame = x==0 || x==8 || z==0 || z==8;
            if (frame) {
                if ((x==0 && z==0) || (x==8 && z==0) || (x==0 && z==8) || (x==8 && z==8)) setBlock(w, x, y, z, ox, oy, oz, Blocks.JUNGLE_LOG);
                else if (y == 3) setBlock(w, x, y, z, ox, oy, oz, Blocks.JUNGLE_LOG);
                else setBlock(w, x, y, z, ox, oy, oz, Blocks.GLASS);
            }
            else {
                if (y == 1) {
                    if (Math.abs(x-cx) <= 1 && Math.abs(z-cz) <= 1) {
                        setBlock(w, x, y, z, ox, oy, oz, pickOne(Blocks.MELON, Blocks.PUMPKIN, Blocks.AZALEA));
                    }
                    else {
                        if (chance(60)) setBlock(w, x, y, z, ox, oy, oz, pickOne(Blocks.POPPY, Blocks.FERN, Blocks.DANDELION, Blocks.ORANGE_TULIP));
                        else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
                        if (chance(2)) spawnMob(w, x, y, z, ox, oy, oz, EntityType.PANDA, "Panda");
                    }
                } else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            }
            return;
        }
        double domeY = y - 3;
        double dist = Math.sqrt(Math.pow(x - cx, 2) + Math.pow(z - cz, 2));
        if (dist <= radius * Math.cos(domeY / radius * 1.2)) {
            setBlock(w, x, y, z, ox, oy, oz, Blocks.GLASS);
        } else {
            setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
        }
    }
}