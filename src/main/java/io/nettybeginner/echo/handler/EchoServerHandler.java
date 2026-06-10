package io.nettybeginner.echo.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

public class EchoServerHandler
    extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelActive(ChannelHandlerContext ctx){
        System.out.println(
                "Client connected: "
                + ctx.channel().id()
        );
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg){

        System.out.println(
                "Received: " + msg
        );

        ctx.writeAndFlush(msg + "\n");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {

        System.out.println(
                "Client disconnected: "
                        + ctx.channel().id()
        );
    }

    @Override
    public void exceptionCaught(
            ChannelHandlerContext ctx,
            Throwable cause) {

        System.err.println(
                "Connection error: "
                        + ctx.channel().id()
        );

        cause.printStackTrace();

        ctx.close();
    }
}
