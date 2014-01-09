#!/bin/sh

echo "SVN UPDATE"
svn update

echo "mvn install"
mvn install

echo "starting jetty"
cd tpi-web
mvn jetty:run
