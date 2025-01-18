const sh = require("./shell");
const utils = require('./utils')
const fs = require("fs");
const Ajv = require("ajv");

let path = require('path')
const git = require('isomorphic-git')
const http = require('isomorphic-git/http/node')
const {branch} = require("isomorphic-git");

const refactoringConfigSchema = {
  "type": "object",
  "properties": {
    "exerciseId": { "type": "string" },
    "class_name": { "type": "string" },
    "refactoring_game_configuration": {
      "type": "object",
      "properties": {
        "dependencies": {
          "type": "array",
          "items": {
            "type": "string"
          }
        },
        "refactoring_limit": { "type": "integer" },
        "smells_allowed": { "type": "integer" },
        "level": { "type": "integer" },
        "ignored_smells": {
          "type": "array",
          "items": {
            "type": "string"
          }
        }
      },
      "required": ["dependencies", "refactoring_limit", "smells_allowed", "level", "ignored_smells"]
    },
    "auto_valutative": { "type": "boolean" }
  },
  "required": ["exerciseId", "class_name", "refactoring_game_configuration", "auto_valutative"]
};

const checkSmellSchema = {
  "type": "object",
  "properties": {
    "exerciseId": { "type": "string" },
    "check_game_configuration": {
      "type": "object",
      "properties": {
        "questions": {
          "type": "array",
          "items": {
            "type": "object",
            "properties": {
              "questionTitle": { "type": "string" },
              "questionCode": { "type": "string" },
              "answers": {
                "type": "array",
                "items": {
                  "type": "object",
                  "properties": {
                    "answerText": { "type": "string" },
                    "isCorrect": { "type": "boolean" }
                  },
                  "required": ["answerText", "isCorrect"]
                }
              }
            },
            "required": ["questionTitle", "questionCode", "answers"]
          }
        },
        "level": { "type": "integer" }
      },
      "required": ["questions", "level"]
    },
    "auto_valutative": { "type": "boolean" }
  },
  "required": ["exerciseId", "check_game_configuration", "auto_valutative"]
};

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

  if (className === 'RefactoringExercise') {
    if (!(jsonFiles[0] instanceof Object || jsonFiles[0].id)) {
      const error = 'The first item in the list is not of the expected type "RefactoringExercise". Please ensure that the data is correctly formatted.';
      console.error(error);
      return new Map([['message', error]]);
    }

    if (refactoringExerciseHaveDuplicateIds(jsonFiles)) {
      const error = 'Duplicate exercise identifiers found';
      console.error(error);
      return new Map([['message', error]]);
    }
  } else if (className === 'CheckSmellExercise') {
    if (!(jsonFiles[0] instanceof Object || jsonFiles[0].id)) {
      const error = 'The first item in the list is not of the expected type "CheckSmellExercise". Please ensure that the data is correctly formatted.';
      console.error(error);
      return new Map([['message', error]]);
    }

    if (checkSmellExerciseHaveDuplicateIds(jsonFiles)) {
      const error = 'Duplicate exercise identifiers found';
      console.error(error);
      return new Map([['message', error]]);
    }
  }

  return jsonFiles;
}

function getJsonFilesRecursively(directory) {
  let jsonFilePaths = [];

  try {
    const files = fs.readdirSync(directory);

    files.forEach(file => {
      const filePath = path.join(directory, file);
      const stats = fs.statSync(filePath);

      if (stats.isDirectory()) {
        jsonFilePaths = jsonFilePaths.concat(getJsonFilesRecursively(filePath));
      } else if (filePath.endsWith('.json') && stats.isFile()) {
        jsonFilePaths.push(filePath);
      }
    });
  } catch (err) {
    const error = 'Error occurred while reading json files';
    console.error(`${error}: ${err.message}`);
    return new Map([['message', error]]);
  }

  return jsonFilePaths;
}

function getAllJsonFilePaths(directory) {
  let jsonFilePaths = [];

  console.log("getAllJsonFilePaths dir bd: ", directory);
  if (!fs.existsSync(directory) || !fs.statSync(directory).isDirectory()) {
    return new Map([['message', 'Database not found']]);
  }

  jsonFilePaths = getJsonFilesRecursively(directory);
  if (jsonFilePaths instanceof Map)
    return jsonFilePaths;

  if (jsonFilePaths.length === 0) {
    const error = 'Json files not found';
    console.error(error);
    return new Map([['message', error]]);
  }

  return jsonFilePaths;
}

function deserializeJson(filePath, className) {
  const ajv = new Ajv();
  let validate;

  if (className === 'RefactoringGameExerciseConfiguration')
    validate = ajv.compile(refactoringConfigSchema);
  else
    validate = ajv.compile(checkSmellSchema);

  console.log("className: ", className);
  console.log("validate: ", validate)
  try {
    const fileContent = fs.readFileSync(filePath, 'utf8');
    const json =  JSON.parse(fileContent);
    const valid = validate(json);

    if (!valid) {
      const error = `Error validating schema for ${path.basename(filePath)}`;
      console.error(`${error}: `, validate.errors);
      return new Map([['message', error]]);
    } else {
      return json;
    }
  } catch (e) {
    if (e.name === 'SyntaxError') {
      const error = `Error reading file ${path.basename(filePath)}`;
      console.error(`${error}: ${e.message}`);
      return new Map([['message', error]]);
    }
    return new Map([['message', e.message]]);
  }
}

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

function getRefactoringExerciseFile(exerciseId, type) {
  const result = getAllJsonFilePaths(process.env.ROOT_PATH + "src/external/compiler/LocalExercises" + "\\ExerciseDB\\RefactoringGame\\");

  if (result instanceof Map) return result;

  console.log("ExerciseId: ", exerciseId);

  const configFilePaths = result;
  let exercisePath;
  for (let i = 0; i < configFilePaths.length; i++) {
    const deserializedFile = deserializeJson(configFilePaths[i], 'RefactoringGameExerciseConfiguration');
    if (deserializedFile.exerciseId !== undefined && deserializedFile.exerciseId === exerciseId) {
      exercisePath = configFilePaths[i];
      break
    }
  }

  if (exercisePath) {
    let parentDirectory;
    let productionFile;

    switch (type) {
      case 'Production':
        parentDirectory = path.dirname(exercisePath);

        productionFile = fs.readdirSync(parentDirectory).find(file => file.endsWith(".java") && !file.endsWith("Test.java"));
        if (productionFile) {
          try {
            return fs.readFileSync(parentDirectory + "\\" + productionFile, 'utf8');
          } catch (e) {
            const error = `Failed to read ${exerciseId} Production file`;
            console.error(`${error}: ${e.message}`);
            return new Map([['message', error]]);
          }
        } else {
          const error = 'File not found';
          console.error(error);
          return new Map([['message', error]]);
        }

      case 'Test':
        parentDirectory = path.dirname(exercisePath);

        productionFile = fs.readdirSync(parentDirectory).find(file => file.endsWith("Test.java"));

        if (productionFile) {
          try {
            return fs.readFileSync(parentDirectory + "\\" + productionFile, 'utf8');
          } catch (e) {
            const error = `Failed to read ${exerciseId} Test file`;
            console.error(`${error}: ${e.message}`);
            return new Map([['message', error]]);
          }
        } else {
          const error = 'File not found';
          console.error(error);
          return new Map([['message', error]]);
        }

      default:
        parentDirectory = path.dirname(exercisePath);
        try {
          console.log("Config file: ",  deserializeJson(exercisePath, 'RefactoringGameExerciseConfiguration'));
          return deserializeJson(exercisePath, 'RefactoringGameExerciseConfiguration');
        } catch (e) {
          const error = `Failed to read ${exerciseId} Config file`;
          console.error(`${error}: ${e.message}`);
          return new Map([['message', error]]);
        }
    }
  } else {
    const error = 'File not found';
    console.error(error);
    return new Map([['message', error]]);
  }
}

function getJsonFileById(fileId, className) {
  let directory;

  switch (className) {
    case "CheckGameExerciseConfig":
      directory = process.env.ROOT_PATH + "src/external/compiler/LocalExercises" + "\\ExerciseDB\\CheckSmellGame\\";
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
    return new Map([['message', error]]);
  }
}

module.exports = {
  cloneRepository,
  getAllJsonFilesInDirectory,
  getRefactoringExerciseFile,
  getJsonFileById
}
