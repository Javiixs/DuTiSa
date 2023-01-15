package Main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicReference;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Main extends Application{

	Scene scene;
	AnchorPane bPane;
	Label titlelbl, loginlbl, mottolbl;
	Button loginbtn, exitbtn;
	TextField nameField;
	PasswordField passwordField;
	VBox  vBoxLeft, vBoxRight;
	FlowPane fPane;
	HBox hBox;
	BorderPane imgBorderPane;
	
	Group foxx;
	Group fusion;

	javafx.scene.image.Image Fox1 = new javafx.scene.image.Image("File:..\\..\\assets\\dog0.png");
	javafx.scene.image.Image Fox2 = new javafx.scene.image.Image("File:..\\..\\assets\\dog1.png");
	javafx.scene.image.Image Fox3 = new javafx.scene.image.Image("File:..\\..\\assets\\dog2.png");
	javafx.scene.image.Image Fox4 = new javafx.scene.image.Image("File:..\\..\\assets\\dog3.png");
	javafx.scene.image.Image Fox5 = new javafx.scene.image.Image("File:..\\..\\assets\\dog4.png");
	javafx.scene.image.Image Fox6 = new javafx.scene.image.Image("File:..\\..\\assets\\dog5.png");
	javafx.scene.image.Image Fox7 = new javafx.scene.image.Image("File:..\\..\\assets\\dog6.png");
	javafx.scene.image.Image Fox8 = new javafx.scene.image.Image("File:..\\..\\assets\\dog7.png");
	

	ImageView fox1 = new ImageView(Fox1);
	ImageView fox2 = new ImageView(Fox2);
	ImageView fox3 = new ImageView(Fox3);
	ImageView fox4 = new ImageView(Fox4);
	ImageView fox5 = new ImageView(Fox5);
	ImageView fox6 = new ImageView(Fox6);
	ImageView fox7 = new ImageView(Fox7);
	ImageView fox8 = new ImageView(Fox8);
	
	public void initialize () {
		bPane = new AnchorPane();
		scene = new Scene(bPane);
		hBox = new HBox();
		imgBorderPane = new BorderPane();
		
		foxx = new Group(fox1);
		foxx.setTranslateX(100);
		foxx.setTranslateY(0);
		
		Timeline tem = new Timeline();
		tem.setCycleCount(Timeline.INDEFINITE);
		
		tem.getKeyFrames().add(new KeyFrame(
				Duration.millis(400),(ActionEvent event) ->{
					foxx.getChildren().setAll(fox4);
				}
				));
		tem.getKeyFrames().add(new KeyFrame(
				Duration.millis(800),(ActionEvent event) ->{
					foxx.getChildren().setAll(fox5);
				}
				));
		tem.getKeyFrames().add(new KeyFrame(
				Duration.millis(1200),(ActionEvent event) ->{
					foxx.getChildren().setAll(fox6);
				}
				));
		tem.getKeyFrames().add(new KeyFrame(
			Duration.millis(1600),(ActionEvent event) ->{
				foxx.getChildren().setAll(fox7);
				}
				));
		tem.getKeyFrames().add(new KeyFrame(
			Duration.millis(2000),(ActionEvent event) ->{
				foxx.getChildren().setAll(fox6);
				}
				));
		tem.getKeyFrames().add(new KeyFrame(
			Duration.millis(2200),(ActionEvent event) ->{
					foxx.getChildren().setAll(fox5);
				}
				));
		tem.getKeyFrames().add(new KeyFrame(
				Duration.millis(2400),(ActionEvent event) ->{
					foxx.getChildren().setAll(fox4);
				}
				));
		tem.getKeyFrames().add(new KeyFrame(
				Duration.millis(2600),(ActionEvent event) ->{
					foxx.getChildren().setAll(fox5);
				}
				));
		tem.getKeyFrames().add(new KeyFrame(
				Duration.millis(2800),(ActionEvent event) ->{
					foxx.getChildren().setAll(fox6);
				}
				));
		tem.getKeyFrames().add(new KeyFrame(
				Duration.millis(3000),(ActionEvent event) ->{
					foxx.getChildren().setAll(fox7);
				}
				));
		tem.getKeyFrames().add(new KeyFrame(
				Duration.millis(3200),(ActionEvent event) ->{
					foxx.getChildren().setAll(fox5);
				}
				));
		tem.getKeyFrames().add(new KeyFrame(
				Duration.millis(3400),(ActionEvent event) ->{
					foxx.getChildren().setAll(fox6);
				}
				));
		tem.getKeyFrames().add(new KeyFrame(
				Duration.millis(3800),(ActionEvent event) ->{
					foxx.getChildren().setAll(fox7);
				}
				));
		tem.getKeyFrames().add(new KeyFrame(
				Duration.millis(4200),(ActionEvent event) ->{
					foxx.getChildren().setAll(fox6);
				}
				));
		tem.getKeyFrames().add(new KeyFrame(
				Duration.millis(4600),(ActionEvent event) ->{
					foxx.getChildren().setAll(fox6);
				}
				));
		tem.getKeyFrames().add(new KeyFrame(
				Duration.millis(5000),(ActionEvent event) ->{
					foxx.getChildren().setAll(fox5);
				}
				));
		
		tem.play();
		
		vBoxLeft = new VBox();
		vBoxLeft.setPadding(new Insets(200, 40, 200, 60));
		vBoxRight = new VBox();
		vBoxRight.setPadding(new Insets(200, 70, 210, 80));
		
		bPane.getChildren().add(hBox);
		hBox.getChildren().add(vBoxLeft);
		hBox.getChildren().add(vBoxRight);
		
		titlelbl = new Label(" NAR 23-1 ");
		titlelbl.setFont(Font.font("helvetica", FontWeight.EXTRA_BOLD, 30));
		titlelbl.setTextFill(Color.WHITE);
		
		mottolbl = new Label("\"Breaking and Overcoming Challenges Through \n          Courage, Hardword and Persistence\"");
		mottolbl.setFont(Font.font("helvetica",FontWeight.EXTRA_BOLD, 20));
		mottolbl.setTextFill(Color.WHITE);
		
		loginlbl = new Label("LOGIN");
		loginlbl.setFont(Font.font("CooperBlack",FontWeight.EXTRA_BOLD, 25));
		loginlbl.setTextFill(Color.PURPLE);
		
		loginbtn = new Button("LOGIN");
		loginbtn.setFont(Font.font("CooperBlack", 15));
		
		exitbtn = new Button("EXIT");
		exitbtn.setFont(Font.font("CooperBlack", 15));
		exitbtn.setOnAction(event -> {
			System.exit(0);
		});

		
		nameField = new TextField();
		nameField.setPromptText("Username");
		nameField.setPrefWidth(30);
		
		passwordField = new PasswordField();
		passwordField.setPromptText("Password");
		passwordField.setPrefWidth(30);
		
		fPane = new FlowPane();
		
		try {
			FileInputStream bgImage = new FileInputStream("src\\main\\background.png");
			Image img = new Image(bgImage);
			BackgroundImage bgImg = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false));
			Background background = new Background(bgImg);
			vBoxRight.setBackground(background);
		} catch (FileNotFoundException e) {
			e.printStackTrace();}
		
		vBoxLeft.setSpacing(30);
		vBoxRight.setSpacing(30);
	}
	
	public void positioning() {
		fPane.getChildren().add(loginbtn);
		fPane.getChildren().add(exitbtn);
		fPane.setHgap(50);
		fPane.setAlignment(Pos.CENTER);
		
		imgBorderPane.getChildren().add(foxx);
		imgBorderPane.setPadding(new Insets(50,50,50,50));
		vBoxRight.getChildren().add(imgBorderPane);
		vBoxRight.setAlignment(Pos.CENTER);
		vBoxRight.getChildren().add(titlelbl);
		vBoxRight.getChildren().add(mottolbl);

		vBoxLeft.setAlignment(Pos.CENTER);
		vBoxLeft.getChildren().add(loginlbl);
		vBoxLeft.getChildren().add(nameField);
		vBoxLeft.getChildren().add(passwordField);
		vBoxLeft.getChildren().add(fPane);
		
	}
	
	private void invisible() {
		scene.setFill(Color.TRANSPARENT);
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stg) throws Exception {
		initialize();
		positioning();
		invisible();
		
		AtomicReference<Double> x = new AtomicReference<>(0.0);
		AtomicReference<Double> y = new AtomicReference<>(0.0);
		scene.setOnMousePressed(event -> {
			x.set(event.getSceneX());
			y.set(event.getSceneY());
		});
		
		scene.setOnMouseDragged(event -> {
			stg.setX(event.getScreenX() - x.get());
			stg.setY(event.getScreenY() - y.get());
		});
		
		
		stg.setTitle("Login Page");
		stg.setResizable(false);
		stg.initStyle(StageStyle.TRANSPARENT);
		stg.setScene(scene);
		stg.show();
		
	}


	

}
