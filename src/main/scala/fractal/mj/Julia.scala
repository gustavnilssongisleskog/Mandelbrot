package fractal.mj

import fractal.Complex
import util.Random.nextDouble

import Julia._
case class Julia(override val reDim: Int = Fractal.dim, //reDim: Int = 1000, imDim: Int = 1000,
                     override val reMid: Double = initRe, override val reSize: Double = initSize,
                     override val imMid: Double = initIm, override val imSize: Double = initSize,
                     c: Complex = Complex()) extends Fractal(reDim, reMid, reSize, imMid, imSize){

    override val scaleFactor = reSize / initSize

    def repetitions(z: Complex): Int = {
        var res = 0
        var cur = z
        while cur.sqrSum < 64 && res < maxReps do
            cur = cur.sqr + c
            res += 1

        res
    }
    

    override protected def evaluate(): Unit = {
        for
            x <- 0 until reDim
            y <- 0 until imDim
        do
            repsArray(x)(y) = repetitions(pixelToComplex(x, imDim - 1 - y))
    }

    override def toString: String = s"Julia at c ${super.toString}"
}
object Julia{
    val maxReps = Fractal.maxReps
    val initSize = 3
    val initRe = 0D
    val initIm = 0D

    def random(dim: Int) = Julia(reDim = dim, c = Complex(nextDouble * 0.65 - 1.4, nextDouble * 0.8 - 0.4))
}