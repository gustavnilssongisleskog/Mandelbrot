package fractal.graphics

import java.awt._
import java.awt.event._
import javax.swing._

class Picture(reps: Vector[Vector[Int]]) extends JPanel{

    override def paintComponent(g: Graphics): Unit = {
        super.paintComponent(g)
        this.setBackground(Color.black)

        for
            x <- 0 until reps.size
            y <- 0 until reps(0).size
        do 
            g.setColor(SColor.color(reps(x)(y)))
            g.fillRect(x, y, 1, 1)//g.drawLine(x, y, x, y)
            
    }

}