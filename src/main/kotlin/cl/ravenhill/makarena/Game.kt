///*
// * "Makarena" (c) by R8V.
// * "Makarena" is licensed under a
// * Creative Commons Attribution 4.0 International License.
// * You should have received a copy of the license along with this
// *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
// */
//
///**
// * This file provides interfaces to represent games.
// */
//
//package cl.ravenhill.makarena
//
////import cl.ravenhill.makarena.model.Marker
//import java.util.EnumMap
//import kotlin.properties.Delegates
//
//
///** This represents a generic game. */
//interface Game {
//    /** Scoreboard of the game. */
//    val scores: MutableMap<Marker, Int>
//    fun reset() = scores.keys.forEach { scores[it] = 0 }
//}
//
///** This represents a Tic-Tac-Toe game. */
//class TicTacToeGame : Game {
//    override val scores = EnumMap<Marker, Int>(Marker::class.java).apply {
//        put(Marker.X, 0)
//        put(Marker.O, 0)
//    }
//}
