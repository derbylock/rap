/*******************************************************************************
 * Copyright (c) 2010, 2011 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    EclipseSource - initial API and implementation
 ******************************************************************************/

qx.Class.define( "org.eclipse.rwt.test.tests.ComboTest", {
  extend : qx.core.Object,
  
  members : {

    testCreateDispose : function() {
      var testUtil = org.eclipse.rwt.test.fixture.TestUtil;
      var combo = this._createDefaultCombo();
      assertTrue( combo instanceof org.eclipse.swt.widgets.Combo );
      combo.destroy();
      testUtil.flush();
      assertTrue( combo.isDisposed() );
      combo.destroy();
    },
    
    testOpenList : function() {
      var testUtil = org.eclipse.rwt.test.fixture.TestUtil;
      var combo = this._createDefaultCombo();
      combo.setListVisible( true );
      testUtil.flush();
      assertTrue( combo._list.isSeeable() );
      assertEquals( "hidden", combo._list.getOverflow() );
      combo.destroy();
    },

    testCloseListOnBlur : function() {
      var testUtil = org.eclipse.rwt.test.fixture.TestUtil;
      var combo = this._createDefaultCombo();
      combo.focus();
      combo.setListVisible( true );
      testUtil.flush();
      assertTrue( combo._list.isSeeable() );
      combo.blur();
      testUtil.flush();
      assertFalse( combo._list.isSeeable() );
      combo.destroy();
    },

    testItems : function() {
      var testUtil = org.eclipse.rwt.test.fixture.TestUtil;
      var combo = this._createDefaultCombo();
      combo.setListVisible( true );
      testUtil.flush();
      var items = this._getItems( combo );
      assertEquals( 6, items.length );
      assertEquals( "Eiffel", items[ 0 ].getLabel() );
      assertEquals( "Smalltalk", items[ 5 ].getLabel() );
      combo.destroy();
    },

    testSelectItem : function() {
      var testUtil = org.eclipse.rwt.test.fixture.TestUtil;
      var combo = this._createDefaultCombo();
      combo.setListVisible( true );
      testUtil.flush();
      combo.select( 1 );
      assertEquals( "Java", combo._field.getValue() );
      assertEquals( "Java", combo._list.getSelectedItems()[ 0 ].getLabel() );
      combo.destroy();
    },

    testHoverClickItem : function() {
      var testUtil = org.eclipse.rwt.test.fixture.TestUtil;
      var combo = this._createDefaultCombo();
      combo.setListVisible( true );
      testUtil.flush();
      var items = this._getItems( combo );
      testUtil.mouseOver( items[ 1 ] );
      assertEquals( "Java", combo._list.getSelectedItems()[ 0 ].getLabel() )
      testUtil.click( items[ 1 ] );
      assertEquals( "Java", combo._field.getValue() );
      combo.destroy();      
    },
    
    testScrollBarClick : function() {
      var testUtil = org.eclipse.rwt.test.fixture.TestUtil;
      var combo = this._createDefaultCombo();
      combo.setListVisible( true );
      testUtil.flush();
      assertTrue( combo._list._vertScrollBar.isSeeable() );
      testUtil.click( combo._list._vertScrollBar._thumb );
      assertTrue( combo._list.isSeeable() );
      combo.destroy();
    },

    testFieldClick : function() {
      var testUtil = org.eclipse.rwt.test.fixture.TestUtil;
      var combo = this._createDefaultCombo();
      testUtil.click( combo._field );
      testUtil.flush();
      assertTrue( combo._list.isSeeable() );
      testUtil.click( combo._list._vertScrollBar._thumb );
      testUtil.click( combo._field );
      testUtil.flush();
      assertFalse( combo._list.isSeeable() );
      combo.destroy();
    },

    // bug 343532
    testEventRedispatch : function() {
      var testUtil = org.eclipse.rwt.test.fixture.TestUtil;
      var combo = this._createDefaultCombo();
      var otherCombo = this._createDefaultCombo();
      otherCombo.setSpace( 239, 81, 36, 23 );
      combo.setListVisible( true );
      testUtil.flush();
      assertTrue( combo._list.isSeeable() );
      testUtil.click( otherCombo._field );
      testUtil.flush();
      assertFalse( combo._list.isSeeable() );
      assertFalse( otherCombo._list.isSeeable() );
      combo.destroy();
      otherCombo.destroy();
    },

    // bug 343557
    testEventRedispatch_2 : function() {
      var testUtil = org.eclipse.rwt.test.fixture.TestUtil;
      var combo = this._createDefaultCombo();
      combo.setListVisible( true );
      var checkbox = new org.eclipse.rwt.widgets.Button( "check" );
      checkbox.addState( "rwt_CHECK" );
      checkbox.addToDocument();
      checkbox.setEnabled( false );
      testUtil.flush();
      assertTrue( combo._list.isSeeable() );
      assertFalse( checkbox.getEnabled() );
      assertFalse( checkbox.hasState( "selected" ) );
      testUtil.click( checkbox );
      assertFalse( combo._list.isSeeable() );
      assertFalse( checkbox.hasState( "selected" ) );
      combo.destroy();
      checkbox.destroy();
    },

    testButtonClick : function() {
      var testUtil = org.eclipse.rwt.test.fixture.TestUtil;
      var combo = this._createDefaultCombo();
      testUtil.click( combo._button );
      testUtil.flush();
      assertTrue( combo._list.isSeeable() );
      testUtil.click( combo._list._vertScrollBar._thumb );
      testUtil.click( combo._button );
      testUtil.flush();
      assertFalse( combo._list.isSeeable() );
      combo.destroy();
    },

    testListPopUpBehavior : function() {
      var testUtil = org.eclipse.rwt.test.fixture.TestUtil;
      var combo = this._createDefaultCombo();
      testUtil.click( combo._field );
      testUtil.flush();
      testUtil.click( combo._list._vertScrollBar._thumb );
      assertTrue( combo._list.isSeeable() );
      testUtil.click( testUtil.getDocument() );
      assertFalse( combo._list.isSeeable() );
      testUtil.click( combo._field );
      testUtil.flush();
      assertTrue( combo._list.isSeeable() );
      combo.destroy();
    },
   
    testListHeight : function() {
      var testUtil = org.eclipse.rwt.test.fixture.TestUtil;
      var combo = this._createDefaultCombo();
      combo.setItems( [ "Eiffel", "Java", "Python" ] );
      var correctHeight = 19 * 3 + combo._list.getFrameHeight();
      combo.setListVisible( true );
      testUtil.flush();
      assertEquals( correctHeight, combo._list.getHeight() );
      combo.destroy();
    },

    testListSelection : function() {
      var testUtil = org.eclipse.rwt.test.fixture.TestUtil;
      var combo = this._createDefaultCombo();
      combo.select( 1 );
      combo._setListSelection( combo._list.getItems()[ 5 ] );
      assertEquals( "Smalltalk", combo._list.getSelectedItems()[ 0 ].getLabel() );
      combo.setListVisible( true );
      assertEquals( "Java", combo._list.getSelectedItems()[ 0 ].getLabel() );
      combo.destroy();
    },

    testScrollToSelectedItem : function() {
      var testUtil = org.eclipse.rwt.test.fixture.TestUtil;
      var combo = this._createDefaultCombo();
      combo.select( 5 );
      combo.setListVisible( true );
      testUtil.flush();
      assertTrue( combo._list._clientArea.isSeeable() );
      assertEquals( "Smalltalk", combo._list.getSelectedItems()[ 0 ].getLabel() );
      assertEquals( 19, combo._list._clientArea.getScrollTop() );
      combo.destroy();
    },

    //////////
    // Helpers

    _createDefaultCombo : function() {
      var testUtil = org.eclipse.rwt.test.fixture.TestUtil;
      var combo = new org.eclipse.swt.widgets.Combo();
      combo.setSpace( 239, 81, 6, 23 );
      combo.setListItemHeight( 19 );
      combo.setEditable( false );
      combo.setItems( [ "Eiffel", "Java", "Python", "Ruby", "Simula", "Smalltalk" ] );
      combo.setMaxListHeight( 95 ); 
      combo.addToDocument();
      testUtil.flush();
      return combo;
    },

    _getItems : function( combo ) {
      return combo._list.getItems();
    }

  }

} );