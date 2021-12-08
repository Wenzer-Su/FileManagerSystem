package com.filemanager.manager;

import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Map;

/**
 * 目录树结点工厂
 * 对外提供生成目录结点和文件结点两种
 */
public class TreeItemBuilder {
    private ViewerPane viewerPane;

    public TreeItemBuilder(ViewerPane viewerPane) {
        this.viewerPane = viewerPane;
    }

    public TreeItem getFileItem(String itemName) {
        Map<TreeItem, FileData> fileDataMap = viewerPane.getFileDataMap();
        FileFactory fileFactory = viewerPane.getFileFactory();

        //创建文件结点步骤
        TreeItem treeItem = this.buildItem(itemName, FileData.FILE);
        FileData fileData = fileDataMap.get(treeItem);
        ImageView imageView = new ImageView(new Image(fileFactory.getFileIcon().toURI().toString()));
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        fileData.setIconImageView(imageView);
        treeItem.setGraphic(imageView);

        return treeItem;
    }

    /**
     * 构建目录类型的结点构造器
     * 构建目录类型结点
     * 设置结点图标
     *
     * @param itemName
     * @return
     */
    public TreeItem getDirItem(String itemName) {
        Map<TreeItem, FileData> fileDataMap = viewerPane.getFileDataMap();
        FileFactory fileFactory = viewerPane.getFileFactory();

        //创建目录结点步骤
        TreeItem treeItem = this.buildItem(itemName, FileData.DIRECTORY);
        FileData fileData = fileDataMap.get(treeItem);
        ImageView imageView = new ImageView(new Image(fileFactory.getDirectory().toURI().toString()));
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        fileData.setIconImageView(imageView);
        treeItem.setGraphic(imageView);

        return treeItem;
    }

    /**
     * 创建一个树结点
     * 关联数据对象并指定类型
     * 并在FAT表中分配其位置
     *
     * @param itemName
     * @return
     */
    private TreeItem buildItem(String itemName, int itemType) {
        ArrayList<TreeItem> treeItemList = viewerPane.getTreeItemList();
        Map<TreeItem, FileData> fileDataMap = viewerPane.getFileDataMap();
        ArrayList<FileData> fileDataList = viewerPane.getFileDataList();
        Map<TreeItem, Integer> allocationMap = viewerPane.getAllocationMap();
        Fat fat = viewerPane.getFat();

        //创建结点步骤
        TreeItem treeItem = new TreeItem(itemName);
        //注册结点
        treeItemList.add(treeItem);
        if (fileDataMap.get(treeItem) == null) {
            // 关联数据对象
            FileData fileData = new FileData();
            fileData.setName(itemName);
            fileData.setType(itemType);
            //加到总数据列表里
            fileDataList.add(fileData);
            fileDataMap.put(treeItem, fileData);
        }
        allocationMap.put(treeItem, fat.getFatLocation());

        return treeItem;
    }

}
