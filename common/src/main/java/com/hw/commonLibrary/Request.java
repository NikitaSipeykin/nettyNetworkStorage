package com.hw.commonLibrary;

public class Request {
    private String fileName;
    private String command;
    private byte[] filePiece;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public byte[] getFilePiece() {
        return filePiece;
    }

    public void setFilePiece(byte[] filePiece) {
        this.filePiece = filePiece;
    }
}
