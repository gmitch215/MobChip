echo \[MobChip JavaDoc Builder] Building...
mvn package

echo \[MobChip JavaDoc Builder] Starting...
rm -rf docs
mkdir docs
mkdir docs/bukkit
mkdir docs/paper
mkdir docs/base

echo \[MobChip JavaDoc Builder\] Injecting...
cp -R mobchip-base/target/apidocs/* docs/base
cp -R mobchip-bukkit/target/apidocs/* docs/bukkit
cp -R mobchip-paper/target/apidocs/* docs/paper

echo \[MobChip JavaDoc Builder\] Done!