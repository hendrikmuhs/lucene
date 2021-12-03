/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.lucene.demo.keyvi;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.lucene.store.IndexOutput;
import org.apache.lucene.util.keyvi.Generator;

/** Dictionary builder using the keyvi algorithm. */
public class KeyviBuilder {

  private int ordinal = 1;
  private final Generator keyviCompiler;

  /**
   * Create a keyvi builder.
   *
   * @throws IOException
   */
  public KeyviBuilder() throws IOException {
    keyviCompiler = new Generator();
  }

  /**
   * Build the keyvi fst
   *
   * @param inputFile input file in text format, 1 key per line
   * @param output the index output to write the result to
   */
  public void build(Path inputFile, IndexOutput output, boolean compatMode) throws IOException {
    try (BufferedReader in = Files.newBufferedReader(inputFile)) {

      while (addOneLine(in, output)) {
        // continue;
      }
      keyviCompiler.closeFeeding();
      if (compatMode) {
        keyviCompiler.writeKeyvi(output);
      } else {
        keyviCompiler.write(output);
      }
    }
  }

  private boolean addOneLine(BufferedReader in, IndexOutput out) throws IOException {

    String line = in.readLine();
    if (line == null || line.length() == 0) {
      return false;
    }
    // System.out.println("Adding: " + line);
    // keyviCompiler.add(line);
    keyviCompiler.add(line, ordinal++);

    return true;
  }
}
