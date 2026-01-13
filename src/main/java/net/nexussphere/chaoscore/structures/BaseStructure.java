package net.nexussphere.chaoscore.structures;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.nexussphere.chaoscore.config.ModConfig;
import net.nexussphere.chaoscore.managers.LootManager;
import java.util.Random;
public abstract class BaseStructure implements ChaosStructure {
    protected final Random random = new Random();
    protected abstract void placeBlock(ServerWorld world, int localX, int localY, int localZ, int w, int h, int l, int originX, int originY, int originZ);
    protected abstract LootManager.LootType getLootType();
    protected abstract Block getFloorBlock();
    @Override
    public void build(ServerWorld world, int x1, int y1, int z1, int w, int h, int l) {
        int width = Math.abs(w - x1) + 1;
        int height = Math.abs(h - y1) + 1;
        int length = Math.abs(l - z1) + 1;
        int lootChance = ModConfig.get().lootChance;
        int chestsPlaced = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < length; z++) {
                    int worldX = x1 + x;
                    int worldY = y1 + y;
                    int worldZ = z1 + z;
                    BlockPos pos = new BlockPos(worldX, worldY, worldZ);
                    if (y == 0) {
                        world.setBlockState(pos, getFloorBlock().getDefaultState());
                        continue;
                    }
                    placeBlock(world, x, y, z, width, height, length, x1, y1, z1);
                    if (chestsPlaced < 3 && random.nextInt(100) < lootChance) {
                        if (!world.getBlockState(pos).isAir() &&
                                world.getBlockEntity(pos) == null &&
                                !world.getBlockState(pos).isOf(Blocks.END_PORTAL_FRAME)) {
                            LootManager.createLootChest(world, pos, getLootType());
                            chestsPlaced++;
                        }
                    }
                }
            }
        }
    }
    protected void setBlock(ServerWorld w, int x, int y, int z, int ox, int oy, int oz, Block block) {
        w.setBlockState(new BlockPos(ox + x, oy + y, oz + z), block.getDefaultState());
    }
    protected Block pickOne(Block... blocks) {
        return blocks[random.nextInt(blocks.length)];
    }
    protected boolean chance(int percent) {
        return random.nextInt(100) < percent;
    }
    protected void spawnMob(ServerWorld w, int x, int y, int z, int ox, int oy, int oz, EntityType<?> type, String name) {
        BlockPos pos = new BlockPos(ox + x, oy + y, oz + z);
        if (w.getBlockState(pos).isAir() && w.getBlockState(pos.down()).isSolid()) {
            MobEntity mob = (MobEntity) type.create(w, SpawnReason.STRUCTURE);
            if (mob != null) {
                mob.refreshPositionAndAngles(pos, 0, 0);
                mob.initialize(w, w.getLocalDifficulty(pos), SpawnReason.STRUCTURE, null);
                if (name != null && !name.isEmpty()) {
                    mob.setCustomName(Text.literal(name));
                    mob.setCustomNameVisible(true);
                }
                mob.setPersistent();
                w.spawnEntity(mob);
            }
        }
    }
}