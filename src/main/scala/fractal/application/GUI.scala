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
    val dim = 765
    val initSize = 2.5D
    val initRe = -0.5
    val initIm = 0D
    private var mandel = Mandelbrot(dim, initRe, initSize, initIm, initSize)
    
    private var scaleFactor = 1D
    def zoomIn: Unit = scaleFactor /= 2
    def zoomOut: Unit = scaleFactor *= 2
    def zoomReset: Unit = scaleFactor = 1D
    def getScaleFactor: Double = scaleFactor
    
    private val mouse = new MouseHandler
    private var mouseRe = 0D
    def getMouseRe = mouseRe
    private var mouseIm = 0D
    def getMouseIm = mouseIm
    def newPosition(x: Int, y: Int): Unit = {
        mouseRe = x * mandel.reSize / dim + mandel.reMid - mandel.reSize / 2
        mouseIm = -y * mandel.imSize / dim + mandel.imMid + mandel.imSize / 2
    }

    private var picture: Picture = new Picture(mandel.repsss)
    def newPicture(reMid: Double = initRe, imMid: Double = initIm, scaleFactor: Double = scaleFactor): Unit = {
        frame.remove(picture)
        mandel = Mandelbrot(dim, reMid, initSize * scaleFactor, imMid, initSize * scaleFactor)
        picture = new Picture(mandel.repsss)
        picture.addMouseListener(mouse)
        picture.addMouseMotionListener(mouse)
        frame.add(picture, BorderLayout.CENTER)
        frame.revalidate

        Labels.zoomLabel.repaint()
    }

    
    @main
    def createWindow: Unit = {

        frame.setDefaultCloseOperation(3)
        frame.add(Panel.commandPanel, BorderLayout.SOUTH)
        picture.addMouseListener(mouse)
        picture.addMouseMotionListener(mouse)
        frame.add(picture)
        frame.setSize(new Dimension(mandel.reDim, mandel.imDim))
        frame.setLocationRelativeTo(null)
        frame.setVisible(true)

    }
}