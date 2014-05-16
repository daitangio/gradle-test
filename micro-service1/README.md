
# So what?
A teaching example about spring boot.
A nice starting point for Spirng-ready micro service


# Preferred Eclipse plugins
Grap Spring Tools (STS) and add Grooovy and Rails plugin

# Please note...

+ SSH console was disabled because it does not work well with spring load reloading engine (out choice)
+ This app can run in a very constrained environments, with less then 128 megabytes.
Anyway in development environment use these settings:
JAVA_OPTS="-Xms256m -Xmx500m -verbose:gc" ./runMe
 


# Code reloading
To get auto reload working do the folloging:
run with 
gradle bootRun

On another shell recompile via 
gradle build
or use your preferred IDE