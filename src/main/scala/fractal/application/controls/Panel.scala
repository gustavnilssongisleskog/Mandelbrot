package fractal.application.controls

import Buttons._
import Labels._

import javax.swing.JPanel
import java.awt.FlowLayout


object Panel{
    val commandPanel = new JPanel(new FlowLayout(FlowLayout.CENTER))
    commandPanel.add(zoomChangeButton)
    commandPanel.add(zoomButton)
    commandPanel.add(resetButton)
    commandPanel.add(zoomLabel)
}