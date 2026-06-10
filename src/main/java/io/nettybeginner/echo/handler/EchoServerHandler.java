package io.nettybeginner.echo.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

public class EchoServerHandler
    extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelActive(ChannelHandlerContext ctx){
        System.out.println(
                "Client connected: "
                + ctx.channel().id()
        );
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf msg){

        String received =
                msg.toString(StandardCharsets.UTF_8);

        System.out.println(
                "Received: " + received
        );

        ctx.writeAndFlush(msg);
    }
}
