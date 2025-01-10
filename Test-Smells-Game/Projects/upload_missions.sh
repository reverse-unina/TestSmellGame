#!/bin/bash

VOLUME_NAME="assets"
SOURCE_DIR="./missions"

# Verifica che la cartella sorgente esista
if [ ! -d "$SOURCE_DIR" ]; then
  echo "Error: the folder '$SOURCE_DIR' doesn't exist."
  exit 1
fi

TEMP_CONTAINER=$(docker create -v "$VOLUME_NAME":/data alpine)

docker cp "$SOURCE_DIR"/. "$TEMP_CONTAINER":/data/missions
docker rm "$TEMP_CONTAINER"

echo "All files in '$SOURCE_DIR' have been uploaded on Docker volume '$VOLUME_NAME'."