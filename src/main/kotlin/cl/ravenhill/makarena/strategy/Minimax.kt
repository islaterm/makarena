/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */
package cl.ravenhill.makarena.strategy

import cl.ravenhill.makarena.model.TicTacToeBoard
import kotlin.math.max
import kotlin.math.min


var player = TicTacToeMark.X
var opponent = TicTacToeMark.O

/** This is the evaluation function as discussed in the previous article (http://goo.gl/sJgv68) */
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
    minimax(board, depth, isMax, Int.MIN_VALUE, Int.MAX_VALUE)

fun minimax(board: Board, depth: Int, isMaximizing: Boolean, alpha: Int, beta: Int): Int =
    evaluate(board).let { score ->  // First we evaluate the score of the board
        if (score == 10 || score == -10 || !board.checkMovesLeft()) {
            score   // If the game is over we return the score
        } else {
            var best = (if (isMaximizing) Int.MIN_VALUE else Int.MAX_VALUE)
            var newAlpha = alpha
            var newBeta = beta
            // If we are maximizing: best is lower bound; if we are minimizing: best is upper bound
            (if (isMaximizing) best::coerceAtLeast else best::coerceAtMost).let { bound ->
                board.possibleMoves.forEach {
                    board.simulateMove(it) {
                        best = bound(minimax(board, depth + 1, !isMaximizing))
                        if (isMaximizing) {
                            newAlpha = max(alpha, best)
                        } else {
                            newBeta = min(beta, best)
                        }
                    }
                    if (beta <= newAlpha) {
                        return@forEach
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
    var bestMove: TicTacToeMove = TicTacToeMove.EmptyMove()
    board.possibleMoves.forEach { move ->
        board.simulateMove(move) {
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
