export class SolutionComment {
  commentId!: number;
  commentAuthor!: string;
  commentText!: string;
  solutionId!: number;
  creationTimestamp!: Date;

  constructor(commentAuthor: string, commentText: string) {
    this.commentAuthor = commentAuthor;
    this.commentText = commentText;
  }
}
