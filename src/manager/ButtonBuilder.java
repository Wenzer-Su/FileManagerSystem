package com.filemanager.manager;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

/**
 * 按钮工厂类
 * 构造各种类型的按钮
 */
public class ButtonBuilder {
    private ViewerPane viewerPane;

    public ButtonBuilder(ViewerPane viewerPane) {
        this.viewerPane = viewerPane;
    }

    /**
     * 构建取消按钮
     *
     * @param stage 当前需要该按钮的窗口
     * @return 具备关闭窗口功能的取消按钮
     */
    public Button getCancelButton(Stage stage) {
        Button button = new Button("取消");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
            }
        });
        return button;
    }

    /**
     * 创建文件时的保存按钮
     *
     * @param stage     需要按钮的窗口
     * @param textField 文件名的输入框
     * @return 执行保存并且关闭窗口的保存按钮
     */
    public Button getSaveButtonCreateFile(Stage stage, TextField textField) {
        Button button = new Button("保存");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String fileName = textField.getText();
                Map<TreeItem, Map<String, Boolean>> itsChildrenMap = viewerPane.getItsChildrenMap();
                TreeItem nowOpenNode = viewerPane.getNowOpenNode();
                TreeItemBuilder treeItemBuilder = viewerPane.getTreeItemBuilder();
                Map<TreeItem, TreeItem> itsFatherMap = viewerPane.getItsFatherMap();
                //创建文件的保存步骤
                if (itsChildrenMap.get(nowOpenNode) == null) {
                    /*
                    当前操作的目录下还没有子结点
                     */
                    //创建子结点登记Map
                    itsChildrenMap.put(nowOpenNode, new HashMap<>());
                    //创建文件结点
                    TreeItem fileItem = treeItemBuilder.getFileItem(fileName);
                    //更新结点数
                    viewerPane.setCounter(viewerPane.getCounter() + 1);
                    //指定其父结点
                    itsFatherMap.put(fileItem, nowOpenNode);
                    //标识其存在
                    itsChildrenMap.get(nowOpenNode).put(fileName, true);
                    //加入到其父结点的子结点列表中
                    nowOpenNode.getChildren().add(fileItem);
                    stage.close();
                } else if (itsChildrenMap.get(nowOpenNode).get(fileName) == null) {
                    //创建文件结点
                    TreeItem fileItem = treeItemBuilder.getFileItem(fileName);
                    //更新结点数
                    viewerPane.setCounter(viewerPane.getCounter() + 1);
                    //指定其父结点
                    itsFatherMap.put(fileItem, nowOpenNode);
                    //标识其存在
                    itsChildrenMap.get(nowOpenNode).put(fileName, true);
                    //加入到其父结点的子结点列表中
                    nowOpenNode.getChildren().add(fileItem);
                    stage.close();
                } else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("提示");
                    alert.setHeaderText(null);
                    alert.setContentText("存在同名文件或目录");
                    alert.showAndWait();
                    stage.close();
                }
            }
        });
        return button;
    }

    /**
     * 构建打开文件的保存按钮
     *
     * @param stage    当前需要这个按钮的窗口
     * @param fileData 当前操作的结点对象
     * @param textArea 要保存到文件中的内容
     * @return 执行保存并且关闭窗口的保存按钮
     */
    public Button getSaveButtonForEditFile(Stage stage, FileData fileData, TextArea textArea) {
        Button button = new Button("保存");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String message = textArea.getText();
                //编辑文件的保存步骤
                fileData.setContent(message);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("提示");
                alert.setHeaderText(null);
                alert.setContentText("保存成功");
                alert.showAndWait();
                stage.close();
            }
        });
        return button;
    }

    /**
     * 构建保存目录的按钮
     * <p>
     * 先查询当前操作的结点是否存在子结点，没有子结点需要创建子结点需要用的Map
     * 创建一个目录结点，总数+1
     * 指定父结点
     * 标识其存在于当前结点的子结点
     * 并且添加到当前结点的子结点
     *
     * @param stage     需要用到这个按钮的窗口
     * @param textField 输入框
     * @return 执行保存的添加目录按钮
     */
    public Button getSaveButtonCreateDir(Stage stage, TextField textField) {
        Button button = new Button("保存");

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String dirName = textField.getText();
                Map<TreeItem, Map<String, Boolean>> itsChildrenMap = viewerPane.getItsChildrenMap();
                TreeItem nowOpenNode = viewerPane.getNowOpenNode();
                TreeItemBuilder treeItemBuilder = viewerPane.getTreeItemBuilder();
                Map<TreeItem, TreeItem> itsFatherMap = viewerPane.getItsFatherMap();

                //创建目录的保存步骤
                if (itsChildrenMap.get(nowOpenNode) == null) {
                    /*
                    当前操作的目录下还没有子结点
                     */
                    itsChildrenMap.put(nowOpenNode, new HashMap<>());
                    TreeItem dirItem = treeItemBuilder.getDirItem(dirName);
                    viewerPane.setCounter(viewerPane.getCounter() + 1);
                    //指定父结点
                    itsFatherMap.put(dirItem, nowOpenNode);
                    //标定其存在
                    itsChildrenMap.get(nowOpenNode).put(dirName, true);
                    nowOpenNode.getChildren().add(dirItem);
                    stage.close();
                } else if (itsChildrenMap.get(nowOpenNode).get(dirName) == null) {
                    TreeItem dirItem = treeItemBuilder.getDirItem(dirName);
                    viewerPane.setCounter(viewerPane.getCounter() + 1);
                    //指定父结点
                    itsFatherMap.put(dirItem, nowOpenNode);
                    //标定其存在
                    itsChildrenMap.get(nowOpenNode).put(dirName, true);
                    nowOpenNode.getChildren().add(dirItem);
                    stage.close();
                } else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("提示");
                    alert.setHeaderText(null);
                    alert.setContentText("当前名字已存在");
                    alert.showAndWait();
                    stage.close();
                }
            }
        });
        return button;
    }

    /**
     * 构建只读按钮
     *
     * @param stage    需要当前按钮的窗口
     * @param fileData 当前操作对象的数据对象
     * @return 执行设置只读的按钮
     */
    public Button getReadOnlyButton(Stage stage, FileData fileData) {
        Button button = new Button("只读");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                fileData.setWritable(false);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("提示");
                alert.setHeaderText(null);
                alert.setContentText("文件已设置成只读");
                alert.showAndWait();
                stage.close();
            }
        });

        return button;
    }

    /**
     * 构建可读写按钮
     *
     * @param stage    需要当前按钮的窗口
     * @param fileData 当前操作对象的数据对象
     * @return 执行设置可读写的按钮
     */
    public Button getWritableButton(Stage stage, FileData fileData) {
        Button button = new Button("可读写");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                fileData.setWritable(true);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("提示");
                alert.setHeaderText(null);
                alert.setContentText("文件已设置成可读写");
                alert.showAndWait();
                stage.close();
            }
        });
        return button;
    }

}
