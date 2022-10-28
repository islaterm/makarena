/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

package cl.ravenhill.keen.statistics

import io.jenetics.engine.EvolutionStatistics

class IntStatistic : EvolutionStatistic<Int> {
    override val jenetics: EvolutionStatistics<Int, *> = EvolutionStatistics.ofNumber()
    override fun toString() = jenetics.toString()
}
