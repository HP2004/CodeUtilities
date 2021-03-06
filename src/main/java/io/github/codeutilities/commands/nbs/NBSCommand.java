package io.github.codeutilities.commands.nbs;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import io.github.codeutilities.commands.Command;
import io.github.codeutilities.commands.arguments.ArgBuilder;
import io.github.codeutilities.nbs.*;
import io.github.codeutilities.nbs.exceptions.OutdatedNBSException;
import io.github.codeutilities.util.*;
import io.github.codeutilities.util.externalfile.ExternalFile;
import io.github.cottonmc.clientcommands.CottonClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.*;
import net.minecraft.text.LiteralText;

import java.io.*;

public class NBSCommand extends Command {


    public static void loadNbs(File file, String fileName) {
        try {
            SongData d = io.github.codeutilities.commands.nbs.NBSDecoder.parse(file);
            String code = new NBSToTemplate(d).convert();
            ItemStack stack = new ItemStack(Items.NOTE_BLOCK);
            TemplateUtils.compressTemplateNBT(stack, d.getName(), d.getAuthor(), code);

            if (d.getName().length() == 0) {
                String name;
                if (d.getFileName().indexOf(".") > 0) {
                    name = d.getFileName().substring(0, d.getFileName().lastIndexOf("."));
                } else {
                    name = d.getFileName();
                }
                stack.setCustomName(new LiteralText("§5SONG§7 -§f " + name));
            } else {
                stack.setCustomName(new LiteralText("§5SONG§7 -§f " + d.getName()));
            }

            ChatUtil.sendMessage("The NBS file §b" + fileName + "§a has successfully been loaded!",
                    ChatType.SUCCESS);
            ItemUtil.giveCreativeItem(stack);
        } catch (OutdatedNBSException e) {
            ChatUtil.sendMessage(
                    "Sorry! Due to how importing system works, this NBS file cannot be imported! " +
                            "Please open this file in the latest version of §bOpen Note Block Studio§r and save it once!",
                    ChatType.FAIL);
        } catch (IOException e) {
            ChatUtil.sendMessage("Couldn't read data inside this file! This file may be corrupted :(", ChatType.FAIL);
        }

    }

    @Override
    public void register(MinecraftClient mc, CommandDispatcher<CottonClientCommandSource> cd) {
        cd.register(ArgBuilder.literal("nbs")
                .then(ArgBuilder.literal("load")
                        .then(ArgBuilder.argument("filename", StringArgumentType.greedyString())
                                .executes(ctx -> {
                                    if (mc.player.isCreative()) {
                                        String filename = StringArgumentType.getString(ctx, "filename");
                                        String childName = filename + (filename.endsWith(".nbs") ? "" : ".nbs");
                                        File file = new File(ExternalFile.NBS_FILES.getFile(), childName);

                                        if (file.exists()) {
                                            loadNbs(file, childName);
                                        } else {
                                            ChatUtil.sendMessage("The file §6" + childName + "§c was not found.", ChatType.FAIL);
                                        }
                                    } else {
                                        ChatUtil.sendTranslateMessage("codeutilities.command.require_creative_mode", ChatType.FAIL);
                                    }
                                    return 1;
                                })
                        )
                )
                .then(ArgBuilder.literal("player")
                        .executes(ctx -> {
                            if (mc.player.isCreative()) {
                                ItemStack stack = new ItemStack(Items.JUKEBOX);
                                TemplateUtils.applyRawTemplateNBT(stack, "Music Player", "CodeUtilities",
                                        "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");
                                stack.setCustomName(new LiteralText("§b§lFunction §3» Code§bUtilities§5 Music Player"));
                                ChatUtil.sendMessage("You received the §dMusic Player§b! Place it down in your codespace and open the chest to get functions!",
                                        ChatType.INFO_BLUE);
                                ItemUtil.giveCreativeItem(stack);
                            } else {
                                ChatUtil.sendTranslateMessage("codeutilities.command.require_creative_mode", ChatType.FAIL);
                            }
                            return 1;
                        })
                )
        );
    }
}
