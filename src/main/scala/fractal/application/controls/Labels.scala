package fractal.application.controls

import fractal.application.GUI.{getScaleFactor, getMouseRe, getMouseIm}


import javax.swing.JLabel
import java.awt.Graphics

object Labels{
    val zoomLabel = new JLabel("Zoom: 1x") {
        override def paintComponent(g: Graphics) = {
            setText(s"Zoom: ${(1 / getScaleFactor)}x")
            super.paintComponent(g)
        }
    }

    val positionLabel = new JLabel("a"){
        override def paintComponent(g: Graphics) = {
            //setText(s"${(getMouseRe * 100).floor / 100}${if getMouseIm < 0 then (getMouseIm * 100).floor / 100 else s"+${(getMouseIm * 100).floor / 100}"}i")
            setText(fractal.Complex(getMouseRe, getMouseIm).toString)
            super.paintComponent(g)
        }
    }
}