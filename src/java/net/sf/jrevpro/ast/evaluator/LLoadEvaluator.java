/**
 * JReversePro - Java Decompiler / Disassembler.
 * Copyright (C) 2008 Karthik Kumar.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *  
 *  	http://www.apache.org/licenses/LICENSE-2.0 
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 * 
 */
package net.sf.jrevpro.ast.evaluator;


import java.util.Arrays;

import net.sf.jrevpro.ast.expression.Variable;
import net.sf.jrevpro.reflect.instruction.Instruction;

public class LLoadEvaluator extends AbstractInstructionEvaluator {

  public LLoadEvaluator(EvaluatorContext context) {
    super(context);
  }

  /*
   * (non-Javadoc)
   * 
   * @see net.sf.jrevpro.decompile.instructioneval.AbstractInstructionEvaluator
   * #evaluate(net.sf.jrevpro.reflect.instruction.Instruction)
   */
  @Override
  void evaluate(Instruction ins) {
    switch (ins.opcode) {
    case OPCODE_LLOAD:
      operateLoadInstruction(ins, ins.getArgUnsignedWide());
      break;
    case OPCODE_LLOAD_0:
    case OPCODE_LLOAD_1:
    case OPCODE_LLOAD_2:
    case OPCODE_LLOAD_3:
      operateLoadInstruction(ins, ins.opcode - OPCODE_LLOAD_0);
      break;

    }

  }

  private void operateLoadInstruction(Instruction ins,
      int variableIndexToSymbolTable) {
    Variable var = new Variable(varTable, JVM_TYPE_LONG,
        variableIndexToSymbolTable, ins.currentPc);
    evalStack.push(var);
  }

  /*
   * (non-Javadoc)
   * 
   * @see net.sf.jrevpro.decompile.instructioneval.AbstractInstructionEvaluator
   * #getProcessingOpcodes()
   */
  @Override
  Iterable<Integer> getProcessingOpcodes() {
    return Arrays.asList(OPCODE_LLOAD, OPCODE_LLOAD_0, OPCODE_LLOAD_1,
        OPCODE_LLOAD_2, OPCODE_LLOAD_3);
  }

}
