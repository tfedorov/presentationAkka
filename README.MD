## PresentationAkka

Set of applications, for briefly review of the **akka** functionality.

  - WebApp
  - MemServerApp
  - SentimentApp
  - PingPongApp
  - HelloActorApp
  - SimpleStreamApp
  
  
### WebApp

App creates the Web Application with routes:
  - http://localhost:8080/META-INF path to the **META-INF/MANIFEST.MF** file
  - http://localhost:8080/META-INF/MANIFEST.MF content of the **META-INF/MANIFEST.MF** file

Note: Path to **META-INF/MANIFEST.MF** could be different.
```sh
$ sbt clean assembly
$ java -cp target/scala-2.12/presentation-assembly-0.1.jar com.tfedorov.web.WebApp
```

WebApp describes akka functionality for creating a server.
```scala
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives.{complete, concat, get, path, pathPrefix}
import akka.http.scaladsl.Http

  pathPrefix("META-INF") {
      concat(
        path("MANIFEST.MF") {
          complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, ""))
        })
}
```
### MemServerApp
MemServerApp obtains abbreviations and processes them using the API: http://apimeme.com.

```sh
$ sbt clean assembly
$ java -cp target/scala-2.12/presentation-assembly-0.1.jar com.tfedorov.mem.MemServerApp
```
Go to http://localhost:8080/ and receive just generated Meme.
![Screen shot example](https://github.com/tfedorov/presentationAkka/blob/master/src/main/resources/Meme.png?raw=true)

MemServerApp create akka server and akka client in one flow.
APP On server creates request to the 3rd party API and reuses it HTTP response.

### SentimentApp
The SentimentApp reads a reviews file './src/main/resources/Amazon_Unlocked_Mobile.csv'. 
*Amazon_Unlocked_Mobile.csv* it is file with phone's reviews.
And process reviews throw http://api.deepai.org .The 3rd party API which makes sentiment analyses of the text.

SentimentApp reads a file, parse text, creates akka client in one flow.

### PingPongApp
The PingPongApp creates 2 actors and pulls messages between it.
TBD

### HelloActorApp
HelloActorApp is a simple actor application. It just creates 2 actors and iterate with them.
TBD

### PingPongApp
SimpleStreamApp is an simple example of the Graph: source + flow + sink
TBD
