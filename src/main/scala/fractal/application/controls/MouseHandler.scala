package fractal.application.controls

import fractal.application.GUI.{dim, frame, newPosition, zoomIn, zoomOut, newPicture, getMouseRe, getMouseIm}
import Labels.positionLabel
import Panel.commandPanel

import java.awt.event.MouseListener
import java.awt.event.MouseEvent
import javax.swing.event.MouseInputAdapter


class MouseHandler extends MouseInputAdapter{

    override def mouseClicked(e: MouseEvent): Unit = {
        e.getButton match
            case MouseEvent.BUTTON1 => 
                zoomIn
                newPicture(getMouseRe, getMouseIm)
                println("Left")
            
            case MouseEvent.BUTTON2 => 
                newPicture(getMouseRe, getMouseIm)
                println("Wheel")
            
            case MouseEvent.BUTTON3 => 
                zoomOut
                newPicture(getMouseRe, getMouseIm)
                println("Right")

            case _ => println("undefined")
        mouseMoved(e)
    }

    /*override def mousePressed(e: MouseEvent): Unit = {
        
    }

    override def mouseReleased(e: MouseEvent): Unit = {
        
    }*/

    override def mouseEntered(e: MouseEvent): Unit = {
        println("hej")
    }

    override def mouseExited(e: MouseEvent): Unit = {
        println("hejdå")
    }

    override def mouseMoved(e: MouseEvent): Unit = {
        println("moved")
        newPosition(e.getX, e.getY)
        positionLabel.repaint()
    }

}