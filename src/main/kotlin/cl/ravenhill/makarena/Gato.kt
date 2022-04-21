package cl.ravenhill.makarena

import kotlin.properties.Delegates

enum class Marker {
  X, O, EMPTY;

  override fun toString(): String {
    return if (this == EMPTY) " " else super.toString()
  }
}

private typealias MarkerRow = Triple<Marker, Marker, Marker>
typealias MarkerBoard = Triple<MarkerRow, MarkerRow, MarkerRow>


class Board(
  c00: Marker = Marker.EMPTY,
  c01: Marker = Marker.EMPTY,
  c02: Marker = Marker.EMPTY,
  c10: Marker = Marker.EMPTY,
  c11: Marker = Marker.EMPTY,
  c12: Marker = Marker.EMPTY,
  c20: Marker = Marker.EMPTY,
  c21: Marker = Marker.EMPTY,
  c22: Marker = Marker.EMPTY
) {
  private val state = arrayOf(arrayOf(c00, c01, c02), arrayOf(c10, c11, c12), arrayOf(c20, c21, c22))
  private val subscribers = mutableListOf<GameObserver>()

  var winner: Marker? by Delegates.observable(null) { _, _, newValue ->
    subscribers.parallelStream().forEach { it.notifyWinner(newValue) }
  }

  fun addObserver(observer: GameObserver) {
    subscribers.add(observer)
  }

  fun move(marker: Marker, x: Int, y: Int) {
    state[x][y] = marker
    checkStatus()
  }

  fun checkStatus() {
    for (i in 0 until 3) {
      checkHorizontal()
      checkVertical()
      checkDiagonal()
    }
  }

  private fun checkDiagonal() {
    if ((state[0][0] != Marker.EMPTY && state[0][0] == state[1][1] && state[1][1] == state[2][2])
      || (state[2][0] != Marker.EMPTY && state[2][0] == state[1][1] && state[1][1] == state[0][2])
    ) {
      winner = state[1][1]
    }
  }

  private fun checkHorizontal() {
    for (i in 0 until 3) {
      if (state[i][0] != Marker.EMPTY && state[i][0] == state[i][1] && state[i][1] == state[i][2]) {
        winner = state[i][0]
      }
    }
  }

  private fun checkVertical() {
    for (i in 0 until 3) {
      if (state[0][i] != Marker.EMPTY && state[0][i] == state[1][i] && state[1][i] == state[2][i]) {
        winner = state[0][i]
      }
    }
  }

  override fun toString() = """
     ${state[0][0]} | ${state[0][1]} | ${state[0][2]} 
    ---|---|---
     ${state[1][0]} | ${state[1][1]} | ${state[1][2]} 
    ---|---|---
     ${state[2][0]} | ${state[2][1]} | ${state[2][2]} 
    """
}

class EndGame(val winner: Marker) : Throwable()

fun main() {
  val board = Board()
  board.addObserver(GameObserver())
  board.move(Marker.X, 0, 0)
  board.move(Marker.O, 1, 1)
  board.move(Marker.X, 0, 1)
  board.move(Marker.O, 2, 2)
  board.move(Marker.X, 1, 0)
  board.move(Marker.O, 2, 1)
  board.move(Marker.X, 2, 0)
  board.move(Marker.O, 0, 2)
  board.move(Marker.X, 1, 2)
  board.move(Marker.O, 2, 0)
}

