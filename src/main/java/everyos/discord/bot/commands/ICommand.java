package everyos.discord.bot.commands;

import java.util.HashMap;

import discord4j.core.object.entity.Message;
import everyos.discord.bot.adapter.MessageAdapter;
import everyos.discord.bot.localization.Localization;
import everyos.discord.bot.localization.LocalizedString;
import everyos.discord.bot.object.CategoryEnum;

public interface ICommand {
	ICommand invalidSubcommand = new InvalidSubcommand();
	
	void execute(Message message, MessageAdapter adapter, String argument);
    HashMap<String, ICommand> getSubcommands(Localization locale);
    String getBasicUsage(Localization locale);
    String getExtendedUsage(Localization locale);
	CategoryEnum getCategory();
}

class InvalidSubcommand implements ICommand {
	@Override public void execute(Message message, MessageAdapter adapter, String argument) {
		adapter.getChannelAdapter(cadapter->
			adapter.formatTextLocale(LocalizedString.NoSuchSubcommand, str->cadapter.send(str))
		);
	}

	@Override public HashMap<String, ICommand> getSubcommands(Localization locale) { return null; }
	@Override public String getBasicUsage(Localization locale) { return null; }
	@Override public String getExtendedUsage(Localization locale) { return null; }
	@Override public CategoryEnum getCategory() { return null; }
}