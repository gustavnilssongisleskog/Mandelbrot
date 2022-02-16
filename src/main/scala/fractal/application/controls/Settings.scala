package fractal.application.controls

import fractal.mj.Fractal.{getMaxReps, newMaxReps}
import fractal.application.GUI.{reload, music}
import fractal.application.graphics.SColor.{changeColor, rainbow, bbw, random}

import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.JPanel
import java.awt.FlowLayout
import java.awt.BorderLayout
import javax.swing.WindowConstants
import javax.swing.JColorChooser
import java.awt.Color

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
                reload
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
        JOptionPane.showMessageDialog(null, "You just lost The Game!", "Loser", JOptionPane.PLAIN_MESSAGE)
    )
    panel.add(secretButton)

    private val colorButton = new JButton("Change color palette")
    colorButton.addActionListener(e =>
        val options = Array("BBW (Brown, Blue, White)", "Rainbow", "Random colors", "Choose colors yourself", "Cancel").map(_.asInstanceOf[Object])
        val choice = JOptionPane.showOptionDialog(frame, 
            "What color palette would you like to use?",
            "Color change",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options(4)
            
        )
        choice match 
            case 0 => 
                changeColor(bbw)
                reload
            case 1 => 
                changeColor(rainbow)
                reload
            case 2 => 
                changeColor(random)
                reload
            case 3 =>
                var continue = true
                var colors = Vector[Color]()
                while continue do
                    val newColor = JColorChooser.showDialog(null, "Choose a color", Color.WHITE)
                    if newColor == null then
                        continue = false
                    else
                        colors = colors appended newColor
                    if JOptionPane.showConfirmDialog(null, "Would you like to add another color?") != JOptionPane.YES_OPTION then
                        continue = false
                changeColor(colors)
                reload

            case _ => {}

    )
    panel.add(colorButton)
}