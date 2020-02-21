package me.reasonless.codeutilities.commands.nbs;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.realmsclient.gui.ChatFormatting;

import io.github.cottonmc.clientcommands.CottonClientCommandSource;
import me.reasonless.codeutilities.util.TemplateNBT;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.LiteralText;

public class NBSPlayerCommand {
	public static int execute(MinecraftClient mc, CommandContext<CottonClientCommandSource> ctx) throws Exception {
		ItemStack stack = new ItemStack(Items.JUKEBOX);

		TemplateNBT.setTemplateNBTGZIP(stack, "Music Player", "CodeUtilities", "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");
		stack.setCustomName(new LiteralText(ChatFormatting.DARK_PURPLE + "" + ChatFormatting.BOLD + "MUSIC PLAYER PACK"));

		mc.interactionManager.clickCreativeStack(stack, 36 + mc.player.inventory.selectedSlot);
		
		mc.player.sendMessage(new LiteralText(ChatFormatting.GOLD + " - " + ChatFormatting.YELLOW + "You have been given a music player pack!"));
		mc.player.sendMessage(new LiteralText(ChatFormatting.GOLD + " - " + ChatFormatting.YELLOW + "Place this in your codespace and open the chest to get music functions!"));
		mc.player.sendMessage(new LiteralText(ChatFormatting.DARK_PURPLE + " - " + ChatFormatting.LIGHT_PURPLE + "If you don't know how to use, check our discord!"));
		mc.player.sendMessage(new LiteralText(ChatFormatting.DARK_AQUA + " - " + ChatFormatting.AQUA + "" + ChatFormatting.UNDERLINE + "https://discord.gg/QBmXaGb"));

		return 1;
	}
}
