export interface levelConfig {
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
}
