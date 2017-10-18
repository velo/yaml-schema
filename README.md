# yaml-schema

[![Build Status](https://travis-ci.org/${owner}/${name}.svg?branch=master)](https://travis-ci.org/${owner}/${name}?branch=master) 
[![Coverage Status](https://coveralls.io/repos/github/${owner}/${name}/badge.svg?branch=master)](https://coveralls.io/github/${owner}/${name}?branch=master) 
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.marvinformatics/${name}/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.marvinformatics/${name}/) 
[![Issues](https://img.shields.io/github/issues/${owner}/${name}.svg)](https://github.com/${owner}/${name}/issues) 
[![Forks](https://img.shields.io/github/forks/${owner}/${name}.svg)](https://github.com/${owner}/${name}/network) 
[![Stars](https://img.shields.io/github/stars/${owner}/${name}.svg)](https://github.com/${owner}/${name}/stargazers)

Introduction
------------

yaml-schema provides Java-based schema-based validation facilities for YAML documents. 

Examples
--------

TBD

Data Types
----------

yaml-schema supports the following datatypes:

* string (str)
* integer (int)
* natural
* enumeration
* map
* list

Dependencies
------------

yaml-schema depends only on the snakeyaml YAML parser and the jboss-logging library for logging.

Building
--------

yaml-schema runs on Maven 3, and can be built using good old mvn install.

If you're a regular JBoss developer, see:

* http://community.jboss.org/wiki/MavenGettingStarted-Developers

Otherwise, see: 

* http://community.jboss.org/wiki/MavenGettingStarted-Users

Once your repositories are configured, simply type:

    mvn install

Tests can be executed using mvn test.
