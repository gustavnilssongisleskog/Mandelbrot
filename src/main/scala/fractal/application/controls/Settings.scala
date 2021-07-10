package fractal.application.controls

import fractal.mj.Fractal.{getMaxReps, newMaxReps}
import fractal.application.GUI.{reloadIterations, music}

import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.JPanel
import java.awt.FlowLayout
import java.awt.BorderLayout
import javax.swing.WindowConstants

object Settings{
    def open: Unit = {
        frame.setSize(420, 300)
        frame.add(panel, BorderLayout.CENTER)
        frame.setLocationRelativeTo(null)
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
        frame.setResizable(false)
        frame.requestFocus
        frame.setVisible(true)
    }
    
    private val frame = new JFrame("Settings")
    private val panel = new JPanel(new FlowLayout(FlowLayout.CENTER))
    
    
    private val maxIterationButton = new JButton(s"Max iterations: $getMaxReps")
    maxIterationButton.addActionListener(e =>
        val newMax = JOptionPane.showInputDialog("Change max iterations")
        if newMax != null then
            try
                newMaxReps(newMax.toInt)
                maxIterationButton.setText(s"Max iterations: ${newMax.toInt}")
                reloadIterations
            catch
                case e: Exception => JOptionPane.showMessageDialog(frame, "Input must be a positive integer!", "Error", JOptionPane.ERROR_MESSAGE)
    )
    panel.add(maxIterationButton)

    private var preference = 4
    def getPreferece = preference
    private val options = Vector("Zoom 1x", "Find a nice zoom automatically", "Don't make a new Julia set", "Show me the boring picture", "Always ask")
    private val standardBoringButton = new JButton(s"When making a boring Julia set: ${options(preference)}")
    standardBoringButton.addActionListener(e =>
        preference += 1
        preference %= options.size
        standardBoringButton.setText(s"When making a boring Julia set: ${options(preference)}")
    )
    panel.add(standardBoringButton)

    private val secretButton = new JButton("Secret, do not open")
    secretButton.addActionListener(e =>
        music.newSong("Secret")
        JOptionPane.showMessageDialog(frame, "You just lost The Game!", "Loser", JOptionPane.PLAIN_MESSAGE)
    )
    panel.add(secretButton)
    
}