/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */
package cl.ravenhill.keen.util

import io.jenetics.util.Self

/**
 * This interface defines a __*recursive*__ generic type `S`, which represents the type of the
 * implementing class.
 * ```
 *  interface Foo<T extends Foo<T>> extends Self<T> {
 *      // ...
 *  }
 * ```
 * Using the `Self` interface in this case makes it clear that the generic type `T` of the
 * interface `Foo` represents the concrete type of the class, implementing the interface `Foo`.
 *
 * If the interface is used as intended, the following generic `min` method can be implemented as a
 * __*default*__ method.
 * ```
 *  interface Foo<A extends Foo<A>> extends Self<A>, Comparable<A> {
 *      // ...
 *
 *      default A max(final A other) {
 *          return compareTo(other) > 0 ? self() : other;
 *      }
 * }
 * ```
 *
 * @param S the type of the implementing class.
 *
 * @author <a href="https://www.github.com/r8vnhill">R8V</a>
 */
interface Self<S : Self<S>?> {
    /**
     * Return a reference of `this` object as the declared generic type `S`.
     *
     * @return the `this` reference as the generic type `S`
     * @throws ClassCastException if the interface is not used as intended and
     * `(this instanceof S) == false`
     */
    @Suppress("UNCHECKED_CAST")
    fun self() = this as S
}