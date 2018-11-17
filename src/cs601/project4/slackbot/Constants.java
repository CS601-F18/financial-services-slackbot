package cs601.project4.slackbot;

public class Constants {
	public static String CLIENT_ID = "378520430422.465486620272";
	public static String CLIENT_SECRET = "5697590ee4af29d32f2cb5d8317fb657";
	public static String SCOPE = "incoming-webhook,commands,bot, chat:write:bot, im:history";
	public static String IDENTITY_SCOPE = "identity.basic";
	public static String REDIRECT = "https://25badcb0.ngrok.io/auth/confirm";
	public static String REDIRECT_HOME = "https://25badcb0.ngrok.io/auth/confirm/home";
	public static String TOKEN = "xoxp-378520430422-418142168310-465487273584-5dc666eb36b0dd7923f3a2ef6495ae61";
	public static String BOT_TOKEN = "xoxb-378520430422-473875729105-c3V9GN0HJLYfmz3CzdTeP6I0";
	public static String BOT_ID = "UDXRRMF33";
	public static String BOT_ID_2 = "UE51ZAPCL";
	public static final String API_DESTINATION_CHAT = "https://slack.com/api/chat.postMessage";
	public static final String API_DESTINATION_RTM = "https://slack.com/api/rtm.connect";
}
