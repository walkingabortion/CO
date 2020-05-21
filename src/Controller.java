import bench.TestCPU;
import bench.TestGPU;
import benchHDD.TestHDDWriteSpeed;
import benchRAM.VirtualMemoryBenchmark;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import logging.ConsoleLogger;
import timer.Timer;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileWriter;
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
        score = workload /  (Math.sqrt(medie) + 1);

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
        score = workload /  (Math.sqrt(medie) + 1);


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
        score = workload /  (Math.sqrt(medie) + 1);

        averageMedie += medie;
        averageScore += score;

        label3.setText("Workload: " + workload + " in " + medie + " ns");

        label4.setText("The final score is " + df2.format(averageScore/(3 * 10)));


    }

    @FXML
    Label fixed_file;

    public void HDDrun() throws IOException
    {
        TestHDDWriteSpeed bench=new TestHDDWriteSpeed();

        File[] paths;
        FileSystemView fsv = FileSystemView.getFileSystemView();

        // returns pathnames for files and directory
        paths = File.listRoots();

        String result="";

        for(File path:paths) {

            if (fsv.getSystemTypeDescription(path).equals("Local Disk"))
            {
                String disk = path.getAbsolutePath()+"nice";
                String desktopPath = System.getProperty("user.home") + "\\Desktop\\nice";

                if(disk.charAt(0)=='C')
                    disk=desktopPath;

                result+=("drive " + disk.charAt(0) + "\n");

                bench.run("fs", true, disk);
                result+=("File write score fixed file size "
                        + String.format("%.2f", bench.getScore()) + " MB/sec\n");

                bench.run("fb", true, disk);
                result+="File write score fixed file size "
                        + String.format("%.2f", bench.getScore()) + " MB/sec\n";

            }
        }

        String log="hdd_bench_log.txt";
        FileWriter write = new FileWriter(System.getProperty("user.home") +
                "\\Desktop" + "\\" + log);
        write.write(result);
        write.close();

        fixed_file.setText("HDD benchmark results written at "+ System.getProperty("user.home") +
                "\\Desktop" + "\\" + log);
    }

    public void GPURun()
    {
        //TestCPU bench = new TestCPU();
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

    @FXML
    Label wr_speed,r_speed;

    public void RAMRun() {
        VirtualMemoryBenchmark ram_bench=new VirtualMemoryBenchmark();
        long fileSize=8L * 1024 * 1024 * 1024;
        int bufferSize= 16*1024;

        ram_bench.initialize();
        ram_bench.run(fileSize, bufferSize);
        wr_speed.setText(ram_bench.getResult1());
        r_speed.setText(ram_bench.getResult2());
        ram_bench.clean();

    }

    public void startPressedCPU(ActionEvent actionEvent) {
        CPURun();
    }
    public void startPressedGPU(ActionEvent actionEvent) {
        GPURun();
    }

    public void startPressedHDD(ActionEvent actionEvent) {
        try {
            HDDrun();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void startPressedRAM(ActionEvent actionEvent) { RAMRun(); }
}
