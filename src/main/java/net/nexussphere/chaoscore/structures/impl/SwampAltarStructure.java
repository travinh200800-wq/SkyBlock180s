package net.nexussphere.chaoscore.structures.impl;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.server.world.ServerWorld;
import net.nexussphere.chaoscore.managers.LootManager;
import net.nexussphere.chaoscore.structures.BaseStructure;
public class SwampAltarStructure extends BaseStructure {
    @Override public String getName() { return "Bàn Thờ Hắc Ám"; }
    @Override protected LootManager.LootType getLootType() { return LootManager.LootType.NETHER; }
    @Override protected Block getFloorBlock() { return Blocks.PURPLE_TERRACOTTA; }
    @Override protected void placeBlock(ServerWorld w, int x, int y, int z, int width, int height, int length, int ox, int oy, int oz) {
        if (y > 6) { setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR); return; }
        if (y == 1) {
            if ((x+z)%2==0) setBlock(w, x, y, z, ox, oy, oz, Blocks.OBSIDIAN);
            else setBlock(w, x, y, z, ox, oy, oz, Blocks.CRYING_OBSIDIAN);
            if (x == 4 && z == 4) setBlock(w, x, y, z, ox, oy, oz, Blocks.CAULDRON);
            if (x == 4 && z == 3) spawnMob(w, x, y, z, ox, oy, oz, EntityType.WITCH, "Dark Witch");
            return;
        }
        boolean isCorner = (x == 1 || x == 7) && (z == 1 || z == 7);
        if (isCorner && y < 5) {
            setBlock(w, x, y, z, ox, oy, oz, Blocks.STRIPPED_DARK_OAK_LOG);
            return;
        }
        if (y == 5) {
            if (x > 0 && x < 8 && z > 0 && z < 8) {
                if (chance(80)) setBlock(w, x, y, z, ox, oy, oz, Blocks.DARK_OAK_SLAB);
                else setBlock(w, x, y, z, ox, oy, oz, Blocks.COBWEB);
            }
            return;
        }
        if (y == 2) {
            if (x == 4 && z == 4) setBlock(w, x, y, z, ox, oy, oz, Blocks.GREEN_STAINED_GLASS_PANE);
            else if (x == 4 && z == 1) LootManager.createLootChest(w, new net.minecraft.util.math.BlockPos(ox+x, oy+y, oz+z), LootManager.LootType.NETHER);
            else if (chance(10)) setBlock(w, x, y, z, ox, oy, oz, Blocks.SKELETON_SKULL);
            else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
        } else {
            setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
        }
    }
}