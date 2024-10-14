
const fs = require("fs");
const fsextra = require('fs-extra')
const dotenv = require('dotenv')
const dotenvExpand = require('dotenv-expand')
const path = require("path");
const sh = require("./shell")

function configEnvironment(environment){
  process.env.ROOT_PATH = path.join(__dirname, '../../../')
  process.env.OS = process.platform
  process.env.LOCAL_EXERCISE_FOLDER = process.env.ROOT_PATH + 'src/external/compiler/LocalExercises'
  if(environment === "PRODUCTION"){
    const myEnv = dotenv.config({path: process.env.ROOT_PATH + "src/external/electron/production.env"})
    dotenvExpand.expand(myEnv)
  }else if(environment === "DEVELOPMENT"){
    const myEnv = dotenv.config({path: process.env.ROOT_PATH + "src/external/electron/development.env"})
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

function deleteGitDirectory(path){
  fs.rmSync(path, { recursive: true, force: true });
}

function deleteDirectoryFiles(path){
  fsextra.emptyDirSync(path)
}

function editCsv(exercise){
  let data
  data = fs.readFileSync(process.env.ROOT_PATH + "src/external/compiler/tesi/test_template.csv", 'utf8')
  data.toString()

  data = data.replace('{exerciseNameTest}',exercise.exerciseName + "Test.java")
  data = data.replace('{exerciseNameProduction}',exercise.exerciseName + ".java")

  data = data.replace('{pathTest}', (process.env.ROOT_PATH + process.env.REFACTORED_TESTING_PATH).replace("//","/"))
  data = data.replace('{pathProduction}', (process.env.ROOT_PATH + process.env.REFACTORED_PRODUCTION_PATH).replace("//","/"))
  fs.writeFileSync(process.env.ROOT_PATH + "src/external/compiler/tesi/test.csv", data)
}

function cleanErrorResponse(response){
  response = response.substring(response.indexOf("COMPILATION ERROR")).split('Reactor Summary')[0]
  response = response.replaceAll(__dirname, "")
  response = response.replaceAll("/refactored-module/src/test/java/com/dariotintore/tesi", "")
  response = response.replaceAll("/src/external/compiler/tesi/", "")
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
  console.log("Smells : " + smells);
  if(ignoredSmells !== undefined && smells !== ''){
    let result = JSON.parse(smells);
    ignoredSmells.forEach((ignored)=>{
      delete result[ignored];
    })
    delete result['Exception Catching Throwing']
    return JSON.stringify(result)
  }
  return '';
}

function readFile(file){
  return fs.readFileSync(file,'utf8');
}

async function checkMaven() {
  let isMavenInstalled;
  let isJavaInstalled
  await sh.execGenericCommand("mvn -version").then(result => {
    console.log("Risultato shell maven : " + result);
    isMavenInstalled = new RegExp('Apache Maven').test(result)
    isJavaInstalled = new RegExp('Java version: ').test(result)
    const string = ": "
    const index = result.indexOf(string);
    let java_version = result.substring(result.indexOf(string, (index + 1))).split('vendor')[0]
    java_version = java_version.toString().replace(": ", "")
    java_version = java_version.replace(",","")
    process.env.JAVA_VERSION = java_version;
    console.log(process.env.JAVA_VERSION)
  })
  return [isMavenInstalled,isJavaInstalled]
}

async function checkJava() {
  let mavenVersion;
  await sh.execGenericCommand("java -version").then(result => {
    console.log("Risultato shell java: " + result);
    mavenVersion = new RegExp('java version').test(result);
  })
  return mavenVersion
}

module.exports = {
  deleteDirectoryFiles,
  writeFile,
  editCsv,
  cleanErrorResponse,
  cleanSuccessResponse,
  configEnvironment,
  readFile,
  checkMaven,
  deleteGitDirectory,
  removeIgnoredSmells,
}
