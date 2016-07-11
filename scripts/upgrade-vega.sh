#!/bin/bash

set -euo pipefail

TARGET="${1:?Usage: $0 <tag>}"

echo "Upgrading vega to version $TARGET"

pushd ext/vega
git checkout "$TARGET"
npm install
npm run schema
popd

cp ext/vega/vega-schema.json resources/vega_tools/
