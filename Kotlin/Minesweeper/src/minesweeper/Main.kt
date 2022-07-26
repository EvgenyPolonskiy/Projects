package minesweeper

import java.util.Scanner
import kotlin.math.min
import kotlin.random.Random


fun main() {
    val scanner = Scanner(System.`in`)
    val mineField = Grid(9, 9)
    var firstTry = true

    mineField.initGrid()

    print("How many mines do you want on the field? ")
    val nmine = scanner.nextInt()
    println()

    mineField.displayBoard()

    while (true) {
        print("Set/unset mines marks or claim a cell as free: ")
        val y = scanner.nextInt() - 1
        val x = scanner.nextInt() - 1

        val action = scanner.next()
        val cell = mineField.land[mineField.indexOf(x, y)]
        println()

        if (action == "free" && cell.hidden) {
            if (firstTry) {
                mineField.addMines(nmine, cell)
                firstTry = false
            }
            if (cell.isMine()) {
                mineField.showMines()
                mineField.displayBoard()
                println("You stepped on a mine and failed!")
                break
            }
            mineField.discoverCell(cell)
        } else if (action == "mine" && cell.hidden) {
            cell.toggleMark()
        }
        mineField.displayBoard()

        if (!firstTry && mineField.playerWin()) {
            println("Congratulations! You found all the mines!")
            break
        }
    }
}


class Cell(var x: Int, var y: Int) {
    val emptySign = '/'
    val mineSign = 'X'
    val hiddenSign = '.'
    val markSign = '*'

    var value = emptySign
    var mineCounter = 0
    var hidden = true
    var marked = false

    fun isMine(): Boolean {
        return this.value == this.mineSign
    }

    fun isEmpty(): Boolean {
        return this.value == this.emptySign
    }

    fun becomeMine() {
        this.value = this.mineSign
    }

    fun toggleMark() {
        this.marked = !this.marked
    }

    fun discover() {
        this.hidden = false
    }

    fun toDisplay(): Char {
        if (this.hidden && this.marked) {
            return this.markSign
        } else if (this.hidden) {
            return this.hiddenSign
        } else if (this.mineCounter > 0) {
            return this.mineCounter.toString()[0]
        }
        return this.value
    }
}

class Grid(val nCol: Int, val nLine: Int) {

    var land = mutableListOf<Cell>()
    var nmine = 0

    fun initGrid() {
        this.land.clear()

        for (x in 0 until this.nCol) {
            for (y in 0 until nLine) {
                this.land.add(Cell(x, y))
            }
        }
    }

    fun indexOf(x: Int, y: Int): Int {
        for (i in 0 until this.nCol * this.nLine) {
            if (this.land[i].x == x && this.land[i].y == y) {
                return i
            }
        }
        return -1
    }

    fun getAdjacentCells(begin: Cell): MutableList<Cell> {
        val result = mutableListOf<Cell>()
        val indexes = listOf<Int>(
            this.indexOf(begin.x - 1, begin.y - 1),
            this.indexOf(begin.x - 1, begin.y),
            this.indexOf(begin.x - 1, begin.y + 1),
            this.indexOf(begin.x, begin.y - 1),
            this.indexOf(begin.x, begin.y + 1),
            this.indexOf(begin.x + 1, begin.y - 1),
            this.indexOf(begin.x + 1, begin.y),
            this.indexOf(begin.x + 1, begin.y + 1),
        )
        for (i in indexes) {
            if (i != -1) {
                result.add(this.land[i])
            }
        }
        return result
    }


    fun discoverAdjacentCells(begin: Cell) {
        this.getAdjacentCells(begin).forEach {
            if (it.hidden && !it.isMine()) {
                it.hidden = false
                if (it.mineCounter == 0)
                    this.discoverAdjacentCells(it)
            }
        }
    }


    fun countMines(begin: Cell) {
        this.getAdjacentCells(begin).forEach {
            if (!it.isMine())
                it.mineCounter += 1
        }
    }

    fun addMines(nmine: Int, begin: Cell) {
        val ncell = this.nCol * this.nLine
        this.nmine = nmine
        repeat(nmine) {
            while (true) {
                val cell = this.land[Random.nextInt(0, ncell)]
                if (begin == cell) {
                    continue
                }
                if (!cell.isMine()) {
                    cell.becomeMine()
                    this.countMines(cell)
                    break
                }
            }
        }
    }


    fun showMines() {
        this.land.forEach {
            if (it.hidden && it.isMine())
                it.hidden = false
        }
    }

    fun discoverCell(begin: Cell) {
        if (!begin.isMine()) {
            begin.hidden = false
            this.discoverAdjacentCells(begin)
        }
    }

    fun playerWin(): Boolean {
        var nMineMarked = 0
        var nOtherMarked = 0
        var nhidden = 0

        this.land.forEach {
            if (it.marked) {
                if (it.isMine()) nMineMarked++ else nOtherMarked++
            } else if (it.hidden && !it.isMine()) {
                nhidden++
            }
        }
        return nOtherMarked == 0 && (nhidden == 0 || nMineMarked == this.nmine)
    }

    fun displayHeaderBoard() {
        println(" │" + (1.rangeTo(this.nCol)).joinToString("") + "│")
        print("—│")
        for (i in 1..this.nCol) print("—")
        println("│")
    }

    fun displayFooterBoard() {
        print("—│")
        for (i in 1..this.nLine) print("—")
        println("│")
    }

    fun displayBoard() {
        this.displayHeaderBoard()
        for (i in 0 until this.nLine) {
            print((i + 1).toString() + '│')
            for (j in 0 until nCol) print(this.land[this.indexOf(i, j)].toDisplay())
            println('│')
        }
        this.displayFooterBoard()
    }


}