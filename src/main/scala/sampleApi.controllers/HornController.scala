package sampleApi.controllers
import org.scalatra.ScalatraServlet

class HornController extends ScalatraServlet {

  get("/?") {
    <html lang="en">
      <body>
        <a href="/widget">Horn Widget Demo</a>
        <br></br>
        <a href="/popup">Horn Popup Demo</a>
      </body>
    </html>
  }

  get("/widget/") {
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
    val onClick = "\"co.horn.widget.run({channel: '"+name+"', container: 'horn-div', theme: 'light'})\""

    <html lang="en">
      <head>
        <meta charset="UTF-8"></meta>
        <title>Horn inline widget in Javascript</title>
      </head>
      <body>
        <button onlick={onClick} style="width: 120px">
          >{name}
        </button>
        <button onlick="co.horn.widget.run({channel: 'Banjo', container: 'horn-div', theme: 'light'})" style="width: 120px">
          >Banjo
        </button>
        <div id="horn-div" style="width: 240px; height 150px; border: 2px solid #5bc0de"></div>
      </body>
      <script type ="text/javascript" src="https://horn.co/widget/scripts/widget.js"></script>
    </html>
  }

  get("/popup/?") {
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
  }

  get("/popup/:name") {
    val name = params.getOrElse("name", "Welcome to Horn!")
    val onClick = "co.horn.widget.run({channel: "+name+", theme: 'light'})"

        <html lang="en">

        	<head>
        		<meta charset="UTF-8"></meta>
        		<title>{name}</title>
        	</head>

        	<body>
        		<button onclick={onClick}>
        			Hello, {name}!
        		</button>
        	</body>
        	<script type="text/javascript" src="https://horn.co/widget/scripts/widget.js"></script>

        </html>
  }

  notFound {
    <h1>404</h1>
      <br>You definitely appear to be lost.</br>
  }
}