import bench.TestCPU;
import benchHDD.HDDController;
import benchHDD.TestHDDWriteSpeed;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import logging.ConsoleLogger;
import timer.Timer;

import java.io.IOException;
import java.text.DecimalFormat;

public class Controller {

    private Timer timer = new Timer();
    private ConsoleLogger log = new ConsoleLogger();

    private static DecimalFormat df2 = new DecimalFormat("#.##");


    @FXML
    Label label1, label2, label3, label4;

    @FXML Button startButton;

    public void CPURun()
    {
        TestCPU bench = new TestCPU();

        int workload, iterations;

        iterations = 10;
        double totalTime = 0, score, medie;
        double averageMedie = 0, averageScore = 0;


        //warmUp
        workload = 1000;
        timer.start();
        bench.initialize(workload);
        bench.run();
        timer.stop();

        //first run: workload 1000
        workload = 1000;

        for(int i = 0; i < iterations; i++)
        {
            bench.initialize(workload);

            timer.start();
            bench.run();

            totalTime += timer.stop();
        }
        medie =  (double)totalTime / iterations;
        score = workload /  (Math.log(medie) + 1);
        score = Math.log(score);

        averageMedie += medie;
        averageScore += score;

        label1.setText("Workload: " + workload + " in " + medie + " ns");


        //second run: workload 4000
        workload = 4000;

        for(int i = 0; i < iterations; i++)
        {
            bench.initialize(workload);

            timer.start();
            bench.run();

            totalTime += timer.stop();
        }
        medie =  (double)totalTime / iterations;
        score = workload /  (Math.log(medie) + 1);
        score = Math.log(score);

        averageMedie += medie;
        averageScore += score;

        label2.setText("Workload: " + workload + " in " + medie + " ns");



        //third run: workload 7000
        workload = 7000;

        for(int i = 0; i < iterations; i++)
        {
            bench.initialize(workload);

            timer.start();
            bench.run();

            totalTime += timer.stop();
        }
        medie =  (double)totalTime / iterations;
        score = workload /  (Math.log(medie) + 1);
        score = Math.log(score);

        averageMedie += medie;
        averageScore += score;

        label3.setText("Workload: " + workload + " in " + medie + " ns");

        label4.setText("The final score is " + df2.format(averageScore/3));


    }

    @FXML
    Label fixed_file,fixed_buffer;

    public void HDDrun()
    {
        TestHDDWriteSpeed bench=new TestHDDWriteSpeed();

        HDDController h=new HDDController();

        String path="Libraries\\Documents\\nice";


        bench.run("fs",true,path);
        fixed_file.setText("File write score fixed file size "
                + String.format("%.2f", bench.getScore()) + " MB/sec");

        bench.run("fb",true,path);
        fixed_buffer.setText("File write score fixed buffer size "
                + String.format("%.2f", bench.getScore()) + " MB/sec");

    }

    private void insert_path_window()
    {
        Parent root= null;
        try {
            root = FXMLLoader.load(getClass().getResource("/resources/path.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage= (Stage) startButton.getScene().getWindow();

        stage.setTitle("Welcome!");
        stage.setScene(new Scene(root, 399.0, 169.0));
        stage.show();

    }


    public void startPressed(ActionEvent actionEvent) {

        // insert_path_window(); // dont touch pls not good yet

        CPURun();
        HDDrun();

    }
}