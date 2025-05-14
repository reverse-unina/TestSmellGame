## Internal Information for Teachers that want create new contents for the Game

The Exercise Service interacts with the following groups of files: exercises (both refactoring and check smell), content of educational pages, assignments, missions, badges and levelConfig.

Each group is associated with a specific directory, the list of which is reported below:
- `ExerciseDB/RefactoringGame/`: directory containing the refactoring exercises;
- `ExerciseDB/CheckSmellGame/`: directory containing the check-smell exercises;
- `ExerciseDB/LearningContent/`: directory for the content of learning pages;
- `/usr/src/app/assets/assignments/`: directory containing the assignments;
- `/usr/src/app/assets/missions/`: directory containing the missions;
- `/usr/src/app/assets/toolConfig/`: directory containing the file `toolConfig.json`;
- `/usr/src/app/assets/badges/`: directory containing badges in `.png` format.

## Refactoring exercises
Each refactoring exercise consists of 3 files:
- `{Filename1}.java`: file containing the source code of the Java class under test;
- `{Filename2}Test.java`: file containing the source code of the test class;
- `{Filename3}Config.json`: exercise configuration file.

The 3 files must be grouped within the same directory. There is no constraint on the names of the files and the folder that contains them but it is recommended that:
- `{Filename1}` corresponds to the name of the Java class it contains;
- `{Filename2}Test` corresponds to the name of the test Java class it contains;
- `{Filename1}` and `{Filename2}` correspond.

The configuration file must be structured as follows:
```json
{
  "exerciseId": string,
  "class_name": string,
  "refactoring_game_configuration": {
    "dependencies": [
      string
    ],
    "refactoring_limit": number,
    "smells_allowed": number,
    "level": number,
    "ignored_smells": [
      string
    ]
  },
  "availableForGame": boolean
  "auto_valutative": boolean
}
```

where:
- `exerciseId` represents the exercise identifier. This must be unique within the `ExerciseDB/RefactoringGame/` directory;
- `class_name` represents the name of the source code of the Java class under test of the exercise and must be identical to the name of the class reported in the file `{nomeFile1}.java`;
- `refactoring_game_configuration` contains the exercise configuration;
- `dependencies` represents the dependencies needed by the Compiler Service to compile the exercise;
- `refactoring_limit` represents the maximum loss (in percentage) of code coverage accepted in the refactoring of the test code;
- `smells_allowed` represents the maximum number of smells still present in the code acceptable to consider the exercise passed;
- `level` represents the minimum level that the user account must have to enable the exercise;
- `ignored_smells` represents the smell types not considered in this exercise;
- `available_for_game` indicates whether the exercise is available in free play mode;
- `auto_valutative` indicates whether the user can view (`false` - collaborative mode) or not (`true` - competitive mode) the solutions of the exercise uploaded by other users on the leaderboard.

## Check-smell exercises

Each check-smell exercise consists of only 1 file:
- `{Filename}Config.json`: exercise configuration file.

It is not required that the configuration file be placed in a subdirectory of `ExerciseDB/CheckSmellGame/`, as it is not required that it be named `{Filename}`. However, it is recommended to create a separate directory `{Filename}` with `{Filename}Config.json` inside it.

The configuration file must be structured as follows:
```json
{
  "exerciseId": string,
  "check_game_configuration": {
    "questions": [
      {
        "questionTitle": string,
        "questionCode": string,
        "answers": [
          {
            "answerText": string,
            "isCorrect": boolean
          }
        ]
      }
    ],
    "level": 1
  },
  "available_for_game": boolean
  "auto_valutative": boolean
}
```

where:
- `exerciseId` represents the exercise identifier. This must be unique within the `ExerciseDB/CheckSmellGame/` directory;
- `check_game_configuration` contains the exercise configuration. Specifically:
- `questions` represents the list of questions for the exercise;
- `questionTitle` represents the title of the question;
- 'questionCode' represents the Java code block that is the subject of the question;
- `answers` represents the list of multiple choice answers for the question;
- `answerText` represents the text of the answer;
- `isCorrect` represents the correctness of the answer.
- `level` represents the minimum level that the user's account must have to enable the exercise;
- `available_for_game` indicates whether the exercise is available in free play mode.

## Learning pages
Each learning page is composed of only 1 file:
- `{Filename}Config.json`: content file of the educational page.

It is not required that the content file be placed in a subdirectory of `ExerciseDB/LearningContent/`, as it is not required that it be named `{Filename}`. However, it is recommended to create a separate directory `{Filename}` with `{Filename}Config.json` inside it.

The configuration file must be structured as follows:
```json
{
  "learningId": string,
  "title": string,
  "content": string,
  "external_reference": string
}
```

where:
- `leaningId` represents the page identifier. This must be unique within the `ExerciseDB/LearningContent/` directory;
- `title` represents the title of the content that will be displayed;
- `content` represents the learning content to be displayed;
- `external_reference` represents a link to an external resource that the user can access from the learning page to learn more about the topic. This is an optional field.

## Assignments/Experiments
Each assignment consists of only 1 file:
- `{Filename}.json`: assignment configuration file.

It is not required that the content file be placed in a subdirectory of `/usr/src/app/assets/assignments/`, as it is not required that it be named `{Filename}`. For continuity with the previous version, it is advisable to place the file within the path `/usr/src/app/assets/assignments/` without using subdirectories.

The configuration file must be structured as follows:
```json
{
  "assignmentId": string,
  "gameType": string,
  "students": [
    {
      "name": string,
      "exerciseId": string,
      "startTime": string,
      "startDate": string,
      "endTime": string,
      "endDate": string,
      "submitted": boolean
    }
  ],
  "type": ["competitive", "collaborative"]
}
```

where:
- `assignmentId` represents the assignment identifier. This must be unique within the `/usr/src/app/assets/assignments/` directory;
- `gameType` represents the type of exercises assigned with the assignment. This can have the value `refactoring`, to indicate that the exercises are of the refactoring type, or `smell-check`, to indicate that the exercises are of the smell-check type;
- `students` represents the list of students/users to whom the assignment is assigned and the respective exercises of the chosen type assigned;
- `name` represents the username of the user selected for the assignment;
- `exerciseId` represents the identifier of the exercise assigned to the user. The value must match the `exerciseId` of one of the exercises present within `ExerciseDB/`;
- `startTime` represents the assignment start time, from which the user can participate. It must be represented in the form `hh-mm`;
- `startDate` represents the assignment start date, from which the user can participate. It must be represented in the form `dd-mm-yy`;
- `endTime` represents the assignment end time, after which the user will no longer be able to participate. It must be represented in the form `hh-mm`;
- `endDate` represents the assignment end date, after which the user will no longer be able to participate. It must be represented in the form `dd-mm-yy`;
- `submitted` indicates whether the user has submitted the exercise or not. Once the user has submitted the exercise, they will no longer be able to access the assignment;
- `type` indicates whether the solution to the exercise should be published on the solution repository and be visible to users ("collaborative") or not ("competitive").

## Missions
Each mission consists of only 1 file:
- `{Filename}.json`: mission configuration file.

It is not required that the content file be placed in a subdirectory of `/usr/src/app/assets/missions/`, as it is not required that it be named `{Filename}`. For continuity with the previous version, it is advisable to place the file inside the path `/usr/src/app/assets/missions/` without using subdirectories.

The configuration file must be structured as follows:
```json
{
  "missionId": string,
  "name": string,
  "tag": string,
  "badge": string,
  "badge_filename": string,
  "unlock_after": [string]
  "steps": [
    {
      "type": string,
      "id": string
    },
  ]
}
```

where:
- `missionId` represents the mission identifier. This must be unique within the `/usr/src/app/assets/missions/` directory;
- `name` represents the name of the mission. It is not required to be identical to `missionId`;
- `tag` is used to group multiple missions together (e.g. "Tutorial"). This is an **optional** field. If the mission does not contain a `tag`, it will be associated with the "Other Missions" group;
- `badge` represents the name or description of the badge that will be unlocked by the user upon completion of the mission;
- `badge_filename` represents the name of the `.png` file that represents the badge, located in the `/usr/src/app/assets/badges/` directory;
- `unlock_after` represents the list of missions that the user must have completed to unlock the mission in question. It is specified as a list of `missionId` of the missions to complete. This is an **optional** field. If the mission does not contain `unlock_after`, it will be automatically unlocked for all users;
- `steps` describes the list of steps to complete to pass the mission;
- `type` represents the type of step, which can correspond to a refactoring-type exercise, a check-smell-type exercise or the viewing of an educational page. It can assume three values, respectively: `refactoring`, `check-smell` and `learning`;
- `id` represents the identifier of the file associated with the step. This must therefore match the `exerciseId` of an exercise in `ExerciseDB/RefactoringGame/`, the `exerciseId` of an exercise in `ExerciseDB/ChechSmellGame/`, or the `learningId` field of a file in `ExerciseDB/LearningContent/`, depending on the value associated with `type`.

## ToolConfig
The toolConfig.json file is the configuration file for the tool, containing settings for user leveling and more general exercise settings. It must be structured as follows:
```json
{
  "expValues": [number],
  "badgeValues": 
  [
    {
      "name": string,
      "description": string,
      "points": string,
      "filename": string
    }
  ],
  "answerPercentage": number,
  "logTries": boolean
}
```

where:
- `expValues` represents the list of experience points that the user must reach to go up a level. For example, assuming that the associated value is `[5, 10]`, the user must reach 5 experience points to go up to level 2, while he must reach 10 to go up to level 3;
- `badgeValues` describes the badges that the user can obtain by earning experience points;
- `name` represents the name of the badge that will be unlocked;
- `description` represents the description of the badge.
- `points` represents the number of experience points needed by the user to unlock the associated badge. For continuity with the previous version, it has been kept of type string;
- `filename` represents the name of the `.png` file that represents the badge, located in the directory `/usr/src/app/assets/badges/`;
- `answerPercentage` represents, in percentage, the minimum score that a user must reach in a check-smell exercise in order for it to be considered passed;
- `logTries` indicates whether or not the system should log all the users' attempts to solve the exercises. For refactoring exercises, all the user's compilations will be logged, while for check-smell exercises, all the user's attempts to solve the exercises. The logs will concern the missions and the free play mode.