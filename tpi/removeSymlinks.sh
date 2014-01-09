#!/bin/sh

WEBAPP_ROOT=tpi-web/src/main/webapp

echo "Removing existing symlinks"
find $WEBAPP_ROOT -type l | xargs rm -rfv

echo "Symlink removal complete!"
