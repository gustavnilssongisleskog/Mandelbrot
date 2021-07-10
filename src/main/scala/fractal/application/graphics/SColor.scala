package fractal.application.graphics

import java.awt.Graphics
import java.awt.Dimension
import fractal.mj.Fractal
import javax.swing.JPanel

object SColor{
    //import fractal.mj.Mandelbrot.maxReps
    import java.awt.{Color => JColor}
    import scala.util.Random.nextInt

    val black = JColor.BLACK

    val colors = Vector(
        new JColor(255, 0, 0),
        new JColor(255, 64, 0),
        new JColor(255, 128, 0),
        new JColor(255, 191, 0),
        new JColor(255, 255, 0),
        new JColor(191, 255, 0),
        new JColor(128, 255, 0),
        new JColor(64, 255, 0),
        new JColor(0, 255, 0),
        new JColor(0, 255, 64),
        new JColor(0, 255, 128),
        new JColor(0, 255, 191),
        new JColor(0, 255, 255),
        new JColor(0, 191, 255),
        new JColor(0, 128, 255),
        new JColor(0, 64, 255),
        new JColor(0, 0, 255),
        new JColor(64, 0, 255),
        new JColor(128, 0, 255),
        new JColor(191, 0, 255),
        new JColor(255, 0, 255),
        new JColor(255, 0, 191),
        new JColor(255, 0, 128),
        new JColor(255, 0, 64)
    )

    val colors2 = Vector.fill(100)(new JColor(nextInt(256), nextInt(256), nextInt(256)))

    //val colors3 = many(2, blend, Vector(JColor.red, JColor.orange, JColor.yellow, JColor.green, JColor.blue, new JColor(75, 0, 130).brighter, new JColor(148, 0, 211), JColor.red)).dropRight(1)//many(3, blend, Vector(JColor.YELLOW, new JColor(0,0,128), JColor.YELLOW)).reverse.tail.reverse

    val colors3 = many(8, blend, Vector(
        new JColor(9,1,47),
        new JColor(4,4,73),
        new JColor(0,7,100),
        new JColor(12,44,138),
        new JColor(24,82,177),
        new JColor(57,125,209),
        new JColor(134,181,229),
        new JColor(211,236,248),
        new JColor(241,233,191),
        new JColor(248,201,95),
        new JColor(255,170,0),
        new JColor(204,128,0),
        new JColor(153,87,0),
        new JColor(106,52,3),
        new JColor(66,30,15),
        new JColor(25,7,26),
        new JColor(9,1,47)
    )).dropRight(1)

    def color(reps: Double): JColor = if reps == -1 then JColor.BLACK else colors3(((reps * 100).toInt + colors3.size - 1) % colors3.size)

    def many[T](n: Int, f: T => T, x: T): T = {
        if n == 0 then x else many(n - 1, f, f(x))
    }

    def quadAvg(c1: JColor, c2: JColor): JColor = {
        (new JColor(Math.sqrt((c1.getRed * c1.getRed + c2.getRed * c2.getRed) / 2).toInt, 
                    Math.sqrt((c1.getGreen * c1.getGreen + c2.getGreen * c2.getGreen) / 2).toInt, 
                    Math.sqrt((c1.getBlue * c1.getBlue + c2.getBlue * c2.getBlue) / 2).toInt))
    }
    
    def aritAvg(c1: JColor, c2: JColor): JColor = {
        new JColor((c1.getRed + c2.getRed) / 2, (c1.getGreen + c2.getGreen) / 2, (c1.getBlue + c2.getBlue) / 2)
    }

    def blend(xs: Vector[JColor]): Vector[JColor] = {
        xs.sliding(2).toVector.flatten.sliding(2).toVector.map(x => quadAvg(x(0), x(1))).prepended(xs.head).appended(xs.last)
    }

    /*class Rainbow extends JPanel{
        
        val colors = many(2, blend, Vector(JColor.red, JColor.orange, JColor.yellow, JColor.green, JColor.blue, new JColor(75, 0, 130).brighter, new JColor(148, 0, 211), JColor.red))
        //val colors = many(10, blend, Vector(JColor.red, JColor.blue))
        //val colors = many(10, blend, Vector(JColor.white, JColor.black))
        //val colors = many(10, blend, Vector(new JColor(0,0,102), new JColor(204, 102, 0)))
        //val colors = many(4, blend, Vector(JColor.blue, JColor.white))
        
        override def paintComponent(g: Graphics): Unit = {
            super.paintComponent(g)
            this.setBackground(JColor.black)

            colors.indices.foreach(x => {g.setColor(colors(x)); g.fillRect(10 * x, 0, 10, 300)})

        }
    }

    @main
    def main3(): Unit = {
        val frame = new JFrame("hej")
        val img = new Rainbow
        frame.add(img)
        frame.setSize(new Dimension(800, 800))
        frame.setDefaultCloseOperation(3)
        frame.setVisible(true)
    }*/



}