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
package org.apache.lucene.demo;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.demo.fst.FstBuilder;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.store.IndexOutput;

/**
 * Create a dictionary from a text file.
 * 
 * This command-line application is made for comparing the current FST implementation with the keyvi
 * FST algorithm
 */
public class CreateDictionaryFile {

  private CreateDictionaryFile() {}

  /** Create a dictionary from a text file */
  public static void main(String[] args) throws Exception {
    String usage = "java org.apache.lucene.demo.CreateDictionaryFile"
        + " [-i INPUT_FILE] [-o OUTPUT_FILE] [-d TYPE]\n\n"
        + "This creates a dictionary using INPUT_FILE, creating OUTPUT_FILE.\n"
        + "Using -d, the TYPE can be switched from fst (lucene) and keyvi (experimental)";

    String inputFile = null;
    String outputFile = null;
    String type = "fst";

    for (int i = 0; i < args.length; i++) {
      switch (args[i]) {
      case "-i":
        inputFile = args[++i];
        break;
      case "-o":
        outputFile = args[++i];
        break;
      case "-d":
        type = args[++i];
        break;
      default:
        throw new IllegalArgumentException("unknown parameter " + args[i]);
      }
    }

    if (inputFile == null || outputFile == null) {
      System.err.println("Usage: " + usage);
      System.exit(1);
    }

    Directory dir = FSDirectory.open(Paths.get("dictionaries"));
    IndexOutput out = dir.createOutput(outputFile, IOContext.DEFAULT);
    Path inputPath = Paths.get(inputFile);

    if (type.equalsIgnoreCase("fst")) {
      new FstBuilder().build(inputPath, out);

    } else if (type.equalsIgnoreCase("keyvi")) {

    } else {
      System.err.println("type must be either fst or keyvi");
      System.err.println("Usage: " + usage);
      System.exit(1);
    }
  }
}
