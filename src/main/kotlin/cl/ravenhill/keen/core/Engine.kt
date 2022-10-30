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

package cl.ravenhill.keen.core

class Engine<DNA>(
    private val fitnessFunction: (Genotype<DNA>) -> Number,
    private val genotype: Genotype.GenotypeBuilder<DNA>,
    private val populationSize: Int
) {
    fun createPopulation() = (0 until populationSize).map { genotype.build() }

    class Builder<DNA>(private val fitnessFunction: (Genotype<DNA>) -> Number) {

        var populationSize: Int = 50

        lateinit var genotype: Genotype.GenotypeBuilder<DNA>

        fun build() = Engine(fitnessFunction, genotype, populationSize)
    }
}