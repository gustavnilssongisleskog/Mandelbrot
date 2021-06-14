package fractal.application

import fractal.mandelbrot.Mandelbrot

import javax.swing.JList
import scala.collection.mutable.ArrayBuffer
import javax.swing.JOptionPane
import javax.swing.JFrame
import java.util.Stack

class History(first: Mandelbrot){
    import History.StackEntry

    private var cur = new StackEntry(first)
    def back: Unit = if cur.getPrev != None then cur = cur.getPrev.get
    def forward: Unit = if cur.getNext != None then cur = cur.getNext.get
    def newEntry(newMandel: Mandelbrot): Unit = {cur.updateNext(newMandel); forward}
    def getCur: Mandelbrot = cur.mandel
}
object History{
    private class StackEntry(val mandel: Mandelbrot, private val prev: Option[StackEntry] = None){
        private var next: Option[StackEntry] = None

        def updateNext(newMandel: Mandelbrot): Unit = next = Some(new StackEntry(newMandel, Some(this)))
        def getNext: Option[StackEntry] = next
        def getPrev: Option[StackEntry] = prev
    }
    
}