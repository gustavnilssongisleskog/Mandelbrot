package fractal.application.controls

import javax.swing.JButton
import fractal.application.GUI._
import fractal.application.graphics.Picture
import fractal.mandelbrot.Mandelbrot

object Buttons{

    val inButton = new JButton("+")
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
    )

    val resetButton = new JButton("Reset")
    resetButton.addActionListener(e =>
        println("reset")
        zoomReset
        newPicture()
    )
}