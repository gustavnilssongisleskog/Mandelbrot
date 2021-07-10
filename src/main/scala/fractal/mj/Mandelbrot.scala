package fractal.mj
import fractal.Complex


import Mandelbrot._
case class Mandelbrot(override val reDim: Int = Fractal.dim, //reDim: Int = 1000, imDim: Int = 1000,
                     override val reMid: Double = initRe, override val reSize: Double = initSize,
                     override val imMid: Double = initIm, override val imSize: Double = initSize) extends Fractal(reDim, reMid, reSize, imMid, imSize){
    
    override val scaleFactor = reSize / initSize

    override def repetitions(c: Complex): (Int, Complex) = {
        var res = 0
        var z = Complex()

        //z.abs <= 2
        while z.sqrSum <= 256 && res < maxReps do 
            z = z.sqr + c
            res += 1

        (res, z)
    }

    override def toString: String = s"Mandelbrot ${super.toString}"
}
object Mandelbrot{
    val initSize = 2.5
    val initRe = -0.5
    val initIm = 0D
}