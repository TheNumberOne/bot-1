package everyos.discord.exobot.util;

import java.util.function.Consumer;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.spec.EmbedCreateSpec;
import reactor.core.publisher.Mono;

public class MessageHelper {
	public static String filter(String str) {
		return str.replace("@everyone", "@ everyone").replace("@here", "@ here");
	}
	
	public static void send(Mono<MessageChannel> channel, String message, boolean permitPing) {
		send(channel.block(), message, permitPing);
	}
	public static void send(MessageChannel channel, String message, boolean permitPing) {
		message = filter(message);
		channel.createMessage(message).subscribe();
	}
	public static void send(MessageChannel channel, Consumer<? super EmbedCreateSpec> embed) {
		channel.createEmbed(embed).subscribe();
    }

    public static Message sendThen(Mono<MessageChannel> channel, String message, boolean permitPing) {
		return sendThen(channel.block(), message, permitPing);
	}
    public static Message sendThen(MessageChannel channel, String message, boolean permitPing) {
		message = filter(message);
		return channel.createMessage(message).block();
	}
	public static Message sendThen(MessageChannel channel, Consumer<? super EmbedCreateSpec> embed) {
		return channel.createEmbed(embed).block();
	}
}
