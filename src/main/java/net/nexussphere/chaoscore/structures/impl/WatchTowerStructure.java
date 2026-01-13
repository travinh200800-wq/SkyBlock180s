package net.nexussphere.chaoscore.structures.impl;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.nexussphere.chaoscore.managers.LootManager;
import net.nexussphere.chaoscore.structures.BaseStructure;
public class WatchTowerStructure extends BaseStructure {
    @Override public String getName() { return "Tháp Canh Cao"; }
    @Override protected LootManager.LootType getLootType() { return LootManager.LootType.WAR; }
    @Override protected Block getFloorBlock() { return Blocks.COBBLESTONE; }
    @Override protected void placeBlock(ServerWorld w, int x, int y, int z, int width, int height, int length, int ox, int oy, int oz) {
        if (y > 9) { setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR); return; }
        int cx = width / 2; int cz = length / 2;
        if (Math.abs(x - cx) > 2 || Math.abs(z - cz) > 2) { setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR); return; }
        boolean border = Math.abs(x - cx) == 1 || Math.abs(z - cz) == 1;
        if (y < 7) {
            if (border) setBlock(w, x, y, z, ox, oy, oz, Blocks.MOSSY_COBBLESTONE);
            else if (x == cx && z == cz) setBlock(w, x, y, z, ox, oy, oz, Blocks.LADDER);
            else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
        } else if (y == 7) setBlock(w, x, y, z, ox, oy, oz, Blocks.SPRUCE_PLANKS);
        else if (y == 8) {
            if (Math.abs(x - cx) == 2 || Math.abs(z - cz) == 2) setBlock(w, x, y, z, ox, oy, oz, Blocks.SPRUCE_FENCE);
            else {
                setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
                if (x == cx && z == cz) {
                    BlockPos pos = new BlockPos(ox + x, oy + y, oz + z);
                    PillagerEntity captain = EntityType.PILLAGER.create(w, SpawnReason.STRUCTURE);
                    if (captain != null) {
                        captain.refreshPositionAndAngles(pos, 0, 0);
                        captain.initialize(w, w.getLocalDifficulty(pos), SpawnReason.STRUCTURE, null);
                        captain.setCustomName(Text.literal("Raid Captain"));
                        captain.setPersistent();
                        w.spawnEntity(captain);
                    }
                }
            }
        }
    }
}