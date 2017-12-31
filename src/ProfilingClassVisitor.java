/*
 * Author: Michael Wolz
 * MatrikelNr: 1195270
 */

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

class ProfilingClassVisitor extends ClassVisitor implements Opcodes {

    private static final int API_VERSION = ASM5;

    ProfilingClassVisitor(ClassVisitor cv) {
        super(API_VERSION, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        return new RaytracerMethodVisitor(API_VERSION, mv, access, name, desc);
    }

}
