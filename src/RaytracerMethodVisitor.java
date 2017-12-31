/*
 * Author: Michael Wolz
 * MatrikelNr: 1195270
 */

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

public class RaytracerMethodVisitor extends AdviceAdapter implements Opcodes {

    private String methodName;

    private int varID;

    RaytracerMethodVisitor(int api, MethodVisitor mv, int access, String name, String desc) {
        super(api, mv, access, name, desc);
        this.methodName = name;
    }

    @Override
    public void onMethodEnter() {
        if (methodName.equals("main"))
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "ProfilingHandler", "logResults", "()V", false);

        varID = newLocal(Type.LONG_TYPE);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false);
        mv.visitVarInsn(LSTORE, varID);

        super.onMethodEnter();
    }

    @Override
    public void onMethodExit(int opcode) {
        mv.visitLdcInsn(methodName);
        mv.visitVarInsn(LLOAD, varID);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "ProfilingHandler", "processData", "(Ljava/lang/String;JJ)V", false);

        super.onMethodExit(opcode);
    }
}
