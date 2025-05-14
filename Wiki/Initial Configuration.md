## Instructions to evaluate and improve TSGame

TSGame (when executed via docker containers generated with the provided scripts/commands) already has preloaded data at startup, such as users, missions, assignments, badges, learning pages and refactoring/check-smell exercises.

The preloaded user data is as follows:

| Email             | Username | Password |
|-------------------|----------|----------|
| player1@gmail.com | player1  | player1  |
| player2@gmail.com | player2  | player2  |
| player3@gmail.com | player3  | player3  |
| player4@gmail.com | player4  | player4  |

## Upload new files to the Exercise Service docker container
Assuming that the services are run via docker containers, TestSmellGame provides `.bat` scripts for Windows and `.sh` scripts for Linux to automate the upload of new assignments, missions, learning pages or exercises into the container. Specifically:
- `upload_ExerciseDB_files.bat`/`upload_ExerciseDB_files.sh` can be used to upload new check-smell exercises, new refactoring exercises and/or new educational pages;
- `upload_assignment.bat`/`upload_assignment.sh` can be used to upload new assignments;
- `upload_missions.bat`/`upload_missions.sh` can be used to upload new missions.