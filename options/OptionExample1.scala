object OptionExample1 {

	def checkOption(opt: Option[_]) = {
		/**
		 * this is the long way to check for Some/None
		 */
		opt match {
			case Some(v) => println(v)
			case None => println("The Option had no value!")
		}
	}
	
	def checkOptionShort(opt: Option[_]) = {
		opt.getOrElse("The Option had no value!")
	}

	def main(args:Array[String]) = {
		val aValue = Some("this Option has a value")
		val nothing = None
		
		checkOption(aValue)
		checkOption(None)
		
		checkOptionShort(aValue)
		checkOptionShort(None)
	}
}