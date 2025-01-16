@echo off
set volumeName=assets

if "%~1"=="" (
    echo Error: You must specify the source directory as the first argument.
    exit /b 1
)

set sourceDir=%~1
if not exist "%sourceDir%" (
    echo Error: The folder "%sourceDir%" doesn't exist.
    exit /b 1
)

docker volume inspect %volumeName% >nul 2>nul
if %errorlevel% neq 0 (
    echo Volume "%volumeName%" does not exist. Creating it...
    docker volume create %volumeName%
) else (
    echo Volume "%volumeName%" already exists.
)

for /f "tokens=*" %%i in ('docker run -d -v %volumeName%:/data alpine tail -f /dev/null') do set tempContainer=%%i

docker cp "%sourceDir%\." %tempContainer%:/data/missions

docker rm -f %tempContainer%

echo All files in "%sourceDir%" have been uploaded to Docker volume "%volumeName%".
