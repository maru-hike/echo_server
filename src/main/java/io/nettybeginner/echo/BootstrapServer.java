package io.nettybeginner.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.nettybeginner.echo.pipeline.EchoChannelInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BootstrapServer {

    private static final Logger log = LoggerFactory.getLogger(BootstrapServer.class);

    public static void main(String[] args) throws Exception {

        log.info(
                "Available processors: {}",
                Runtime.getRuntime().availableProcessors()
        );

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new EchoChannelInitializer());

            ChannelFuture future = bootstrap.bind(8080).sync();

            log.info("Server listening on port 8080");

            Channel serverChannel = future.channel();
            Runtime.getRuntime().addShutdownHook(
                    new Thread(() -> {

                        log.info("Shutdown requested");

                        serverChannel.close();

                    })
            );
            serverChannel.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}