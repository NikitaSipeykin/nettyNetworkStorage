package com.hw.clientPart;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hw.commonLibrary.Request;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class JSONEncoder extends MessageToMessageEncoder<Request> {
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Request request, List<Object> list) throws Exception {
        byte[] bytes = objectMapper.writeValueAsBytes(request);
        list.add(bytes);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
