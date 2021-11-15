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
package org.apache.lucene.demo.fst;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.lucene.store.IndexOutput;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.IntsRefBuilder;
import org.apache.lucene.util.fst.FST;
import org.apache.lucene.util.fst.FSTCompiler;
import org.apache.lucene.util.fst.PositiveIntOutputs;
import org.apache.lucene.util.fst.Util;

/**
 * FST builder, taking a set of keys and mapping them to a number
 */
public class FstBuilder {
  private final IntsRefBuilder intsRefBuilder = new IntsRefBuilder();
  private final FSTCompiler<Long> fstCompiler = new FSTCompiler<>(FST.INPUT_TYPE.BYTE1, PositiveIntOutputs.getSingleton());
  private long ordinal = 1;

  /**
   * Create a fst builder.
   */
  public FstBuilder() {}
  
  /**
   * Build the fst
   * 
   * @param inputFile input file in text format, 1 key per line
   * @param output the index output to write the result to
   */
  public void build(Path inputFile, IndexOutput output) throws IOException {
    try (BufferedReader in = Files.newBufferedReader(inputFile)) {

      while (addOneLine(in, output)) {
        // continue;
      }
      fstCompiler.compile().save(output, output);
    }
  }

  private boolean addOneLine(BufferedReader in, IndexOutput out) throws IOException {

    String line = in.readLine();
    if (line == null || line.length() == 0) {
      return false;
    }
    fstCompiler.add(Util.toIntsRef(new BytesRef(line), intsRefBuilder), ordinal++);
    return true;
  }

}
