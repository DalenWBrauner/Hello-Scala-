package sampleApi.controllers
import org.scalatra.ScalatraServlet
//import scalate.ScalateSupport

//class HornController extends ScalatraServlet with ScalateSupport {
class HornController extends ScalatraServlet {

  get("/?") {
    "Hello world, I'm HornController!"
  }

  get("/widget/?") {
    <html lang="en">
      <head>
        <meta charset="UTF-8"></meta>
          <title>Horn inline widget in Javascript</title>
      </head>
      <body>
          <button onlick="co.horn.widget.run({channel: 'WidgetChannel', container: 'horn-div', theme: 'light'})" style="width: 120px">
            >WidgetChannel
          </button>
          <button onlick="co.horn.widget.run({channel: 'Banjo', container: 'horn-div', theme: 'light'})" style="width: 120px">
            >Banjo
          </button>
          <div id="horn-div" style="width: 240px; height 150px; border: 2px solid #5bc0de"></div>
      </body>
      <script type ="text/javascript" src="https://horn.co/widget/scripts/widget.js"></script>
    </html>
  }

  get("/widget/:name") {
    val name = params.getOrElse("name", "world")
    "Hello, " + name + " channel widget!"
  }

  get("/popup/?") {
    var horn =
      """
        <html lang="en">

        	<head>
        		<meta charset="UTF-8"></meta>
        		<title>Horn inline widget in Javascript</title>
        	</head>

        	<body>
        		<button onclick="co.horn.widget.run({channel: 'WidgetChannel', theme: 'light'})">
        			Start Widget
        		</button>
        	</body>
        	<script type="text/javascript" src="https://horn.co/widget/scripts/widget.js"></script>

        </html>
      """
    horn
  }

  get("/popup/:name") {
    val name = params.getOrElse("name", "Welcome to Horn!")
    var horn =
      s"""
        <html lang="en">

        	<head>
        		<meta charset="UTF-8"></meta>
        		<title>$name</title>
        	</head>

        	<body>
        		<button onclick="co.horn.widget.run({channel: '$name', theme: 'light'})">
        			Hello, $name!
        		</button>
        	</body>
        	<script type="text/javascript" src="https://horn.co/widget/scripts/widget.js"></script>

        </html>
      """
    horn
  }
}