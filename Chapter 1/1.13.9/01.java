// 导入 ScalaCheck 的 Properties 和 forAll 方法
import org.scalacheck.Properties
import org.scalacheck.Prop.forAll

// 定义一个名为 StringSpec 的属性测试，针对的是 String 类型
object StringSpec extends Properties("String") {
  // 定义一个属性，对于任意两个字符串 a 和 b，它们的连接长度等于它们的长度之和
  property("concatenation length") = forAll { (a: String, b: String) =>
    (a + b).length == a.length + b.length
  }
}