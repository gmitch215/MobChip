echo Installing Spigot...

wget -O BuildTools.jar https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar
java -jar BuildTools.jar --disable-java-check --rev 1.18.2 --remapped

echo Installing Paper...

git clone https://github.com/PaperMC/Paper.git
cd Paper
./gradlew applyPatches
./gradlew publishToMavenLocal

echo Done
