#!/bin/bash

SOURCE_DIR="./exercises"

CONTAINER_NAME="exercise-service"
DEST_DIR="/ExerciseDB"

if [ ! -d "$SOURCE_DIR" ]; then
  echo "Error: the folder '$SOURCE_DIR' doesn't exist."
  exit 1
fi

if ! docker ps --format '{{.Names}}' | grep -q "^${CONTAINER_NAME}$"; then
  echo "Error: the container '$CONTAINER_NAME' isn't running."
  exit 1
fi

if [ -d "$SOURCE_DIR/RefactoringGame" ]; then
  docker cp "$SOURCE_DIR/RefactoringGame"/. "$CONTAINER_NAME":"$DEST_DIR/RefactoringGame"
fi

if [ -d "$SOURCE_DIR/CheckGame" ]; then
  docker cp "$SOURCE_DIR/CheckGame"/. "$CONTAINER_NAME":"$DEST_DIR/CheckGame"
fi

if [ $? -eq 0 ]; then
  echo "All files in '$SOURCE_DIR' have been uploaded in '$DEST_DIR' of container '$CONTAINER_NAME'."
else
  echo "Error: Failed to copy files."
  exit 1
fi