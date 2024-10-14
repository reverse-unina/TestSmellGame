const { exec} = require("child_process")
const dotenv = require("dotenv");
const dotenvExpand = require("dotenv-expand");
const myEnv = dotenv.config()
dotenvExpand.expand(myEnv)

const execMavenCommand = (cmd) => {
  let PROJECT_POM = process.env.ROOT_PATH + process.env.JUNIT_POM
  PROJECT_POM = PROJECT_POM.replace("//","/")
  console.log("PROJECT_POM :" + PROJECT_POM)
  return new Promise((resolve, reject) => {
    console.log("PROJECT_POM :" + PROJECT_POM)
    exec(cmd  + " -f " + "\"" + PROJECT_POM +"\"", (error, stdout, stderr) => {
      if (error) {
        if (error.code === 1) {
          resolve(stdout);
        } else {
          reject(error);
        }
      } else {
        resolve(stdout);
      }
    })
  })
}

const execSmellDetector = () => {
  const smell_detector_path = (process.env.ROOT_PATH + process.env.SMELL_PATH).replace("//","/")
  const test_csv_path = (process.env.ROOT_PATH + process.env.TEST_CSV_PATH).replace("//","/");
  console.log("java -jar " + smell_detector_path +  " \"" + test_csv_path + "\"")
  return new Promise((resolve, reject) => {
    exec("java -jar " + smell_detector_path +  " \"" + test_csv_path + "\"", (error, stdout, stderr) => {
      if (error) {
        if (error.code === 1) {
          resolve(stdout);
        } else {
          reject(error);
        }
      } else {
        resolve(stdout);
      }
    console.log(stdout);
    }
    )
  })
}

const execGenericCommand = (cmd) => {
  return new Promise((resolve, reject) => {
    exec(cmd, (error, stdout, stderr) => {
      if (error) {
        if (error.code === 1) {
          resolve(stdout);
        } else {
          reject(error);
        }
      } else {
        resolve(stdout);
      }
    })
  })
}

module.exports = {
  execMavenCommand,
  execSmellDetector,
  execGenericCommand,
}
