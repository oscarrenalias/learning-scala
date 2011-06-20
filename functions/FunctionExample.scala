object FunctionExample {

	// function defined as a class method
	def function1(x:String) = "function 1: " + x
		
	// function defined as a function object
	object function2 extends Function1[String,String] {
		def apply(x:String) = "function 2: " +  x
	}
		
	def main(args:Array[String]) = {
			
		// function defined as a function literal
		val function3 = (x:String) => "function 3: " + x
		
		println(function1("parameter"))
		println(function2("parameter"))
		println(function3("parameter"))
	}
}