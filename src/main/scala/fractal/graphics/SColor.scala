package fractal.graphics

object SColor{
    import fractal.mandelbrot.Mandelbrot.maxReps
    import java.awt.{Color => JColor}
    import scala.util.Random.nextInt

    val colors = Vector(
        new JColor(255, 0, 0),
        new JColor(255, 64, 0),
        new JColor(255, 128, 0),
        new JColor(255, 191, 0),
        new JColor(255, 255, 0),
        new JColor(191, 255, 0),
        new JColor(128, 255, 0),
        new JColor(64, 255, 0),
        new JColor(0, 255, 0),
        new JColor(0, 255, 64),
        new JColor(0, 255, 128),
        new JColor(0, 255, 191),
        new JColor(0, 255, 255),
        new JColor(0, 191, 255),
        new JColor(0, 128, 255),
        new JColor(0, 64, 255),
        new JColor(0, 0, 255),
        new JColor(64, 0, 255),
        new JColor(128, 0, 255),
        new JColor(191, 0, 255),
        new JColor(255, 0, 255),
        new JColor(255, 0, 191),
        new JColor(255, 0, 128),
        new JColor(255, 0, 64)
    )

    val colors2 = Vector.fill(100)(new JColor(nextInt(256), nextInt(256), nextInt(256)))

    def color(reps: Int): JColor = {
        reps match
            case `maxReps` => JColor.black
            case _ => colors((reps + colors.size - 1) % colors.size)
    }
}