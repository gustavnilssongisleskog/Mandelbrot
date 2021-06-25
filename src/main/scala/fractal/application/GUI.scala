package fractal.application

import graphics._
import fractal.mj.{Mandelbrot, Julia, Fractal}
import controls._
import fractal.Complex

import javax.swing._
import java.awt.Dimension
import java.awt.FlowLayout
import java.awt.BorderLayout
import java.awt.Graphics

object GUI{
    val frame = new JFrame("Salta mandlar")
    val dim = Fractal.dim
    val standardMandel = Mandelbrot()
    private var mandel = standardMandel
    private var julia = Julia.random(dim)
    val music = new Music
    val history = new History(mandel)

    def mandelScaleFactor: Double = mandel.scaleFactor
    def juliaScaleFactor: Double = julia.scaleFactor
    
    private val mouse = new MouseHandler
    private var mouseMandel = false
    private var mouseJulia = false
    def mouseOnFractal: Boolean = mouseMandel || mouseJulia
    private var mouseRe = 0D
    def getMouseRe = mouseRe
    private var mouseIm = 0D
    def getMouseIm = mouseIm
    def newPosition(x: Int, y: Int): Unit = {
        if music.stopped && music.getSong != "John Cage - 4′33″" then music.restart

        mouseMandel = x < dim
        mouseJulia = x >= dim + Picture.borderWidth

        if mouseMandel then
            mouseRe = x * mandel.reSize / dim + mandel.reMid - mandel.reSize / 2    
            mouseIm = -y * mandel.imSize / dim + mandel.imMid + mandel.imSize / 2
        else if mouseJulia then
            mouseRe = (x - dim - Picture.borderWidth) * julia.reSize / dim + julia.reMid - julia.reSize / 2
            mouseIm = -y * julia.imSize / dim + julia.imMid + julia.imSize / 2

    }

    private val keyboard = new KeyBoardHandler
    def activateKeyboard: Unit = picture.requestFocusInWindow
    
    

    private var picture: Picture = new Picture(mandel, julia)
    def newJulia: Unit = {
        julia = Julia(reDim = dim, c = Complex(mouseRe, mouseIm), reSize = Math.sqrt(mandelScaleFactor) * Julia.initSize, imSize = Math.sqrt(mandelScaleFactor) * Julia.initSize)
        while Math.max(julia.reSize, julia.imSize) < Julia.initSize && (julia.repsss.flatten.count(_ == Julia.maxReps) > julia.reDim * julia.imDim / 2 || julia.repsss.flatten.filterNot(_ == Julia.maxReps).groupBy(r => r).map(p => p._2.size).toVector.sortBy(-_).take(2).sum > julia.reDim * julia.imDim / 2) do
            julia = Julia(reDim = dim, c = julia.c, reSize = julia.reSize * 2, imSize = julia.imSize * 2)
            println("retry")
        updateFrame
    }
    def zoomIn: Unit = {
        if mouseMandel then
            history.newEntry(Mandelbrot(dim, mouseRe, mandel.reSize / 2, mouseIm, mandel.imSize / 2))
            updateFrame
        else if mouseJulia then
            julia = Julia(dim, mouseRe, julia.reSize / 2, mouseIm, julia.imSize / 2, julia.c)
            updateFrame
    }
    def zoomOut: Unit = {
        if mouseMandel then
            history.newEntry(Mandelbrot(dim, mouseRe, mandel.reSize * 2, mouseIm, mandel.imSize * 2))
            updateFrame
        else if mouseJulia then
            julia = Julia(dim, mouseRe, julia.reSize * 2, mouseIm, julia.imSize * 2, julia.c)
            updateFrame
    }
    def moveTo: Unit = {
        if mouseMandel then
            history.newEntry(Mandelbrot(dim, mouseRe, mandel.reSize, mouseIm, mandel.imSize))
            updateFrame
        else if mouseJulia then
            julia = Julia(dim, mouseRe, julia.reSize, mouseIm, julia.imSize, julia.c)
            updateFrame
    }
    def reset: Unit = {
        history.newEntry(standardMandel)
        julia = Julia.random(dim)
        updateFrame
    }
    def updateFrame: Unit = {
        mandel = history.getCur
        frame.remove(picture)
        picture = new Picture(history.getCur, julia)
        picture.addMouseListener(mouse)
        picture.addMouseMotionListener(mouse)
        picture.addKeyListener(keyboard)
        frame.add(picture, BorderLayout.CENTER)
        frame.revalidate
        Labels.mandelZoomLabel.repaint()
        Labels.juliaZoomLabel.repaint()
    }

    @main
    def createWindow: Unit = {

        frame.setDefaultCloseOperation(3)
        frame.add(Panel.commandPanel, BorderLayout.SOUTH)
        picture.addMouseListener(mouse)
        picture.addMouseMotionListener(mouse)
        picture.addKeyListener(keyboard)
        frame.add(picture)
        frame.setSize(new Dimension(mandel.reDim, mandel.imDim))
        frame.setLocationRelativeTo(null)
        frame.setVisible(true)

        music.startMusic
    }
}