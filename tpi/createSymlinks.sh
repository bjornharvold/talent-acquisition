#!/bin/sh

WEBAPP_ROOT=tpi-web/src/main/webapp/
RIA_ROOT=../../../../tpi-ria/target

echo "Removing existing symlinks"
find $WEBAPP_ROOT -type l | xargs rm -rfv

echo "Creating new symlinks"

# content store files
ln -sv $RIA_ROOT/tpiria.swf $WEBAPP_ROOT/tpiria.swf
ln -sv $RIA_ROOT/assets $WEBAPP_ROOT/assets

echo "Symlink creation complete!"
