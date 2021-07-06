package fractal.application.controls

import fractal.mj.Fractal.{getMaxReps, newMaxReps}
import fractal.application.GUI.reloadIterations

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

    /*private var standardBoringJulia = -2
    def getStandardBoringJulia = standardBoringJulia
    private val boringJuliaGroup = new ButtonGroup
    private val ask = new JRadioButton("Always ask")
    private val zoom1 = new JRadioButton("Zoom 1x")
    private val automatic = new JRadioButton("Find a nice zoom automatically")
    private val nojulia = new JRadioButton("Don't make a new Julia set")
    private val boringjulia = new JRadioButton("Show me the boring picture")

    ask.setBounds(50, 150, 300, 200)
    zoom1.setBounds(50, 250, 300, 300)
    automatic.setBounds(50, 350, 300, 400)
    nojulia.setBounds(50, 450, 300, 500)
    boringjulia.setBounds(50, 550, 300, 600)

    boringJuliaGroup.add(ask)
    boringJuliaGroup.add(zoom1)
    boringJuliaGroup.add(automatic)
    boringJuliaGroup.add(nojulia)
    boringJuliaGroup.add(boringjulia)
    

    panel2.add(ask)
    panel2.add(zoom1)
    panel2.add(automatic)
    panel2.add(nojulia)
    panel2.add(boringjulia)*/
    
}