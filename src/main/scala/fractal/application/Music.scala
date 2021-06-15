package fractal.application

import javax.sound.sampled.AudioSystem
import java.io.File
import scala.util.Random.nextInt
import javax.swing.JComboBox
import javax.swing.JOptionPane

class Music{
    val songs = Vector(
        "Belinda Carlisle - Circle In The Sand(elbrot)",
        "Carola - Tommy Tycker Om Mig",
        "John Williams - Duel of the Fates",
        "Michael Sembello - Man(delbrot)iac",
        "Rockwell - Somebody's Watching M(andelbrot)e",
        "Sandra - Ma(ndelbrot)ria Ma(ndelbrot)gdalena",
        "Vanessa Paradis - Joe Le Taxi"
    )

    private var song = "John Williams - Duel of the Fates"//songs(nextInt(songs.size))
    def getSong: String = song
    private var music = AudioSystem.getAudioInputStream(new File(s"Music/$song.wav").getAbsoluteFile())
    private var clip = AudioSystem.getClip
    
    private var shuffle = true

    def stopped: Boolean = !clip.isRunning
    
    def startMusic: Unit = {
        clip.open(music)
        clip.start
    }
    def restart: Unit = {
        println(shuffle)
        println(songs.filterNot(_ equals song))
        if shuffle then
            newSong(songs(nextInt(songs.size)))
        else
            clip.setFramePosition(0)
            clip.start
    }

    def chooseSong: Unit = {
        val combobox = new JComboBox((Vector("----------", "--Shuffle--", "John Cage - 4′33″") concat songs).toArray)
        JOptionPane.showMessageDialog(null, combobox, "Choose a song", JOptionPane.QUESTION_MESSAGE)

        val choice = combobox.getSelectedItem.asInstanceOf[String]
        choice match 
            case "----------" => 
            case "--Shuffle--" => 
                shuffle = true
                newSong(songs(nextInt(songs.size)))
            case _ =>
                shuffle = false
                newSong(choice)
    }

    def newSong(nSong: String): Unit = {
        clip.close
        song = nSong
        controls.Labels.musicLabel.repaint()

        if song != "John Cage - 4′33″"
        then
            music = AudioSystem.getAudioInputStream(new File(s"Music/$song.wav").getAbsoluteFile())
            clip = AudioSystem.getClip
            startMusic
        
    }
}