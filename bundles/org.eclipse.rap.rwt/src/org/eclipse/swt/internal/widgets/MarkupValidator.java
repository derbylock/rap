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

import java.io.StringReader;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.rwt.internal.resources.SystemProps;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;


public class MarkupValidator {

  private static final SAXParser SAX_PARSER = createSAXParser();
  private static final Map<String, String[]> SUPPORTED_ELEMENTS = createSupportedElementsMap();

  private MarkupValidator() {
    // prevent instantiation
  }

  public static void validate( String text ) {
    if( !SystemProps.isMarkupValidationDisabled() ) {
      StringBuilder markup = new StringBuilder();
      markup.append( "<html>" );
      markup.append( text );
      markup.append( "</html>" );
      InputSource inputSource = new InputSource( new StringReader( markup.toString() ) );
      try {
        SAX_PARSER.parse( inputSource, new MarkupHandler() );
      } catch( RuntimeException exception ) {
        throw exception;
      } catch( Exception exception ) {
        throw new IllegalArgumentException( "Failed to parse markup text", exception );
      }
    }
  }

  private static SAXParser createSAXParser() {
    SAXParser result = null;
    SAXParserFactory parserFactory = SAXParserFactory.newInstance();
    try {
      result = parserFactory.newSAXParser();
    } catch( Exception exception ) {
      throw new RuntimeException( "Failed to create SAX parser", exception );
    }
    return result;
  }

  private static Map<String, String[]> createSupportedElementsMap() {
    Map<String, String[]> result = new HashMap<String, String[]>();
    result.put( "html", new String[ 0 ] );
    result.put( "b", new String[] { "style" } );
    result.put( "i", new String[] { "style" } );
    result.put( "sub", new String[] { "style" } );
    result.put( "sup", new String[] { "style" } );
    result.put( "br", new String[] { "style" } );
    result.put( "big", new String[] { "style" } );
    result.put( "small", new String[] { "style" } );
    result.put( "del", new String[] { "style" } );
    result.put( "ins", new String[] { "style" } );
    result.put( "em", new String[] { "style" } );
    result.put( "strong", new String[] { "style" } );
    result.put( "dfn", new String[] { "style" } );
    result.put( "code", new String[] { "style" } );
    result.put( "samp", new String[] { "style" } );
    result.put( "kbd", new String[] { "style" } );
    result.put( "var", new String[] { "style" } );
    result.put( "cite", new String[] { "style" } );
    result.put( "span", new String[] { "style" } );
//    result.put( "img", new String[] { "style", "src", "width", "height" } );
//    result.put( "a", new String[] { "style", "href", "target" } );
    return result;
  }

  private static class MarkupHandler extends DefaultHandler {

    @Override
    public void startElement( String uri, String localName, String name, Attributes attributes ) {
      if( !SUPPORTED_ELEMENTS.containsKey( name ) ) {
        throw new IllegalArgumentException( "Unsupported element in markup text: " + name );
      } else if( attributes.getLength() > 0 ) {
        List<String> supportedAttributes = Arrays.asList( SUPPORTED_ELEMENTS.get( name ) );
        int index = 0;
        String attributeName = attributes.getQName( index );
        while( attributeName != null ) {
          if( !supportedAttributes.contains( attributeName ) ) {
            String message = "Unsupported attribute \"{0}\" for element \"{1}\" in markup text";
            message = MessageFormat.format( message, new Object[] { attributeName, name } );
            throw new IllegalArgumentException( message );
          }
          index++;
          attributeName = attributes.getQName( index );
        }
      }
    }

  }

}
