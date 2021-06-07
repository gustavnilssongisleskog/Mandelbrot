package fractal.application

import graphics._
import fractal.mandelbrot.Mandelbrot
import controls._

import javax.swing._
import java.awt.Dimension
import java.awt.FlowLayout
import java.awt.BorderLayout
import java.awt.Graphics


object GUI{
    val frame = new JFrame("Salta mandlar")
    val dim = 800
    val initSize = 2D
    val initRe = -0.639
    val initIm = 0.446
    val mandel = Mandelbrot(dim, reMid = initRe, imMid = initIm)
    
    
    private var scaleFactor = 1D
    def zoomIn: Unit = scaleFactor /= 2
    def zoomOut: Unit = scaleFactor *= 2
    def zoomReset: Unit = scaleFactor = 1D
    def getScaleFactor: Double = scaleFactor
    

    private var picture = new Picture(mandel.repsss)
    def newPicture(reMid: Double = initRe, imMid: Double = initIm, scale: Double = scaleFactor): Unit = {
        frame.remove(picture)
        picture = new Picture(Mandelbrot(dim, reMid, initSize * scale, imMid, initSize * scale).repsss)
        frame.add(picture)
        frame.revalidate

        Labels.zoomLabel.repaint()
    }

    
    @main
    def createWindow: Unit = {

        frame.setDefaultCloseOperation(3)
        frame.add(Panel.commandPanel, BorderLayout.SOUTH)
        frame.add(picture, BorderLayout.CENTER)
        frame.setSize(new Dimension(mandel.reDim, mandel.imDim))
        frame.setLocationRelativeTo(null)
        frame.setVisible(true)

    }
}