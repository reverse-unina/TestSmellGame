$sourceDir = "./exercises"
$containerName = "exercise-service"
$destDir = "/ExerciseDB"

if (-not (Test-Path -Path $sourceDir -PathType Container)) {
    Write-Host "Error: the folder '$sourceDir' doesn't exist."
    exit 1
}

if (-not (docker ps --format '{{.Names}}' | ForEach-Object { $_ -eq $containerName } | Where-Object {$_})) {
    Write-Host "Error: the container '$containerName' isn't running."
    exit 1
}

if (Test-Path "$sourceDir/RefactoringGame") {
    docker cp "$sourceDir/RefactoringGame/." "$containerName:$destDir/RefactoringGame"
}

if (Test-Path "$sourceDir/CheckSmellGame") {
    docker cp "$sourceDir/CheckSmellGame/." "$containerName:$destDir/CheckSmellGame"
}

if (Test-Path "$sourceDir/LearningContent") {
    docker cp "$sourceDir/LearningContent/." "$containerName:$destDir/LearningContent"
}

if ($LASTEXITCODE -eq 0) {
    Write-Host "All files in '$sourceDir' have been uploaded in '$destDir' of container '$containerName'."
} else {
    Write-Host "Error: Failed to copy files."
    exit 1
}
