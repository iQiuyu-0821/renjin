/**
 * Renjin : JVM-based interpreter for the R language for the statistical analysis
 * Copyright © 2010-2016 BeDataDriven Groep B.V. and contributors
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, a copy is available at
 * https://www.gnu.org/licenses/gpl-2.0.txt
 */
package org.renjin.gcc.gimple.type;

import org.renjin.repackaged.asm.Type;
import org.renjin.repackaged.guava.base.Preconditions;

/**
 * Type representing complex numbers
 */
public class GimpleComplexType extends AbstractGimpleType {

  public GimpleComplexType() {
  }
  
  public GimpleComplexType(GimpleRealType partType) {
    setSize(partType.getSize() * 2);
  }

  @Override
  public int sizeOf() {
    return getSize() / 8;
  }

  @Override
  public String toString() {
    return "complex";
  }

  @Override
  public void setSize(int size) {
    Preconditions.checkArgument(size == 64 || size == 128, "Invalid size: " + size);
    super.setSize(size);
  }

  /**
   * 
   * @return the JVM type of this complex number's real and imaginary parts. 
   * Either {@code DOUBLE_TYPE} or {@code FLOAT_TYPE}
   */
  public Type getJvmPartType() {
    return getPartType().jvmType();
  }
  
  public Type getJvmPartArrayType() {
    return Type.getType("[" + getJvmPartType().getDescriptor());
  }
  
  public GimpleRealType getPartType() {
    return new GimpleRealType(getSize() / 2);
  }

  @Override
  public boolean equals(Object other) {
    if(!(other instanceof GimpleComplexType)) {
      return false;
    }
    GimpleComplexType otherType = (GimpleComplexType) other;
    
    return getSize() == otherType.getSize();
  }

  @Override
  public int hashCode() {
    return getSize();
  }
}
