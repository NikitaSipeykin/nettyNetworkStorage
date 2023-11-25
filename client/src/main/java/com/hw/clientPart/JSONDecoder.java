package com.hw.clientPart;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hw.commonLibrary.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class JSONDecoder extends MessageToMessageDecoder<byte[]> {
    ObjectMapper objectMapper = new ObjectMapper();
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, byte[] bytes, List<Object> list) throws Exception {
        Response response = objectMapper.readValue(bytes, Response.class);
        list.add(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
