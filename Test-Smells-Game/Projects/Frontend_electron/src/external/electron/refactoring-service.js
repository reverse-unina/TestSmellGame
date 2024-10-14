const utils = require('./utils')
const {spawn} = require("child_process");
const shell = require('./shell')
const scraper = require('./scraper')
const dm = require('./dependency-manger')
function createUserFiles(exercise){
  utils.writeFile(process.env.ROOT_PATH + process.env.ORIGINAL_PRODUCTION_PATH + exercise.exerciseName + ".java", exercise.originalProductionCode);
  utils.writeFile(process.env.ROOT_PATH + process.env.ORIGINAL_TESTING_PATH + exercise.exerciseName + "Test.java", exercise.originalTestCode);
  utils.writeFile(process.env.ROOT_PATH + process.env.REFACTORED_PRODUCTION_PATH + exercise.exerciseName + ".java", exercise.originalProductionCode);
  utils.writeFile(process.env.ROOT_PATH + process.env.REFACTORED_TESTING_PATH + exercise.exerciseName + "Test.java", exercise.refactoredTestCode);
}

function deleteUserFiles(){
  utils.deleteDirectoryFiles(process.env.ROOT_PATH + process.env.ORIGINAL_PRODUCTION_PATH);
  utils.deleteDirectoryFiles(process.env.ROOT_PATH + process.env.ORIGINAL_TESTING_PATH);
  utils.deleteDirectoryFiles(process.env.ROOT_PATH + process.env.REFACTORED_PRODUCTION_PATH);
  utils.deleteDirectoryFiles(process.env.ROOT_PATH + process.env.REFACTORED_TESTING_PATH);

}

function doCompile(data) {
  let exercise = data[0];
  let exerciseConfiguration = data[1]

  return new Promise((resolve, reject)=>{
    let response = {}
    deleteUserFiles()
    createUserFiles(exercise);
    dm.editPom(exerciseConfiguration)

    let promise = shell.execMavenCommand("mvn clean verify").then((result) => {
      response.testResult = result
      if (response.testResult.includes('BUILD SUCCESS')) {
        let similarity_promise = scraper.checkSimilarity(exerciseConfiguration).then((result) => {
          response.similarityResponse = result[0];
          response.originalCoverage = result[1];
          response.refactoredCoverage = result[2];
        })
        let smell_promise = shell.execSmellDetector().then((result) => {
          response.smellResult = result;
          response.smellResult = response.smellResult.replace(exercise.exerciseName,"");
        });
        Promise.all([similarity_promise, smell_promise]).then(() => {
          response.success = true;
          resolve(response)
        })
      } else {
        response.testResult = utils.cleanErrorResponse(response.testResult)
        response.success = false;
        resolve(response)
      }
    })
    utils.editCsv(exercise)
  })
}


module.exports = {
  doCompile
}
