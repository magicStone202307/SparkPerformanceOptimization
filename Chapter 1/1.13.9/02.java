// 导入 ScalaTest 中的 AnyFunSuite 和 ScalaCheckDrivenPropertyChecks
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks
import StringSpec._

// 定义一个名为 StringSuite 的测试类，它继承了 AnyFunSuite 和 ScalaCheckDrivenPropertyChecks
class StringSuite extends AnyFunSuite with ScalaCheckDrivenPropertyChecks {
  // 定义一个名为 "concatenation length" 的测试，使用了 ScalaCheck 进行参数化测试
  test("concatenation length") {
    forAll { (a: String, b: String) =>
      // 调用 StringSpec 中的属性测试，并使用 assert 断言测试结果
      assert((a + b).length == a.length + b.length)
    }
  }
}