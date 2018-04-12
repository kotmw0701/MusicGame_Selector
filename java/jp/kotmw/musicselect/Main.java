package jp.kotmw.musicselect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.security.auth.login.LoginException;

import jp.kotmw.musicselect.Listener.EventListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class Main {

	static boolean base = false;
	
	public static void main(String[] args) throws ClassNotFoundException, IOException, LoginException, InterruptedException, TwitterException {
		//DataAdder.createChuniDataBase();
		if(base)
			return;
		TwitterStream stream = new TwitterStreamFactory().getInstance();
		stream.addListener(new EventListener());
		stream.user();
		JDA jda = new JDABuilder(AccountType.BOT).setToken("*****").addEventListener(new EventListener()).buildBlocking();
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
			while(true) {
				String text = reader.readLine();
				if(text == null || text.isEmpty())
					continue;
				if(!text.startsWith("%"))
					continue;
				String[] command = text.split(" ");
				if(command[0].equalsIgnoreCase("%stop")) {
					jda.shutdown();
					stream.shutdown();
					System.exit(0);
					return;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
