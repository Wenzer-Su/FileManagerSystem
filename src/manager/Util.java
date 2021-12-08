package com.filemanager.manager;

import javafx.scene.control.TreeItem;

import java.util.ArrayList;
import java.util.Map;

public class Util {
    /**
     * DFS部分
     * 用于递归搜寻删除文件和目录
     */
    public static void dfs(TreeItem treeItem, ViewerPane viewerPane) {
        Fat fat = viewerPane.getFat();
        ArrayList<TreeItem> items = new ArrayList<>();
        for (int i = 0; i < treeItem.getChildren().size(); i++) {
            items.add((TreeItem) treeItem.getChildren().get(i));
        }
        for (int j = 0; j < items.size(); j++) {
            dfs(items.get(j), viewerPane);
        }
        Map<TreeItem, Integer> allocationMap = viewerPane.getAllocationMap();
        Map<TreeItem, FileData> fileDataMap = viewerPane.getFileDataMap();
        ArrayList<FileData> fileDataList = viewerPane.getFileDataList();
        Map<TreeItem, Map<String, Boolean>> itsChildrenMap = viewerPane.getItsChildrenMap();
        Map<TreeItem, TreeItem> itsFatherMap = viewerPane.getItsFatherMap();
        ArrayList<TreeItem> treeItemList = viewerPane.getTreeItemList();

        //取消fat表的分配
        fat.releaseFat(allocationMap.get(treeItem));
        //取消分配登记
        allocationMap.remove(treeItem);
        //计数-1
        viewerPane.setCounter(viewerPane.getCounter() - 1);
        //取消数据对象的关联并取消注册数据对象
        FileData fileData = fileDataMap.get(treeItem);
        fileDataMap.put(treeItem, null);
        fileDataMap.remove(treeItem);
        fileDataList.remove(fileData);
        //从子结点存在性登记Map中删除自己这个父结点
        itsChildrenMap.remove(treeItem);
        //在父结点的子结点存在性map中移除这个子结点
        itsChildrenMap.get(itsFatherMap.get(treeItem)).remove(treeItem);
        //在父结点的子结点列表中删除这个结点
        itsFatherMap.get(treeItem).getChildren().remove(treeItem);
        //取消结点注册
        treeItemList.remove(treeItem);
        System.out.println("Delete-->" + treeItem.getValue());
    }
}
