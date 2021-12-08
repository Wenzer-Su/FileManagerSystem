package com.filemanager.manager;

import javafx.scene.image.ImageView;

/**
 * 数据类
 * 文件或目录均可用
 * 存储该对象的信息
 */
public class FileData {
    /*
    类型
    1：目录
    2：文件
     */
    public static final int DIRECTORY = 1;
    public static final int FILE = 2;

    //文件名或目录名
    private String name;
    //文件或目录的图标
    private ImageView iconImageView;
    /*
    类型
    1：目录
    2：文件
     */
    private int type;
    //文件内容
    private String content;
    /*
    可读写标识
    true：可读写
    false：只读
     */
    private boolean writable = true;

    /**
     * 无参构造方法，主要用这个
     */
    public FileData() {
        //默认可读写
        this.writable = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImageView getIconImageView() {
        return iconImageView;
    }

    public void setIconImageView(ImageView iconImageView) {
        this.iconImageView = iconImageView;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isWritable() {
        return writable;
    }

    public void setWritable(boolean writable) {
        this.writable = writable;
    }
}
