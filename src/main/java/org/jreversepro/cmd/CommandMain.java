/**
 * @(#)CommandMain.java
 *
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
 **/
package org.jreversepro.cmd;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

import org.jreversepro.CustomLoggerFactory;
import org.jreversepro.JReverseProContext;
import org.jreversepro.JavaDecompileVersionContext;
import org.jreversepro.gui.GUIMain;
import org.jreversepro.parser.ClassParserException;
import org.jreversepro.reflect.ClassInfo;


/**
 * @author Karthik Kumar.
 */
public class CommandMain {

  private final CommandLineInterface cli;

  private final JReverseProContext context;

  private final Logger LOGGER = CustomLoggerFactory.createLogger();
  
  public CommandMain() {
    cli = new CommandLineInterface();
    context = new JReverseProContext();
  }

  /**
   * Driving the application.
   * 
   * @param args
   *          Argument to Main.
   */
  public static void main(String[] args) {
    JReverseProContext.checkJREVersion();

    CommandMain main = new CommandMain();
    main.process(args);
  }

  /**
   * @param args
   *          Arguments to the command-line module.
   */
  public void process(String[] args) {
    cli.parse(args);

    JavaDecompileVersionContext.setJavaVersionToDecompile(cli
        .getJavaVersionToDecompile());

    // If GUI is enabled.
    if (cli.isGuiEnabled()) {
      (new GUIMain(context)).setVisible(true);
      return;
    }
    ClassInfo info;
    try {
      info = context.loadResource(cli.getInputResource());

      System.out.println(context.print(cli.getOutputType(), info));

    } catch (FileNotFoundException e) {
      LOGGER.severe(e.getMessage());
    } catch (IOException e) {
      LOGGER.severe(e.getMessage());
    } catch (ClassParserException e) {
      LOGGER.severe(e.getMessage());
    }

  }



}
