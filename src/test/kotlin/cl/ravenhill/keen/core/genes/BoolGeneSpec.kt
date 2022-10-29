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

/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

package cl.ravenhill.keen.core.genes

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe


class BoolGeneSpec : WordSpec({
    "A BoolGene" should {
        "be valid" {
            BoolGene.True.verify() shouldBe true
            BoolGene.False.verify() shouldBe true
        }

        "be convertible to boolean" {
            BoolGene.True.toBool() shouldBe true
            BoolGene.False.toBool() shouldBe false
        }

        "be convertible to integer" {
            BoolGene.True.toInt() shouldBe 1
            BoolGene.False.toInt() shouldBe 0
        }
    }
})