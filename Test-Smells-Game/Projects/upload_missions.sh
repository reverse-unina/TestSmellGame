#!/bin/bash

VOLUME_NAME="assets"

if [ -z "$1" ]; then
  echo "Error: You must specify the source directory as the first argument."
  exit 1
fi

SOURCE_DIR="$1"

if [ ! -d "$SOURCE_DIR" ]; then
  echo "Error: the folder '$SOURCE_DIR' doesn't exist."
  exit 1
fi

TEMP_CONTAINER=$(docker create -v "$VOLUME_NAME":/data alpine)

docker cp "$SOURCE_DIR"/. "$TEMP_CONTAINER":/data/missions
docker rm "$TEMP_CONTAINER"

echo "All files in '$SOURCE_DIR' have been uploaded on Docker volume '$VOLUME_NAME'."