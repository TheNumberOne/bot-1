package everyos.discord.bot.command.fun;

import discord4j.core.object.entity.Message;
import everyos.discord.bot.command.CommandData;
import everyos.discord.bot.command.ICommand;
import reactor.core.publisher.Mono;

public class LuckCommand implements ICommand { //For every 10000 messages posted, this command will give 20 gems
	@Override public Mono<?> execute(Message message, CommandData data, String argument) {
		return null;
	}
}
