package com.filemanager.operatoringsystemdesign;

import com.filemanager.manager.Fat;
import com.filemanager.manager.FatView;
import com.filemanager.manager.FileFactory;
import com.filemanager.manager.ViewerPane;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ViewerPane viewerPane = new ViewerPane();
        FileFactory fileFactory = viewerPane.getFileFactory();
        File icon = fileFactory.getIcon();
        Image image = new Image(icon.toURI().toString());
        Scene scene = new Scene(viewerPane);
        stage.setScene(scene);
        stage.setTitle("文件管理系统");
        stage.getIcons().add(image);

        Platform.setImplicitExit(false);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                windowEvent.consume();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("退出程序");
                alert.setHeaderText(null);
                alert.setContentText("您是否要退出程序");
                Optional<ButtonType> buttonType = alert.showAndWait();
                if (buttonType.get().equals(ButtonType.OK)) {
                    Platform.exit();
                }
            }
        });

        stage.show();
//        testEverything();
    }

    public static void main(String[] args) {
        launch();
    }

    private void testEverything() {
//        testFatView();
//        testFat();
        try {
            testFileFactory();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void testFatView() {
        Fat fat = new Fat(128);
        fat.getFatLocation();
        fat.getFatLocation();
        fat.getFatLocation();
        fat.getFatLocation();
        fat.releaseFat(3);
        fat.releaseFat(1);
        fat.showFat();
        FatView fatView = new FatView(fat, 2);
        fatView.showFat();
    }

    private void testFat() {
        Fat fat = new Fat(128);
        fat.getFatLocation();
        fat.getFatLocation();
        fat.getFatLocation();
        fat.getFatLocation();
        fat.releaseFat(3);
        fat.releaseFat(1);
        fat.showFat();
    }

    private void testFileFactory() throws IOException {
        FileFactory fileFactory = new FileFactory();
        System.out.println("Fat-->" + fileFactory.getFat().toURI().toString());
        System.out.println("File-->" + fileFactory.getFileIcon().toURI().toString());
        System.out.println("Icon-->" + fileFactory.getIcon().toURI().toString());
        System.out.println("Directory-->" + fileFactory.getDirectory().toURI().toString());
    }
}