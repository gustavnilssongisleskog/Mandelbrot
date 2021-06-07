package fractal.application.controls

import fractal.application.GUI.getScaleFactor
import javax.swing.JLabel
import java.awt.Graphics

object Labels{
    val zoomLabel = new JLabel("Zoom: 1x") {
        override def paintComponent(g: Graphics) = {
            setText(s"Zoom: ${(1 / getScaleFactor)}x");
            super.paintComponent(g);
        }
    }
}