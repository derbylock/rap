/*******************************************************************************
 * Copyright (c) 2007, 2011 Innoopract Informationssysteme GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Innoopract Informationssysteme GmbH - initial API and implementation
 *     EclipseSource - ongoing development
 ******************************************************************************/
package org.eclipse.swt.internal.widgets;

import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public interface ITableAdapter {

  int getCheckWidthWithMargin();
  int getCheckWidth();
  int getCheckLeft();
  int getItemImageWidth( int columnIndex );

  int getFocusIndex();
  void setFocusIndex( int focusIndex );

  int getColumnLeftOffset( int columnIndex );
  int getLeftOffset();
  void setLeftOffset( int leftOffset );

  void checkData();
  void checkData( int index );

  int getColumnLeft( TableColumn column );
  int getDefaultColumnWidth();

  boolean isItemVisible( TableItem item );
  boolean isItemVirtual( int index );
  TableItem[] getCachedItems();
  TableItem[] getCreatedItems();

  boolean hasHScrollBar();
  boolean hasVScrollBar();

  TableItem getMeasureItem();
}