export interface ToolConfig {
  expValues: number[];
  badgeValues: [
    {
      name: string;
      description: string;
      points: number;
      filename: string;
    }
  ];
  answerPercentage: number;
  logTries: boolean;
}
