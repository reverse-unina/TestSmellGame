@echo off
set sourceDir=.\exercises
set containerName=exercise-service
set destDir=/ExerciseDB

if not exist "%sourceDir%" (
    echo Error: the folder "%sourceDir%" doesn't exist.
    exit /b 1
)

docker ps --format "{{.Names}}" | findstr /i "%containerName%" >nul
if %errorlevel% neq 0 (
    echo Error: the container "%containerName%" isn't running.
    exit /b 1
)

if exist "%sourceDir%\RefactoringGame" (
    docker cp "%sourceDir%\RefactoringGame\." %containerName%:%destDir%/RefactoringGame
)

if exist "%sourceDir%\CheckSmellGame" (
    docker cp "%sourceDir%\CheckSmellGame\." %containerName%:%destDir%/CheckSmellGame
)

if exist "%sourceDir%\LearningContent" (
    docker cp "%sourceDir%\LearningContent\." %containerName%:%destDir%/LearningContent
)

if %errorlevel% equ 0 (
    echo All files in "%sourceDir%" have been uploaded in "%destDir%" of container "%containerName%"!
) else (
    echo Error: Failed to copy files.
    exit /b 1
)
