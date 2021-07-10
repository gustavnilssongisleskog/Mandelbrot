package fractal.application.graphics

import fractal.application.GUI._
import fractal.mj.{Mandelbrot, Julia}
import javax.swing.JPanel
import java.awt.Graphics
import java.awt.Color

class Picture(mandel: Mandelbrot, julia: Julia) extends JPanel{

    override def paintComponent(g: Graphics): Unit = {
        super.paintComponent(g)
        this.setBackground(Color.black)

        //Mandelbrot
        val mcolors = mandel.colors
        for
            x <- 0 until mcolors.size
            y <- 0 until mcolors(0).size
        do 
            g.setColor(mcolors(x)(y))
            g.fillRect(x, y, 1, 1)//g.drawLine(x, y, x, y)

        //Border
        import Picture.borderWidth
        for
            x <- 0 until borderWidth / 2
        do
            x % 2 match
                case 0 => g.setColor(Color.BLACK)
                case 1 => g.setColor(Color.WHITE)
            g.fillRect(x + mcolors.size, x, borderWidth - 2 * x, mcolors(0).size - 2 * x)

        //Julia
        val jcolors = julia.colors
        for
            x <- 0 until jcolors.size
            y <- 0 until jcolors(0).size
        do 
            g.setColor(jcolors(x)(y))
            g.fillRect(mcolors.size + borderWidth + x, y, 1, 1)//g.drawLine(x, y, x, y)
    }
}
object Picture{
    val borderWidth = 20
}