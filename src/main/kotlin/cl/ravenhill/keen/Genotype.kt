/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

package cl.ravenhill.keen

import cl.ravenhill.keen.chromosomes.Chromosome
import io.jenetics.Gene
import io.jenetics.Genotype as JGenotype

class Genotype<T : Gene<*, T>>(vararg val chromosomes: Chromosome<T>) {
    var jenetics: JGenotype<T> = JGenotype.of(chromosomes.map { it.jenetics }.toMutableList())
        private set

    val chromosome = chromosomes[0]

    constructor(jenetics: JGenotype<T>) : this() {
        this.jenetics = jenetics
    }

    override fun toString() = jenetics.toString()
}

val <G : Gene<*, G>> JGenotype<G>.keen: Genotype<G>
    get() {
        return Genotype(this)
    }