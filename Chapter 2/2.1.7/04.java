val numbers = Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).par
val evenNumbers = numbers.filter(_ % 2 == 0)
val squaredNumbers = evenNumbers.map(num => num * num)
val sum = squaredNumbers.reduce(_ + _)

println("Sum of squares of even numbers: " + sum)