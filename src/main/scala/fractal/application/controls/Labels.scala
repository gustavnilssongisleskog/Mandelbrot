package fractal.application.controls

import fractal.application.GUI.{scaleFactor, getMouseRe, getMouseIm, music}


import javax.swing.JLabel
import java.awt.Graphics

object Labels{
    val zoomLabel = new JLabel("Zoom: 1x") {
        override def paintComponent(g: Graphics) = {
            setText(s"Zoom: ${(1 / scaleFactor)}x")
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

    val musicLabel = new JLabel("Currently playing: "){
        override def paintComponent(g: Graphics) = {
            setText(s"🎵 Currently playing: ${music.getSong} 🎵")
            super.paintComponent(g)
        }
    }
}