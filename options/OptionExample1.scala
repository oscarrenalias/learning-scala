/**
 * Basic example of how to deal with Scala's Option type
 *
 * http://www.scala-lang.org/api/current/index.html#scala.Option
 */
object OptionExample1 {

	def getFileExt(fileName:String): Option[String] = fileName.lastIndexOf(".") match {
		case -1 => None
		case i => Some(fileName.substring(i+1, fileName.length))
	}

	def main(args:Array[String]) = {
		val aValue = Some("this Option has a value")
		val nothing = None
		
		// long way to check for the contents of an Option
		aValue match {
			case Some(v) => println("The value is: " + v)
			case _ => println("There was on value")
		}

		// shorter way
		println(aValue.getOrElse("There was no value, again"))
		println(nothing.getOrElse("There was no value, again"))

		// how to operate with the contents of an Option without extracting the value, and only if the value
		// is not None
		aValue.map(v => println("The value is: " + v))		

		// how to deal with functions that return Options
		getFileExt("myFile.txt").map { v =>
			println("Extension is: " + v)
			// ... all other operations that depend on the existence of the file extension go here
		}

		// ...more to come
	}
}