package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.io.IOException;

public class Controller {

    @FXML
    private TextField incomeSec;




    @FXML
    public void shotdown(ActionEvent e) throws IOException {

        ProcessBuilder process = new ProcessBuilder("C:\\Windows\\System32\\shutdown.exe", "/s", "/f", "/t", String.valueOf(getIncomeSec()));
        process.start();
    }

    @FXML
    public void abortShutdown(ActionEvent e) throws IOException {
        ProcessBuilder process = new ProcessBuilder("C:\\Windows\\System32\\shutdown.exe", "-a");
        process.start();
    }


    public void btnCloseApp(ActionEvent e) {
        System.exit(0);
    }


    public int getIncomeSec() {
        int sec = Integer.parseInt(String.valueOf(incomeSec.getText()));
        return sec*60;
    }

    public void setIncome(String seconds){
        incomeSec.setText(seconds);
    }

    @FXML
    public void setFiveMin(ActionEvent e){
        setIncome("5");
    }

    @FXML
    public void setTwentyMin(ActionEvent e){
        setIncome("20");
    }

    @FXML
    public void setFortyMin(ActionEvent e){
        setIncome("40");
    }

    @FXML
    public void setSixtyMin(ActionEvent e){
        setIncome("60");
    }

    @FXML
    public void setOneHundredTwentyMin(ActionEvent e){
        setIncome("120");
    }

    @FXML
    public void setOneHundredFiftyMin(ActionEvent e){
        setIncome("150");
    }








}
