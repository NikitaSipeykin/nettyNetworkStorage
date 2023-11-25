package com.hw.serverPart;

import com.hw.commonLibrary.Request;
import com.hw.commonLibrary.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class FileHandler extends SimpleChannelInboundHandler<Request> {
    LocalDateTime currentDateTime = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final Logger logger = Logger.getLogger(FileHandler.class.getName());
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Request request) throws Exception {
        String dateTime = currentDateTime.format(formatter);

        LogManager logManager = LogManager.getLogManager();
        logManager.readConfiguration(new FileInputStream("server/src/main/resources/logging.properties"));

        String fileName = request.getFileName();
        byte[] buffer = new byte[1024 * 512];
        try(RandomAccessFile randomAccessFile = new RandomAccessFile(fileName, "r")){
            while (true){
                Response response = new Response();
                response.setFileName(fileName);
                response.setPosition(randomAccessFile.getFilePointer());
                int read = randomAccessFile.read(buffer);
                if(read < buffer.length -1 ){
                    byte[] tempBuffer = new byte[read];
                    System.arraycopy(buffer, 0, tempBuffer, 0, read);
                    response.setFile(tempBuffer);
                    channelHandlerContext.writeAndFlush(response);
                    logger.log(Level.INFO, "File is download! "  + " date: " + dateTime);
                    break;
                }else {
                    response.setFile(buffer);
                    channelHandlerContext.writeAndFlush(response);
                }
                buffer = new byte[1024 * 512];
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        String dateTime = currentDateTime.format(formatter);

        logger.log(Level.SEVERE, "Exception caught: " + ctx + " date: " + dateTime, cause);
    }
}
