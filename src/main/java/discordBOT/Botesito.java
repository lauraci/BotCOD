package discordBOT;

import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.GuildMessageChannel;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.MessageCreateSpec;
import discord4j.rest.util.Color;
import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.Instant;

import static discord4j.core.object.audit.OptionKey.CHANNEL_ID;

public class Botesito {
    public static void main(final String[] args) {
        final String token = "OTY2NzYyODMzMDYyODAxNDk5.YmGeDA.My3qTMwL56Y0dnAX3yp76KNDUSs";//Se crea una variable String para almacenar el token
        final DiscordClient client = DiscordClient.create(token);//Se crea el cliente utilizado el token
        final GatewayDiscordClient gateway = client.login().block();//Se crea una pasarela usando el cliente

           /*
          Creamos 3 constantes:
          -Una llamada token que es donde guarda el token del bot de Discord
          -Una llamada cliente para el discord
          -Una llamada gateway para podernos logear en el discord

    /*
      Aqui hacemos el embed donde nos da el titulo y la imagen del mismo.
     */

        EmbedCreateSpec embed = EmbedCreateSpec.builder()
                .title("Loki")
                .image("attachment://loki.jpg")
                .build();

        gateway.on(MessageCreateEvent.class).subscribe(event -> {
            final Message message = event.getMessage();

        /*
          Aqui hacemos el !ping donde si nosotros enviamos !ping el bot nos devolverá el !Pong
         */

            if ("!ping".equals(message.getContent())) {
                final MessageChannel channel = message.getChannel().block();
                channel.createMessage("Pong!").block();
            }


        /*
        Aqui está el funcionamiento del !embed que es muy parecido al !ping y Pong! solo que nos devuelve un embed
        en vez de un mensaje.
         */

            if ("!embed".equals(message.getContent())) {
                String IMAGE_URL = "https://i.imgflip.com/1d4rel.jpg";
                String ANY_URL = "https://www.youtube.com/watch?v=0HVI9Zr3FgY";
                final MessageChannel channel = message.getChannel().block();
                EmbedCreateSpec.Builder builder = EmbedCreateSpec.builder();
                builder.author("¿Pues quién va a ser? Laura", ANY_URL, IMAGE_URL);
                builder.image(IMAGE_URL);
                builder.title("Mi querido Bot");
                builder.url(ANY_URL);
                builder.description("Bienvenidos al canal, sentaos y poneos cómodos. Empieza el camino del terror");
                builder.thumbnail(IMAGE_URL);
                builder.footer("GIF", IMAGE_URL);
                builder.timestamp(Instant.now());
                channel.createMessage(builder.build()).block();
            }

        /*
          Si escribimos "!img" el bot mandará una imágen y está en un try catch por si no encuentra la imagen
          (FileNotFoundException).
         */

            if ("!img".equals(message.getContent())) {
                final MessageChannel channel = message.getChannel().block();

                InputStream fileAsInputStream = null;
                try {
                    fileAsInputStream = new FileInputStream("cat.jpg");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                channel.createMessage(MessageCreateSpec.builder()
                        .content("Lindo gatito")
                        .addFile("cat.jpg", fileAsInputStream)
                        .addEmbed(embed)
                        .build()).subscribe();
            }
        });

        gateway.onDisconnect().block();
    }
}

