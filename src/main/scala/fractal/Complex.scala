package fractal

case class Complex(val re: Double = 0, val im: Double = 0){

    def +(other: Complex): Complex = Complex(re + other.re, im + other.im)

    def *(other: Complex): Complex = Complex(re * other.re - im * other.im, re * other.im + im * other.re)

    def ^(n: Int): Complex = {
        var res = Complex(1)
        for i <- 0 until n do res = res * this
        res
    }

    def sqr: Complex = this ^ 2

    def abs: Double = Math.sqrt(re * re + im * im)

    override def toString(): String = s"$re + ${im}i"
}