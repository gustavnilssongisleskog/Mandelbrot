package fractal.application

import javax.sound.sampled.AudioSystem
import java.io.File
import scala.util.Random.nextInt
import javax.swing.JComboBox
import javax.swing.JOptionPane

class Music{
    private val musicPath = "Music/"
    val songs = Vector(
        "Belinda Carlisle - Circle In The Sand(elbrot)",
        "Carola - Tommy Tycker Om Mig",
        "John Williams - Duel of the Fates",
        "Michael Sembello - Man(delbrot)iac",
        "Rockwell - Somebody's Watching M(andelbrot)e",
        "Sandra - Ma(ndelbrot)ria Ma(ndelbrot)gdalena",
        "Vanessa Paradis - Joe Le Taxi"
    )
    private var recentSongs = Vector.fill(5)("")

    private var song = songs(nextInt(songs.size))
    def getSong: String = song
    private var music = AudioSystem.getAudioInputStream(new File(s"${musicPath}$song.wav").getAbsoluteFile())
    private var clip = AudioSystem.getClip
    
    private var shuffle = true

    def stopped: Boolean = !clip.isRunning
    
    def startMusic: Unit = {
        recentSongs = recentSongs.tail.appended(song)
        //println(song)
        clip.open(music)
        clip.start
    }
    def restart: Unit = {
        if shuffle then
            newSong(randomSong(songs diff recentSongs))
        else
            clip.setFramePosition(0)
            clip.start
    }
    def randomSong(xs: Vector[String]): String = xs(nextInt(xs.size))
    
    
    private val sameOption = "----------"
    private val shuffleOption = "--Shuffle--"
    def chooseSong: Unit = {
        val combobox = new JComboBox((Vector(sameOption, shuffleOption, "John Cage - 4′33″") concat songs).toArray)
        JOptionPane.showMessageDialog(null, combobox, "Choose a song", JOptionPane.QUESTION_MESSAGE)

        val choice = combobox.getSelectedItem.asInstanceOf[String]
        choice match 
            case `sameOption` => 
            case `shuffleOption` => 
                shuffle = true
                newSong(randomSong(songs diff recentSongs))
            case _ =>
                shuffle = false
                newSong(choice)
    }

    def newSong(nSong: String): Unit = {
        clip.close
        song = nSong
        controls.Labels.musicLabel.repaint()

        if song != "John Cage - 4′33″" then
            music = AudioSystem.getAudioInputStream(new File(s"${musicPath}$song.wav").getAbsoluteFile())
            clip = AudioSystem.getClip
            startMusic
        
    }
}