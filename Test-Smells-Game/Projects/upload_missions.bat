$volumeName = "assets"
$sourceDir = "./missions"

if (-not (Test-Path -Path $sourceDir -PathType Container)) {
    Write-Host "Error: the folder '$sourceDir' doesn't exist."
    exit 1
}

$tempContainer = docker create -v "$volumeName:/data" alpine

docker cp "$sourceDir/." "$tempContainer:/data/missions"

docker rm $tempContainer

Write-Host "All files in '$sourceDir' have been uploaded on Docker volume '$volumeName'."
