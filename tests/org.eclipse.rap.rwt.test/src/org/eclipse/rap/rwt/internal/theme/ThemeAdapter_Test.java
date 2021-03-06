/*******************************************************************************
 * Copyright (c) 2008, 2015 Innoopract Informationssysteme GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Innoopract Informationssysteme GmbH - initial API and implementation
 *    EclipseSource - ongoing development
 ******************************************************************************/
package org.eclipse.rap.rwt.internal.theme;

import static org.eclipse.rap.rwt.internal.service.ContextProvider.getApplicationContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.eclipse.rap.rwt.internal.service.ContextProvider;
import org.eclipse.rap.rwt.internal.theme.css.StyleSheet;
import org.eclipse.rap.rwt.testfixture.internal.Fixture;
import org.eclipse.rap.rwt.testfixture.internal.engine.ThemeManagerHelper;
import org.eclipse.rap.rwt.theme.BoxDimensions;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ThemeAdapter_Test {

  private Shell shell;

  @Before
  public void setUp() {
    Fixture.setUp();
    Fixture.fakeNewRequest();
    Display display = new Display();
    shell = new Shell( display );
  }

  @After
  public void tearDown() {
    getApplicationContext().getThemeManager().deactivate();
    Fixture.tearDown();
  }

  @Test
  public void testGetPrimaryElementForLabel() {
    Label label = new Label( shell, SWT.NONE );
    assertEquals( "Label", ThemeAdapter.getPrimaryElement( label ) );
  }

  @Test
  public void testGetPrimaryElementForButton() {
    Button button = new Button( shell, SWT.BORDER );
    assertEquals( "Button", ThemeAdapter.getPrimaryElement( button ) );
  }

  @Test
  public void testGetPrimaryElementForTree() {
    Tree tree = new Tree( shell, SWT.BORDER );
    assertEquals( "Tree", ThemeAdapter.getPrimaryElement( tree ) );
  }

  @Test
  public void testGetPrimaryElementForCompositeSubclass() {
    Composite customComposite = new Composite( shell, SWT.BORDER ) {
      // empty subclass
    };
    assertEquals( "Composite", ThemeAdapter.getPrimaryElement( customComposite ) );
  }

  @Test
  public void testGetCssValues() throws Exception {
    CustomWidget custom = new CustomWidget( shell, SWT.NONE );
    StyleSheet styleSheet = ThemeTestUtil.getStyleSheet( "TestExample.css" );
    Theme theme = new Theme( "customId", "Custom Theme", styleSheet );
    ThemeManagerHelper.resetThemeManager();
    ThemeManager themeManager = getApplicationContext().getThemeManager();
    themeManager.initialize();
    themeManager.registerTheme( theme );
    themeManager.activate();
    ThemeAdapter adapter = new ThemeAdapter() {
      @Override
      protected void configureMatcher( WidgetMatcher matcher ) {
      }
    };
    // create theme adapter
    ThemeableWidget widget = new ThemeableWidget( CustomWidget.class.getName(), null );
    CssElement element = new CssElementImpl( "CustomWidget" );
    widget.elements = new CssElement[] { element };
    // check default values
    Color defaultColor = adapter.getCssColor( "CustomWidget", "color", custom );
    assertNotNull( defaultColor );
    BoxDimensions defaultBorderWidth = adapter.getCssBorder( "CustomWidget", custom );
    // switch theme
    ThemeUtil.setCurrentThemeId( ContextProvider.getUISession(), "customId" );
    // color is redefined
    Color customColor = adapter.getCssColor( "CustomWidget", "color", custom );
    assertFalse( defaultColor.equals( customColor ) );
    // borderWidth is not
    BoxDimensions customBorderWidth = adapter.getCssBorder( "CustomWidget", custom );
    assertEquals( defaultBorderWidth, customBorderWidth );
    getApplicationContext().getThemeManager().deactivate();
  }

}
