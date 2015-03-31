///////////////////////////////////////////////////////////////////////////////
//PROJECT:       Micro-Manager
//SUBSYSTEM:     Display API
//-----------------------------------------------------------------------------
//
// AUTHOR:       Chris Weisiger, 2015
//
// COPYRIGHT:    University of California, San Francisco, 2015
//
// LICENSE:      This file is distributed under the BSD license.
//               License text is included with the source distribution.
//
//               This file is distributed in the hope that it will be useful,
//               but WITHOUT ANY WARRANTY; without even the implied warranty
//               of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
//
//               IN NO EVENT SHALL THE COPYRIGHT OWNER OR
//               CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
//               INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES.

package org.micromanager.display;

import ij.gui.ImageCanvas;

import java.awt.Graphics;

import javax.swing.JPanel;

import org.micromanager.data.Image;
import org.micromanager.display.DisplayWindow;

/**
 * An OverlayPanel provides a GUI for configuring how to draw an overlay
 * on top of an image canvas. See the API function
 * DisplayManager.registerOverlay() for how to attach these panels to image
 * display windows.
 */
public abstract class OverlayPanel extends JPanel {
   private DisplayManager manager_;
   private DisplayWindow display_;

   /**
    * Receive a reference to the DisplayManager, for use in instantiating
    * objects.
    */
   public void setManager(DisplayManager manager) {
      manager_ = manager;
   }

   /**
    * Receive the DisplayWindow that this instance of the panel will be
    * drawing on.
    * Note that you can request a redraw of the DisplayWindow (for example,
    * after the parameters of your overlay have been changed by the user) by
    * using DisplayWindow.postEvent(DisplayManager.createRequestToDrawEvent());
    */
   public void setDisplay(DisplayWindow display) {
      display_ = display;
   };

   /**
    * Force a redraw of the DisplayWindow, so that changes in the overlay can
    * be shown.
    */
   protected void redraw() {
      if (display_ != null) {
         display_.postEvent(manager_.createRequestToDrawEvent(null));
      }
   }

   /**
    * Draw the overlay using the provided Graphics object. This is called
    * immediately after the canvas has been drawn.
    */
   public abstract void drawOverlay(Graphics g, DisplayWindow display,
         Image image, ImageCanvas canvas);

   /**
    * Return a short string describing the overlay(s) this panel provides.
    * These will be used to create a titled border around the panel.
    */
   public abstract String getTitle();
}
