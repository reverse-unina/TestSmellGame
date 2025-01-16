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

for /f "tokens=*" %%i in ('docker run -d -v %volumeName%:/data alpine tail -f /dev/null') do set tempContainer=%%i

docker cp "%sourceDir%\." %tempContainer%:/data/assignments

docker rm -f %tempContainer%

echo All files in "%sourceDir%" have been uploaded to Docker volume "%volumeName%"!
