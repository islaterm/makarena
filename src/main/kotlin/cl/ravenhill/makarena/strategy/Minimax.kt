/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */
package cl.ravenhill.makarena.strategy


var player = 'x'
var opponent = 'o'

// This function returns true if there are moves
// remaining on the board. It returns false if
// there are no moves left to play.
fun isMovesLeft(board: Array<CharArray>): Boolean {
    for (i in 0..2) for (j in 0..2) if (board[i][j] == '_') return true
    return false
}

// This is the evaluation function as discussed
// in the previous article ( http://goo.gl/sJgv68 )
fun evaluate(b: Array<CharArray>): Int {
    // Checking for Rows for X or O victory.
    for (row in 0..2) {
        if (b[row][0] == b[row][1] &&
            b[row][1] == b[row][2]
        ) {
            if (b[row][0] == player) return +10 else if (b[row][0] == opponent) return -10
        }
    }

    // Checking for Columns for X or O victory.
    for (col in 0..2) {
        if (b[0][col] == b[1][col] &&
            b[1][col] == b[2][col]
        ) {
            if (b[0][col] == player) return +10 else if (b[0][col] == opponent) return -10
        }
    }

    // Checking for Diagonals for X or O victory.
    if (b[0][0] == b[1][1] && b[1][1] == b[2][2]) {
        if (b[0][0] == player) return +10 else if (b[0][0] == opponent) return -10
    }
    if (b[0][2] == b[1][1] && b[1][1] == b[2][0]) {
        if (b[0][2] == player) return +10 else if (b[0][2] == opponent) return -10
    }

    // Else if none of them have won then return 0
    return 0
}

// This is the minimax function. It considers all
// the possible ways the game can go and returns
// the value of the board
fun minimax(board: Board, depth: Int, isMax: Boolean): Int =
    evaluate(board).let { score ->
        if (score == 10 || score == -10 || !isMovesLeft(board)) {
            score
        } else if (isMax) {
            var best = Int.MIN_VALUE
            board.possibleMoves.forEach {
                board[it.row][it.column] = player
                best = best.coerceAtLeast(minimax(board, depth + 1, false))
                board[it.row][it.column] = '_'
            }
            best
        } else {
            var best = Int.MAX_VALUE
            board.possibleMoves.forEach {
                board[it.row][it.column] = opponent
                best = best.coerceAtMost(minimax(board, depth + 1, true))
                board[it.row][it.column] = '_'
            }
            best
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
    board.possibleMoves.forEach {
        board[it.row][it.column] = player
        it.score = minimax(board, 0, false)
        board[it.row][it.column] = '_'
        if (it.score > bestMove.score) {
            bestMove = it
        }
    }
    return bestMove
}


typealias Board = Array<CharArray>

private val Board.possibleMoves: List<TicTacToeMove>
    get() = mutableListOf<TicTacToeMove>().apply {
        for (i in 0..2) {
            for (j in 0..2) {
                if (this@possibleMoves[i][j] == '_') {
                    this.add(TicTacToeMove(i, j, 0))
                }
            }
        }
    }.toList()
