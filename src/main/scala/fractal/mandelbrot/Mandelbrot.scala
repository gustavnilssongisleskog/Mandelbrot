package fractal.mandelbrot
import fractal.Complex

case class Mandelbrot(reDim: Int = 700, //reDim: Int = 1000, imDim: Int = 1000,
                     reMid: Double = -0.5, reSize: Double = 2,
                     imMid: Double = 0, imSize: Double = 2){
    import Mandelbrot.maxReps

    val imDim = (reDim * imSize / reSize).toInt

    val reInc = reSize / reDim.toDouble
    val imInc = imSize / imDim.toDouble

    
    
    //Returnerar det komplexa talet som motsvaras av pixelpositionen
    def pixelToComplex(x: Int, y: Int): Complex = Complex(reMid - reSize / 2 + x * reInc, imMid - imSize / 2 + y * imInc)
    
    //Antal iterationer för ett komplext tal c att explodera under f
    def repetitions(c: Complex): Int = {
        var res = 0
        var z = Complex()

        while z.abs <= 2 && res < maxReps do 
            z = z.sqr + c
            res += 1

        res
    }

    val repsss = Vector.tabulate(reDim)(x => 
                             Vector.tabulate(imDim)(y => 
                             repetitions(pixelToComplex(x, imDim - y - 1))))

    def printGrid(): Unit = {
        for
            y <- 0 until imDim
            x <- 0 until reDim
        do {if repsss(x)(y) == maxReps then print("#") else print('.')
            if x == reDim - 1 then println}
    }


}
object Mandelbrot{
    val maxReps = 200
}