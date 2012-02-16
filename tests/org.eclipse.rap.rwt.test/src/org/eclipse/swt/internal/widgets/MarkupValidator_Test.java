/*******************************************************************************
 * Copyright (c) 2012 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    EclipseSource - initial API and implementation
 ******************************************************************************/
package org.eclipse.swt.internal.widgets;

import junit.framework.TestCase;


public class MarkupValidator_Test extends TestCase {

  public void testValidate() {
    String markup = "<b>foo</b><br/><span style=\"background-color: blue\">bar</span>";

    try {
      MarkupValidator.validate( markup );
    } catch( Exception ex ) {
      ex.printStackTrace();
      fail( ex.getMessage() );
    }
  }

  public void testValidate_NotWellFormedMarkup() {
    String markup = "<b>foo<br/><i>bar</i>";

    try {
      MarkupValidator.validate( markup );
      fail( "validation should throw an exception" );
    } catch( Exception expected ) {
      assertTrue( expected instanceof IllegalArgumentException );
      assertEquals( "Failed to parse markup text", expected.getMessage() );
    }
  }

  public void testValidate_UnsupportedElement() {
    String markup = "<ul>foo</ul><br/><i>bar</i>";

    try {
      MarkupValidator.validate( markup );
      fail( "validation should throw an exception" );
    } catch( Exception expected ) {
      assertTrue( expected instanceof IllegalArgumentException );
      assertEquals( "Unsupported element in markup text: ul", expected.getMessage() );
    }
  }

  public void testValidate_UnsupportedAttribute() {
    String markup = "<b>foo</b><br/><span href=\"abc\">bar</span>";

    try {
      MarkupValidator.validate( markup );
      fail( "validation should throw an exception" );
    } catch( Exception expected ) {
      assertTrue( expected instanceof IllegalArgumentException );
      String expectedMessage = "Unsupported attribute \"href\" for element \"span\" in markup text";
      assertEquals( expectedMessage, expected.getMessage() );
    }
  }

}
