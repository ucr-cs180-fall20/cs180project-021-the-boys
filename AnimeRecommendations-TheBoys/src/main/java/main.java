import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;

public class main extends ListenerAdapter {
    private static JDA jda;
    private static Operations op;

    public static void main(String[] args) throws  LoginException {
        op = new Operations();
        jda = JDABuilder.createDefault("NzExMDY3NjczNjg0ODY5MTgw.Xr9neA.eVMng9D87Y-WWf0JoYGOxP0oDU4").build();
        jda.addEventListener(new main());
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String channelID = event.getChannel().getId();
        if(channelID.equals("766151933307125763")){
            if(event.getAuthor().isBot()) return;
            System.out.println("Message received: " + event.getMessage().getContentRaw() + "\n From: " + event.getAuthor().getName());

            if(event.getMessage().getContentRaw().equals("hello")){
                System.out.println("Replied: Hello World");
                event.getChannel().sendMessage("Hello World!").complete();
            }
            if(event.getMessage().getContentRaw().startsWith("test")){
                op.animeListEmbed(event);
            }
            if(event.getMessage().getContentRaw().equals("save")){
                op.messageEmbed(event);
                System.out.println("Replied: Saving CSV");
                event.getChannel().sendMessage("Saving CSV").complete();
            }
        }
    }
}