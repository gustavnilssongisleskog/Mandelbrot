package fractal.graphics

import javax.swing._
import java.awt.Dimension
import fractal.mandelbrot.Mandelbrot
import java.awt.FlowLayout
import java.awt.BorderLayout
import java.awt.Graphics

object GUI{
    val dim = 800
    val initSize = 2D
    val initRe = -0.639
    val initIm = 0.446
    private var scaleFactor = 1D
    @main
    def main: Unit = {
        val frame = new JFrame("Salta mandlar")
        frame.setDefaultCloseOperation(3)

        //Fixa bild
        //val mandel = Mandelbrot()
        val mandel = Mandelbrot(dim, reMid = initRe, imMid = initIm)
        var picture = new Picture(mandel.repsss)
        //Textvisare
        val zoomLabel = new JLabel("Zoom: 1x") {
            override def paintComponent(g: Graphics) = {
                setText(s"Zoom: ${(1 / scaleFactor)}x");
                super.paintComponent(g);
            }
        }
        def newPicture(reMid: Double = initRe, imMid: Double = initIm, scale: Double = 1): Unit = {
            frame.remove(picture)
            picture = new Picture(Mandelbrot(dim, reMid, initSize * scale, imMid, initSize * scale).repsss)
            frame.add(picture)
            frame.revalidate

            zoomLabel.repaint()
        }

        

        //Knappar
        val inButton = new JButton("+")
        inButton.addActionListener(e => 
            println("in")
            scaleFactor /= 2

            newPicture(scale = scaleFactor)
        )

        val outButton = new JButton("-")
        outButton.addActionListener(e =>
            println("ut")
            scaleFactor *= 2

            newPicture(scale = scaleFactor)
        )
        var zoomDirection = 0.5D
        val zoomChangeButton = new JButton("Current: +")
        zoomChangeButton.addActionListener(e =>
            println("byt")

            zoomDirection = 1 / zoomDirection
            if zoomDirection == 2 then zoomChangeButton.setText("Current: -") else zoomChangeButton.setText("Current: +")
        )

        val zoomButton = new JButton("Zoom")
        zoomButton.addActionListener(e =>
            println("zoom")
            scaleFactor *= zoomDirection

            newPicture(scale = scaleFactor)
        )

        val resetButton = new JButton("Reset")
        resetButton.addActionListener(e =>
            println("reset")
            scaleFactor = 1

            newPicture(scale = scaleFactor)
        )

        val commandPanel = new JPanel(new FlowLayout(FlowLayout.CENTER))
        //commandPanel.add(inButton)
        //commandPanel.add(outButton)
        commandPanel.add(zoomChangeButton)
        commandPanel.add(zoomButton)
        commandPanel.add(resetButton)


        
        commandPanel.add(zoomLabel)

        //Avslutande fixar
        frame.add(commandPanel, BorderLayout.SOUTH)
        frame.add(picture, BorderLayout.CENTER)
        frame.setSize(new Dimension(mandel.reDim, mandel.imDim))
        frame.setLocationRelativeTo(null)
        frame.setVisible(true)
    }
}