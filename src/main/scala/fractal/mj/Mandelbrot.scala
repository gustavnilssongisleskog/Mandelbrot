package fractal.mj
import fractal.Complex


import Mandelbrot._
case class Mandelbrot(override val reDim: Int = Fractal.dim, //reDim: Int = 1000, imDim: Int = 1000,
                     override val reMid: Double = initRe, override val reSize: Double = initSize,
                     override val imMid: Double = initIm, override val imSize: Double = initSize) extends Fractal(reDim, reMid, reSize, imMid, imSize){
    
    override val scaleFactor = reSize / initSize

    override def repetitions(c: Complex): Int = {
        var res = 0
        var z = Complex()

        //z.abs <= 2
        while z.sqrSum <= 4 && res < maxReps do 
            z = z.sqr + c
            res += 1

        res
    }

    override def toString: String = s"Mandelbrot ${super.toString}"
}
object Mandelbrot{
    val maxReps = Fractal.maxReps
    val initSize = 2.5
    val initRe = -0.5
    val initIm = 0D

}