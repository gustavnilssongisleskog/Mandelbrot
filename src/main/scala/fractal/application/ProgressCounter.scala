package fractal.application

import javax.swing.JLabel
import java.awt.Graphics
import java.awt.BorderLayout
import javax.swing.JInternalFrame

class ProgressCounter(goal: Int) extends JLabel{
    private var progress = 0
    def progressPercentage: Int = progress * 100 / goal

    def inc: Unit = {progress += 1; this.repaint()}

    override def paintComponent(g: Graphics) = {
        setText(s"Loading... $progressPercentage%")
        super.paintComponent(g)
    }
}
class ProgressFrame(goal: Int) extends JInternalFrame("", false, false, false, false){
    private val counter = new ProgressCounter(goal)
    add(counter, BorderLayout.CENTER)

    def inc: Unit = counter.inc
}