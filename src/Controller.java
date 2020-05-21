import bench.TestCPU;
import bench.TestGPU;
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

    public void GPURun()
    {
        TestCPU bench = new TestCPU();
        TestGPU gpuBench = new TestGPU();

        int buffer_size = 1024*1024; // input float buffer size
        int repeat_times = 5;

        String gpu_bench_log = "gpu_benchmark.log";
        gpuBench.initialize(buffer_size, gpu_bench_log);

        int num_gpus;
        timer.start();
        num_gpus = gpuBench.run_multiple_and_get_best(repeat_times);
        long elapsed_time = timer.stop();

        gpuBench.deinitialize();

        label1.setText("Found and successfully benchmarked " + num_gpus + " GPUs ");
        label2.setText("Workload: " + buffer_size * 1000 * 5 + " FP32 operations. Repeated " + repeat_times + " times");
        label3.setText("Total elapsed time: " + Math.floor((elapsed_time/1000000000.0) * 100) / 100 + " s");
        label4.setText("Benchmark results written in " + gpu_bench_log);
    }

    public void startPressedCPU(ActionEvent actionEvent) {
        CPURun();
    }

    public void startPressedGPU(ActionEvent actionEvent) {
        GPURun();
    }
}
