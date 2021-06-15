package fractal.application.controls

import Buttons._
import Labels._

import javax.swing.JPanel
import java.awt.FlowLayout


object Panel{
    val commandPanel = new JPanel(new FlowLayout(FlowLayout.CENTER))
    commandPanel.add(musicLabel)
    commandPanel.add(musicButton)
    commandPanel.add(backButton)
    commandPanel.add(forwardButton)
    commandPanel.add(resetButton)
    
    commandPanel.add(zoomLabel)
    commandPanel.add(positionLabel)

}