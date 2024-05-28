export class Repository {

  branchName!: string;
  url!: string;

  constructor(branchName: string, url: string) {
    this.branchName = branchName;
    this.url = url;
  }
}
