/*******************************************************************************
 * Copyright (c) 2011 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    EclipseSource - initial API and implementation
 ******************************************************************************/
package org.eclipse.rap.rwt.cluster.test.entrypoints;

import org.eclipse.rwt.RWT;
import org.eclipse.rwt.lifecycle.IEntryPoint;
import org.eclipse.rwt.service.ISessionStore;
import org.eclipse.rwt.widgets.DialogCallback;
import org.eclipse.rwt.widgets.DialogUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


public class DialogEntryPoint implements IEntryPoint {
  private static final String RETURN_CODE = "returnCode";
  
  public static int getDialogReturnCode( ISessionStore sessionStore ) {
    return ( ( Integer )sessionStore.getAttribute( RETURN_CODE ) ).intValue();
  }

  public int createUI() {
    Display display = new Display();
    Shell shell = new Shell( display );
    shell.open();
    ColorDialog dialog = new ColorDialog( shell, SWT.NONE );
    DialogUtil.open( dialog, new ColorDialogCallback() );
    return 0;
  }

  private static class ColorDialogCallback implements DialogCallback {

    public void dialogClosed( int returnCode ) {
      RWT.getSessionStore().setAttribute( RETURN_CODE, new Integer( returnCode ) );
    }
  }
}
