package com.github.badaccuracy.id.dutisa;

import com.github.badaccuracy.id.dutisa.utils.Closer;
import com.github.badaccuracy.id.dutisa.utils.Utils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.File;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class DuTiSa extends Application {

    @Getter
    public static final Map<String, URL> DESIGN_MAP = new HashMap<>();
    @Getter
    private static DuTiSa instance;
    @Getter
    @Setter
    private static Parent parent;
    @Getter
    @Setter
    private static Stage rootStage;

    @Getter
    private final Image icon = Utils.getImage("img/nar.png");

    public static void main(String[] args) {
        DuTiSa.launch(args);
    }

    @Override
    public void start(Stage stage) {
        instance = this;
        loadDesigns();

        parent = Utils.getDesign("Login.fxml");
        rootStage = stage;

        assert parent != null;

        AtomicReference<Double> x = new AtomicReference<>((double) 0);
        AtomicReference<Double> y = new AtomicReference<>((double) 0);

        parent.setOnMousePressed(event -> {
            x.set(event.getSceneX());
            y.set(event.getSceneY());
        });

        parent.setOnMouseDragged(event -> {
            x.set(event.getSceneX());
            y.set(event.getSceneY());
        });

        rootStage.setTitle("DuTiSa - DuaTiga-Satu!");
        rootStage.getIcons().add(icon);

        Scene scene = new Scene(parent);
        scene.setFill(Color.TRANSPARENT);

        rootStage.setScene(scene);
        rootStage.setResizable(false);
        rootStage.initStyle(StageStyle.TRANSPARENT);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            DESIGN_MAP.clear();
        }));
        rootStage.setOnCloseRequest(e -> System.exit(0));

        rootStage.show();
    }

    @SneakyThrows
    private void loadDesigns() {
        File initialJarFile = new File(DuTiSa.class.getProtectionDomain().getCodeSource().getLocation().toURI());

        if (initialJarFile.isFile()) { // this works when executing the (compiled) jar file
            try (Closer closer = new Closer()) {
                JarFile jarFile = closer.add(new JarFile(initialJarFile));
                Enumeration<JarEntry> entries = jarFile.entries();

                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();

                    if (!entry.getName().endsWith(".fxml"))
                        continue;

                    DESIGN_MAP.put(entry.getName(), Utils.getResource(entry.getName()));
                }
            }
        } else { // this works when executing inside the IDE
            File dir = Utils.getResourceFile("");
            assert dir != null;

            //noinspection ConstantConditions
            for (File file : dir.listFiles(f -> f.getName().endsWith(".fxml"))) {
                // tests the designs first
                try {
                    FXMLLoader.load(file.toURI().toURL());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                DESIGN_MAP.put(file.getName(), file.toURI().toURL());
                System.out.println("Load test passed and loaded " + file.getName());
            }
        }
    }
}
