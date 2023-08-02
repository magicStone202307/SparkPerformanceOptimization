val numbers = Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
var sum = 0

for (num <- numbers) {
  if (num % 2 == 0) {
    sum += num * num
  }
}

println("Sum of squares of even numbers: " + sum)