package everyos.bot.luwu.command.modules.moderation;

import java.util.ArrayList;

import everyos.bot.chat4j.enm.ChatPermission;
import everyos.bot.chat4j.functionality.member.ChatMemberModerationInterface;
import everyos.bot.luwu.annotation.CommandID;
import everyos.bot.luwu.annotation.Help;
import everyos.bot.luwu.annotation.Permissions;
import everyos.bot.luwu.command.modules.moderation.BanCommand.BanArguments;
import everyos.bot.luwu.entity.Locale;
import everyos.bot.luwu.entity.Member;
import everyos.bot.luwu.exception.TextException;
import everyos.bot.luwu.parser.ArgumentParser;
import reactor.core.publisher.Mono;

@CommandID(id=1)
@Permissions(permissions={ChatPermission.BAN})
@Help(help="command.ban.help", ehelp="command.ban.ehelp", usage="command.ban.usage")
public class BanCommand extends ModerationCommandBase<BanArguments> {
	private BanCommand() {
		super(new ChatPermission[] {ChatPermission.BAN});
	}
	
	private static BanCommand instance;
	public static BanCommand get() {
		if (instance==null) instance = new BanCommand();
		return instance;
	}
	
	@Override protected Mono<BanArguments> parseArgs(ArgumentParser parser, Locale locale) {
		//Read each of the users that must be banned
		ArrayList<Long> ids = new ArrayList<>();
		String reason = null;
		while (!parser.isEmpty()) {
			if (!parser.couldBeUserID()) {
				return Mono.error(new TextException(locale.localize("command.error.usage", "expected", locale.localize("user"), "got", "`"+parser.eat()+"`")));
			}
			ids.add(parser.eatUserID());
			
			//TODO: Temp time
			
			//We have found a mod-log reason
			if (!parser.isEmpty()&&parser.peek().equals(";")) {
				parser.eat();
				reason = parser.toString();
				break;
			}
		}
		
		//Prevents us from hitting ratelimits, probably
		if (ids.size()<1) return Mono.error(new TextException(locale.localize("command.error.banmin", "min", "1")));
		if (ids.size()>3) return Mono.error(new TextException(locale.localize("command.error.banmax", "max", "3")));//Read each of the users that must be banned
		
		String freason = reason;
		return Mono.just(new BanArguments() {
			@Override public Long[] getUsers() {
				return ids.toArray(new Long[ids.size()]);
			}

			@Override public String getReason() {
				return freason;
			}
		});
	}
	
	@Override protected Mono<Result> performAction(BanArguments arguments, Member member, Locale locale) {
		return member.getInterface(ChatMemberModerationInterface.class).ban(arguments.getReason())
			.then(Mono.just(new Result(true, member)));
	}
	
	static protected interface BanArguments extends ModerationArguments {
		
	}
}
