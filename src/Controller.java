import bench.TestCPU;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import logging.ConsoleLogger;
import timer.Timer;

public class Controller {

    private Timer timer = new Timer();
    private ConsoleLogger log = new ConsoleLogger();

    @FXML
    Label label1, label2, label3, label4;

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

        label1.setText("Average time for workload " + workload + " is " + medie + " and the score is " + score);


        //second run: workload 5000
        workload = 5000;

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

        label2.setText("Average time for workload " + workload + " is " + medie + " and the score is " + score);



        //third run: workload 10000
        workload = 10000;

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

        label3.setText("Average time for workload " + workload + " is " + medie + " and the score is " + score);

        label4.setText("Average time was " + averageMedie/3 + " and the average score is " + averageScore/3);


    }

    public void startPressed(ActionEvent actionEvent) {
        CPURun();

    }
}
