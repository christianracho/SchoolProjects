import java.util.Arrays;
import java.util.Collections;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.util.Duration;

public class ColorTrap extends Application
{
    private Scene scene;
    private BorderPane borderPane;
    private Text txtCountDown;
    private Timeline timeline;
    private Color[] colorArray = {Color.BLACK, Color.ORANGE, Color.PURPLE, Color.BLUE, Color.YELLOW, Color.RED, Color.BROWN};
    private String[] textArray = {"BLACK", "ORANGE", "PURPLE", "BLUE", "YELLOW", "RED", "BROWN"};   
    private Text[] optionArray = new Text[7];
    private HBox hboxTop = new HBox();
    private FlowPane flowPane = new FlowPane();
    private HBox hboxBottom = new HBox();
    private int score = 0;
    private Text trapWord;
    private Text txtScore;
    
    private final int TIMER = 15;
    private int count = 0;
    
    //setting up the initial Stage and Scene
    @Override
    public void start(Stage primaryStage)
    {
        borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: lightgrey");
        scene = new Scene(borderPane, 600, 300);
        primaryStage.setMinHeight(300);
        primaryStage.setMinWidth(600);
        initializeGame();
        startPlay();

        primaryStage.setTitle("Color Trap");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //initial setup to start the timer once the game is being played
    public void startPlay()
    {
        chooseTrapWordAndColor();
        colorNameOptions();

        count = TIMER;
        txtCountDown.setText(TIMER + "");
        timeline = new Timeline(new KeyFrame(
                Duration.millis(1000), e -> {


                    if(count >= 0)
                    {
                        txtCountDown.setText(count + "");
                        count--;
                    }
                    else
                    {
                        endOfGame();
                    }
                }));
        timeline.setCycleCount(TIMER + 2);
        timeline.play();

    }
    
    //function will be called once the timer has expired
    public void endOfGame()
    {
        
        VBox centerEnd = new VBox();  
        HBox bottomReset = new HBox();
        Button btnReset = new Button("Play again");
        Text endText = new Text();
        endText.setText("Your score is: " + score);
        endText.setFont(Font.font("Marker Felt"));
        endText.setStyle("-fx-font-size:40");
        centerEnd.setSpacing(15);
        centerEnd.setAlignment(Pos.CENTER);
        centerEnd.getChildren().addAll(endText, btnReset);
        
        hboxTop.getChildren().removeAll(trapWord);
        flowPane.getChildren().removeAll(optionArray[0],optionArray[1],optionArray[2],optionArray[3],optionArray[4],optionArray[5],optionArray[6]);

        borderPane.setCenter(centerEnd);
        borderPane.setBottom(bottomReset);
        
        btnReset.setOnAction(e->{
            score = 0;
            initializeGame();
            startPlay();
        });
    }

    //adds score if the user's choice meets the winning condition
     public void checkChoice(Text choice)
    {
        if(trapWord.getFill() == (Color.valueOf(choice.getText()))){
                score++;
                txtScore.setText("Score: " + score);
         }
        
        hboxTop.getChildren().removeAll(trapWord);
        flowPane.getChildren().removeAll(optionArray[0],optionArray[1],optionArray[2],optionArray[3],optionArray[4],optionArray[5],optionArray[6]);
        chooseTrapWordAndColor();
        colorNameOptions();
    }

    //randomizing the words with a different fill color
    public void chooseTrapWordAndColor()
    {
        String text = textArray[(int)(Math.random()* 6) + 1];
        Color trapColor = colorArray[(int)(Math.random() * 6) + 1];
        
        trapWord = new Text(text);
        trapWord.setFill(trapColor);
        trapWord.setStyle("-fx-font-size:60");
        trapWord.setFont(Font.font("Marker Felt"));
        hboxTop.getChildren().add(trapWord);

    }
    
    public void colorNameOptions()
    {
        Collections.shuffle(Arrays.asList(colorArray));
        Collections.shuffle(Arrays.asList(textArray));

        for(int i = 0; i < optionArray.length; i++){
            optionArray[i] = new Text(textArray[i]);
            optionArray[i].setFill(colorArray[i]);
            optionArray[i].setStyle("-fx-font-size:40");
            optionArray[i].setFont(Font.font("Marker Felt"));
            flowPane.getChildren().add(optionArray[i]);
        }
        
        optionArray[0].setOnMouseClicked(e->{
            checkChoice(optionArray[0]);
        });
        
        optionArray[1].setOnMouseClicked(e->{
            checkChoice(optionArray[1]);
        });
        
        optionArray[2].setOnMouseClicked(e->{
            checkChoice(optionArray[2]);
        });
        
        optionArray[3].setOnMouseClicked(e->{
            checkChoice(optionArray[3]);
        });
        
        optionArray[4].setOnMouseClicked(e->{
            checkChoice(optionArray[4]);
        });
        
        optionArray[5].setOnMouseClicked(e->{
            checkChoice(optionArray[5]);
        });
        
        optionArray[6].setOnMouseClicked(e->{
            checkChoice(optionArray[6]);
        });
        
    }
    
    
    public void initializeGame()
    {
        txtCountDown = new Text();
        
        hboxTop.setPadding(new Insets(10));
        hboxTop.setAlignment(Pos.CENTER);
        hboxTop.maxHeightProperty().bind(borderPane.heightProperty().multiply(0.35));
        
        flowPane.setPrefWrapLength(50);
        flowPane.setOrientation(Orientation.HORIZONTAL);
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setHgap(10);
        flowPane.maxHeightProperty().bind(borderPane.heightProperty().multiply(0.55));
        flowPane.maxWidthProperty().bind(borderPane.widthProperty().multiply(0.65));
       
        hboxBottom = new HBox(10);
        hboxBottom.setPadding(new Insets(10));
        txtScore = new Text("Score: " + score);
        Text txtTimer = new Text("Time Remaining:");
        txtScore.setStyle("-fx-font-size:20");
        txtTimer.setStyle("-fx-font-size:20");
        txtCountDown.setStyle("-fx-font-size:20");
        
        hboxBottom.getChildren().addAll(txtScore, txtTimer, txtCountDown);
        hboxBottom.setAlignment(Pos.CENTER);
        hboxBottom.maxHeightProperty().bind(borderPane.heightProperty().multiply(0.1));
        
        borderPane.setTop(hboxTop);
        borderPane.setCenter(flowPane);
        borderPane.setBottom(hboxBottom);
        
        borderPane.maxWidthProperty().bind(scene.widthProperty());
        borderPane.maxHeightProperty().bind(scene.heightProperty());
    }
    public static void main(String[] args)
    {
        launch(args);
    }
}
