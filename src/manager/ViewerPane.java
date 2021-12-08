package com.filemanager.manager;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 主要面板，负责主要面板的构建工作
 */
public class ViewerPane extends BorderPane {
    /**
     * 各种工厂
     */
    private FileFactory fileFactory = new FileFactory();
    private TreeItemBuilder treeItemBuilder;
    private ButtonBuilder buttonBuilder;
    private MenuItemBuilder menuItemBuilder;
    private ContextMenuBuilder contextMenuBuilder;
    /**
     * 相关的数据域
     */
    private Fat fat;
    //关联 目录/文件-->数据对象
    private Map<TreeItem, FileData> fileDataMap;
    //当前目录下的子对象-->文件/子目录，标识其存在
    private Map<TreeItem, Map<String, Boolean>> itsChildrenMap;
    //FAT表位置分配
    private Map<TreeItem, Integer> allocationMap;
    //所有子目录、文件数据对象的列表
    private ArrayList<FileData> fileDataList;
    //所有目录树上的结点汇总
    private ArrayList<TreeItem> treeItemList;
    //指明父结点的键值对：Key:子-->Value:父
    private Map<TreeItem, TreeItem> itsFatherMap;
    //总对象数-->文件+目录
    private int counter;

    /**
     * 运行过程中要用到的对象
     */
    //总目录树
    private TreeView folderTreeView;
    //目录树的根节点
    private TreeItem rootItem;
    //现在选中的结点
    private TreeItem nowOpenNode;
    private Button fatButton;


    public ViewerPane() {
        this.initField();
        this.buildFactories();
        /**
         * 构建两大主要组件
         */
        buildTreeView();
        buildFatButton();
        /**
         * 把构建好的东西都组装到总面板上
         */
        this.setLeft(folderTreeView);
        this.setRight(fatButton);

//        this.counter=128;
    }

    /**
     * 初始化数据域
     */
    private void initField() {
        this.fileDataList = new ArrayList<FileData>();
        this.treeItemList = new ArrayList<TreeItem>();
        this.allocationMap = new HashMap<>();
        this.itsFatherMap = new HashMap<>();
        this.fileDataMap = new HashMap<>();
        this.itsChildrenMap = new HashMap<>();
        this.fat = new Fat(128);
        this.counter = 0;

        buildTreeView();

    }

    /**
     * 构建各个工厂
     */
    private void buildFactories() {
//        this.fileFactory = new FileFactory();
        this.treeItemBuilder = new TreeItemBuilder(this);
        this.buttonBuilder = new ButtonBuilder(this);
        this.menuItemBuilder = new MenuItemBuilder(this);
        this.contextMenuBuilder = new ContextMenuBuilder(this);
    }

    /**
     * 构建整个目录树
     */
    private void buildTreeView() {
        /**
         * 构建根结点部分
         */
        this.rootItem = new TreeItem("root");
        ImageView rootItemView = new ImageView(new Image(this.fileFactory.getDirectory().toURI().toString()));
        rootItemView.setFitWidth(20);
        rootItemView.setFitHeight(20);
        FileData rootData = new FileData();
        rootData.setType(FileData.DIRECTORY);
        rootData.setName("root");
        rootData.setIconImageView(rootItemView);
        rootItem.setGraphic(rootItemView);

        //注册数据对象
        fileDataList.add(rootData);
        //注册根结点
        treeItemList.add(rootItem);
        //分配FAT表位置
//        System.out.println();
//        System.out.println("构建目录树时：");
//        fat.showFat();
        allocationMap.put(rootItem, 1);
//        fat.getFatLocation();
//        System.out.println();
//        System.out.println("构建目录树后：");
//        fat.showFat();
        //父结点表注册，没有父结点
        itsFatherMap.put(rootItem, null);
        //关联数据对象
        if (fileDataMap.get(rootItem) == null) {
            fileDataMap.put(rootItem, rootData);
        }
        //更新结点总数
        this.counter = 1;

        /**
         * 构建目录树部分
         */
        this.folderTreeView = new TreeView(rootItem);
        folderTreeView.setMinHeight(700);
        folderTreeView.setMinWidth(300);
        folderTreeView.addEventFilter(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (mouseEvent.getButton().name().equalsIgnoreCase(MouseButton.SECONDARY.name())) {
                            nowOpenNode = (TreeItem) folderTreeView.getSelectionModel().getSelectedItem();
                            FileData nowFileData = fileDataMap.get(nowOpenNode);
                            if (nowFileData.getType() == FileData.FILE) {
                                folderTreeView.setContextMenu(contextMenuBuilder.buildFileMenu());
                            } else {
                                folderTreeView.setContextMenu(contextMenuBuilder.buildDirMenu());
                            }
                            //获取当前目录树中的所有的键值对
                            Set<Map.Entry<TreeItem, FileData>> entrySet = fileDataMap.entrySet();
                            for (Map.Entry<TreeItem, FileData> entry : entrySet) {
                                System.out.println(entry.getKey().getValue() + "-->" + entry.getValue().getType());
                            }
                        }
                    }
                }
        );
    }

    /**
     * 构建打开FAT表的按钮
     */
    private void buildFatButton() {
        this.fatButton = new Button();
        ImageView imageView = new ImageView(new Image(fileFactory.getFat().toURI().toString()));
        imageView.setFitHeight(355);
        imageView.setFitWidth(474);
        fatButton.setGraphic(imageView);
        fatButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                fat.showFat();
                FatView fatView = new FatView(fat, counter);
                fatView.showFat();
            }
        });
    }

    public FileFactory getFileFactory() {
        return fileFactory;
    }

    public void setFileFactory(FileFactory fileFactory) {
        this.fileFactory = fileFactory;
    }

    public TreeItemBuilder getTreeItemBuilder() {
        return treeItemBuilder;
    }

    public void setTreeItemBuilder(TreeItemBuilder treeItemBuilder) {
        this.treeItemBuilder = treeItemBuilder;
    }

    public ButtonBuilder getButtonBuilder() {
        return buttonBuilder;
    }

    public void setButtonBuilder(ButtonBuilder buttonBuilder) {
        this.buttonBuilder = buttonBuilder;
    }

    public MenuItemBuilder getMenuItemBuilder() {
        return menuItemBuilder;
    }

    public void setMenuItemBuilder(MenuItemBuilder menuItemBuilder) {
        this.menuItemBuilder = menuItemBuilder;
    }

    public ContextMenuBuilder getContextMenuBuilder() {
        return contextMenuBuilder;
    }

    public void setContextMenuBuilder(ContextMenuBuilder contextMenuBuilder) {
        this.contextMenuBuilder = contextMenuBuilder;
    }

    public Fat getFat() {
        return fat;
    }

    public void setFat(Fat fat) {
        this.fat = fat;
    }

    public Map<TreeItem, FileData> getFileDataMap() {
        return fileDataMap;
    }

    public void setFileDataMap(Map<TreeItem, FileData> fileDataMap) {
        this.fileDataMap = fileDataMap;
    }

    public Map<TreeItem, Map<String, Boolean>> getItsChildrenMap() {
        return itsChildrenMap;
    }

    public void setItsChildrenMap(Map<TreeItem, Map<String, Boolean>> itsChildrenMap) {
        this.itsChildrenMap = itsChildrenMap;
    }

    public Map<TreeItem, Integer> getAllocationMap() {
        return allocationMap;
    }

    public void setAllocationMap(Map<TreeItem, Integer> allocationMap) {
        this.allocationMap = allocationMap;
    }

    public ArrayList<FileData> getFileDataList() {
        return fileDataList;
    }

    public void setFileDataList(ArrayList<FileData> fileDataList) {
        this.fileDataList = fileDataList;
    }

    public ArrayList<TreeItem> getTreeItemList() {
        return treeItemList;
    }

    public void setTreeItemList(ArrayList<TreeItem> treeItemList) {
        this.treeItemList = treeItemList;
    }

    public Map<TreeItem, TreeItem> getItsFatherMap() {
        return itsFatherMap;
    }

    public void setItsFatherMap(Map<TreeItem, TreeItem> itsFatherMap) {
        this.itsFatherMap = itsFatherMap;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public TreeView getFolderTreeView() {
        return folderTreeView;
    }

    public void setFolderTreeView(TreeView folderTreeView) {
        this.folderTreeView = folderTreeView;
    }

    public TreeItem getRootItem() {
        return rootItem;
    }

    public void setRootItem(TreeItem rootItem) {
        this.rootItem = rootItem;
    }

    public TreeItem getNowOpenNode() {
        return nowOpenNode;
    }

    public void setNowOpenNode(TreeItem nowOpenNode) {
        this.nowOpenNode = nowOpenNode;
    }

    public Button getFatButton() {
        return fatButton;
    }

    public void setFatButton(Button fatButton) {
        this.fatButton = fatButton;
    }
}
