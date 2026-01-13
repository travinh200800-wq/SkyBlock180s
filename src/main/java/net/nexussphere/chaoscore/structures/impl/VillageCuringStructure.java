package net.nexussphere.chaoscore.structures.impl;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.nexussphere.chaoscore.managers.LootManager;
import net.nexussphere.chaoscore.structures.BaseStructure;
public class VillageCuringStructure extends BaseStructure {
    @Override public String getName() { return "Viện Nghiên Cứu Cách Ly"; }
    @Override protected LootManager.LootType getLootType() { return LootManager.LootType.CURING; }
    @Override protected Block getFloorBlock() { return Blocks.POLISHED_DIORITE; }
    @Override protected void placeBlock(ServerWorld w, int x, int y, int z, int width, int height, int length, int ox, int oy, int oz) {
        if (y > 8) { setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR); return; }
        boolean isWall = x == 0 || x == 8 || z == 0 || z == 8;
        if (isWall) {
            if (y > 0) setBlock(w, x, y, z, ox, oy, oz, Blocks.STONE_BRICKS);
            return;
        }
        if (x == 4 && z == 4) {
            if (y > 0 && y < 8) setBlock(w, x, y, z, ox, oy, oz, Blocks.POLISHED_DIORITE);
            else if (y == 8) setBlock(w, x, y, z, ox, oy, oz, Blocks.STONE_BRICKS);
            return;
        }
        if (x == 3 && z == 4) {
            if (y > 0 && y < 8) {
                w.setBlockState(new BlockPos(ox+x, oy+y, oz+z), Blocks.LADDER.getDefaultState().with(LadderBlock.FACING, Direction.WEST));
            }
            return;
        }
        if (y == 0) return;
        if (y == 4) {
            if (x == 3 && z == 4) setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            else setBlock(w, x, y, z, ox, oy, oz, Blocks.POLISHED_DIORITE);
            return;
        }
        if (y == 8) {
            setBlock(w, x, y, z, ox, oy, oz, Blocks.STONE_BRICKS);
            return;
        }
        setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
        boolean insideCage = (x == 1 || x == 7) && (z == 1 || z == 7);
        boolean isBarX = (x == 2 || x == 6) && (z <= 2 || z >= 6);
        boolean isBarZ = (z == 2 || z == 6) && (x <= 2 || x >= 6);
        if (y < 4 || (y > 4 && y < 8)) {
            if (insideCage) {
                if (y == 1 || y == 5) spawnPatient(w, x, y, z, ox, oy, oz, "Mẫu vật #" + (x+y+z));
            }
            else if (isBarX || isBarZ) {
                setBlock(w, x, y, z, ox, oy, oz, Blocks.IRON_BARS);
            }
        }
        if (y == 1 || y == 5) {
            if (x == 4 && (z == 2 || z == 6)) setBlock(w, x, y, z, ox, oy, oz, Blocks.LANTERN);
            if (y == 1) {
                if (x == 4 && z == 2) LootManager.createLootChest(w, new BlockPos(ox+x, oy+y, oz+z), LootManager.LootType.CURING);
                if (x == 4 && z == 6) setBlock(w, x, y, z, ox, oy, oz, Blocks.BREWING_STAND);
            }
        }
    }
    private void spawnPatient(ServerWorld w, int x, int y, int z, int ox, int oy, int oz, String name) {
        BlockPos pos = new BlockPos(ox + x, oy + y, oz + z);
        ZombieVillagerEntity mob = EntityType.ZOMBIE_VILLAGER.create(w, SpawnReason.STRUCTURE);
        if (mob != null) {
            mob.refreshPositionAndAngles(pos, 0, 0);
            mob.initialize(w, w.getLocalDifficulty(pos), SpawnReason.STRUCTURE, null);
            mob.setBaby(false);
            mob.setCustomName(Text.literal(name));
            mob.setCustomNameVisible(true);
            mob.setPersistent();
            w.spawnEntity(mob);
        }
    }
}