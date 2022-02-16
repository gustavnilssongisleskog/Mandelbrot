package fractal.application

import graphics._
import fractal.mj.{Mandelbrot, Julia, Fractal}
import controls._
import fractal.Complex

//import javax.swing
import java.awt.Dimension
import java.awt.FlowLayout
import java.awt.BorderLayout
import java.awt.Graphics
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.WindowConstants

object GUI{
    val frame = new JFrame("Salta mandlar")
    val dim = Fractal.dim
    private var mandel = Mandelbrot()
    private var julia = Julia.random(dim)
    val music = new Music
    val mandelHistory = new History(mandel)
    val juliaHistory = new History(julia)

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
        if mouseOnFractal then
            var newJulia = Julia(reDim = dim, c = Complex(mouseRe, mouseIm), reSize = Math.sqrt(mandelScaleFactor) * Julia.initSize, imSize = Math.sqrt(mandelScaleFactor) * Julia.initSize)
            if newJulia.isBoring then 
                val options = Array("Zoom 1x", "Find a nice zoom automatically", "Don't make a new Julia set", "Show me the boring picture").map(_.asInstanceOf[Object])
                val n = if Settings.getPreferece == 4 then JOptionPane.showOptionDialog(frame,
                    "This Julia set will probably be pretty boring at the current zoom level.\n" +
                      "You can either view the fractal 1x zoom, let the computer try to find a nice zoom automatically, \n" +
                      "not make a new Julia set and just keep the old one, or just view the boring picture.",
                    "Oopsie Daisy",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options(3))
                else Settings.getPreferece
                n match 
                    case 0 => 
                        juliaHistory.newEntry(Julia(reDim = dim, c = newJulia.c, reSize = Julia.initSize, imSize = Julia.initSize))
                        updateFrame
                    case 1 => 
                        while newJulia.isBoring do
                            newJulia = Julia(reDim = dim, c = newJulia.c, reSize = newJulia.reSize * 2, imSize = newJulia.imSize * 2)
                            println("retry")
                        juliaHistory.newEntry(newJulia)
                        updateFrame
                    case 2 => 
                    case 3 =>
                        juliaHistory.newEntry(newJulia)
                        updateFrame
                    case -1 => 
            else
                juliaHistory.newEntry(newJulia)
                updateFrame
    }
    def zoomIn: Unit = {
        if mouseMandel then
            mandelHistory.newEntry(Mandelbrot(dim, mouseRe, mandel.reSize / 2, mouseIm, mandel.imSize / 2))
            updateFrame
        else if mouseJulia then
            juliaHistory.newEntry(Julia(dim, mouseRe, julia.reSize / 2, mouseIm, julia.imSize / 2, julia.c))
            updateFrame
    }
    def zoomOut: Unit = {
        if mouseMandel then
            mandelHistory.newEntry(Mandelbrot(dim, mouseRe, mandel.reSize * 2, mouseIm, mandel.imSize * 2))
            updateFrame
        else if mouseJulia then
            juliaHistory.newEntry(Julia(dim, mouseRe, julia.reSize * 2, mouseIm, julia.imSize * 2, julia.c))
            updateFrame
    }
    def moveTo: Unit = {
        if mouseMandel then
            mandelHistory.newEntry(Mandelbrot(dim, mouseRe, mandel.reSize, mouseIm, mandel.imSize))
            updateFrame
        else if mouseJulia then
            juliaHistory.newEntry(Julia(dim, mouseRe, julia.reSize, mouseIm, julia.imSize, julia.c))
            updateFrame
    }
    def mandelReset: Unit = {
        mandelHistory.newEntry(Mandelbrot(dim))
        updateFrame
    }
    def juliaReset: Unit = {
        juliaHistory.newEntry(Julia(reDim = dim, c = julia.c, reSize = Julia.initSize, imSize = Julia.initSize))
        updateFrame
    }
    def reload: Unit = {
        mandelHistory.newEntry(Mandelbrot(dim, mandel.reMid, mandel.reSize, mandel.imMid, mandel.imSize))
        juliaHistory.newEntry(Julia(dim, julia.reMid, julia.reSize, julia.imMid, julia.imSize, julia.c))
        updateFrame
    }
    def updateFrame: Unit = {
        //println(julia.repsss.flatten.filterNot(_ == Julia.maxReps).groupBy(r => r).map(p => p._2.size).toVector.sortBy(-_).take(3).sum.toDouble / (julia.reDim * julia.imDim))
        mandel = mandelHistory.getCur
        julia = juliaHistory.getCur
        frame.remove(picture)
        picture = new Picture(mandelHistory.getCur, julia)
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

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
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