import {Question} from "../question/question.model";

export class MissionConfiguration {
  id!: string;
  name!: string;
  tag!: string;
  badge!: string;
  badgeFilename!: string;
  unlockAfter!: string[];
  steps!: MissionStep[];
}

export class MissionStep {
  id!: string;
  type!: string;
}

export class MissionStatus {
  missionId!: string;
  steps!: number;

  constructor(missionId: string, steps: number) {
    this.missionId = missionId;
    this.steps = steps;
  }
}
