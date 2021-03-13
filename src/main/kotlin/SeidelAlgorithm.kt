object SeidelAlgorithm {

    /**
     * Calculate distance matrix for given [adjacencyMatrix] using the Seidel's algorithm.
     * Uses Strassen's matrix multiplication under the hood.
     */
    fun calculateDistanceMatrix(adjacencyMatrix: IntMatrix): IntMatrix {
        val reachabilityMatrices = calculateReachabilityMatrices(createRelatedStrassenMatrix(adjacencyMatrix))
        var distanceMatrix = reachabilityMatrices.last()
        for (reachabilityMatrix in reachabilityMatrices.reversed()) {
            distanceMatrix = calculatePreviousDistanceMatrix(distanceMatrix, reachabilityMatrix)
        }
        val res = Array(adjacencyMatrix.size) { IntArray(adjacencyMatrix.size) }
        for (i in adjacencyMatrix.indices) {
            for (j in adjacencyMatrix.indices) {
                res[i][j] = distanceMatrix[i, j]
            }
        }
        return res
    }

    /**
     * Calculate previous distance matrix using the multiplication
     * of [currentDistanceMatrix] and [currentAdjacencyMatrix].
     */
    private fun calculatePreviousDistanceMatrix(
        currentDistanceMatrix: StrassenMatrix,
        currentAdjacencyMatrix: StrassenMatrix,
    ): StrassenMatrix {
        val previousDistanceMatrix = StrassenMatrix(currentDistanceMatrix.size)
        val daProd = currentDistanceMatrix * currentAdjacencyMatrix
        for (i in 0 until previousDistanceMatrix.size) {
            for (j in 0 until previousDistanceMatrix.size) {
                previousDistanceMatrix[i, j] = when {
                    i == j -> {
                        0
                    }
                    daProd[i, j] >= currentDistanceMatrix[i, j] * currentAdjacencyMatrix.getRowSum(j) -> {
                        2 * currentDistanceMatrix[i, j]
                    }
                    else -> {
                        2 * currentDistanceMatrix[i, j] - 1
                    }
                }
            }
        }
        return previousDistanceMatrix
    }

    /**
     * Get list of reachability matrices using raise to the power of 2.
     */
    private fun calculateReachabilityMatrices(initialReachabilityMatrix: StrassenMatrix) =
        mutableListOf<StrassenMatrix>().apply {
            add(initialReachabilityMatrix)
            var currentReachabilityMatrix = initialReachabilityMatrix
            var currN = 1
            while (currN < initialReachabilityMatrix.size) {
                currentReachabilityMatrix = truncateValues(currentReachabilityMatrix * currentReachabilityMatrix)
                add(clearDiagonal(currentReachabilityMatrix))
                currN *= 2
            }
        }

    /**
     * Get copy of the [matrix] but with 1 instead of all the positive values.
     */
    private fun truncateValues(matrix: StrassenMatrix): StrassenMatrix {
        val newMatrix = Array(matrix.size) { IntArray(matrix.size) }
        for (i in 0 until matrix.size) {
            for (j in 0 until matrix.size) {
                newMatrix[i][j] = matrix[i, j].coerceAtMost(1)
            }
        }
        return StrassenMatrix(newMatrix)
    }

    /**
     * Get copy of the [matrix] but with 0 set on the main diagonal.
     */
    private fun clearDiagonal(matrix: StrassenMatrix): StrassenMatrix {
        val newMatrix = Array(matrix.size) { IntArray(matrix.size) }
        for (i in 0 until matrix.size) {
            for (j in 0 until matrix.size) {
                newMatrix[i][j] = if (i == j) 0 else matrix[i, j]
            }
        }
        return StrassenMatrix(newMatrix)
    }

    /**
     * Calculate ceiling number for [value] which is the power of 2.
     */
    private fun ceilToPowerOfTwo(value: Int): Int {
        var res = 1
        while (res < value) {
            res *= 2
        }
        return res
    }

    /**
     * Create Strassen matrix of the given [matrix].
     * Strassen matrix means square matrix with [StrassenMatrix.size] equal to a power of two.
     * If the [matrix] size is not a power of two, necessary rows and columns of zeros will be added.
     *
     * Also, `1` will be set at the main diagonal elements.
     */
    private fun createRelatedStrassenMatrix(matrix: IntMatrix): StrassenMatrix {
        val strassenMatrixSize = ceilToPowerOfTwo(matrix.size)
        return StrassenMatrix(Array(strassenMatrixSize) { IntArray(strassenMatrixSize) }).apply {
            for (i in matrix.indices) {
                for (j in matrix.indices) {
                    set(i, j, if (i == j) 1 else matrix[i][j])
                }
            }
            for (i in matrix.size until strassenMatrixSize) {
                set(i, i, 1)
            }
        }
    }
}