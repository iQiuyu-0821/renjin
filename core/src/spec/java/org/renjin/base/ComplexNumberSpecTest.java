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
package org.renjin.base;

import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.renjin.EvalTestCase;

import java.io.IOException;
import java.text.DecimalFormat;

@RunWith(ConcordionRunner.class)
public class ComplexNumberSpecTest extends EvalTestCase {

  @Before
  public void setup() throws IOException {
    assumingBasePackagesLoad();
  }

  public double r(String str) {
    return round(eval(str).asReal());
  }

  double round(double d) {
    DecimalFormat twoDForm = new DecimalFormat("#.#######");
    return Double.valueOf(twoDForm.format(d));
  }
}
