import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test

import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class SeidelAlgorithmTest {

    @Test
    fun calculateDistanceMatrix() {
        assertArrayEquals(
            arrayOf(
                intArrayOf(0, 1, 1, 1),
                intArrayOf(1, 0, 1, 1),
                intArrayOf(1, 1, 0, 1),
                intArrayOf(1, 1, 1, 0),
            ),
            SeidelAlgorithm.calculateDistanceMatrix(
                arrayOf(
                    intArrayOf(0, 1, 1, 1),
                    intArrayOf(1, 0, 1, 1),
                    intArrayOf(1, 1, 0, 1),
                    intArrayOf(1, 1, 1, 0),
                )
            )
        )
        assertArrayEquals(
            arrayOf(
                intArrayOf(0, 0, 0, 0),
                intArrayOf(0, 0, 0, 0),
                intArrayOf(0, 0, 0, 0),
                intArrayOf(0, 0, 0, 0),
            ),
            SeidelAlgorithm.calculateDistanceMatrix(
                arrayOf(
                    intArrayOf(0, 0, 0, 0),
                    intArrayOf(0, 0, 0, 0),
                    intArrayOf(0, 0, 0, 0),
                    intArrayOf(0, 0, 0, 0),
                )
            )
        )
        assertArrayEquals(
            arrayOf(intArrayOf(0)),
            SeidelAlgorithm.calculateDistanceMatrix(arrayOf(intArrayOf(0)))
        )
        assertArrayEquals(
            arrayOf(
                intArrayOf(0, 1),
                intArrayOf(1, 0)
            ),
            SeidelAlgorithm.calculateDistanceMatrix(
                arrayOf(
                    intArrayOf(0, 1),
                    intArrayOf(1, 0)
                )
            )
        )
        assertArrayEquals(
            arrayOf(
                intArrayOf(0, 0),
                intArrayOf(0, 0)
            ),
            SeidelAlgorithm.calculateDistanceMatrix(
                arrayOf(
                    intArrayOf(0, 0),
                    intArrayOf(0, 0)
                )
            )
        )
    }
}