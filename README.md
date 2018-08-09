# Play Cinnamon MDC example

An example of Cinnamon MDC in a Play application

## Prerequisites

Make sure you have access to the Cinnamon JAR files and save them in the `libs` folder.

## Build

#### Gradle

```
> ./gradlew stage
```

#### sbt

```
> sbt dist
```

## Run example

#### Gradle

```
> ./build/stage/playBinary/bin/playBinary
```

_Since you probably have put the Cinnamon agent JAR in the `libs` folder you can link to there._ 

#### sbt

```
> cd target/universal
> unzip play-scala-starter-example-1.0-SNAPSHOT.zip
> ./play-scala-starter-example-1.0-SNAPSHOT/bin/play-scala-starter-example
```

### Start up logging
 
You should see something similar to this being logged when you start the application. 

**If you don't see this output it means that the agent has not been started, i.e. there will not be any Cinnamon related functionality.**
```
[INFO] [08/09/2018 13:31:05.176] [Cinnamon] Agent version 2.10.0
[INFO] [08/09/2018 13:31:05.324] [Cinnamon] Agent found Play version 2.6.17
[INFO] [08/09/2018 13:31:05.347] [Cinnamon] Agent found Scala Futures version 2.12.6
[INFO] [08/09/2018 13:31:06.306] [Cinnamon] Agent found Scala version: 2.12.6
[INFO] [08/09/2018 13:31:06.306] [Cinnamon] Agent found Akka version: 2.5.11
[INFO] [08/09/2018 13:31:06.312] [Cinnamon] Agent found Akka Streams version 2.5.11
[info] application - ApplicationTimer demo: Starting application at 2018-08-09T17:31:07.860Z.
[info] play.api.Play - Application started (Prod)
[INFO] [08/09/2018 13:31:07.877] [Cinnamon] Agent found Akka HTTP version: 10.0.13
[info] p.c.s.AkkaHttpServer - Listening for HTTP on /0:0:0:0:0:0:0:0:9000
```

### Verifying MDC propagation

There are a couple of endpoints available to test the MDC propagation.

#### Synchronous call

```
> curl localhost:9000
```

_Expected result (values will differ as they are nano seconds):_

```
{filterATime=time-484774567273027, filterBTime=time-484774567407943, testKey=testValue}
```

_Server logging:_

```
filterA (inbound MDC): {testKey=testValue}
filterB (inbound MDC): {filterATime=time-484774567273027, testKey=testValue}
filterA: (returning result): {filterATime=time-484774567273027, filterBTime=time-484774567407943, testKey=testValue}
```


#### Asynchronous call

```
> curl localhost:9000/future
```

_Expected result (values will differ as they are nano seconds):_

```
{filterATime=time-484846102478530, filterBTime=time-484846102545111, testKey=testValue, futureComputationTime=time-484846108038690}D
```

_Server logging:_

```
filterA (inbound MDC): {testKey=testValue}
filterB (inbound MDC): {filterATime=time-484846102478530, testKey=testValue}
filterA: (returning result): {filterATime=time-484846102478530, filterBTime=time-484846102545111, testKey=testValue, futureComputationTime=time-484846108038690}
```


#### Call using custom Router

```
> curl localhost:9000/mdc/test
```

_Expected result (values will differ as they are nano seconds):_

```
{customRouterTime=time-484877718287303, filterATime=time-484877718646453, filterBTime=time-484877718680394, testKey=testValue}
```

_Server logging:_

```
filterA (inbound MDC): {customRouterTime=time-484877718287303, testKey=testValue}
filterB (inbound MDC): {customRouterTime=time-484877718287303, filterATime=time-484877718646453, testKey=testValue}
filterA: (returning result): {customRouterTime=time-484877718287303, filterATime=time-484877718646453, filterBTime=time-484877718680394, testKey=testValue}
```
