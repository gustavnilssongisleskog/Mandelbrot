package fractal.application

import fractal.mj.Fractal

class History[T](first: T){
    import History.StackEntry

    private var cur = new StackEntry(first)
    def back: Boolean = if cur.getPrev != None then {cur = cur.getPrev.get; true} else false
    def forward: Boolean = if cur.getNext != None then {cur = cur.getNext.get; true} else false
    def newEntry(newFractal: T): Unit = {cur.updateNext(newFractal); forward}
    def getCur: T = cur.fractal
}
object History{
    private class StackEntry[T](val fractal: T, private val prev: Option[StackEntry[T]] = None){
        private var next: Option[StackEntry[T]] = None

        def updateNext(newFractal: T): Unit = next = Some(new StackEntry(newFractal, Some(this)))
        def getNext: Option[StackEntry[T]] = next
        def getPrev: Option[StackEntry[T]] = prev
    }
    
}