package fractal.graphics

import fractal.mandelbrot.Mandelbrot
import fractal.Complex
import fractal.application.graphics.SColor

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

object FractalImage{

    def createBufferedImage(
        reDim: Int = 1000,
        reMid: Double = -0.5, reSize: Double = 2,
        imMid: Double = 0, imSize: Double = 2): BufferedImage = {
        
        val mandel = Mandelbrot(reDim, reMid, reSize, imMid, imSize)
        val image = new BufferedImage(reDim, mandel.imDim, 1)
        
        for
            x <- 0 until reDim
            y <- 0 until mandel.imDim
        do
            image.setRGB(x, y, SColor.color(mandel.repsss(x)(y)).getRGB)
        
        image
    }

    //@main
    def main2(): Unit = createPNG(dim = 1000)

    def createPNG(
        name: Option[String] = None,
        dim: Int = 1500,
        reMid: Double = -0.5, reSize: Double = 2,
        imMid: Double = 0, imSize: Double = 2): Unit = {
        
        try{
            val file = new File(s"${Mandelbrot()}.png")
            //val file = new File(s"${name getOrElse s"Mandelbrot ${(2 / reSize).toInt}x zoom"}.png")
            //val file = new File(s"${name getOrElse s"Mandelbrot: ${2 / reSize}x zoom, ${Complex(reMid - reSize / 2, imMid - imSize / 2)}, ${Complex(reMid + reSize / 2, imMid + imSize / 2)}"}.png")
            println(file.getName)
            ImageIO.write(createBufferedImage(dim, reMid, reSize, imMid, imSize), "PNG", file)
        } catch {
            case e: Exception => println(e)
        }
    }

}
