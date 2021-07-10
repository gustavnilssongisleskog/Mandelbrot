package fractal.application.controls

import fractal.application.GUI.{mandelScaleFactor, juliaScaleFactor, getMouseRe, getMouseIm, music, mouseOnFractal}


import javax.swing.JLabel
import java.awt.Graphics

object Labels{
    val mandelZoomLabel = new JLabel("Zoom: 1x") {
        override def paintComponent(g: Graphics) = {
            setText(s"Mandelbrot Zoom: ${(1 / mandelScaleFactor)}x")
            super.paintComponent(g)
        }
    }

    val juliaZoomLabel = new JLabel("Zoom: 1x") {
        override def paintComponent(g: Graphics) = {
            if juliaScaleFactor < 1 then
                setText(s"Julia Zoom: ${(10 / juliaScaleFactor).toInt.toDouble / 10}x")
            else
                setText(s"Julia Zoom: ${1 / juliaScaleFactor}x")
            super.paintComponent(g)
        }
    }

    val positionLabel = new JLabel("a"){
        override def paintComponent(g: Graphics) = {
            //setText(s"${(getMouseRe * 100).floor / 100}${if getMouseIm < 0 then (getMouseIm * 100).floor / 100 else s"+${(getMouseIm * 100).floor / 100}"}i")
            if mouseOnFractal then 
                setText(fractal.Complex(getMouseRe, getMouseIm).toString)
            else
                setText(" ")
            super.paintComponent(g)
        }
    }

    val musicLabel = new JLabel("Currently playing: "){
        override def paintComponent(g: Graphics) = {
            if music.getSong == "Secret" then
                setText("🎵 Currently losing: The Game 🎵")
            else
                setText(s"🎵 Currently playing: ${music.getSong} 🎵")
            super.paintComponent(g)
        }
    }
}