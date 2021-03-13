import org.junit.jupiter.api.Test

import kotlin.test.assertEquals

internal class StrassenMatrixTest {

    @Test
    fun get() {
    }

    @Test
    fun set() {
    }

    @Test
    fun plus() {
    }

    @Test
    fun minus() {
    }

    @Test
    fun times() {
        assertEquals(
            expected = StrassenMatrix(arrayOf(intArrayOf(6))),
            actual = StrassenMatrix(arrayOf(intArrayOf(3))) * StrassenMatrix(arrayOf(intArrayOf(2)))
        )
        val a = StrassenMatrix(
            arrayOf(
                intArrayOf(1, 2, 3, 4),
                intArrayOf(4, 3, 0, 1),
                intArrayOf(5, 6, 1, 1),
                intArrayOf(0, 2, 5, 6),
            )
        )
        val b = StrassenMatrix(
            arrayOf(
                intArrayOf(1, 0, 5, 1),
                intArrayOf(1, 2, 0, 2),
                intArrayOf(0, 3, 2, 3),
                intArrayOf(1, 2, 1, 2),
            )
        )
        assertEquals(
            expected = StrassenMatrix(
                arrayOf(
                    intArrayOf(7, 21, 15, 22),
                    intArrayOf(8, 8, 21, 12),
                    intArrayOf(12, 17, 28, 22),
                    intArrayOf(8, 31, 16, 31),
                )
            ),
            actual = a * b
        )
        assertEquals(
            expected = StrassenMatrix(
                arrayOf(
                    intArrayOf(-83, -177, 1516, 1142),
                    intArrayOf(410, 390, 2285, 3042),
                    intArrayOf(77, 72, -592, -189),
                    intArrayOf(129, 276, -510, -214)
                )
            ),
            actual = StrassenMatrix(
                arrayOf(
                    intArrayOf(23, -43, 3, 3),
                    intArrayOf(47, 57, 2, 0),
                    intArrayOf(-4, -5, -7, 2),
                    intArrayOf(-5, 5, -4, 3)
                )
            ) * StrassenMatrix(
                arrayOf(
                    intArrayOf(3, -3, 51, 59),
                    intArrayOf(5, 9, -4, 5),
                    intArrayOf(-8, 9, 58, -8),
                    intArrayOf(29, 84, -1, 8)
                )
            )
        )
    }
}