/**
  * Created by durrah on 11/5/16.
  */

import scala.xml._
import scala.xml.transform.{RewriteRule, RuleTransformer}

object InlineDsl {
  def main(args: Array[String]): Unit = {


    val xml = <root>
      <a action="remove">element1</a> <a action="edit">element2</a>
    </root>

    val l = (xml \\ "a").map(_.text)
    println(l)//List(element1, element2)

    val removeIt = new RewriteRule {
      override def transform(n: Node): Seq[Node] = n match {
        case e: Elem if (e \ "@action").text == "remove" => NodeSeq.Empty
        case `n` => n
      }
    }

    val newXml = new RuleTransformer(removeIt).transform(xml)

    println(newXml)
    // <root><a action="edit">simple</a></root>
  }

}
