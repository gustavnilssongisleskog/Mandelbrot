package fractal.mj

import fractal.Complex
import Fractal.maxReps

abstract class Fractal(val reDim: Int, val reMid: Double, val reSize: Double, val imMid: Double, val imSize: Double){

    val imDim = (reDim * imSize / reSize).toInt

    val reInc = reSize / reDim.toDouble
    val imInc = imSize / imDim.toDouble

    //Returnerar det komplexa talet som motsvaras av pixelpositionen
    def pixelToComplex(x: Int, y: Int): Complex = Complex(reMid - reSize / 2 + x * reInc, imMid - imSize / 2 + y * imInc)

    lazy val repsss = {evaluate(); repsArray.toVector.map(xs => xs.toVector)}
    /*val repsss = Vector.tabulate(reDim)(x => 
                             Vector.tabulate(imDim)(y => 
                             repetitions(pixelToComplex(x, imDim - 1 - y))))*/

    protected val repsArray = Array.fill(reDim)(Array.fill(imDim)(-1))


    protected def evaluate(): Unit


    def printGrid(): Unit = {
        for
            y <- 0 until imDim
            x <- 0 until reDim
        do {if repsss(x)(y) == maxReps then print("#") else print('.')
            if x == reDim - 1 then println}
    }


    override def toString: String = s"(${Complex(reMid, imMid)}, Zoom ${2.5 / reSize}x)"
}
object Fractal{
    val maxReps = 1000
}