package bench;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bridj.Pointer;

import com.nativelibs4java.opencl.CLBuffer;
import com.nativelibs4java.opencl.CLContext;
import com.nativelibs4java.opencl.CLDevice;
import com.nativelibs4java.opencl.CLEvent;
import com.nativelibs4java.opencl.CLKernel;
import com.nativelibs4java.opencl.CLMem.Usage;
import com.nativelibs4java.opencl.CLPlatform;
import com.nativelibs4java.opencl.CLPlatform.ContextProperties;
import com.nativelibs4java.opencl.CLProgram;
import com.nativelibs4java.opencl.CLQueue;
import com.nativelibs4java.opencl.JavaCL;
import com.nativelibs4java.util.IOUtils;

public class TestGPU {
    private int buffer_size;
    private List<CLDevice> devices;
    private BufferedWriter writer;

    public TestGPU() {
        buffer_size = 1;
    }

    public TestGPU(int buffer_size) {
        this.buffer_size = buffer_size;
    }

    // Get all GPUs (and CPUs if they supports SIMD architecture)
    private static List<CLDevice> getDevices() {
        List<CLDevice> devices = new ArrayList<>();
        for (CLPlatform platform : JavaCL.listPlatforms()) {
            for (CLDevice device : platform.listAllDevices(true)) {
                devices.add(device);
            }
        }
        return devices;
    }

    // Assign input buffer size and open FileWriter for writing results in a file
    public void initialize(int buffer_size, String results_log_file) {
        this.buffer_size = buffer_size;
        try {
            writer = new BufferedWriter(new FileWriter(results_log_file, false));
        } catch (IOException e) {
            e.printStackTrace();
        }
        devices = getDevices();
    }

    // Flush and close output file
    public void deinitialize()
    {
        try {
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Actual benchmark function
    private void bench(CLContext context, int loop_count) {
        // Create a work queue for the device (GPU or CPU)
        CLQueue queue = context.createDefaultQueue();

        // Get device endianness (BIG or LITTLE)
        ByteOrder byteOrder = context.getByteOrder();

        // Allocate and initialize an FP32 buffer
        // The buffer will most likely get copied into the VRAM using a DMA HW block (Direct Memory Access)
        Pointer<Float> inPtr =
                Pointer.allocateFloats(buffer_size).order(byteOrder);

        // Initialize the buffer with ascending values (buf[i] = i)
        for (int i = 0; i < buffer_size; ++i) {
            inPtr.set(i, (float) i);
        }

        CLBuffer<Float> in = context.createFloatBuffer(Usage.Input, inPtr);

        // Create an output buffer of the same size
        CLBuffer<Float> out = context.createFloatBuffer(Usage.Output, buffer_size);

        // Transform the OpenCL kernel to text
        String str;
        try {
            str = IOUtils.readTextClose(TestGPU.class.getResourceAsStream("gpu_bench.cl"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Pass the kernel text to OpenCL, which will parse it and make out of it an OpenCL routine
        CLProgram program = context.createProgram(str).build();

        long best_time = Long.MAX_VALUE;
        long average_time = 0;

        // Execute the routine 'loop_count' times, we can obtain a best result and an average result
        for (int c = 0; c < loop_count; ++c) {
            // Transform the routine into an OpenCL kernel (a set of instructions that the device (GPU or CPU) can execute)
            // The kernel is compiled by an OpenCL compiler at run-time
            // The compilation has to be done at runtime because the compiler
            // needs to know the device architecture (ex: Turing for NVIDIA RTX 2080 Ti, Navi RDNA for Radeon RX 5700, etc)
            CLKernel kernel = program.createKernel("bench", in, out, buffer_size);
            long start = System.nanoTime();

            // Enqueue the 'task' for the device to run (task meaning running the kernel)
            CLEvent event = kernel.enqueueNDRange(queue, new int[]{buffer_size});

            // Wait for the device to finish
            Pointer<Float> result = out.read(queue, event).order(byteOrder);

            long end = System.nanoTime();
            average_time += (end - start);

            best_time = Math.min(end - start, best_time);
        }
        average_time /= loop_count;

        // Write the results to the output file
        try {
            writer.write("Best time: " + String.format("%,.2f ms", best_time / 1000000.0) + "\n");
            writer.write("Average time: " + String.format("%,.2f ms", average_time / 1000000.0) + "\n\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Run only 1 time
    public int run() {
        for (CLDevice device : devices) {
            CLContext context =
                    JavaCL.createContext(
                            Collections.<ContextProperties, Object>emptyMap(),
                            device);

            printDeviceInfo(context);
            bench(context, 1);
        }

        return devices.size(); // return number of GPUs OpenCL found
    }

    // Run 'loop_count' times, so we can get an average benchmark result
    public int run_multiple_and_get_best(int loop_count) {
        for (CLDevice device : devices) {
            CLContext context =
                    JavaCL.createContext(
                            Collections.<ContextProperties, Object>emptyMap(),
                            device);

            printDeviceInfo(context);
            bench(context, loop_count);
        }

        return devices.size(); // return number of GPUs OpenCL found
    }

    // Also write the device to the output file
    // because one can have multiple GPUs
    private void printDeviceInfo(CLContext context) {
        try {
            writer.write("Platform: " + context.getPlatform().getName() + "\n");
            writer.write("Device: " + context.getDevices()[0].getName() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
