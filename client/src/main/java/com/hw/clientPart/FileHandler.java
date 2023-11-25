package com.hw.clientPart;

import com.hw.commonLibrary.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.RandomAccessFile;

public class FileHandler extends SimpleChannelInboundHandler<Response> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Response response) throws Exception {
        try (RandomAccessFile accessFile = new RandomAccessFile("Response_" + response.getFileName(), "rw")){
            accessFile.seek(response.getPosition());
            accessFile.write(response.getFile());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
