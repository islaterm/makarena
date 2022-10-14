/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

package cl.ravenhill.makarena.gui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
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
            Column(Modifier.fillMaxSize(), Arrangement.spacedBy(5.dp)) {
                Button(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(5.dp)
                        .height(50.dp)
                        .width(50.dp),
                    onClick = {
                        cells[0] = TicTacToeGame.player
                        TicTacToeGame.move(0, 0)
                    }) {
                    Text("${cells[0]}", fontSize = 30.sp)
                }
            }
        }
    }
}
