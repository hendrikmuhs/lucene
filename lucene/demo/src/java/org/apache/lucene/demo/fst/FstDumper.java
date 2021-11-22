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

import java.io.IOException;
import org.apache.lucene.store.IndexInput;
import org.apache.lucene.util.fst.BytesRefFSTEnum;
import org.apache.lucene.util.fst.FST;
import org.apache.lucene.util.fst.PositiveIntOutputs;

/** FST dumper */
public class FstDumper {

  /** Create a fst dumper. */
  public FstDumper() {}

  /**
   * Dump a fst dictionary
   *
   * @param input the dictionary input
   */
  public void dump(IndexInput input) throws IOException {
    FST<Long> fst = new FST<>(input, input, PositiveIntOutputs.getSingleton());

    final BytesRefFSTEnum<Long> fstEnum = new BytesRefFSTEnum<>(fst);
    BytesRefFSTEnum.InputOutput<Long> entry;
    while ((entry = fstEnum.next()) != null) {
      System.out.println(entry.input.utf8ToString()); // + "\t" + entry.output);
    }
  }
}
