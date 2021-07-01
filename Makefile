.PHONY: clean
clean:
	rm -rf out/
run:
	javac -cp "lib/*:" -sourcepath src -d out/ src/*.java && java -cp "lib/*:out/" Main