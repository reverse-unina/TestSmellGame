const fs = require("fs");
const fsextra = require('fs-extra')
const dotenv = require('dotenv')
const dotenvExpand = require('dotenv-expand')
const path = require("path");


function configEnvironment(environment){
  process.env.ROOT_PATH = path.join(__dirname, '../../')
  process.env.OS = process.platform
  process.env.LOCAL_EXERCISE_FOLDER = process.env.ROOT_PATH + 'external/compiler/LocalExercises'
  if(environment === "PRODUCTION"){
    const myEnv = dotenv.config({path: process.env.ROOT_PATH + "external/server/production.env"})
    dotenvExpand.expand(myEnv)
  }else if(environment === "DEVELOPMENT"){
    const myEnv = dotenv.config({path: process.env.ROOT_PATH + "external/server/development.env"})
    dotenvExpand.expand(myEnv)
  }
}

function writeFile(path, content){
  fs.writeFileSync(path, content, err => {
    if (err) {
      console.error(err);
    }
    console.log("File written successfully")
  });
}

function deleteDirectoryFiles(path){
  fsextra.emptyDirSync(path)
}

function editCsv(exercise){
  let data
  data = fs.readFileSync(process.env.ROOT_PATH + "external/compiler/tesi/test_template.csv", 'utf8')
  data.toString()

  data = data.replace('{exerciseNameTest}',exercise.exerciseName + "Test.java")
  data = data.replace('{exerciseNameProduction}',exercise.exerciseName + ".java")

  data = data.replace('{pathTest}', (process.env.ROOT_PATH + process.env.REFACTORED_TESTING_PATH).replace("//","/"))
  data = data.replace('{pathProduction}', (process.env.ROOT_PATH + process.env.REFACTORED_PRODUCTION_PATH).replace("//","/"))
  fs.writeFileSync(process.env.ROOT_PATH + "external/compiler/tesi/test.csv", data)
}

function cleanErrorResponse(response){
  response = response.substring(response.indexOf("COMPILATION ERROR")).split('Reactor Summary')[0]
  response = response.replaceAll(__dirname, "")
  response = response.replaceAll("/refactored-module/test/java/com/dariotintore/tesi", "")
  response = response.replaceAll("/external/compiler/tesi/", "")
  return response
}

function cleanSuccessResponse(response){
  if(response.includes("ERROR")){
    const string = "[INFO]  T E S T S"
    const index = response.indexOf(string);
    response = response.substring(response.indexOf(string, (index + 1))).split('There are test failures')[0]
  }else {
  const string = "[INFO]  T E S T S"
  const index = response.indexOf(string);
  response = response.substring(response.indexOf(string, (index + 1))).split('Skipped:')[0]
  }
  return response
}

function removeIgnoredSmells(smells, exerciseConfiguration){
  const ignoredSmells = exerciseConfiguration["ignored_smells"];
  let result = JSON.parse(smells);
  if(ignoredSmells !== undefined){
    ignoredSmells.forEach((ignored)=>{
      delete result[ignored];
    })
  }
  delete result['Exception Catching Throwing']
  return JSON.stringify(result)
}




module.exports = {
  deleteDirectoryFiles,
  writeFile,
  editCsv,
  cleanErrorResponse,
  cleanSuccessResponse,
  configEnvironment,
  removeIgnoredSmells
}
