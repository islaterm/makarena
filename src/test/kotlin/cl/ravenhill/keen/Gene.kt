/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

package cl.ravenhill.keen

import io.jenetics.Gene as JGene

/**
 * Atom of the _Keen_ library.
 * They contain the actual information (alleles) of the encoded solution.
 *
 * @see [JGene]
 */
interface Gene<DNA, GENE : JGene<DNA, GENE>> {
    /**
     * The _Jenetics_ representation of this gene.
     */
    val jenetics: JGene<DNA, GENE>

    /**
     * Check if this object is valid.
     *
     * @see [JGene.isValid]
     */
    val isValid: Boolean
        get() = jenetics.isValid

    /**
     * Actual value of this gene.
     *
     * @see [JGene.allele]
     */
    val allele: DNA
        get() = jenetics.allele()

    /**
     * Return a new, random gene with the same type and with the same constraints as this gene.
     *
     * @see [JGene.newInstance]
     */
    fun newInstance(): GENE = jenetics.newInstance()

    /**
     * Create a new gene from the given `value` and the gene context.
     *
     * @see [JGene.newInstance]
     */
    fun newInstance(allele: DNA): GENE = jenetics.newInstance(allele)
}