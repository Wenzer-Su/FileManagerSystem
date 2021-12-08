package com.filemanager.manager;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

/**
 * FAT表的展示对象
 * 生成一个展示FAT表分配情况的面板对象
 */
public class FatView {
    private Fat fat;
    private int used;

    public FatView(Fat fat, int used) {
        this.fat = fat;
        this.used = used;
    }

    /**
     * 显示FAT表显示的窗口
     */
    public void showFat() {
        //构建提示标签部分
        Label totalSpaceLabel = new Label("总空间：");
        Label allocatedSpaceLabel = new Label("已分配空间：");
        Integer fatSize = (Integer) this.fat.getFatSize();
        Label totalSpace = new Label(fatSize.toString());
        Integer usedValue = this.used;
        Label allocated = new Label(usedValue.toString());

        HBox hBox1 = new HBox();
        HBox hBox2 = new HBox();
        hBox1.getChildren().addAll(totalSpaceLabel, totalSpace);
        hBox2.getChildren().addAll(allocatedSpaceLabel, allocated);
        VBox vBox = new VBox();
        //把上面的标签组装到一起
        vBox.getChildren().addAll(hBox1, hBox2);

        FlowPane fatViewPane = buildFatViewPane();

        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(fatViewPane);
        borderPane.setRight(vBox);

        Scene scene = new Scene(borderPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * 构建显示FAT表部分的面板
     *
     * @return
     */
    private FlowPane buildFatViewPane() {
        //创建一个上下、左右间距均为10的流式面板
        FlowPane flowPane = new FlowPane(10, 10);
        flowPane.setPrefWidth(250);
        flowPane.setPadding(new Insets(10));

        int[] fatList = this.fat.getFatList();
//        fat.showFat();
        int fatSize = this.fat.getFatSize();
        for (int i = 1; i <= fatSize; i++) {
            Button fatItemView = new Button();
            //设置尺寸
            fatItemView.setMinHeight(20);
            fatItemView.setMinWidth(20);
            //表示已分配的填充
            BackgroundFill backgroundFill = new BackgroundFill(
                    Paint.valueOf("#8FBC8F"), new CornerRadii(20), Insets.EMPTY
            );
            Background background = new Background(backgroundFill);
            if (fatList[i] != -1) {
                //已被分配的显示成色块
                fatItemView.setBackground(background);
            }
            //加到流式面板上
            flowPane.getChildren().add(fatItemView);
        }

        return flowPane;
    }


}
