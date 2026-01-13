package net.nexussphere.chaoscore.structures.impl;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.server.world.ServerWorld;
import net.nexussphere.chaoscore.managers.LootManager;
import net.nexussphere.chaoscore.structures.BaseStructure;
public class AncientLibraryStructure extends BaseStructure {
    @Override public String getName() { return "Thư Viện Hoàng Gia"; }
    @Override protected LootManager.LootType getLootType() { return LootManager.LootType.ANCIENT; }
    @Override protected Block getFloorBlock() { return Blocks.SPRUCE_PLANKS; }
    @Override protected void placeBlock(ServerWorld w, int x, int y, int z, int width, int height, int length, int ox, int oy, int oz) {
        if (y > 7) { setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR); return; }
        if (y == 7) { setBlock(w, x, y, z, ox, oy, oz, Blocks.SPRUCE_SLAB); return; }
        if (x == 0 || x == width - 1 || z == 0 || z == length - 1) {
            setBlock(w, x, y, z, ox, oy, oz, Blocks.STRIPPED_SPRUCE_LOG);
            return;
        }
        boolean isAisle = (x >= 3 && x <= 5);
        if (y < 6) {
            if (!isAisle) {
                if (z != length / 2) setBlock(w, x, y, z, ox, oy, oz, Blocks.BOOKSHELF);
                else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
            }
            else {
                setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
                if (y == 5 && x == width/2 && z % 2 == 0) {
                    setBlock(w, x, y, z, ox, oy, oz, Blocks.LANTERN);
                    setBlock(w, x, y+1, z, ox, oy, oz, Blocks.SPRUCE_FENCE);
                }
                if (y == 1 && x == width/2 && z == length/2 && chance(5))
                    spawnMob(w, x, y, z, ox, oy, oz, EntityType.WITCH, "Librarian Witch");
            }
        } else if (y == 6) {
            if (x == width/2 || z == length/2) setBlock(w, x, y, z, ox, oy, oz, Blocks.SPRUCE_PLANKS);
            else setBlock(w, x, y, z, ox, oy, oz, Blocks.AIR);
        }
    }
}