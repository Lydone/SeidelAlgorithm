fun main(args: Array<String>) {
    println(
        SeidelAlgorithm.calculateDistanceMatrix(
            arrayOf(
                intArrayOf(0, 1, 0),
                intArrayOf(1, 0, 0),
                intArrayOf(0, 0, 0),
            ),
        ).contentDeepToString()
    )
}