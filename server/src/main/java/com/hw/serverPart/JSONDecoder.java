package com.hw.serverPart;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hw.commonLibrary.Request;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class JSONDecoder extends MessageToMessageDecoder<byte[]> {
    ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = Logger.getLogger(JSONDecoder.class.getName());

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, byte[] bytes, List<Object> list) throws Exception {
        Request request = objectMapper.readValue(bytes, Request.class);
        list.add(request);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LogManager logManager = LogManager.getLogManager();
        logManager.readConfiguration(new FileInputStream("server/src/main/resources/logging.properties"));

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTime = currentDateTime.format(formatter);

        logger.log(Level.SEVERE, "Exception caught: " + ctx + " date:" + dateTime, cause);
    }
}
