package com.github.badaccuracy.id.dutisa.utils;

import com.github.badaccuracy.id.dutisa.DuTiSa;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.net.URL;
import java.util.concurrent.atomic.AtomicReference;

@UtilityClass
public class Utils {

    public static void switchScene(String resource) {
        AtomicReference<Double> x = new AtomicReference<>((double) 0);
        AtomicReference<Double> y = new AtomicReference<>((double) 0);

        Parent newParent = Utils.getDesign(resource);
        if (newParent == null)
            throw new NullPointerException("Design cannot be null!");

        Stage rootStage = DuTiSa.getRootStage();
        Scene newScene = new Scene(newParent);

        newParent.setOnMousePressed(event -> {
            x.set(event.getSceneX());
            y.set(event.getSceneY());
        });

        newParent.setOnMouseDragged(event -> {
            rootStage.setX(event.getScreenX() - x.get());
            rootStage.setY(event.getScreenY() - y.get());
        });

        rootStage.setScene(newScene);
        rootStage.show();

        DuTiSa.setParent(newParent);
    }

    public static Pair<Parent, Stage> newWindowFromDesign(String resource) {
        Parent parent = Utils.getDesign(resource);
        Stage stage = new Stage();

        if (parent == null)
            throw new NullPointerException("Cannot find authors content design!");

        Scene scene = new Scene(parent);
        scene.setFill(Color.TRANSPARENT);

        stage.setScene(scene);
        stage.setResizable(false);

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);

        return new Pair<>(parent, stage);
    }

    @SneakyThrows
    public static Parent getDesign(String name) {
        URL url = DuTiSa.DESIGN_MAP.getOrDefault(name.endsWith(".fxml") ? name : name + ".fxml", null);

        if (url == null)
            return null;

        return FXMLLoader.load(url);
    }

    public static URL getResource(String path) {
        return DuTiSa.class.getClassLoader().getResource(path);
    }

    @Nullable
    public static File getResourceFile(String path) {
        File file = null;
        try {
            file = new File(Utils.getResource(path).toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public Image getImage(String path) {
        return new Image(String.valueOf(Utils.getResource(path)));
    }

}
