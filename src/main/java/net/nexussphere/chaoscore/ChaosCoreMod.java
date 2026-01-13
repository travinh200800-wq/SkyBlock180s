package net.nexussphere.chaoscore;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.item.Items;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.nexussphere.chaoscore.config.ModConfig;
import net.nexussphere.chaoscore.managers.ChaosManager;
import net.nexussphere.chaoscore.managers.RegionManager;
import net.nexussphere.chaoscore.structures.StructureManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class ChaosCoreMod implements ModInitializer {
    public static final String MOD_ID = "chaoscore";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private static RegionManager regionManager;
    private static ChaosManager chaosManager;
    @Override
    public void onInitialize() {
        ModConfig.load();
        regionManager = new RegionManager();
        chaosManager = new ChaosManager();
        LOGGER.info("ChaosCoreMod Initializing...");
        ServerTickEvents.END_SERVER_TICK.register(chaosManager::tick);
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("chaos")
                    .requires(source -> source.hasPermissionLevel(2))
                    .then(CommandManager.literal("wand").executes(context -> {
                        context.getSource().getPlayerOrThrow().giveItemStack(Items.GOLDEN_AXE.getDefaultStack());
                        context.getSource().sendFeedback(() -> Text.literal("§e[Chaos] Đã nhận Wand! (Chuột trái: Pos1, Phải: Pos2)"), false);
                        return 1;
                    }))
                    .then(CommandManager.literal("setregion").executes(context -> {
                        if (regionManager.saveRegion(context.getSource().getPlayerOrThrow(), context.getSource().getServer(), 1)) {
                            context.getSource().sendFeedback(() -> Text.literal("§a✔ Đã lưu vùng Chaos hiện tại!"), false);
                        } else {
                            context.getSource().sendFeedback(() -> Text.literal("§c✘ Hãy chọn đủ 2 điểm bằng Rìu Vàng trước!"), false);
                        }
                        return 1;
                    }))
                    .then(CommandManager.literal("start").executes(context -> {
                        chaosManager.start(context.getSource().getServer());
                        context.getSource().sendFeedback(() -> Text.literal("§a▶ Chaos System: Đã BẬT!"), true);
                        return 1;
                    }))
                    .then(CommandManager.literal("stop").executes(context -> {
                        chaosManager.stop(context.getSource().getServer());
                        context.getSource().sendFeedback(() -> Text.literal("§c⏹ Chaos System: Đã TẮT!"), true);
                        return 1;
                    }))
                    .then(CommandManager.literal("time")
                            .then(CommandManager.literal("set")
                                    .then(CommandManager.argument("seconds", IntegerArgumentType.integer(1))
                                            .executes(context -> {
                                                int seconds = IntegerArgumentType.getInteger(context, "seconds");
                                                chaosManager.setTime(context.getSource().getServer(), seconds);
                                                context.getSource().sendFeedback(() -> Text.literal("§a✔ Đã chỉnh thời gian Chaos thành: " + seconds + "s"), true);
                                                return 1;
                                            })
                                    )
                            )
                    )
                    .then(CommandManager.literal("reload").executes(context -> {
                        ModConfig.load();
                        context.getSource().sendFeedback(() -> Text.literal("§eConfig Reloaded!"), false);
                        return 1;
                    }))
                    .then(CommandManager.literal("testall").executes(context -> {
                        context.getSource().sendFeedback(() -> Text.literal("§aĐang kiểm tra tất cả cấu trúc (9x9)..."), true);
                        try {
                            new StructureManager().testAll(context.getSource().getWorld(), context.getSource().getPlayerOrThrow().getBlockPos());
                        } catch (Exception e) {
                            context.getSource().sendFeedback(() -> Text.literal("§cLỗi khi test: " + e.getMessage()), false);
                        }
                        return 1;
                    }))
            );
        });
        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
            if (!world.isClient() && hand == Hand.MAIN_HAND && player.getMainHandStack().isOf(Items.GOLDEN_AXE)) {
                regionManager.setPos1(player.getUuid(), pos);
                player.sendMessage(Text.literal("§d[Chaos] §fPos 1 đặt tại: §a" + pos.toShortString()), true);
                return ActionResult.SUCCESS;
            }
            return ActionResult.PASS;
        });
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (!world.isClient() && hand == Hand.MAIN_HAND && player.getMainHandStack().isOf(Items.GOLDEN_AXE)) {
                regionManager.setPos2(player.getUuid(), hitResult.getBlockPos());
                player.sendMessage(Text.literal("§d[Chaos] §fPos 2 đặt tại: §a" + hitResult.getBlockPos().toShortString()), true);
                return ActionResult.SUCCESS;
            }
            return ActionResult.PASS;
        });
    }
}