package fractal.mandelbrot
import fractal.Complex
import scala.collection.mutable.{Queue, Set}

case class Mandelbrot(reDim: Int = 700, //reDim: Int = 1000, imDim: Int = 1000,
                     reMid: Double = -0.5, reSize: Double = 2,
                     imMid: Double = 0, imSize: Double = 2){
    import Mandelbrot._

    val imDim = (reDim * imSize / reSize).toInt

    val reInc = reSize / reDim.toDouble
    val imInc = imSize / imDim.toDouble

    
    
    //Returnerar det komplexa talet som motsvaras av pixelpositionen
    def pixelToComplex(x: Int, y: Int): Complex = Complex(reMid - reSize / 2 + x * reInc, imMid - imSize / 2 + y * imInc)
    
    
    lazy val repsss = {evaluate(); repsArray.toVector.map(xs => xs.toVector)}
    /*val repsss = Vector.tabulate(reDim)(x => 
                             Vector.tabulate(imDim)(y => 
                             repetitions(pixelToComplex(x, imDim - 1 - y))))*/

    private val repsArray = Array.fill(reDim)(Array.fill(imDim)(-1))

    private def evaluate(): Unit = {

        /*def evaluate(x: Int, y: Int): Unit = {
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
*/

        def evaluate(x: Int, y: Int): Int = {
            if repsArray(x)(y) == -1 then 
                repsArray(x)(y) = repetitions(pixelToComplex(x, imDim - 1 - y))
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
    }

    def printGrid(): Unit = {
        for
            y <- 0 until imDim
            x <- 0 until reDim
        do {if repsss(x)(y) == maxReps then print("#") else print('.')
            if x == reDim - 1 then println}
    }

    override def toString: String = s"Mandelbrot ${Complex(reMid, imMid)}, Zoom ${2.5 / reSize}x"
}
object Mandelbrot{
    val maxReps = 1000

    //Antal iterationer för ett komplext tal c att explodera under f
    def repetitions(c: Complex): Int = {
        var res = 0
        var z = Complex()


        //z.abs <= 2
        while z.sqrSum <= 4 && res < maxReps do 
            z = z.sqr + c
            res += 1

        res
    }

}