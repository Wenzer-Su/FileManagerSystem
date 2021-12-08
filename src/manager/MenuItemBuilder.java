package com.filemanager.manager;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Map;

/**
 *菜单项工厂
 * 生成所需用到的各种菜单选项
 */
public class MenuItemBuilder {
    private ViewerPane viewerPane;

    public MenuItemBuilder(ViewerPane viewerPane) {
        this.viewerPane = viewerPane;
    }

    /**
     * 生成“添加子目录”的菜单项
     * 输入新目录名的输入框
     *
     * @return
     */
    public MenuItem getAddDirItem() {
        MenuItem menuItem = new MenuItem("添加目录");
        menuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (viewerPane.getCounter() >= 128) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("提示");
                    alert.setHeaderText(null);
                    alert.setContentText("磁盘已满");
                    alert.showAndWait();
                } else {
                    TreeItem nowOpenNode = viewerPane.getNowOpenNode();
                    Stage stage = new Stage();
                    ButtonBuilder buttonBuilder = viewerPane.getButtonBuilder();
                    //添加目录的步骤
                    HBox dirNameBox = new HBox();
                    Label label = new Label("目录名");
                    label.setPrefWidth(70);
                    //用于输入目录名
                    TextField textField = new TextField();
                    textField.setPrefWidth(200);
                    dirNameBox.getChildren().addAll(label, textField);

                    HBox buttonBox = new HBox();
                    Button saveButton = buttonBuilder.getSaveButtonCreateDir(stage, textField);
//                Button saveButton = buttonBuilder.getSaveButtonCreateFile(stage, textField);
                    Button cancelButton = buttonBuilder.getCancelButton(stage);
                    buttonBox.getChildren().addAll(saveButton, cancelButton);

                    VBox vBox = new VBox();
                    vBox.getChildren().addAll(dirNameBox, buttonBox);
                    Scene scene = new Scene(vBox);
                    stage.setScene(scene);
                    stage.show();
                }
            }
        });
        return menuItem;
    }

    /**
     * 生成“添加文件“的菜单项
     *
     * @return
     */
    public MenuItem getAddFileItem() {
        MenuItem menuItem = new MenuItem("添加文件");
        menuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                if (viewerPane.getCounter() >= 128) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("提示");
                    alert.setHeaderText(null);
                    alert.setContentText("磁盘已满");
                    alert.showAndWait();
                } else {
                    TreeItem nowOpenNode = viewerPane.getNowOpenNode();
                    Stage stage = new Stage();
                    ButtonBuilder buttonBuilder = viewerPane.getButtonBuilder();
                    //添加文件的步骤
                    HBox fileNameBox = new HBox();
                    Label label = new Label("文件名");
                    label.setPrefWidth(50);
                    TextField textField = new TextField();
                    textField.setPrefWidth(200);
                    fileNameBox.getChildren().addAll(label, textField);

                    HBox buttonBox = new HBox();
                    Button saveButton = buttonBuilder.getSaveButtonCreateFile(stage, textField);
                    Button cancelButton = buttonBuilder.getCancelButton(stage);
                    buttonBox.getChildren().addAll(saveButton, cancelButton);

                    //组装面板
                    VBox vBox = new VBox();
                    vBox.getChildren().addAll(fileNameBox, buttonBox);
                    Scene scene = new Scene(vBox);
                    stage.setScene(scene);
                    stage.show();
                }
            }
        });
        return menuItem;
    }

    /**
     * 生成”删除目录“的菜单项
     * 判定是否为根目录
     * 根目录不能删除
     * 不是根目录调用dfs进行删除
     *
     * @return
     */
    public MenuItem getDeleteDirItem() {
        MenuItem menuItem = new MenuItem("删除目录");
        menuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TreeItem nowOpenNode = viewerPane.getNowOpenNode();
                //删除目录的步骤
                if (nowOpenNode.equals(viewerPane.getRootItem())) {
                    /*
                    删除时遇到根目录
                    不可删除
                     */
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("提示");
                    alert.setHeaderText(null);
                    alert.setContentText("根目录无法删除");
                    alert.showAndWait();
                } else {
                    Util.dfs(nowOpenNode, viewerPane);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("提示");
                    alert.setHeaderText(null);
                    alert.setContentText("删除成功");
                    alert.showAndWait();
                }
            }
        });
        return menuItem;
    }

    /**
     * 生成“打开文件”菜单项
     *
     * @return
     */
    public MenuItem getOpenFileItem() {
        MenuItem menuItem = new MenuItem("打开文件");
        menuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = new Stage();
                TreeItem nowOpenNode = viewerPane.getNowOpenNode();
                Map<TreeItem, FileData> fileDataMap = viewerPane.getFileDataMap();
                ButtonBuilder buttonBuilder = viewerPane.getButtonBuilder();
                //打开文件的步骤
                //当前打开对象的数据
                FileData fileData = fileDataMap.get(nowOpenNode);
                //打开对象的对象的文件内容
                String content = fileData.getContent();

                TextArea textArea = new TextArea();
                if (content != null) {
                    textArea.setText(content);
                } else {
                    textArea.setPromptText("Type in here");
                }
                /*
                设置自动换行
                根据文件的可读性设置是否可以编辑
                设置最大尺寸
                 */
                textArea.setWrapText(true);
                textArea.setEditable(fileData.isWritable());
                textArea.setMaxWidth(300);
                textArea.setMaxHeight(250);

                HBox hBox = new HBox();
                Button saveButton = buttonBuilder.getSaveButtonForEditFile(stage, fileData, textArea);
                Button cancelButton = buttonBuilder.getCancelButton(stage);
                hBox.getChildren().addAll(saveButton, cancelButton);

                BorderPane borderPane = new BorderPane();
                borderPane.setCenter(textArea);
                borderPane.setBottom(hBox);
                Scene scene = new Scene(borderPane);
                stage.setScene(scene);
                stage.show();
            }
        });
        return menuItem;
    }

    /**
     * 构建“更改属性”菜单项
     *
     * @return
     */
    public MenuItem getSetPropertyItem() {
        MenuItem menuItem = new MenuItem("更改属性");
        menuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = new Stage();
                TreeItem nowOpenNode = viewerPane.getNowOpenNode();
                Map<TreeItem, FileData> fileDataMap = viewerPane.getFileDataMap();
                ButtonBuilder buttonBuilder = viewerPane.getButtonBuilder();
                //更改属性的步骤
                FileData fileData = fileDataMap.get(nowOpenNode);

                Button readOnlyButton = buttonBuilder.getReadOnlyButton(stage, fileData);
                Button writableButton = buttonBuilder.getWritableButton(stage, fileData);
                Button cancelButton = buttonBuilder.getCancelButton(stage);
                readOnlyButton.setPrefWidth(100);
                readOnlyButton.setPrefHeight(50);
                writableButton.setPrefHeight(50);
                writableButton.setPrefWidth(100);
                cancelButton.setPrefHeight(50);
                cancelButton.setPrefWidth(100);

                HBox buttonBox = new HBox();
                buttonBox.setPrefWidth(300);
                buttonBox.getChildren().addAll(readOnlyButton, writableButton, cancelButton);
                Scene scene = new Scene(buttonBox);
                stage.setScene(scene);
                stage.show();
            }
        });
        return menuItem;
    }

    /**
     * 构建“删除文件”菜单项
     *
     * @return
     */
    public MenuItem getDeleteFileItem() {
        MenuItem menuItem = new MenuItem("删除文件");
        menuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TreeItem nowOpenNode = viewerPane.getNowOpenNode();
                //删除文件的步骤
                //删除当前结点及其子结点
                Util.dfs(nowOpenNode, viewerPane);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("提示");
                alert.setHeaderText(null);
                alert.setContentText("删除成功");
                alert.showAndWait();
            }
        });
        return menuItem;
    }

}
