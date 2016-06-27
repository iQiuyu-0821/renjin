package org.renjin.compiler.ir.tac.statements;

import org.objectweb.asm.commons.InstructionAdapter;
import org.renjin.compiler.emit.EmitContext;
import org.renjin.compiler.ir.tac.IRLabel;
import org.renjin.compiler.ir.tac.TreeNode;
import org.renjin.compiler.ir.tac.expressions.Expression;


public interface Statement extends TreeNode {

  Iterable<IRLabel> possibleTargets();

  Expression getRHS();

  void setRHS(Expression newRHS);

  void accept(StatementVisitor visitor);

  /**
   * Emits the bytecode for this instruction
   * @param emitContext
   * @param mv
   * @return the required increase to the stack
   */
  int emit(EmitContext emitContext, InstructionAdapter mv);
  
}