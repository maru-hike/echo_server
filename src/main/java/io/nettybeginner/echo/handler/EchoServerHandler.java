package io.nettybeginner.echo.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

import io.nettybeginner.echo.protocol.CommandProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class EchoServerHandler
    extends SimpleChannelInboundHandler<String> {
    private static final Logger log =
            LoggerFactory.getLogger(
                    EchoServerHandler.class
            );
    private final CommandProcessor processor =
            new CommandProcessor();
    private static final AttributeKey<String> USERNAME = AttributeKey.valueOf("username");

    @Override
    public void channelActive(ChannelHandlerContext ctx){
       log.info(
                "[{}] Client connected"
                , ctx.channel().id()
        );
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg){

        String command, args, reply;

        log.info(
                "[{}] Received: {}", ctx.channel().id(), msg
        );
        int firstSpace = msg.indexOf(' ');
        if (firstSpace == -1) {
            command = msg;
            args = "";
        } else {
            command = msg.substring(0, firstSpace);
            args = msg.substring(firstSpace+1);
        }
        command = command.toUpperCase();
        reply = processor.process(ctx, command, args);
        ctx.writeAndFlush(reply + "\n");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {

        log.info(
                "[{}] Client disconnected"
                        , ctx.channel().id()
        );
    }

    @Override
    public void exceptionCaught(
            ChannelHandlerContext ctx,
            Throwable cause) {

        log.error(
                "Connection error: {}"
                        , ctx.channel().id(), cause
        );

        ctx.close();
    }

    @Override
    public void channelRegistered(
            ChannelHandlerContext ctx)
            throws Exception {

        log.info(
                "[{}] channelRegistered",
                ctx.channel().id()
        );

        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(
            ChannelHandlerContext ctx)
            throws Exception {

        log.info(
                "[{}] channelUnregistered",
                ctx.channel().id()
        );

        super.channelUnregistered(ctx);
    }
}
