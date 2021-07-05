package fractal.application.controls

import fractal.application.GUI._
import fractal.application.graphics.Picture
import fractal.mj.Mandelbrot

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

    val mandelResetButton = new JButton("Reset")
    mandelResetButton.addActionListener(e =>
        //println("reset")
        mandelReset
    )

    val juliaResetButton = new JButton("Reset")
    juliaResetButton.addActionListener(e =>
        juliaReset
    )

    val mandelBackButton = new JButton("\u2b9c")
    mandelBackButton.addActionListener(e =>
        //println("back")

        if mandelHistory.back 
        then
            updateFrame
        else
            val panel = new JPanel
            JOptionPane.showMessageDialog(panel, "Can't go back any further!", "Warning", JOptionPane.WARNING_MESSAGE)
    )

    val juliaBackButton = new JButton("\u2b9c")
    juliaBackButton.addActionListener(e =>
        //println("back")

        if juliaHistory.back 
        then
            updateFrame
        else
            val panel = new JPanel
            JOptionPane.showMessageDialog(panel, "Can't go back any further!", "Warning", JOptionPane.WARNING_MESSAGE)
    )

    val mandelForwardButton = new JButton("\u2b9e")
    mandelForwardButton.addActionListener(e =>
        //println("forward")
        if mandelHistory.forward 
        then
            updateFrame
        else
            val panel = new JPanel
            JOptionPane.showMessageDialog(panel, "Can't go forward any further!", "Warning", JOptionPane.WARNING_MESSAGE)
    )

    val juliaForwardButton = new JButton("\u2b9e")
    juliaForwardButton.addActionListener(e =>
        //println("forward")
        if juliaHistory.forward 
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