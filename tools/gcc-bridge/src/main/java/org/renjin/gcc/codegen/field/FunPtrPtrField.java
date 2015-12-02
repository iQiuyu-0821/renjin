package org.renjin.gcc.codegen.field;


import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.renjin.gcc.codegen.expr.AbstractExprGenerator;
import org.renjin.gcc.codegen.expr.ExprGenerator;
import org.renjin.gcc.codegen.pointers.DereferencedFunPtr;
import org.renjin.gcc.gimple.GimpleVarDecl;
import org.renjin.gcc.gimple.type.GimpleFunctionType;
import org.renjin.gcc.gimple.type.GimpleType;

import java.lang.invoke.MethodHandle;

/**
 * A field pointing to one or more function pointers. Compiled as an array+offset of MethodHandles.
 */
public class FunPtrPtrField extends FieldGenerator {

  private String className;
  private String arrayFieldName;
  private final String arrayFieldDescriptor;
  private final String offsetFieldName;
  private GimpleFunctionType functionType;

  public FunPtrPtrField(String className, String fieldName, GimpleFunctionType functionType) {
    this.className = className;
    this.arrayFieldName = fieldName;
    this.arrayFieldDescriptor = "[" + Type.getDescriptor(MethodHandle.class);
    this.offsetFieldName = fieldName + "$offset";
    this.functionType = functionType;
  }

  @Override
  public GimpleType getType() {
    return functionType.pointerTo().pointerTo();
  }

  @Override
  public void emitStaticField(ClassVisitor cv, GimpleVarDecl decl) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void emitInstanceField(ClassVisitor cv) {
    cv.visitField(Opcodes.ACC_PUBLIC, arrayFieldName, arrayFieldDescriptor, null, null).visitEnd();
    cv.visitField(Opcodes.ACC_PUBLIC, offsetFieldName, "I", null, 0).visitEnd();
  }

  @Override
  public ExprGenerator staticExprGenerator() {
    throw new UnsupportedOperationException();
  }

  @Override
  public ExprGenerator memberExprGenerator(ExprGenerator instanceGenerator) {
    return new MemberExpr(instanceGenerator);
  }
  
  private class MemberExpr extends AbstractExprGenerator {
    private ExprGenerator instance;

    public MemberExpr(ExprGenerator instance) {
      this.instance = instance;
    }

    @Override
    public void emitPushPtrArrayAndOffset(MethodVisitor mv) {
      instance.emitPushRecordRef(mv);
      mv.visitInsn(Opcodes.DUP);
      mv.visitFieldInsn(Opcodes.GETFIELD, className, arrayFieldName, arrayFieldDescriptor);
      // stack : [instance, array]
      mv.visitInsn(Opcodes.SWAP);
      // stack: [array, instance]
      mv.visitFieldInsn(Opcodes.GETFIELD, className, offsetFieldName, "I");
    }

    @Override
    public GimpleType getGimpleType() {
      return FunPtrPtrField.this.getType();
    }

    @Override
    public ExprGenerator valueOf() {
      return new DereferencedFunPtr(this);
    }
  }
  
}
