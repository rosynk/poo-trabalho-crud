rmdir /S /Q ".\build\"
mkdir build
javac -d ./build -cp .;./lib/mariadb-java-client-3.5.1.jar ./src/livros/*.java
java -cp .;./build;./lib/mariadb-java-client-3.5.1.jar /src/PrincipalBoundary.java