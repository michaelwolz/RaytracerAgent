/*
 * Author: Michael Wolz
 * MatrikelNr: 1195270

 * Compile sources, generate a jar file and add it as javaagent to the target program:
 * java -javaagent:PATHTOJAR TARGET
 */

import java.lang.instrument.Instrumentation;

public class RaytracerAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new RaytracerTransformer());
    }
}
