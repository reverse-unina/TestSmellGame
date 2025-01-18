#!/bin/bash

SOURCE_DIR="./Exercise_service/ExerciseDB"
CONTAINER_NAME="exercise-service"

if [ -z "$1" ]; then
  echo "Error: You must specify the source directory as the first argument."
  exit 1
fi

SOURCE_DIR="$1"

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

if [ -d "$SOURCE_DIR/CheckSmellGame" ]; then
  docker cp "$SOURCE_DIR/CheckSmellGame"/. "$CONTAINER_NAME":"$DEST_DIR/CheckSmellGame"
fi

if [ -d "$SOURCE_DIR/LearningContent" ]; then
  docker cp "$SOURCE_DIR/LearningContent"/. "$CONTAINER_NAME":"$DEST_DIR/LearningContent"
fi

if [ $? -eq 0 ]; then
  echo "All files in '$SOURCE_DIR' have been uploaded in '$DEST_DIR' of container '$CONTAINER_NAME'."
else
  echo "Error: Failed to copy files."
  exit 1
fi