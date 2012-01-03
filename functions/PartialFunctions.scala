/**
 * An example of combinable partial functions that implements a very simple HTTP request handler.
 *
 * Each one of the different "routes"/URLs of the server is implemented in a handler as a partial function that
 * processes it.
 *
 * The advantage is that instead of one large if-else chain, we've got one function per handler that can be combined
 * in multiple ways to handle the routes
 */
object PartialFunctions {
	// Route could be defined as a real class but here we use a type alias
	type Route = PartialFunction[HttpRequest, HttpResponse]	

	// case classes provide extractors for pattern matching automatically, will be used later below to
	// deconstruct input parameters and extract the information that we need from the request
	case class HttpRequest(path:String, params:Map[String,String] = Map())
	case class HttpResponse(responseCode:Int, contentType:String, body:String)	
	
	def main(args:Array[String]) = {

		val root: Route = {
			case HttpRequest(path, _) if path == "/" => {
				HttpResponse(200, "text/html", "Root path")
			}
		}
	
		val helloWorld: Route = {
			// we can have multiple guards for pattern matching, always the most specific must go first
			case HttpRequest(path, params) if path == "/hello/world" && params.size > 0 => {
				// returns a string with the parameters into one long string
				HttpResponse(200, "text/html", "Hello world with parameters = " + params.foldLeft("")({(x,y)=>x + " " + y._1.toString + " = " + y._2  + ", "}) )
			}			
			case HttpRequest(path, _) if path == "/hello/world" => {
				HttpResponse(200, "text/html", "Hello, world")
			}			
		}
	
		// Using the the val syntax yields more concise and shorter code, but it's also possible to extend
		// the PartialFunction type and create a fully-featured object or class
		object NotFound extends Route {
			def apply(r:HttpRequest) = HttpResponse(404, "text/plain", "Not found")
			def isDefinedAt(r:HttpRequest) = true
		}
	
		// Build our simple HTTP request processor based on combinable partial functions, see how some of the components
		// of the composed function are defined as function literals while NotFound is defined as a function object
		// Please note that the most specific matcher must always go first, and the least specific (catch all) last
		val simpleServer = helloWorld orElse root orElse NotFound
		
		println(simpleServer(HttpRequest("/")))
		println(simpleServer(HttpRequest("/hello/world")))
		println(simpleServer(HttpRequest("/hello/world", Map("param1" -> "value1", "param2" -> "value2"))))
		println(simpleServer(HttpRequest("/notfound")))
	}
}