Spark poc
----------------------------------------------------------
Spark tiny web framework

#### Resources ####
* [Spark docs](http://sparkjava.com/documentation.html)
* [Morphia](https://github.com/mongodb/morphia)
* [Owner Configuration](http://owner.aeonbits.org/docs/welcome/)

#### Dev Stack ####

###### Frontend ######

* AngularJS
* Build-time compilation via Grunt, Bower, SASS (see package.json/Gruntfile.js/bower.json)
* Testing via Karma/Jasmine/PhatomJS

###### Backend ######

* [Spark Framework](http://sparkjava.com/)
* Java 8+
* Mongo with Object Mapping from [Morphia](https://github.com/mongodb/morphia)

###### Environment Setup ######

```
Set Project language level to 8
```

```
* Tomcat 8
* Embedded Jetty from the CLI
```
Enable connector 8443 in <TOMCAT_HOME>/conf/server.xml

```
* Java 8 
* Brew
```

Set VM Variable: -DCONFIG_FILE=/path/to/spark-poc/config.properties


/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
```
* Node 0.12.* (also builds with Node v5.7.0)
* Mongo (currently version 3.2.3)
* git
* PhantomJS
```