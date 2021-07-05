package fractal.application.controls

import Buttons._
import Labels._

import javax.swing.JPanel
import java.awt.FlowLayout


object Panel{
    val commandPanel = new JPanel(new FlowLayout(FlowLayout.CENTER))
    commandPanel.add(mandelZoomLabel)
    
    
    commandPanel.add(mandelBackButton)
    commandPanel.add(mandelForwardButton)
    commandPanel.add(mandelResetButton)
    
    commandPanel.add(musicLabel)
    commandPanel.add(musicButton)

    
    
    
    commandPanel.add(juliaZoomLabel)
    commandPanel.add(juliaBackButton)
    commandPanel.add(juliaForwardButton)
    commandPanel.add(juliaResetButton)
    
    
    commandPanel.add(positionLabel)

}