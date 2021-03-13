typealias IntMatrix = Array<IntArray>

/**
 * Square matrix with [size] equal to a power of two where [StrassenMatrix.times] operation implemented with
 * the Strassen's algorithm.
 *
 * @property matrix Content of the matrix.
 */
class StrassenMatrix(private val matrix: IntMatrix) {

    constructor(size: Int) : this(Array(size) { IntArray(size) })

    init {
        assertSizeIsPowerOfTwo()
    }

    /**
     * Size of this matrix.
     */
    val size get() = matrix.size

    /**
     * Get the sum of numbers in the row at [rowIndex] position.
     */
    fun getRowSum(rowIndex: Int) = matrix[rowIndex].sum()

    /**
     * Get the number at [i] row and [j] column positions.
     */
    operator fun get(i: Int, j: Int) = matrix[i][j]

    /**
     * Set the number [value] at [i] row and [j] column positions.
     */
    operator fun set(i: Int, j: Int, value: Int) {
        matrix[i][j] = value
    }

    /**
     * Matrix addition operation. Asymptotic complexity is O([size]^2).
     */
    operator fun plus(other: StrassenMatrix): StrassenMatrix {
        if (size != other.size) {
            throw IllegalArgumentException("Incompatible matrices sizes $size and ${other.size}")
        }
        val newMatrix = Array(size) { IntArray(size) }
        for (i in 0 until size) {
            for (j in 0 until size) {
                newMatrix[i][j] = get(i, j) + other[i, j]
            }
        }
        return StrassenMatrix(newMatrix)
    }

    /**
     * Matrix subtraction operation. Asymptotic complexity is O([size]^2).
     */
    operator fun minus(other: StrassenMatrix): StrassenMatrix {
        if (size != other.size) {
            throw IllegalArgumentException("Incompatible matrices sizes $size and ${other.size}")
        }
        val newMatrix = Array(size) { IntArray(size) }
        for (i in 0 until size) {
            for (j in 0 until size) {
                newMatrix[i][j] = get(i, j) - other[i, j]
            }
        }
        return StrassenMatrix(newMatrix)
    }

    /**
     * Matrix multiplication operation using the Strassen divide and conquer algorithm.
     * Asymptotic complexity is O([size]^2.8).
     */
    operator fun times(other: StrassenMatrix): StrassenMatrix {
        if (size != other.size) {
            throw IllegalArgumentException("Incompatible matrices sizes $size and ${other.size}")
        }
        if (size == 1) {
            return StrassenMatrix(arrayOf(intArrayOf(get(0, 0) * other[0, 0])))
        }
        val aTopLeft = extractTopLeftQuarter()
        val aTopRight = extractTopRightQuarter()
        val aBottomLeft = extractBottomLeftQuarter()
        val aBottomRight = extractBottomRightQuarter()
        val bTopLeft = other.extractTopLeftQuarter()
        val bTopRight = other.extractTopRightQuarter()
        val bBottomLeft = other.extractBottomLeftQuarter()
        val bBottomRight = other.extractBottomRightQuarter()
        val m1 = (aTopLeft + aBottomRight) * (bTopLeft + bBottomRight)
        val m2 = (aBottomLeft + aBottomRight) * bTopLeft
        val m3 = aTopLeft * (bTopRight - bBottomRight)
        val m4 = aBottomRight * (bBottomLeft - bTopLeft)
        val m5 = (aTopLeft + aTopRight) * bBottomRight
        val m6 = (aBottomLeft - aTopLeft) * (bTopLeft + bTopRight)
        val m7 = (aTopRight - aBottomRight) * (bBottomLeft + bBottomRight)
        return createByMergingQuarters(
            topLeft = m1 + m4 - m5 + m7,
            topRight = m3 + m5,
            bottomLeft = m2 + m4,
            bottomRight = m1 - m2 + m3 + m6,
        )
    }

    override fun equals(other: Any?): Boolean {
        return if (this === other) {
            true
        } else {
            (other as? StrassenMatrix)?.let { otherMatrix ->
                matrix contentDeepEquals otherMatrix.matrix
            } ?: false
        }
    }

    override fun hashCode() = matrix.contentDeepHashCode()

    override fun toString() = matrix.contentDeepToString()

    /**
     * Check whether the [size] is a power of two or not.
     *
     * @throws IllegalArgumentException if the [size] is not a power of 2.
     */
    private fun assertSizeIsPowerOfTwo() {
        var currSize = size
        if (currSize == 0) throw IllegalArgumentException("Size $size is not a power of 2")
        while (currSize != 1) {
            if (currSize % 2 != 0) {
                throw IllegalArgumentException("Size $size is not a power of 2")
            }
            currSize /= 2
        }
    }

    /**
     * Extract top left quarter of this matrix and return it as a new object.
     */
    private fun extractTopLeftQuarter() = (size / 2).let { quarterSize ->
        StrassenMatrix(
            matrix.sliceArray(0 until quarterSize).map { row ->
                row.sliceArray(0 until quarterSize)
            }.toTypedArray()
        )
    }

    /**
     * Extract top right quarter of this matrix and return it as a new object.
     */
    private fun extractTopRightQuarter() = (size / 2).let { quarterSize ->
        StrassenMatrix(
            matrix.sliceArray(0 until quarterSize).map { row ->
                row.sliceArray(quarterSize until size)
            }.toTypedArray()
        )
    }

    /**
     * Extract bottom left quarter of this matrix and return it as a new object.
     */
    private fun extractBottomLeftQuarter() = (size / 2).let { quarterSize ->
        StrassenMatrix(
            matrix.sliceArray(quarterSize until size).map { row ->
                row.sliceArray(0 until quarterSize)
            }.toTypedArray()
        )
    }

    /**
     * Extract bottom right quarter of this matrix and return it as a new object.
     */
    private fun extractBottomRightQuarter() = (size / 2).let { quarterSize ->
        StrassenMatrix(
            matrix.sliceArray(quarterSize until size).map { row ->
                row.sliceArray(quarterSize until size)
            }.toTypedArray()
        )
    }

    /**
     * Merge [quarter] into this matrix. The top left position is [startI] row and [startJ] column.
     */
    private fun mergeQuarter(quarter: StrassenMatrix, startI: Int, startJ: Int) {
        for (i in 0 until quarter.size) {
            for (j in 0 until quarter.size) {
                set(i + startI, j + startJ, quarter[i, j])
            }
        }
    }


    companion object {

        /**
         * Create matrix consisting of quarters [topLeft], [topRight], [bottomLeft], [bottomRight].
         */
        fun createByMergingQuarters(
            topLeft: StrassenMatrix,
            topRight: StrassenMatrix,
            bottomLeft: StrassenMatrix,
            bottomRight: StrassenMatrix,
        ) = StrassenMatrix(topLeft.size * 2).apply {
            mergeQuarter(topLeft, 0, 0)
            mergeQuarter(topRight, 0, topLeft.size)
            mergeQuarter(bottomLeft, topLeft.size, 0)
            mergeQuarter(bottomRight, topLeft.size, topLeft.size)
        }
    }
}