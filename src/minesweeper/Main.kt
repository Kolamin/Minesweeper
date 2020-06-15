package minesweeper

import java.util.*


fun main(args: Array<String>) {
    val sc = Scanner(System.`in`)
    print("How many mines do you want on the field? > ")
    val mine = sc.nextInt()
    val myList = ArrayList<String?>()
    val size = 81
    for (i in 0 until size) {
        if (i < mine) {
            myList.add(i, "X")
        } else {
            myList.add(i, ".")
        }
    }
    val mtrMine = Array(9) { arrayOfNulls<String>(9) }
    val arrayOfNearbyMines = Array(9) { IntArray(9) }
    myList.shuffle()
    val strings = myList.toTypedArray()
    var index = 0
    for (i in 0..8) {
        for (j in 0..8) {
            mtrMine[i][j] = strings[index++]
        }
    }

    countMine(mtrMine, arrayOfNearbyMines)

    for (i in 0..8) {
        for (j in 0..8) {
            if (mtrMine[i][j] == "X") {
                continue
            } else {
                mtrMine[i][j] = arrayOfNearbyMines[i][j].toString()
            }
        }
    }
    for (i in 0..8) {
        for (j in 0..8) {
            if (mtrMine[i][j] == "0" || mtrMine[i][j] == "X") {
                mtrMine[i][j] = "."
            }
        }
    }
    makeBoard(mtrMine)

    var checkOut = true

    while (checkOut){
        print("Set/delete mines marks (x and y coordinates): > ")
        var x: Int = sc.nextInt()-1
        var y: Int = sc.nextInt()-1

        when {
            arrayOfNearbyMines[y][x] > 0 -> {
                println("There is a number here!")
            }
            mtrMine[y][x].equals(".") -> {
                mtrMine[y][x] = "*"
                makeBoard(mtrMine)
            }
            else -> {
                mtrMine[y][x] = "."
                makeBoard(mtrMine)
            }
        }
    }
}

fun countMine(mtrMine: Array<Array<String?>>, arrayOfNearbyMines: Array<IntArray>) {
    for (i in 0..8) {
        for (j in 0..8) {
            if (mtrMine[i][j] == "X") continue
            for (dX in -1..1) {
                for (dY in -1..1) {
                    var nX = i + dX
                    var nY = j + dY
                    nX = if (nX < 0) {
                        continue
                    } else {
                        nX
                    }
                    nY = if (nY < 0) {
                        continue
                    } else {
                        nY
                    }
                    nX = if (nX > 9 - 1) {
                        continue
                    } else nX
                    nY = if (nY > 9 - 1) {
                        continue
                    } else {
                        nY
                    }
                    if (mtrMine[i][j] == ".") {
                        arrayOfNearbyMines[i][j] += if (mtrMine[nX][nY] == "X") 1 else 0
                    }
                }
            }
        }
    }
}

fun makeBoard(array: Array<Array<String?>>) {
    println(" │123456789│")
    println("—│—————————│")
    var index = 1
    for (i in 0..8) {
        println("${index++}|${array[i].joinToString("")}|")
    }
    println("—│—————————│")
}