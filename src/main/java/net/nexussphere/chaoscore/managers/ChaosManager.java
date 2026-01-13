package net.nexussphere.chaoscore.managers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.nexussphere.chaoscore.config.ModConfig;
import net.nexussphere.chaoscore.state.ChaosState;
import net.nexussphere.chaoscore.structures.StructureManager;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
public class ChaosManager {
    private final StructureManager structureManager;
    private final ServerBossBar bossBar;
    private final List<Block> SOLID_MATERIALS = Arrays.asList(
            Blocks.OAK_LOG, Blocks.SPRUCE_LOG, Blocks.BIRCH_LOG, Blocks.JUNGLE_LOG,
            Blocks.ACACIA_LOG, Blocks.DARK_OAK_LOG, Blocks.MANGROVE_LOG, Blocks.CHERRY_LOG,
            Blocks.STRIPPED_OAK_LOG, Blocks.STRIPPED_SPRUCE_LOG, Blocks.STRIPPED_BIRCH_LOG,
            Blocks.STRIPPED_JUNGLE_LOG, Blocks.STRIPPED_ACACIA_LOG, Blocks.STRIPPED_DARK_OAK_LOG,
            Blocks.STRIPPED_MANGROVE_LOG, Blocks.STRIPPED_CHERRY_LOG,
            Blocks.OAK_WOOD, Blocks.SPRUCE_WOOD, Blocks.BIRCH_WOOD, Blocks.JUNGLE_WOOD,
            Blocks.ACACIA_WOOD, Blocks.DARK_OAK_WOOD, Blocks.MANGROVE_WOOD, Blocks.CHERRY_WOOD,
            Blocks.OAK_PLANKS, Blocks.SPRUCE_PLANKS, Blocks.BIRCH_PLANKS, Blocks.JUNGLE_PLANKS,
            Blocks.ACACIA_PLANKS, Blocks.DARK_OAK_PLANKS, Blocks.MANGROVE_PLANKS, Blocks.CHERRY_PLANKS,
            Blocks.BAMBOO_BLOCK, Blocks.STRIPPED_BAMBOO_BLOCK, Blocks.BAMBOO_PLANKS, Blocks.BAMBOO_MOSAIC,
            Blocks.OAK_LEAVES, Blocks.SPRUCE_LEAVES, Blocks.BIRCH_LEAVES, Blocks.JUNGLE_LEAVES,
            Blocks.ACACIA_LEAVES, Blocks.DARK_OAK_LEAVES, Blocks.AZALEA_LEAVES, Blocks.FLOWERING_AZALEA_LEAVES,
            Blocks.MANGROVE_LEAVES, Blocks.CHERRY_LEAVES,
            Blocks.STONE, Blocks.GRANITE, Blocks.POLISHED_GRANITE,
            Blocks.DIORITE, Blocks.POLISHED_DIORITE,
            Blocks.ANDESITE, Blocks.POLISHED_ANDESITE,
            Blocks.DEEPSLATE, Blocks.COBBLED_DEEPSLATE, Blocks.POLISHED_DEEPSLATE,
            Blocks.TUFF, Blocks.CALCITE, Blocks.DRIPSTONE_BLOCK,
            Blocks.MOSSY_COBBLESTONE, Blocks.COBBLESTONE,
            Blocks.DIAMOND_ORE, Blocks.DEEPSLATE_DIAMOND_ORE,
            Blocks.EMERALD_ORE, Blocks.DEEPSLATE_EMERALD_ORE,
            Blocks.GOLD_ORE, Blocks.DEEPSLATE_GOLD_ORE,
            Blocks.IRON_ORE, Blocks.DEEPSLATE_IRON_ORE,
            Blocks.COPPER_ORE, Blocks.DEEPSLATE_COPPER_ORE,
            Blocks.COAL_ORE, Blocks.DEEPSLATE_COAL_ORE,
            Blocks.LAPIS_ORE, Blocks.DEEPSLATE_LAPIS_ORE,
            Blocks.REDSTONE_ORE, Blocks.DEEPSLATE_REDSTONE_ORE,
            Blocks.DIAMOND_BLOCK, Blocks.EMERALD_BLOCK, Blocks.GOLD_BLOCK, Blocks.IRON_BLOCK,
            Blocks.COPPER_BLOCK, Blocks.RAW_IRON_BLOCK, Blocks.RAW_GOLD_BLOCK, Blocks.RAW_COPPER_BLOCK,
            Blocks.NETHERITE_BLOCK, Blocks.COAL_BLOCK, Blocks.LAPIS_BLOCK, Blocks.REDSTONE_BLOCK, Blocks.AMETHYST_BLOCK,
            Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.ROOTED_DIRT, Blocks.MUD, Blocks.PACKED_MUD, Blocks.CLAY,
            Blocks.SAND, Blocks.RED_SAND, Blocks.GRAVEL, Blocks.SUSPICIOUS_SAND, Blocks.SUSPICIOUS_GRAVEL,
            Blocks.MOSS_BLOCK, Blocks.MYCELIUM, Blocks.PODZOL, Blocks.GRASS_BLOCK,
            Blocks.NETHERRACK, Blocks.SOUL_SAND, Blocks.SOUL_SOIL,
            Blocks.BASALT, Blocks.POLISHED_BASALT, Blocks.SMOOTH_BASALT,
            Blocks.BLACKSTONE, Blocks.POLISHED_BLACKSTONE, Blocks.GILDED_BLACKSTONE,
            Blocks.CRIMSON_NYLIUM, Blocks.WARPED_NYLIUM,
            Blocks.NETHER_WART_BLOCK, Blocks.WARPED_WART_BLOCK, Blocks.SHROOMLIGHT,
            Blocks.GLOWSTONE, Blocks.MAGMA_BLOCK, Blocks.OBSIDIAN, Blocks.CRYING_OBSIDIAN, Blocks.RESPAWN_ANCHOR,
            Blocks.END_STONE, Blocks.END_STONE_BRICKS, Blocks.PURPUR_BLOCK, Blocks.PURPUR_PILLAR,
            Blocks.BRICKS, Blocks.MUD_BRICKS,
            Blocks.STONE_BRICKS, Blocks.MOSSY_STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS, Blocks.CHISELED_STONE_BRICKS,
            Blocks.DEEPSLATE_BRICKS, Blocks.CRACKED_DEEPSLATE_BRICKS, Blocks.DEEPSLATE_TILES, Blocks.CRACKED_DEEPSLATE_TILES,
            Blocks.NETHER_BRICKS, Blocks.RED_NETHER_BRICKS, Blocks.CRACKED_NETHER_BRICKS, Blocks.CHISELED_NETHER_BRICKS,
            Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS,
            Blocks.QUARTZ_BLOCK, Blocks.CHISELED_QUARTZ_BLOCK, Blocks.QUARTZ_BRICKS, Blocks.QUARTZ_PILLAR, Blocks.SMOOTH_QUARTZ,
            Blocks.PRISMARINE, Blocks.PRISMARINE_BRICKS, Blocks.DARK_PRISMARINE, Blocks.SEA_LANTERN,
            Blocks.WHITE_CONCRETE, Blocks.ORANGE_CONCRETE, Blocks.MAGENTA_CONCRETE, Blocks.LIGHT_BLUE_CONCRETE,
            Blocks.YELLOW_CONCRETE, Blocks.LIME_CONCRETE, Blocks.PINK_CONCRETE, Blocks.GRAY_CONCRETE,
            Blocks.LIGHT_GRAY_CONCRETE, Blocks.CYAN_CONCRETE, Blocks.PURPLE_CONCRETE, Blocks.BLUE_CONCRETE,
            Blocks.BROWN_CONCRETE, Blocks.GREEN_CONCRETE, Blocks.RED_CONCRETE, Blocks.BLACK_CONCRETE,
            Blocks.WHITE_CONCRETE_POWDER, Blocks.ORANGE_CONCRETE_POWDER, Blocks.MAGENTA_CONCRETE_POWDER, Blocks.LIGHT_BLUE_CONCRETE_POWDER,
            Blocks.YELLOW_CONCRETE_POWDER, Blocks.LIME_CONCRETE_POWDER, Blocks.PINK_CONCRETE_POWDER, Blocks.GRAY_CONCRETE_POWDER,
            Blocks.LIGHT_GRAY_CONCRETE_POWDER, Blocks.CYAN_CONCRETE_POWDER, Blocks.PURPLE_CONCRETE_POWDER, Blocks.BLUE_CONCRETE_POWDER,
            Blocks.BROWN_CONCRETE_POWDER, Blocks.GREEN_CONCRETE_POWDER, Blocks.RED_CONCRETE_POWDER, Blocks.BLACK_CONCRETE_POWDER,
            Blocks.WHITE_WOOL, Blocks.ORANGE_WOOL, Blocks.MAGENTA_WOOL, Blocks.LIGHT_BLUE_WOOL,
            Blocks.YELLOW_WOOL, Blocks.LIME_WOOL, Blocks.PINK_WOOL, Blocks.GRAY_WOOL,
            Blocks.LIGHT_GRAY_WOOL, Blocks.CYAN_WOOL, Blocks.PURPLE_WOOL, Blocks.BLUE_WOOL,
            Blocks.BROWN_WOOL, Blocks.GREEN_WOOL, Blocks.RED_WOOL, Blocks.BLACK_WOOL,
            Blocks.TERRACOTTA, Blocks.WHITE_TERRACOTTA, Blocks.ORANGE_TERRACOTTA, Blocks.MAGENTA_TERRACOTTA,
            Blocks.LIGHT_BLUE_TERRACOTTA, Blocks.YELLOW_TERRACOTTA, Blocks.LIME_TERRACOTTA, Blocks.PINK_TERRACOTTA,
            Blocks.GRAY_TERRACOTTA, Blocks.LIGHT_GRAY_TERRACOTTA, Blocks.CYAN_TERRACOTTA, Blocks.PURPLE_TERRACOTTA,
            Blocks.BLUE_TERRACOTTA, Blocks.BROWN_TERRACOTTA, Blocks.GREEN_TERRACOTTA, Blocks.RED_TERRACOTTA, Blocks.BLACK_TERRACOTTA,
            Blocks.GLASS, Blocks.TINTED_GLASS,
            Blocks.WHITE_STAINED_GLASS, Blocks.ORANGE_STAINED_GLASS, Blocks.MAGENTA_STAINED_GLASS, Blocks.LIGHT_BLUE_STAINED_GLASS,
            Blocks.YELLOW_STAINED_GLASS, Blocks.LIME_STAINED_GLASS, Blocks.PINK_STAINED_GLASS, Blocks.GRAY_STAINED_GLASS,
            Blocks.LIGHT_GRAY_STAINED_GLASS, Blocks.CYAN_STAINED_GLASS, Blocks.PURPLE_STAINED_GLASS, Blocks.BLUE_STAINED_GLASS,
            Blocks.BROWN_STAINED_GLASS, Blocks.GREEN_STAINED_GLASS, Blocks.RED_STAINED_GLASS, Blocks.BLACK_STAINED_GLASS,
            Blocks.CRAFTING_TABLE, Blocks.FURNACE, Blocks.BLAST_FURNACE, Blocks.SMOKER,
            Blocks.DISPENSER, Blocks.DROPPER, Blocks.OBSERVER,
            Blocks.PISTON, Blocks.STICKY_PISTON,
            Blocks.NOTE_BLOCK, Blocks.JUKEBOX, Blocks.LODESTONE,
            Blocks.BARREL, Blocks.COMPOSTER, Blocks.TARGET,
            Blocks.BOOKSHELF, Blocks.CHISELED_BOOKSHELF,
            Blocks.SMITHING_TABLE, Blocks.FLETCHING_TABLE, Blocks.CARTOGRAPHY_TABLE, Blocks.LOOM,
            Blocks.BEEHIVE, Blocks.BEE_NEST,
            Blocks.TNT, Blocks.SLIME_BLOCK, Blocks.HONEY_BLOCK,
            Blocks.HAY_BLOCK, Blocks.BONE_BLOCK,
            Blocks.PUMPKIN, Blocks.CARVED_PUMPKIN, Blocks.JACK_O_LANTERN, Blocks.MELON,
            Blocks.SPONGE, Blocks.WET_SPONGE, Blocks.DRIED_KELP_BLOCK,
            Blocks.DEAD_TUBE_CORAL_BLOCK, Blocks.DEAD_BRAIN_CORAL_BLOCK, Blocks.DEAD_BUBBLE_CORAL_BLOCK,
            Blocks.DEAD_FIRE_CORAL_BLOCK, Blocks.DEAD_HORN_CORAL_BLOCK,
            Blocks.SNOW_BLOCK, Blocks.POWDER_SNOW,
            Blocks.ICE, Blocks.PACKED_ICE, Blocks.BLUE_ICE,
            Blocks.OCHRE_FROGLIGHT, Blocks.VERDANT_FROGLIGHT, Blocks.PEARLESCENT_FROGLIGHT,
            Blocks.REINFORCED_DEEPSLATE, Blocks.SCULK, Blocks.SCULK_CATALYST,
            Blocks.CRAFTER, Blocks.COPPER_BULB, Blocks.TUFF_BRICKS, Blocks.CHISELED_TUFF_BRICKS, Blocks.POLISHED_TUFF
    );
    public ChaosManager() {
        this.structureManager = new StructureManager();
        this.bossBar = new ServerBossBar(Text.literal("Chaos System"), BossBar.Color.RED, BossBar.Style.NOTCHED_10);
    }
    public void start(MinecraftServer server) {
        ChaosState state = ChaosState.getServerState(server);
        state.isRunning = true;
        state.markDirty(server);
        bossBar.setVisible(true);
    }
    public void stop(MinecraftServer server) {
        ChaosState state = ChaosState.getServerState(server);
        state.isRunning = false;
        state.markDirty(server);
        bossBar.setVisible(false);
        bossBar.clearPlayers();
    }
    public void setTime(MinecraftServer server, int seconds) {
        ChaosState state = ChaosState.getServerState(server);
        state.timer = seconds * 20;
        state.markDirty(server);
    }
    public void tick(MinecraftServer server) {
        ChaosState state = ChaosState.getServerState(server);
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            bossBar.addPlayer(player);
        }
        if (!state.isRunning) {
            bossBar.setVisible(false);
            return;
        }
        bossBar.setVisible(true);
        state.timer--;
        if (state.timer % 100 == 0) {
            state.markDirty(server);
        }
        int totalTicks = ModConfig.get().chaosTimer * 20;
        float percent = (float) state.timer / totalTicks;
        bossBar.setPercent(percent);
        int secondsLeft = state.timer / 20;
        bossBar.setName(Text.literal("§c⚠ Reset Chaos sau: §e" + secondsLeft + " giây"));
        if (state.timer <= 0) {
            triggerChaos(server, state);
            state.timer = ModConfig.get().chaosTimer * 20;
            state.markDirty(server);
        }
    }
    private void triggerChaos(MinecraftServer server, ChaosState state) {
        server.getPlayerManager().broadcast(Text.literal("§a✔ Chaos Event: Vùng chọn đã được làm mới!"), false);
        ServerWorld world = server.getOverworld();
        if (state.getPos1_1().getY() != 0) {
            execute(world, state.getPos1_1(), state.getPos2_1());
        }
        if (state.getPos1_2().getY() != 0) {
            execute(world, state.getPos1_2(), state.getPos2_2());
        }
    }
    private void execute(ServerWorld world, BlockPos p1, BlockPos p2) {
        int x1 = Math.min(p1.getX(), p2.getX());
        int y1 = Math.min(p1.getY(), p2.getY());
        int z1 = Math.min(p1.getZ(), p2.getZ());
        int x2 = Math.max(p1.getX(), p2.getX());
        int y2 = Math.max(p1.getY(), p2.getY());
        int z2 = Math.max(p1.getZ(), p2.getZ());
        int width = x2 - x1 + 1;
        int length = z2 - z1 + 1;
        int cx = x1 + width / 2;
        int cz = z1 + length / 2;
        BlockPos center = new BlockPos(cx, y2 + 2, cz);
        world.playSound(null, center, SoundEvents.BLOCK_PORTAL_TRAVEL, SoundCategory.MASTER, 0.3f, 1.5f);
        Box box = new Box(x1, y1, z1, x2 + 1, y2 + 2, z2 + 1);
        for (Entity e : world.getEntitiesByClass(Entity.class, box, entity -> true)) {
            if (e instanceof ItemEntity || e instanceof PlayerEntity) {
                e.teleport(world, cx, y2 + 2, cz, Set.of(), 0, 0, false);
            } else {
                e.discard();
            }
        }
        Random random = new Random();
        Block solidBlock = SOLID_MATERIALS.get(random.nextInt(SOLID_MATERIALS.size()));
        BlockState solidState = solidBlock.getDefaultState();
        if (width >= 9 && length >= 9 && random.nextInt(100) < ModConfig.get().structureChance) {
            int randX = random.nextInt(width - 9 + 1);
            int randZ = random.nextInt(length - 9 + 1);
            int structX1 = x1 + randX;
            int structZ1 = z1 + randZ;
            int structX2 = structX1 + 8;
            int structZ2 = structZ1 + 8;
            structureManager.generate(world, structX1, y1, structZ1, structX2, y1 + 40, structZ2);
        } else {
            for (int x = x1; x <= x2; x++) {
                for (int y = y1; y <= y2; y++) {
                    for (int z = z1; z <= z2; z++) {
                        world.setBlockState(new BlockPos(x, y, z), solidState, Block.NOTIFY_LISTENERS);
                    }
                }
            }
            int chestCount = 1 + random.nextInt(2);
            for (int i = 0; i < chestCount; i++) {
                int chestX = x1 + random.nextInt(width);
                int chestZ = z1 + random.nextInt(length);
                BlockPos chestPos = new BlockPos(chestX, y2, chestZ);
                LootManager.createDropChest(world, chestPos, solidBlock);
            }
        }
    }
}