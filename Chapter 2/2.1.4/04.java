// 递归实现阶乘
def factorialRecursive(n: Int): Int = {
  if (n == 0) 1
  else n * factorialRecursive(n - 1)
}

// 尾递归优化的阶乘实现
def factorialTailRecursive(n: Int): Int = {
  def factorialHelper(n: Int, acc: Int): Int = {
    if (n == 0) acc
    else factorialHelper(n - 1, acc * n)
  }
  factorialHelper(n, 1)
}

// 调用递归阶乘函数
val result1 = factorialRecursive(5)

// 调用尾递归优化的阶乘函数
val result2 = factorialTailRecursive(5)