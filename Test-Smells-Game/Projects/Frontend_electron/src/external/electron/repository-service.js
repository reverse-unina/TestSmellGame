const sh = require("./shell");
const utils = require('./utils')
const fs = require("fs");

let path = require('path')
const git = require('isomorphic-git')
const http = require('isomorphic-git/http/node')
const {branch} = require("isomorphic-git");

async function cloneRepository(data) {
  console.log(process.env.ROOT_PATH);
  console.log("clone repo data: ", data);
  const dir = process.env.ROOT_PATH + "src/external/compiler/LocalExercises"
  console.log(dir);
  utils.deleteGitDirectory(dir);
  await git.clone({
    fs,
    http,
    dir,
    ref: data.branchName,
    url: data.url
  })
}

// Function to get all JSON files in a directory
function getAllJsonFilesInDirectory(directory, className) {
  const result = getAllJsonFilePaths(directory);

  if (result instanceof Map) return result;

  const jsonFilePaths = result;
  const jsonFiles = [];

  for (let i = 0; i < jsonFilePaths.length; i++) {
    const filePath = jsonFilePaths[i];
    const fileResult = deserializeJson(filePath, className);

    if (fileResult instanceof Map) return fileResult;

    jsonFiles.push(fileResult);
  }

  // Validation based on the className
  if (className === 'Assignment') {
    if (!(jsonFiles[0] instanceof Object || jsonFiles[0].name)) {
      const error = 'The first item in the list is not of the expected type "Assignment". Please ensure that the data is correctly formatted.';
      console.error(error);
      return new Map([['INTERNAL_SERVER_ERROR', error]]);
    }

    if (assignmentsHaveDuplicateNames(jsonFiles)) {
      const error = 'Duplicate assignment names found';
      console.error(error);
      return new Map([['INTERNAL_SERVER_ERROR', error]]);
    }
  } else if (className === 'Mission') {
    if (!(jsonFiles[0] instanceof Object || jsonFiles[0].id)) {
      const error = 'The first item in the list is not of the expected type "Mission". Please ensure that the data is correctly formatted.';
      console.error(error);
      return new Map([['INTERNAL_SERVER_ERROR', error]]);
    }

    if (missionsHaveDuplicateIds(jsonFiles)) {
      const error = 'Duplicate mission identifiers found';
      console.error(error);
      return new Map([['INTERNAL_SERVER_ERROR', error]]);
    }
  } else if (className === 'RefactoringExercise') {
    if (!(jsonFiles[0] instanceof Object || jsonFiles[0].id)) {
      const error = 'The first item in the list is not of the expected type "RefactoringExercise". Please ensure that the data is correctly formatted.';
      console.error(error);
      return new Map([['INTERNAL_SERVER_ERROR', error]]);
    }

    if (refactoringExerciseHaveDuplicateIds(jsonFiles)) {
      const error = 'Duplicate exercise identifiers found';
      console.error(error);
      return new Map([['INTERNAL_SERVER_ERROR', error]]);
    }
  } else if (className === 'CheckSmellExercise') {
    if (!(jsonFiles[0] instanceof Object || jsonFiles[0].id)) {
      const error = 'The first item in the list is not of the expected type "CheckSmellExercise". Please ensure that the data is correctly formatted.';
      console.error(error);
      return new Map([['INTERNAL_SERVER_ERROR', error]]);
    }

    if (checkSmellExerciseHaveDuplicateIds(jsonFiles)) {
      const error = 'Duplicate exercise identifiers found';
      console.error(error);
      return new Map([['INTERNAL_SERVER_ERROR', error]]);
    }
  } else if (className === 'LevelConfig') {
    if (!(jsonFiles[0] instanceof Object || jsonFiles[0].id)) {
      const error = 'The first item in the list is not of the expected type "LevelConfig". Please ensure that the data is correctly formatted.';
      console.error(error);
      return new Map([['INTERNAL_SERVER_ERROR', error]]);
    }
  }

  return jsonFiles;
}

// Function to get all JSON file paths in a directory
function getAllJsonFilePaths(directory) {
  const jsonFilePaths = [];

  if (!fs.existsSync(directory) || !fs.statSync(directory).isDirectory()) {
    return new Map([['NOT_FOUND', 'Database not found']]);
  }

  try {
    const files = fs.readdirSync(directory);
    files.forEach(file => {
      const filePath = path.join(directory, file);
      if (filePath.endsWith('.json') && fs.statSync(filePath).isFile()) {
        jsonFilePaths.push(filePath);
      }
    });
  } catch (err) {
    const error = 'Error occurred while reading json files';
    console.error(`${error}: ${err.message}`);
    return new Map([['INTERNAL_SERVER_ERROR', error]]);
  }

  if (jsonFilePaths.length === 0) {
    const error = 'Json files not found';
    console.error(error);
    return new Map([['NOT_FOUND', error]]);
  }

  return jsonFilePaths;
}

// Function to deserialize JSON data from a file
function deserializeJson(filePath, className) {
  try {
    const fileContent = fs.readFileSync(filePath, 'utf8');
    return JSON.parse(fileContent);
  } catch (e) {
    if (e.name === 'SyntaxError') {
      const error = `Error reading file ${path.basename(filePath)}`;
      console.error(`${error}: ${e.message}`);
      return new Map([['INTERNAL_SERVER_ERROR', error]]);
    }
    return new Map([['INTERNAL_SERVER_ERROR', e.message]]);
  }
}

// Function to check for duplicate refactoring exercise ids
function refactoringExerciseHaveDuplicateIds(exercises) {
  const ids = new Set();
  for (const exercise of exercises) {
    if (ids.has(exercise.id)) {
      return true;
    }
    ids.add(exercise.id);
  }
  return false;
}

// Function to check for duplicate check smell exercise ids
function checkSmellExerciseHaveDuplicateIds(exercises) {
  const ids = new Set();
  for (const exercise of exercises) {
    if (ids.has(exercise.id)) {
      return true;
    }
    ids.add(exercise.id);
  }
  return false;
}

// Function to get the Refactoring Exercise file based on exerciseId and type
function getRefactoringExerciseFile(exerciseId, type) {
  const result = getAllJsonFilePaths('path/to/refactoringDB');

  if (result instanceof Map) return result;

  const configFilePaths = result;
  const exercisePath = configFilePaths
    .map(filePath => deserializeJson(filePath, 'RefactoringGameExerciseConfiguration'))
    .find(exercise => exercise.exerciseId === exerciseId);

  if (exercisePath) {
    let parentDirectory;
    let productionFile;

    switch (type) {
      case 'Production':
        parentDirectory = path.dirname(exercisePath.filePath);
        console.info('Parent directory: ' + parentDirectory);

        productionFile = findFile(parentDirectory, '.java', 'Test.java');
        if (productionFile) {
          try {
            return fs.readFileSync(productionFile, 'utf8');
          } catch (e) {
            const error = `Failed to read ${exerciseId} Production file`;
            console.error(`${error}: ${e.message}`);
            return new Map([['INTERNAL_SERVER_ERROR', error]]);
          }
        } else {
          const error = 'File not found';
          console.error(error);
          return new Map([['NOT_FOUND', error]]);
        }

      case 'Test':
        parentDirectory = path.dirname(exercisePath.filePath);

        productionFile = findFile(parentDirectory, 'Test.java');
        if (productionFile) {
          try {
            return fs.readFileSync(productionFile, 'utf8');
          } catch (e) {
            const error = `Failed to read ${exerciseId} Test file`;
            console.error(`${error}: ${e.message}`);
            return new Map([['INTERNAL_SERVER_ERROR', error]]);
          }
        } else {
          const error = 'File not found';
          console.error(error);
          return new Map([['NOT_FOUND', error]]);
        }

      default:
        return exercisePath;
    }
  } else {
    const error = 'File not found';
    console.error(error);
    return new Map([['NOT_FOUND', error]]);
  }
}

// Helper function to find a file in a directory that matches certain criteria
function findFile(directory, ...criteria) {
  const files = fs.readdirSync(directory);
  return files.find(file => criteria.some(criterion => file.includes(criterion)));
}

// Function to get JSON file by ID from the directory
function getJsonFileById(fileId, className) {
  let directory;

  switch (className) {
    case "CheckGameExerciseConfig":
      directory = process.env.LOCAL_EXERCISE_DIRECTORY + "ExerciseDB/CheckSmellGame/";
      break;
  }

  const result = getAllJsonFilePaths(directory);

  if (result instanceof Map) return result;

  const configFilePaths = result;
  console.info("Paths: " + configFilePaths);

  const jsonExercise = configFilePaths
    .map(filePath => deserializeJson(filePath, className))
    .find(exercise => {
      if (className === 'CheckGameExerciseConfig') {
        return exercise.exerciseId === fileId;
      } else if (className === 'RefactoringExerciseConfig') {
        return exercise.exerciseId === fileId;
      } else if (className === 'LearningContent') {
        return exercise.learningId === fileId;
      } else {
        return false;
      }
    });

  if (jsonExercise) {
    return jsonExercise;
  } else {
    const error = 'File not found';
    console.error(error);
    return new Map([['NOT_FOUND', error]]);
  }
}

module.exports = {
  cloneRepository,
  getAllJsonFilesInDirectory,
  getRefactoringExerciseFile,
  getJsonFileById
}
