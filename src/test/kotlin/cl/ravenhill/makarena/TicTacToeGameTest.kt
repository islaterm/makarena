///*
// * "Makarena" (c) by R8V.
// * "Makarena" is licensed under a
// * Creative Commons Attribution 4.0 International License.
// * You should have received a copy of the license along with this
// *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
// */
//package cl.ravenhill.makarena
//
//import cl.ravenhill.makarena.model.Marker
//import io.kotest.core.spec.style.StringSpec
//import io.kotest.matchers.shouldBe
//import io.kotest.property.Exhaustive
//import io.kotest.property.checkAll
//import io.kotest.property.exhaustive.enum
//
///**
// * Test suite for a game of Tic Tac Toe.
// *
// * @author <a href="https://github.com/r8vnhill">R8V</a>
// * @see TicTacToeGame
// */
//class TicTacToeGameTest : StringSpec({
//    beforeAny {
//        TicTacToeGame.reset()
//    }
//
//    "All players should have a score of 0 at the beginning of the game" {
//        TicTacToeGame.scores.values.forEach {
//            it shouldBe 0
//        }
//    }
//
//    "The game starts with players X and O" {
//        checkAll(Exhaustive.enum<Marker>()) {
//            TicTacToeGame.scores.containsKey(it)
//        }
//    }
//})