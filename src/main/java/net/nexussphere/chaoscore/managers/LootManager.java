package net.nexussphere.chaoscore.managers;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
public class LootManager {
    private static final Random random = new Random();
    public enum LootType {
        NETHER, GARDEN, ICE, END, STRONGHOLD, ORE, DUNGEON, GRAVEYARD, ANCIENT, CURING,
        DESERT, SWAMP, WAR, WITCH, MINE, LIGHT, FORGE, TRIAL_OMINOUS
    }
    private static ItemStack createRealEnchantedBook(World world) {
        ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
        var keys = Arrays.asList(
                Enchantments.SHARPNESS, Enchantments.PROTECTION, Enchantments.EFFICIENCY,
                Enchantments.UNBREAKING, Enchantments.MENDING, Enchantments.FORTUNE,
                Enchantments.SILK_TOUCH, Enchantments.POWER, Enchantments.LOOTING,
                Enchantments.FEATHER_FALLING
        );
        var selectedKey = keys.get(random.nextInt(keys.size()));
        Registry<Enchantment> registry = world.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT);
        Optional<RegistryEntry.Reference<Enchantment>> entry = registry.getEntry(selectedKey.getValue());
        if (entry.isPresent()) {
            int level = 1 + random.nextInt(3);
            ItemEnchantmentsComponent.Builder builder = new ItemEnchantmentsComponent.Builder(ItemEnchantmentsComponent.DEFAULT);
            builder.add(entry.get(), level);
            book.set(DataComponentTypes.STORED_ENCHANTMENTS, builder.build());
        }
        return book;
    }
    public static void createDropChest(World world, BlockPos pos, Block sourceBlock) {
        world.setBlockState(pos, Blocks.CHEST.getDefaultState());
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof ChestBlockEntity chest) {
            Item drop = getDropFromBlock(sourceBlock);
            int amount = 1 + random.nextInt(5);
            int slot = random.nextInt(chest.size());
            chest.setStack(slot, new ItemStack(drop, amount));
        }
    }
    public static void createMaterialChest(World world, BlockPos pos, Item material) {
        createDropChest(world, pos, Block.getBlockFromItem(material));
    }
    private static Item getDropFromBlock(Block block) {
        if (block == Blocks.DIAMOND_ORE || block == Blocks.DEEPSLATE_DIAMOND_ORE) return Items.DIAMOND;
        if (block == Blocks.EMERALD_ORE || block == Blocks.DEEPSLATE_EMERALD_ORE) return Items.EMERALD;
        if (block == Blocks.COAL_ORE || block == Blocks.DEEPSLATE_COAL_ORE) return Items.COAL;
        if (block == Blocks.IRON_ORE || block == Blocks.DEEPSLATE_IRON_ORE) return Items.RAW_IRON;
        if (block == Blocks.GOLD_ORE || block == Blocks.DEEPSLATE_GOLD_ORE || block == Blocks.NETHER_GOLD_ORE) return Items.RAW_GOLD;
        if (block == Blocks.COPPER_ORE || block == Blocks.DEEPSLATE_COPPER_ORE) return Items.RAW_COPPER;
        if (block == Blocks.LAPIS_ORE || block == Blocks.DEEPSLATE_LAPIS_ORE) return Items.LAPIS_LAZULI;
        if (block == Blocks.REDSTONE_ORE || block == Blocks.DEEPSLATE_REDSTONE_ORE) return Items.REDSTONE;
        if (block == Blocks.NETHER_QUARTZ_ORE) return Items.QUARTZ;
        if (block == Blocks.ANCIENT_DEBRIS) return Items.NETHERITE_SCRAP;
        if (block == Blocks.STONE) return Items.COBBLESTONE;
        if (block == Blocks.CLAY) return Items.CLAY_BALL;
        if (block == Blocks.GLOWSTONE) return Items.GLOWSTONE_DUST;
        if (block == Blocks.MELON) return Items.MELON_SLICE;
        if (block == Blocks.OAK_LOG || block == Blocks.STRIPPED_OAK_LOG) return Items.OAK_PLANKS;
        if (block == Blocks.SPRUCE_LOG || block == Blocks.STRIPPED_SPRUCE_LOG) return Items.SPRUCE_PLANKS;
        if (block == Blocks.BIRCH_LOG || block == Blocks.STRIPPED_BIRCH_LOG) return Items.BIRCH_PLANKS;
        if (block == Blocks.JUNGLE_LOG) return Items.JUNGLE_PLANKS;
        if (block == Blocks.ACACIA_LOG) return Items.ACACIA_PLANKS;
        if (block == Blocks.DARK_OAK_LOG) return Items.DARK_OAK_PLANKS;
        if (block == Blocks.CHERRY_LOG) return Items.CHERRY_PLANKS;
        if (block == Blocks.MANGROVE_LOG) return Items.MANGROVE_PLANKS;
        return block.asItem();
    }
    public static void createLootChest(World world, BlockPos pos, LootType type) {
        world.setBlockState(pos, Blocks.CHEST.getDefaultState());
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof ChestBlockEntity chest) {
            if (type == LootType.CURING) {
                int quantity = 4 + random.nextInt(2);
                ItemStack apple = new ItemStack(Items.GOLDEN_APPLE);
                ItemStack potion = new ItemStack(Items.SPLASH_POTION);
                potion.set(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(Potions.WEAKNESS));
                for (int i = 0; i < quantity; i++) {
                    chest.setStack(i, apple.copy());
                    chest.setStack(i + 9, potion.copy());
                }
                return;
            }
            List<Item> pool = getPool(type);
            int count = 4 + random.nextInt(4);
            for (int i = 0; i < count; i++) {
                Item item = pool.get(random.nextInt(pool.size()));
                int slot = random.nextInt(chest.size());
                int amount = 1 + random.nextInt(3);
                if (item == Items.ENCHANTED_BOOK) {
                    chest.setStack(slot, createRealEnchantedBook(world));
                } else {
                    chest.setStack(slot, new ItemStack(item, amount));
                }
            }
        }
    }
    private static List<Item> getPool(LootType type) {
        return switch (type) {
            case TRIAL_OMINOUS -> Arrays.asList(Items.HEAVY_CORE, Items.OMINOUS_TRIAL_KEY, Items.ENCHANTED_GOLDEN_APPLE, Items.DIAMOND_BLOCK, Items.OMINOUS_BOTTLE, Items.WIND_CHARGE);
            case FORGE -> Arrays.asList(Items.TRIAL_KEY, Items.COPPER_INGOT, Items.IRON_INGOT, Items.BREEZE_ROD, Items.CHISELED_COPPER, Items.BAKED_POTATO);
            case WAR -> Arrays.asList(Items.CROSSBOW, Items.ARROW, Items.SPECTRAL_ARROW, Items.IRON_AXE, Items.SHIELD, Items.EMERALD, Items.IRON_HELMET);
            case END -> Arrays.asList(Items.ELYTRA, Items.SHULKER_SHELL, Items.DIAMOND, Items.DIAMOND_SWORD, Items.DIAMOND_CHESTPLATE, Items.ENCHANTED_BOOK, Items.DRAGON_BREATH, Items.CHORUS_FRUIT);
            case STRONGHOLD -> Arrays.asList(Items.ENDER_PEARL, Items.ENDER_EYE, Items.IRON_INGOT, Items.GOLD_INGOT, Items.BOOK, Items.IRON_SWORD, Items.IRON_PICKAXE, Items.GOLDEN_APPLE);
            case DUNGEON -> Arrays.asList(Items.ROTTEN_FLESH, Items.BONE, Items.ARROW, Items.STRING, Items.GOLDEN_APPLE, Items.SADDLE, Items.NAME_TAG, Items.MUSIC_DISC_13, Items.ENCHANTED_BOOK);
            case WITCH -> Arrays.asList(Items.BREWING_STAND, Items.GLASS_BOTTLE, Items.GLOWSTONE_DUST, Items.REDSTONE, Items.GUNPOWDER, Items.SPIDER_EYE, Items.GHAST_TEAR, Items.BLAZE_POWDER, Items.NETHER_WART);
            case MINE -> Arrays.asList(Items.IRON_INGOT, Items.GOLD_INGOT, Items.LAPIS_LAZULI, Items.DIAMOND, Items.RAIL, Items.MINECART, Items.TNT, Items.IRON_PICKAXE, Items.COAL, Items.BREAD);
            case LIGHT -> Arrays.asList(Items.QUARTZ, Items.AMETHYST_SHARD, Items.GLOWSTONE, Items.SEA_LANTERN, Items.EXPERIENCE_BOTTLE, Items.GOLD_INGOT, Items.ENCHANTED_BOOK, Items.GOLD_NUGGET);
            case NETHER -> Arrays.asList(Items.GOLD_INGOT, Items.QUARTZ, Items.BLAZE_ROD, Items.MAGMA_CREAM, Items.NETHERITE_SCRAP, Items.OBSIDIAN, Items.SOUL_SAND, Items.FIRE_CHARGE);
            case GARDEN -> Arrays.asList(Items.WHEAT, Items.CARROT, Items.POTATO, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BONE_MEAL, Items.SHEARS, Items.HONEY_BOTTLE);
            case ICE -> Arrays.asList(Items.SNOWBALL, Items.ICE, Items.PACKED_ICE, Items.BLUE_ICE, Items.COD, Items.SALMON, Items.TRIDENT);
            case ORE -> Arrays.asList(Items.COAL, Items.RAW_IRON, Items.RAW_GOLD, Items.DIAMOND, Items.EMERALD, Items.LAPIS_LAZULI, Items.IRON_PICKAXE);
            case GRAVEYARD -> Arrays.asList(Items.ROTTEN_FLESH, Items.BONE, Items.SKELETON_SKULL, Items.POISONOUS_POTATO, Items.GOLD_NUGGET);
            case ANCIENT -> Arrays.asList(Items.AMETHYST_SHARD, Items.ECHO_SHARD, Items.ENCHANTED_BOOK, Items.EXPERIENCE_BOTTLE, Items.COMPASS, Items.LAPIS_LAZULI, Items.DISC_FRAGMENT_5);
            case DESERT -> Arrays.asList(Items.DIAMOND, Items.EMERALD, Items.GOLD_INGOT, Items.IRON_INGOT, Items.BONE, Items.ROTTEN_FLESH, Items.TNT, Items.GUNPOWDER, Items.SAND, Items.ENCHANTED_BOOK, Items.SADDLE);
            case SWAMP -> Arrays.asList(Items.SLIME_BALL, Items.RED_MUSHROOM, Items.BROWN_MUSHROOM, Items.SPIDER_EYE, Items.SUGAR, Items.GLASS_BOTTLE, Items.FERMENTED_SPIDER_EYE, Items.STICK);
            default -> Arrays.asList(Items.DIRT);
        };
    }
}