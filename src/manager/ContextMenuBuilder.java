package com.filemanager.manager;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

/**
 * 菜单工厂
 * 构建两种菜单
 * 一种适用于文件对象
 * 一种适用于目录对象
 */
public class ContextMenuBuilder {
    private ViewerPane viewerPane;

    public ContextMenuBuilder(ViewerPane viewerPane) {
        this.viewerPane = viewerPane;
    }

    /**
     * 组建加在文件对象上的菜单
     *
     * @return
     */
    public ContextMenu buildFileMenu() {
        MenuItemBuilder menuItemBuilder = viewerPane.getMenuItemBuilder();

        //创建文件菜单的步骤
        ContextMenu contextMenu = new ContextMenu();
        // “打开文件”选项
        MenuItem openFileItem = menuItemBuilder.getOpenFileItem();
        // “更改属性”选项
        MenuItem setPropertyItem = menuItemBuilder.getSetPropertyItem();
        //“删除文件”选项
        MenuItem deleteFileItem = menuItemBuilder.getDeleteFileItem();
        //组装到菜单
        contextMenu.getItems().addAll(openFileItem, setPropertyItem, deleteFileItem);

        return contextMenu;
    }

    /**
     * 组建加在目录对象上的菜单
     *
     * @return
     */
    public ContextMenu buildDirMenu() {
        MenuItemBuilder menuItemBuilder = viewerPane.getMenuItemBuilder();
        //创建目录菜单的步骤
        ContextMenu contextMenu = new ContextMenu();
        // “添加子目录”选项
        MenuItem addDirItem = menuItemBuilder.getAddDirItem();
        //“添加文件”选项
        MenuItem addFileItem = menuItemBuilder.getAddFileItem();
        //“删除目录”选项
        MenuItem deleteFileItem = menuItemBuilder.getDeleteFileItem();
        //组装菜单
        contextMenu.getItems().addAll(addDirItem, addFileItem, deleteFileItem);
        return contextMenu;
    }

}
