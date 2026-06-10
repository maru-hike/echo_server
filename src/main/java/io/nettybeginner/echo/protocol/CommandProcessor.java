package io.nettybeginner.echo.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;


public class CommandProcessor {
    private static final AttributeKey<String> USERNAME =
            AttributeKey.valueOf("username");
    public String process(
            ChannelHandlerContext ctx,
            String command,
            String args
    ) {
        return switch (command) {
            case "PING" -> "PONG";
            case "LOGIN" -> {
                ctx.channel()
                        .attr(USERNAME)
                        .set(args);

                yield "Welcome " + args + "!";
            }
            case "ECHO" -> args;
            case "WHOAMI" -> {

                String username =
                        ctx.channel()
                                .attr(USERNAME)
                                .get();

                if (username == null) {
                    yield "anonymous";
                }

                yield username;
            }
            default -> "ERROR Unknown command";
        };
    }
}
