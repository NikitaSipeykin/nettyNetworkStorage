package com.hw.serverPart;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hw.commonLibrary.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class JSONEncoder extends MessageToMessageEncoder<Response> {
    ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = Logger.getLogger(JSONEncoder.class.getName());

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Response response, List<Object> list) throws Exception {
        byte[] bytes = objectMapper.writeValueAsBytes(response);
        list.add(bytes);
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
