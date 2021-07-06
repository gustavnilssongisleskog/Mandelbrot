package fractal.application.controls

import fractal.mj.Fractal.{getMaxReps, newMaxReps}
import fractal.application.GUI.reloadIterations

import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JOptionPane

object Settings{
    def open: Unit = {
        
    }
    
    private val frame = new JFrame("Settings")
    
    val maxIterationButton = new JButton(s"Max iterations: $getMaxReps")
    maxIterationButton.addActionListener(e =>
        val newMax = JOptionPane.showInputDialog("Change max iterations")
        if newMax != null then
            try
                newMaxReps(newMax.toInt)
                //if newMax.toInt > 10000
                maxIterationButton.setText(s"Max iterations: ${newMax.toInt}")
                reloadIterations
            catch
                case e: Exception => JOptionPane.showMessageDialog(frame, "Input must be a positive integer!", "Error", JOptionPane.ERROR_MESSAGE)
    )
}