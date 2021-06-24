package fractal.application.graphics

import java.awt._
import java.awt.event._
import javax.swing._
import fractal.application.GUI._
import fractal.mj.{Mandelbrot, Julia}

class Picture(mandel: Mandelbrot, julia: Julia) extends JPanel{

    override def paintComponent(g: Graphics): Unit = {
        super.paintComponent(g)
        this.setBackground(Color.black)

        //Mandelbrot
        val mreps = mandel.repsss
        for
            x <- 0 until mreps.size
            y <- 0 until mreps(0).size
        do 
            g.setColor(SColor.color(mreps(x)(y)))
            g.fillRect(x, y, 1, 1)//g.drawLine(x, y, x, y)

        //Border
        import Picture.borderWidth
        for
            x <- 0 until borderWidth / 2
        do
            x % 2 match
                case 0 => g.setColor(Color.BLACK)
                case 1 => g.setColor(Color.WHITE)
            g.fillRect(x + mreps.size, x, borderWidth - 2 * x, mreps(0).size - 2 * x)

        //Julia
        val jreps = julia.repsss
        for
            x <- 0 until jreps.size
            y <- 0 until jreps(0).size
        do 
            g.setColor(SColor.color(jreps(x)(y)))
            g.fillRect(mreps.size + borderWidth + x, y, 1, 1)//g.drawLine(x, y, x, y)
    }
}
object Picture{
    val borderWidth = 20
}