package fractal.application.controls

import fractal.application.GUI._
import fractal.application.graphics.Picture
import fractal.mandelbrot.Mandelbrot

import javax.swing.JButton
import javax.swing.JOptionPane
import javax.swing.JPanel

object Buttons{

    /*val inButton = new JButton("+")
    inButton.addActionListener(e => 
        println("in")
        zoomIn
        newPicture()
    )

    val outButton = new JButton("-")
    outButton.addActionListener(e =>
        println("ut")
        zoomOut
        newPicture()
    )

    private var zoomDirection = 0.5D
    val zoomChangeButton = new JButton("Current: +")
    zoomChangeButton.addActionListener(e =>
        println("byt")
        zoomDirection = 1 / zoomDirection
        if zoomDirection == 2 then zoomChangeButton.setText("Current: -") else zoomChangeButton.setText("Current: +")
    )

    val zoomButton = new JButton("Zoom")
    zoomButton.addActionListener(e =>
        println("zoom")
        zoomDirection match {case 2D => zoomOut; case 0.5 => zoomIn}
        newPicture()
    )*/

    val resetButton = new JButton("Reset")
    resetButton.addActionListener(e =>
        //println("reset")
        reset
    )

    val backButton = new JButton("\u2b9c")
    backButton.addActionListener(e =>
        //println("back")

        if history.back 
        then
            updateFrame
        else
            val panel = new JPanel
            JOptionPane.showMessageDialog(panel, "Can't go back any further!", "Warning", JOptionPane.WARNING_MESSAGE)
    )

    val forwardButton = new JButton("\u2b9e")
    forwardButton.addActionListener(e =>
        //println("forward")
        if history.forward 
        then
            updateFrame
        else
            val panel = new JPanel
            JOptionPane.showMessageDialog(panel, "Can't go forward any further!", "Warning", JOptionPane.WARNING_MESSAGE)
    )

    val musicButton = new JButton("Music")
    musicButton.addActionListener(e =>
        //println("music")
        music.chooseSong
    )
}