package fractal

case class Complex(val re: Double = 0, val im: Double = 0){

    def +(other: Complex): Complex = Complex(re + other.re, im + other.im)

    def *(other: Complex): Complex = Complex(re * other.re - im * other.im, re * other.im + im * other.re)

    def ^(n: Int): Complex = {
        var res = Complex(1)
        for i <- 0 until n do res = res * this
        res
    }

    def sqr: Complex = this * this

    def sqrSum: Double = re * re + im * im

    def abs: Double = Math.sqrt(re * re + im * im)

    override def toString(): String = s"$re ${if im < 0 then s"- ${-im}" else s"+ $im"}i"
}