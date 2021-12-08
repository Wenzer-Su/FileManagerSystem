package com.filemanager.manager;

import java.io.File;

/**
 * 文件工厂
 * 主要用于对应到系统用到的图片的文件
 * 方便快速查询文件
 */
public class FileFactory {
    private File fat;
    private File fileIcon;
    private File directory;
    private File icon;

    public FileFactory() {
        this.fat = new File("src/pictures/fat.png");
        this.fileIcon = new File("src/pictures/wenjian.png");
        this.directory = new File("src/pictures/mulu.png");
        this.icon=new File("src/pictures/game.png");
    }


    public File getFat() {
        return fat;
    }

    public void setFat(File fat) {
        this.fat = fat;
    }

    public File getFileIcon() {
        return fileIcon;
    }

    public void setFileIcon(File fileIcon) {
        this.fileIcon = fileIcon;
    }

    public File getDirectory() {
        return directory;
    }

    public void setDirectory(File directory) {
        this.directory = directory;
    }

    public File getIcon() {
        return icon;
    }

    public void setIcon(File icon) {
        this.icon = icon;
    }
}
