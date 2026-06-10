package io.nettybeginner.echo.pipeline;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.nettybeginner.echo.handler.EchoServerHandler;

public class EchoChannelInitializer
    extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) {
        System.out.println("New channel initialized: " + ch.id());
        ch.pipeline()
                .addLast(new EchoServerHandler());
    }
}
