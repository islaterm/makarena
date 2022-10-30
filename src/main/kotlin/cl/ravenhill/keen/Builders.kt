/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

package cl.ravenhill.keen

import cl.ravenhill.keen.core.Engine
import cl.ravenhill.keen.core.Genotype

object Builders {

    fun <DNA> engine(
        fitnessFunction: (Genotype<DNA>) -> Number,
        init: Engine.Builder<DNA>.() -> Unit
    ) =
        Engine.Builder(fitnessFunction).apply(init).build()

    fun <DNA> genotype(init: Genotype.GenotypeBuilder<DNA>.() -> Unit) =
        Genotype.GenotypeBuilder<DNA>().apply(init)

}