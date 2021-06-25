package fractal.application.controls

import java.awt.event.KeyListener
import java.awt.event.KeyEvent

import fractal.application.GUI.newJulia

class KeyBoardHandler extends KeyListener{

    override def keyTyped(e: KeyEvent): Unit = {
        println(e.getKeyChar)
        if e.getKeyChar.toLower == 'j' || e.getKeyChar == ' ' then newJulia
    }

    override def keyPressed(e: KeyEvent): Unit = {}

    override def keyReleased(e: KeyEvent): Unit = {}

}