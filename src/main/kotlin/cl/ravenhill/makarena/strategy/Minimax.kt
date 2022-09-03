package cl.ravenhill.makarena.strategy

import cl.ravenhill.makarena.TicTacToeGame
import cl.ravenhill.makarena.model.Marker
import cl.ravenhill.makarena.model.MutableList2D
import cl.ravenhill.makarena.model.player

private val MutableList2D<Marker>.diagonals: MutableList2D<Marker>
  get() = mutableListOf(
    MutableList(size) { i -> this[i][i] },
    MutableList(size) { i -> this[i][size - 1 - i] }
  )

private val MutableList2D<Marker>.columns: MutableList2D<Marker>
  get() = MutableList(size) { i -> MutableList(size) { j -> this[j][i] } }

private val MutableList2D<Marker>.winner: Marker
  get() {
    val searchWinnerIn = { l: MutableList2D<Marker> ->
      try {
        l.first { it.allEqual() && it.first() != Marker.EMPTY }.first()
      } catch (e: NoSuchElementException) {
        Marker.EMPTY
      }
    }
    return searchWinnerIn(this).takeIf { it != Marker.EMPTY }
      ?: searchWinnerIn(this.columns).takeIf { it != Marker.EMPTY }
      ?: searchWinnerIn(this.diagonals)
  }

private fun <T> List<T>.allEqual() = this.all { it == this[0] }
private fun <T> MutableList2D<T>.checkMovesLeft() =
  this.any { row -> row.any { mark -> mark == Marker.EMPTY } }

// This is the evaluation function as discussed
// in the previous article ( http://goo.gl/sJgv68 )
fun evaluate(b: MutableList2D<Marker>) = when (b.winner) {
  Marker.X -> 10
  Marker.O -> -10
  else -> 0
}

private fun Int.matchPredicate(predicate: (Int) -> Boolean) = predicate(this)

// This is the minimax function. It considers all
// the possible ways the game can go and returns
// the value of the board
fun minimax(
  board: MutableList2D<Marker>,
  depth: Int, isMax: Boolean
): Int = when {
  evaluate(board).matchPredicate { it == 10 || it == -10 } -> TicTacToeGame.scores[player]!!
  !board.checkMovesLeft() -> 0
  else -> {
    val moves = board.possibleMoves()
    var best = if (isMax) Int.MIN_VALUE else Int.MAX_VALUE
    val coerceFn = if (isMax) best::coerceAtLeast else best::coerceAtMost
    for (move in moves) {
      board[move.first][move.second] = if (isMax) Marker.X else Marker.O
      val value = minimax(board, depth + 1, !isMax)
      best = coerceFn(value)
      board[move.first][move.second] = Marker.EMPTY
    }
    best
  }
}

private fun MutableList2D<Marker>.possibleMoves() = mutableListOf<Pair<Int, Int>>().apply {
  val size = this@possibleMoves.size
  for (i in 0 until size) {
    for (j in 0 until size) {
      if (this@possibleMoves[i][j] == Marker.EMPTY) {
        this.add(Pair(i, j))
      }
    }
  }
}

// This will return the best possible
// move for the player
fun findBestMove(board: MutableList2D<Marker>): Move {
  var bestMove = TicTacToeMove(-1, -1, Int.MIN_VALUE)

  // Traverse all cells, evaluate minimax function
  // for all empty cells. And return the cell
  // with optimal value.
  for (i in 0..2) {
    for (j in 0..2) {
      // Check if cell is empty
      if (board[i][j] == Marker.EMPTY) {
        // Make the move
        board[i][j] = player

        // compute evaluation function for this
        // move.
        val moveVal = minimax(board, 0, false)

        // Undo the move
        board[i][j] = Marker.EMPTY

        // If the value of the current move is
        // more than the best value, then update
        // best/
        if (moveVal > bestMove.score) {
          bestMove = TicTacToeMove(i, j, moveVal)
        }
      }
    }
  }
  return bestMove
}
