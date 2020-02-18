package everyos.discord.bot.filter;

public class EveryoneFilter implements Filter {
	public static EveryoneFilter filter;

	@Override public String filter(String text) {
		return text.replace("@everyone", "@ everyone").replace("@ here", "@here");
	}
	
	static {
		filter = new EveryoneFilter();
	}
}
