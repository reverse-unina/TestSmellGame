## Game modes

TestSmellGame has 3 game modes:
- Free play, where a user can practice solving refactoring and check-smell exercises by choosing from those made available. Each exercise can be solved as many times as desired;
- Assignment/experiments, where a user has a maximum time within which to complete the assigned refactoring and/or check-smell exercises. Each exercise can be solved only once. Once handed in, the user will no longer be able to access it. This mode is recommended for teachers that want to organize practical lectures or activities with his students;
- Missions, where a user can try to solve a series of exercises in sequence, possibly including also educational contents. To access the next step, the user must have successfully completed the previous step. Each step can be repeated indefinitely until it is passed. However, once passed, it will no longer be possible to go back. Once the mission is completed, the user will no longer be able to access it.

## Calculating scores and experience points
### Refactoring exercises
A refactoring exercise is considered successfully completed if:
- The number of remaining test smells in the test class is less than or equal to the number of allowed smells (property `smells_allowed` in `{Filename}config.json`);
- The code coverage of the refactored test is equal to that of the original test class (minus a possible loss in percentage fixed by the property `refactoring_limit` in `{Filename}config.json`).

If these conditions are met, the user will be assigned a score calculated as:

$$ Score = smells\\_allowed - smells\\_remaining $$

Three scenarios are possible:
- If the exercise was completed for the first time, the user will get experience points, will increase his score for the "refactoring" mode and will be assigned a score for the solved exercise equal to the obtained score;
- If the exercise has been completed previously and the score has already assigned in the past, the user will gain experience points and increase his or her score for the "refactoring" mode by the difference between the new score and the previous one. The score for the exercise will be updated with the new score;
- If the exercise has been completed previously and the score obtained is equal to or lower than the previous one, the experience points and scores will not be updated.

### Check-smell exercises
A check-smell exercise is considered to have been successfully completed if:
- The score obtained is equal to or higher than the `answerPercentage` field in the `levelConfig.json` file. The score is calculated as follows:

$$ Score = \frac{\sum{correct\\_answers} - 0.5 \cdot \sum{incorrect\\_answers}}{total_correct\\_answers} \cdot 100 $$

Successfully completing an exercise will earn the user 1 experience point and will increase his "check-game" score by the same amount. If he successfully complete the exercise again, he will not earn any new points.

### Missions
A mission is considered completed (successfully) when the user has successfully completed all of its steps. The score assigned is calculated as follows:

$$ Score = \sum{step\\_refactoring} + \sum{step\\_check-smell} $$

This score will help determine their "missions" ranking. Additionally, at the end of a mission, he will unlock the associated badge, visible on their user profile page.

## Experience points and user level
Upon reaching a certain number of experience points, the user:
- Will level up, unlocking new exercises of increasing difficulty;
- Will unlock new badges visible on his user page.