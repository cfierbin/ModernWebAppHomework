package gui;

import emulator.BidEmulator;
import emulator.BidListener;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Bid;
import model.Product;
import model.User;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("auction_monitor.fxml"));
        primaryStage.setTitle("Auction Monitor");
        primaryStage.setScene(new Scene(root, 550, 475));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
