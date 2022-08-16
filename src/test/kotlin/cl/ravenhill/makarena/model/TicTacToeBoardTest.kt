/*
 * "makarena" (c) by Ignacio Slater M.
 * "makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 * work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
 */
package cl.ravenhill.makarena.model

import cl.ravenhill.makarena.MakarenaException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import io.kotest.property.checkAll


class TicTacToeBoardTest : StringSpec({
  "Builder instances are independent" {
    TicTacToeBoard.builder shouldNotBeSameInstanceAs TicTacToeBoard.builder
  }

  "A board with an invalid number of rows should throw an exception" {
    checkAll<Marker, Marker, Marker, Marker, Marker, Marker> { a, b, c, d, e, f ->
      for (rows in 0..2) {
        shouldThrow<MakarenaException> {
          with(TicTacToeBoard.builder) {
            if (rows > 1) {
              row(a, b, c)
            }
            if (rows > 0) {
              row(d, e, f)
            }
            build()
          }
        }
      }
    }
  }
})