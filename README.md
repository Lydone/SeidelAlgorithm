# Seidel’s shortest path algorithm
## Description
Seidel's algorithm is an algorithm designed by Raimund Seidel in 1992 for the all-pairs-shortest-path problem for undirected, unweighted, connected graphs.

## Implementation
The algorithm was implemented using the [Kotlin](https://kotlinlang.org/) programming language. All the algorithm methods are in the `SeidelAlgorithm.kt` file.

Matrix multiplication was implemented from scratch using the [Strassen's algorithm](https://stanford.edu/~rezab/classes/cme323/S16/notes/Lecture03/cme323_lec3.pdf).
`StrassenMatrix.kt` file stores all the essential methods for matrix operations and Strassen's algorithm.

Check [Kdoc](https://kotlinlang.org/docs/kotlin-doc.html) for more detailed documentation for each method. 

## Compiling and running
Clone the project and open it in the [IntelliJIDEA](https://www.jetbrains.com/idea/). 

* Run `main` method in `main.kt` file in order to launch project. 
* Run `SeidelAlgorithmTest`and `StrassenMatrixTest` in order to run tests.
* One more to run all the test is to launch `test` [Gradle](https://gradle.org/) task.

## GitHub Actions
Automated tests are launched on every push or pull request. These tests include all the tests in `SeidelAlgorithmTest`and `StrassenMatrixTest` files.
