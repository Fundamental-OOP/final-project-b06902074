#!/bin/bash

rm -r out/ 
javac -cp "lib/*:" -sourcepath src -d out/ src/*.java && 
java -cp "lib/*:out/" Main