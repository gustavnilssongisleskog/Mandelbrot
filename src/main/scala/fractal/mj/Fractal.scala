package fractal.mj

import fractal.Complex
import Fractal.maxReps
import fractal.application.graphics.SColor.color

import scala.collection.mutable.{Queue, Set}
import java.awt.{Color => JColor}

abstract class Fractal(val reDim: Int, val reMid: Double, val reSize: Double, val imMid: Double, val imSize: Double){

    val scaleFactor: Double
    
    val imDim = (reDim * imSize / reSize).toInt

    val reInc = reSize / reDim.toDouble
    val imInc = imSize / imDim.toDouble

    val maxReps = Fractal.maxReps

    def pixelToComplex(x: Int, y: Int): Complex = Complex(reMid - reSize / 2 + x * reInc, imMid - imSize / 2 + y * imInc)

    lazy val colors = {
        val repsArray = Array.fill(reDim)(Array.fill(imDim)(-1))
        val bailoutArray = Array.fill(reDim)(Array.fill(imDim)(Complex()))

        def evaluate(x: Int, y: Int): Int = {
            if repsArray(x)(y) == -1 then 
                val r = repetitions(pixelToComplex(x, imDim - 1 - y))
                repsArray(x)(y) = r._1
                bailoutArray(x)(y) = r._2


            repsArray(x)(y)
        }
        
        val qIn = Queue[(Int, Int)]()
        val qOut = Queue[(Int, Int)]()
        val visited = Set[(Int, Int)]()

        def isOutside(x: Int, y: Int): Boolean = x < 0 || x >= reDim || y < 0 || y >= imDim

        def neighbors(p: (Int, Int)) = Vector((p._1 - 1, p._2 - 1), (p._1, p._2 - 1), (p._1 + 1, p._2 - 1), (p._1 - 1, p._2), (p._1 + 1, p._2), (p._1 - 1, p._2 + 1), (p._1, p._2 + 1), (p._1 + 1, p._2 + 1))
        
        def borderTrace(x: Int, y: Int): Unit = {
            qOut.enqueue((x, y))
            visited.add((x, y))

            while(qOut.nonEmpty) do
                neighbors(qOut.front).filterNot(visited).foreach(investigate)
                qOut.dequeue
        }

        def investigate(p: (Int, Int)): Unit = {
            val xs = neighbors(p).filter((x, y) => !isOutside(x, y) && evaluate(x, y) == maxReps)
            if xs.nonEmpty && (isOutside(p._1, p._2) || repsArray(p._1)(p._2) != maxReps) then 
                qOut.enqueue(p)
                visited.add(p)
            qIn.addAll(xs)
        }

        def fill(): Unit = {
            while(qIn.nonEmpty) do
                neighbors(qIn.front).filter((x0, y0) => !isOutside(x0, y0) && repsArray(x0)(y0) == -1).foreach((x0, y0) =>
                    repsArray(x0)(y0) = maxReps
                    qIn.addOne(x0, y0)
                )
                qIn.dequeue
        }

        for
            x <- 0 until reDim
            y <- 0 until imDim
        do
            if repsArray(x)(y) == -1 && evaluate(x, y) == maxReps then
                borderTrace(x - 1, y)
                fill()

        
        Vector.tabulate(reDim)(x =>
            Vector.tabulate(imDim)(y =>
                color(if repsArray(x)(y) == maxReps then
                    -1D
                else
                    import Math.log
                    val log_zn = log(bailoutArray(x)(y).sqrSum) / 2
                    val nu = log(log_zn / log(2)) / log(2)
                    repsArray(x)(y) + 1 - nu)
            )
        )
    }

    lazy val preview = Vector.tabulate(reDim / 10)(x => 
        Vector.tabulate(imDim / 10)(y =>
            repetitions(pixelToComplex(10 * x, 10 * y))._1))

    /*lazy val bailouts = {repsss; bailoutArray.toVector.map(_.toVector)}

    lazy val repsss = {evaluate(); repsArray.toVector.map(_.toVector)}
    val repsss = Vector.tabulate(reDim)(x => 
                             Vector.tabulate(imDim)(y => 
                             repetitions(pixelToComplex(x, imDim - 1 - y))))

    lazy val smoothIterations = {
        evaluate()
        Vector.tabulate(reDim)(x =>
            Vector.tabulate(imDim)(y =>
                if repsArray(x)(y) == maxReps then
                    maxReps.toDouble
                else
                    import Math.log
                    val log_zn = log(bailoutArray(x)(y).sqrSum) / 2
                    val nu = log(log_zn / log(2)) / log(2)
                    repsArray(x)(y) + 1 - nu
            )
        )
    }

    protected val repsArray = Array.fill(reDim)(Array.fill(imDim)(-1))
    protected val bailoutArray = Array.fill(reDim)(Array.fill(imDim)(Complex()))


    protected def evaluate(): Unit = {

        def evaluate(x: Int, y: Int): Unit = {
            repsArray(x)(y) = repetitions(pixelToComplex(x, imDim - 1 - y))
        }
      
        def evaluateLine(y: Int): Unit = {
            (0 until reDim).foreach(x => evaluate(x, y))
        }

        val t0 = System.currentTimeMillis
        val available = 2 * Runtime.getRuntime.availableProcessors
        println(available)
        val threads = Vector.tabulate(available)(y => new Thread(){
            override def run(): Unit = {
                val t0 = System.currentTimeMillis
                Range(y, imDim, available).foreach(evaluateLine)
                val t1 = System.currentTimeMillis
                println(s"Thread #$y: ${t1 - t0}")
            }
        })

        //val t1 = System.currentTimeMillis

        threads.foreach(_.start)
        threads.foreach(_.join)

        //val t2 = System.currentTimeMillis
        //println(s"Initialize: ${t1 - t0}")
        //println(s"Execute:    ${t2 - t1}")
        //println(s"Total:      ${t2 - t0}")


        def evaluate(x: Int, y: Int): Int = {
            if repsArray(x)(y) == -1 then 
                val r = repetitions(pixelToComplex(x, imDim - 1 - y))
                repsArray(x)(y) = r._1
                bailoutArray(x)(y) = r._2


            repsArray(x)(y)
        }
        
        val qIn = Queue[(Int, Int)]()
        val qOut = Queue[(Int, Int)]()
        val visited = Set[(Int, Int)]()

        def isOutside(x: Int, y: Int): Boolean = x < 0 || x >= reDim || y < 0 || y >= imDim

        def neighbors(p: (Int, Int)) = Vector((p._1 - 1, p._2 - 1), (p._1, p._2 - 1), (p._1 + 1, p._2 - 1), (p._1 - 1, p._2), (p._1 + 1, p._2), (p._1 - 1, p._2 + 1), (p._1, p._2 + 1), (p._1 + 1, p._2 + 1))
        
        def borderTrace(x: Int, y: Int): Unit = {
            qOut.enqueue((x, y))
            visited.add((x, y))

            while(qOut.nonEmpty) do
                neighbors(qOut.front).filterNot(visited).foreach(investigate)
                qOut.dequeue
        }

        def investigate(p: (Int, Int)): Unit = {
            val xs = neighbors(p).filter((x, y) => !isOutside(x, y) && evaluate(x, y) == maxReps)
            if xs.nonEmpty && (isOutside(p._1, p._2) || repsArray(p._1)(p._2) != maxReps) then 
                qOut.enqueue(p)
                visited.add(p)
            qIn.addAll(xs)
        }

        def fill(): Unit = {
            while(qIn.nonEmpty) do
                neighbors(qIn.front).filter((x0, y0) => !isOutside(x0, y0) && repsArray(x0)(y0) == -1).foreach((x0, y0) =>
                    repsArray(x0)(y0) = maxReps
                    qIn.addOne(x0, y0)
                )
                qIn.dequeue
        }

        for
            x <- 0 until reDim
            y <- 0 until imDim
        do
            if repsArray(x)(y) == -1 && evaluate(x, y) == maxReps then
                borderTrace(x - 1, y)
                fill()
    }*/

    def repetitions(z: Complex): (Int, Complex)


    /*def printGrid(): Unit = {
        for
            y <- 0 until imDim
            x <- 0 until reDim
        do {if repsss(x)(y) == maxReps then print("#") else print('.')
            if x == reDim - 1 then println}
    }*/


    override def toString: String = s"(${Complex(reMid, imMid)}, Zoom ${(10 / scaleFactor).toInt.toDouble / 10}x)"
}
object Fractal{
    private var maxReps = 1000
    def newMaxReps(reps: Int): Unit = {
        require(reps > 0)
        maxReps = reps
    }
    def getMaxReps = maxReps
    val dim = 765
}