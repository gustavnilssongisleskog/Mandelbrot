package fractal.mj

import fractal.Complex


case class Julia(override val reDim: Int = 700, //reDim: Int = 1000, imDim: Int = 1000,
                     override val reMid: Double = 0, override val reSize: Double = 3,
                     override val imMid: Double = 0, override val imSize: Double = 3,
                     c: Complex = Complex()) extends Fractal(reDim, reMid, reSize, imMid, imSize){

    import Julia.maxReps

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
}