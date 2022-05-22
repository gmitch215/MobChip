#!/bin/bash

git config --local user.email "action@github.com"
git config --local user.name "GitHub Actions"
git fetch origin gh-pages

echo "[MobChip JavaDoc Builder] Starting..."

rm -rf docs/bukkit
rm -rf docs/base

mkdir ./docs/base
mkdir ./docs/bukkit

echo "[MobChip JavaDoc Builder] Injecting..."

cp -R mobchip-base/target/apidocs/* docs/base
cp -R mobchip-bukkit/target/apidocs/* docs/bukkit

git checkout gh-pages

for dir in ./*
do
  if [ "$dir" == "./docs" ]; then
    continue
  fi

  rm -rf "$dir"
done

cp -Rfv ./docs/* ./
rm -rf ./docs

echo "[MobChip JavaDoc Builder] Committing..."

git add .
git branch -D gh-pages
git branch -m gh-pages
git commit -m "Update Javadocs"
git push -f origin gh-pages

echo "[MobChip JavaDoc Builder] Done!"
