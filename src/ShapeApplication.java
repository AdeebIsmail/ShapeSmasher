import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ShapeApplication extends Application {


    Rectangle Square = null;
    Ellipse Circle = null;
    Polygon Triangle = null;
    long squareMaxTime = 3000;
    long circleMaxTime = 2500;
    long triangleMaxTime = 2000;
    long circleSpawnTime = 0;
    long squareSpawnTime = 0;
    long triangleSpawnTime = 0;
    long numUpdatesPerSeconds = 35;
    long updatesDone = 0;
    long timeBetweenUpdates = 1000 / numUpdatesPerSeconds;
    long startTime = System.nanoTime();
    boolean gameOver = false;
    int lives = 6;
    int deaths = 0;
    int points = 0;
    Image Skull;
    private Canvas canvas;



    @Override
    public void start(Stage stage) throws Exception {
        Skull = new Image("File:Images/Skull.png");
        Group group = new Group();
        canvas = new Canvas(500, 700);
        group.getChildren().add(canvas);
        Scene scene = new Scene(group);
        stage.setScene(scene);
        stage.show();
        new AnimationTimer() {
            @Override
            public void handle(long currentTime) {
                long updatesNeed = ((currentTime - startTime) / 1000000) / timeBetweenUpdates;
                for (; updatesDone < updatesNeed; updatesDone++) {
                    update();
                }
                paint(canvas.getGraphicsContext2D());

            }


        }.start();
        canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().name() == "PRIMARY") {
                    double x = mouseEvent.getX();
                    double y = mouseEvent.getY();
                    System.out.println("MOUSEPRESS");
                    if (Square.contains(x, y) && gameOver == false) {
                        moveSquare();
                        points += 1;
                    }
                    if (Circle.contains(x, y) && gameOver == false) {
                        moveCircle();
                        points += 3;
                    }
                    if (Triangle.contains(x, y) && gameOver == false) {
                        moveTriangle();
                        points += 5;
                    }


                }
                if (mouseEvent.getButton().name() == "SECONDARY" && gameOver == true) {
                    reset();
                }
                paint(canvas.getGraphicsContext2D());

            }
        });


    }

    public void paint(GraphicsContext gc) {

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 500, 700);
        gc.setFill(Color.RED);
        gc.fillRect((int) Square.getX(), (int) Square.getY(), 40, 40);
        gc.setFill(Color.GREEN);
        gc.fillOval((int) Circle.getCenterX(), (int) Circle.getCenterY(), 40, 40);
        gc.setFill(Color.GREEN);
        gc.setFill(Color.RED);
        gc.setFont(new Font("Comic Sans", 50));
        gc.fillText("Points" + points, 170, 700);
        double[] xPoints = {Triangle.getPoints().get(0), Triangle.getPoints().get(2), Triangle.getPoints().get(4)};
        double[] yPoints = {Triangle.getPoints().get(1), Triangle.getPoints().get(3), Triangle.getPoints().get(5)};
        gc.setFill(Color.TEAL);
        gc.fillPolygon(xPoints, yPoints, 3);
        gc.setFill(Color.BLUE);
        for (int i = 3; i < deaths; i++) {
            gc.drawImage(Skull, i * 57, 600, 50, 50);
        }
        if (lives == 0) {
            gameOver = true;
            gc.setFont(new Font("Comic Sans", 20));
            gc.fillText("GAME OVER, RIGHT CLICK FOR NEW GAME", 57, 300);
        }

    }

    public void reset() {
        gameOver = false;
        moveSquare();
        moveTriangle();
        moveCircle();
        update();
        points = 0;
        lives = 3;
        deaths = 3;


    }

    public void moveSquare() {
        double x = Math.random() * 450 + 1;
        double y = Math.random() * 450 + 1;
        Square = new Rectangle(x, y, 40, 40);
        squareSpawnTime = System.nanoTime();
    }

    public void moveCircle() {
        double x = Math.random() * 450 + 1;
        double y = Math.random() * 450 + 1;
        Circle = new Ellipse(x, y, 40, 40);
        circleSpawnTime = System.nanoTime();

    }

    public void moveTriangle() {
        double x3 = Math.random() * 450 + 1;
        double y3 = Math.random() * 450 + 1;
        double x = (x3) + 20;
        double y = (y3);
        double x1 = (x3) + 40;
        double y1 = (y3) + 40;
        double x2 = (x3);
        double y2 = (y3) + 40;
        Triangle = new Polygon();
        Triangle.getPoints().addAll(new Double[]{x, y, x1, y1, x2, y2});
        triangleSpawnTime = System.nanoTime();


    }

    public void update() {
        if ((System.nanoTime() - squareSpawnTime) / 1000000 >= squareMaxTime && gameOver == false) {
            lives--;
            deaths++;
            moveSquare();


        }
        if ((System.nanoTime() - circleSpawnTime) / 1000000 >= circleMaxTime && gameOver == false) {
            lives--;
            deaths++;
            moveCircle();

        }
        if ((System.nanoTime() - triangleSpawnTime) / 1000000 >= triangleMaxTime && gameOver == false) {
            lives--;
            deaths++;
            moveTriangle();

        }

    }



}


