@echo off
set volumeName=assets
set sourceDir=.\assignments

if not exist "%sourceDir%" (
    echo Error: the folder "%sourceDir%" doesn't exist.
    exit /b 1
)

for /f "tokens=*" %%i in ('docker run -d -v %volumeName%:/data alpine tail -f /dev/null') do set tempContainer=%%i

docker cp "%sourceDir%\." %tempContainer%:/data/assignments

docker rm -f %tempContainer%

echo All files in "%sourceDir%" have been uploaded to Docker volume "%volumeName%"!
