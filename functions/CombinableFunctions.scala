/**
 * Attempt of a practical example to show how to combine functions with andThen to create a 
 * chain pattern where the same input is optionally processed by different functions.
 */
object CombinableFunctions {

	// This case class will be used as a parameter object to pass around our chain of functions, 
	// instead of having multiple single parameters. Please note that this object will hold internal
	// state
	case class FunctionParameter(file:String, lang:String = "EN", var result:Option[String] = None)

	// Main trait that our chanined functions extend. We could repeat Function1[...] all over the place
	// but it's more neat if we have it as our own type
	trait ChainedFunction extends Function1[FunctionParameter,FunctionParameter]
	
	// Converts the image to a format suitable for OCR
	object ImageConversion extends ChainedFunction {
		def apply(x:FunctionParameter) = {
			println("Converting file: " + x.file)
			x
		}
	}
	
	// Executes the OCR operation
	object ImageRecognition extends ChainedFunction {
		def apply(x:FunctionParameter) = {
			println("Performing OCR on file: " + x.file + ", using language: " + x.lang)
			x.result = Some("these would be the contents of the scanned file")
			x
		}
	}
	
	// Stores the image in some permanent storage after it's been processed
	object ImageStorage extends ChainedFunction {
		def apply(x:FunctionParameter) = {
			println("Storing image in database: " + x.file)
			x
		}
	}

	def main(args:Array[String]) = {		
		// create a function as a chain of the other ones and execute it
		val scanPipeline = ImageConversion andThen ImageRecognition andThen ImageStorage		
		val result = scanPipeline(FunctionParameter("image.jpg", "EN"))
		println("result = " + result)
	}
}