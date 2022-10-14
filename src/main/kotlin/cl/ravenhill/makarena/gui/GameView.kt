/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

@file:Suppress("FunctionName")

package cl.ravenhill.makarena.gui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import cl.ravenhill.makarena.driver.exceptions.InvalidMoveException
import cl.ravenhill.makarena.driver.ttt.TicTacToeGame
import cl.ravenhill.makarena.driver.ttt.TicTacToeMark
import cl.ravenhill.makarena.driver.ttt.TicTacToeMark.Empty

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Compose for Desktop",
        state = rememberWindowState(width = 300.dp, height = 300.dp)
    ) {
        val cells: SnapshotStateList<TicTacToeMark> = remember {
            mutableStateListOf(
                Empty, Empty, Empty,
                Empty, Empty, Empty,
                Empty, Empty, Empty
            )
        }
        MaterialTheme {
            Row {
                Column {
                    CellButton(cells, 0, 0)
                    CellButton(cells, 0, 1)
                    CellButton(cells, 0, 2)
                }
                Column {
                    CellButton(cells, 1, 0)
                    CellButton(cells, 1, 1)
                    CellButton(cells, 1, 2)
                }
                Column {
                    CellButton(cells, 2, 0)
                    CellButton(cells, 2, 1)
                    CellButton(cells, 2, 2)
                }
            }
        }
    }
}

@Composable
fun CellButton(cells: SnapshotStateList<TicTacToeMark>, i: Int, j: Int) {
    Button(
        modifier = Modifier
            .padding(5.dp)
            .height(50.dp)
            .width(50.dp),
        onClick = {
            val player = TicTacToeGame.player
            try {
                TicTacToeGame.move(i, j)
                cells[i * 3 + j] = player
            } catch (e: InvalidMoveException) {
                println(e.localizedMessage) // TODO: Change for logger
            }
        }) {
        Text("${cells[i * 3 + j]}", fontSize = 30.sp)
    }
}
