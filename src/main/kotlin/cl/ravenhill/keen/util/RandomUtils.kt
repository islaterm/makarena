/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

package cl.ravenhill.keen.util

import org.apache.commons.lang3.RandomStringUtils
import java.util.Random


/**
 * Documentation
 */
fun Random.nextChar() =
    RandomStringUtils.random(1, 0, 0, true, true, null, this).first()