/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */
package cl.ravenhill.makarena.strategy

import cl.ravenhill.makarena.model.TicTacToeBoard


var player = 'X'
var opponent = 'O'

/**
 * Checks if a TicTacToe board is full.
 *
 * @return `true` if the board is not full, `false` otherwise.
 */
//private fun Board.checkMovesLeft() =
//    this.any { row -> row.any { mark -> mark == '_' } }

/** Extension property to determine if the game of TicTacToe has a winner. */
//private val Board.winner: Char
//    get() {
//        val searchWinnerIn = { l: Board ->
//            try {
//                l.first { it.allEqual() && it.first() != '_' }.first()
//            } catch (e: NoSuchElementException) {
//                '_'
//            }
//        }
//        return searchWinnerIn(this).takeIf { it != '_' }
//            ?: searchWinnerIn(this.columns).takeIf { it != '_' }
//            ?: searchWinnerIn(this.diagonals)
//    }

// This is the evaluation function as discussed
// in the previous article ( http://goo.gl/sJgv68 )
fun evaluate(b: Board) = when (b.winner) {
    player -> 10
    opponent -> -10
    else -> 0
}

/**
 * Recursively evaluates all possible moves and returns the score of the best move.
 *
 * @param board The board to evaluate
 * @param depth The depth of the tree to evaluate
 * @param isMax True if the current player is the maximizer, false otherwise
 *
 * @return The score of the best move
 */
fun minimax(board: Board, depth: Int, isMax: Boolean): Int =
    evaluate(board).let { score ->  // First we evaluate the score of the board
        if (score == 10 || score == -10 || !board.checkMovesLeft()) {
            score   // If the game is over we return the score
        } else {
            var best = (if (isMax) Int.MIN_VALUE else Int.MAX_VALUE)
            // If we are maximizing: best is lower bound; if we are minimizing: best is upper bound
            (if (isMax) best::coerceAtLeast else best::coerceAtMost).let { bound ->
                board.possibleMoves.forEach {
                    board.simulateMove(it, if (isMax) player else opponent) {
                        best = bound(minimax(board, depth + 1, !isMax))
                    }
                }
                best
            }
        }
    }

/**
 * Finds the best move for the player on a Tic Tac Toe board.
 *
 * @param board The board to evaluate.
 * @return The best move for the player.
 */
fun findBestMove(board: Board): Move {
    var bestMove = TicTacToeMove(-1, -1, Int.MIN_VALUE)
    board.possibleMoves.forEach { move ->
        board.simulateMove(move, player) {
            it.score = minimax(board, 0, false)
            if (it.score > bestMove.score) {
                bestMove = it
            }
        }
    }
    return bestMove
}

/** Representation of a 2D Tic Tac Toe board. */
typealias Board = TicTacToeBoard
