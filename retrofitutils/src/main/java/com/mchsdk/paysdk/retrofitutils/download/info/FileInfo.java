package com.mchsdk.paysdk.retrofitutils.download.info;


public class FileInfo {
    public String fileName;
    public String parentPath;
    public long fileTotalSize;
    public long fileCurrentSize;
    public boolean done;

    @Override
    public String toString() {
        return "FileInfo{" +
                "fileName='" + fileName + '\'' +
                ", parentPath='" + parentPath + '\'' +
                ", fileTotalSize=" + fileTotalSize +
                ", fileCurrentSize=" + fileCurrentSize +
                ", done=" + done +
                '}';
    }
}
