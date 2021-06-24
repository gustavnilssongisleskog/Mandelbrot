package fractal.application

import graphics._
import fractal.mj.{Mandelbrot, Julia}
import controls._
import fractal.Complex

import javax.swing._
import java.awt.Dimension
import java.awt.FlowLayout
import java.awt.BorderLayout
import java.awt.Graphics

import util.Random.nextDouble


object GUI{
    val frame = new JFrame("Salta mandlar")
    val dim = 765
    val initSize = 2.5D
    val initRe = -0.5
    val initIm = 0D
    val standardMandel = Mandelbrot(dim, initRe, initSize, initIm, initSize)
    private var mandel = standardMandel
    //val standardJulia = Julia(reDim = dim, c = Complex(nextDouble, nextDouble))//-0.532, -0.6))
    private var julia = Julia(reDim = dim, c = Complex(nextDouble, nextDouble))//standardJulia
    val music = new Music
    val history = new History(mandel)

    /*private var scaleFactor = 1D
    def zoomIn: Unit = scaleFactor /= 2
    def zoomOut: Unit = scaleFactor *= 2
    def zoomReset: Unit = scaleFactor = 1D*/
    def scaleFactor: Double = mandel.reSize / initSize
    
    private val mouse = new MouseHandler
    private var mouseRe = 0D
    def getMouseRe = mouseRe
    private var mouseIm = 0D
    def getMouseIm = mouseIm
    def newPosition(x: Int, y: Int): Unit = {
        if music.stopped && music.getSong != "John Cage - 4′33″" then music.restart
        mouseRe = x * mandel.reSize / dim + mandel.reMid - mandel.reSize / 2
        mouseIm = -y * mandel.imSize / dim + mandel.imMid + mandel.imSize / 2
    }
    
    

    private var picture: Picture = new Picture(mandel, julia)
    def newJulia(reC: Double, imC: Double): Unit = {
        julia = Julia(reDim = dim, c = Complex(reC, imC), reSize = Math.sqrt(scaleFactor) * 3, imSize = Math.sqrt(scaleFactor) * 3)
        while Math.max(julia.reSize, julia.imSize) < 3 && (julia.repsss.flatten.count(_ == Julia.maxReps) > julia.reDim * julia.imDim / 2 || julia.repsss.flatten.filterNot(_ == Julia.maxReps).groupBy(r => r).map(p => p._2.size).toVector.sortBy(-_).take(2).sum > julia.reDim * julia.imDim / 2) do
            julia = Julia(reDim = dim, c = julia.c, reSize = julia.reSize * 2, imSize = julia.imSize * 2)
            println("retry")
        updateFrame
    }
    def zoomIn(reMid: Double, imMid: Double): Unit = {   
        history.newEntry(Mandelbrot(dim, reMid, initSize * scaleFactor / 2, imMid, initSize * scaleFactor / 2))
        updateFrame
    }
    def zoomOut(reMid: Double, imMid: Double): Unit = {
        history.newEntry(Mandelbrot(dim, reMid, initSize * scaleFactor * 2, imMid, initSize * scaleFactor * 2))
        updateFrame
    }
    def moveTo(reMid: Double, imMid: Double): Unit = {
        history.newEntry(Mandelbrot(dim, reMid, initSize * scaleFactor, imMid, initSize * scaleFactor))
        updateFrame
    }
    def reset: Unit = {
        history.newEntry(standardMandel)
        updateFrame
    }
    def updateFrame: Unit = {
        mandel = history.getCur
        frame.remove(picture)
        picture = new Picture(history.getCur, julia)
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

        music.startMusic
    }
}