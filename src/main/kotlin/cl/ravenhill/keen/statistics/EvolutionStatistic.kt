/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

package cl.ravenhill.keen.statistics

import io.jenetics.Gene
import io.jenetics.engine.EvolutionResult
import java.util.stream.Stream

interface EvolutionStatistic<T : Comparable<T>> {
    val jenetics: io.jenetics.engine.EvolutionStatistics<T, *>
}

fun <G : Gene<*, G>, C : Comparable<C>> Stream<EvolutionResult<G, C>>.peek(
    statistics: EvolutionStatistic<C>
): Stream<EvolutionResult<G, C>> = this.peek(statistics.jenetics)
