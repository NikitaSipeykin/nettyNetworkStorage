package com.hw.clientPart;

import com.hw.commonLibrary.Request;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;

import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;

import java.io.IOException;

public class Client {


    public static void main(String[] args) throws InterruptedException, IOException {
        new Client().start();
    }

    private void start() throws InterruptedException {
        NioEventLoopGroup loopGroup = new NioEventLoopGroup();
        try{
            Bootstrap client = new Bootstrap();
            client.
                    group(loopGroup).
                    channel(NioSocketChannel.class).
                    handler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                    nioSocketChannel.pipeline().addLast(
                            new LengthFieldBasedFrameDecoder(
                                    1024 * 1024 * 1024,
                                    0,
                                    8,
                                    0,
                                    8),
                            new LengthFieldPrepender(8),
                            new ByteArrayDecoder(),
                            new ByteArrayEncoder(),
                            new JSONDecoder(),
                            new JSONEncoder(),
                            new FileHandler()
                    );
                }
            });

            ChannelFuture channelFuture = client.connect("localhost", 9000).sync();

            System.out.println("Client started");


            Request request = new Request();
            request.setFileName("beer.mp3");

            channelFuture.channel().writeAndFlush(request);
            channelFuture.channel().closeFuture().sync();

        }finally {
            loopGroup.shutdownGracefully();
        }
    }
}
