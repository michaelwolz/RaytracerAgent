/*
 * Author: Michael Wolz
 * MatrikelNr: 1195270
 */

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class ProfilingHandler {
    private static HashMap<String, MethodCall> methodCalls = new HashMap<>();

    private static class MethodCall {
        private int count = 0;
        private double avgTime = 0d;

        void add(long time) {
            avgTime += ((avgTime * count) + time) / (count + 1);
            count++;
        }
    }

    public static void processData(String methodName, long start, long end) {
        if (!methodCalls.containsKey(methodName))
            methodCalls.put(methodName, new MethodCall());
        methodCalls.get(methodName).add(end - start);
    }

    public static void logResults() {
        Runtime.getRuntime().addShutdownHook(new Thread(() ->
        {
            try {
                FileWriter fileWriter = new FileWriter("log.txt");
                PrintWriter printWriter = new PrintWriter(fileWriter);

                printWriter.print("MethodName;Calls;AvgExecutionTime\n");

                methodCalls.forEach((key, value) ->
                        printWriter.printf("%s;%d;%fms\n", key, value.count, value.avgTime)
                );

                printWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
    }

}
