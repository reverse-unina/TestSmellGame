import {Component, Input, OnInit} from '@angular/core';
import {SolutionComment} from "../../model/comment/comment";
import {NgForm} from "@angular/forms";
import {LeaderboardService} from "../../services/leaderboard/leaderboard.service";
import {UserService} from "../../services/user/user.service";

@Component({
  selector: 'app-solution',
  templateUrl: './solution.component.html',
  styleUrls: ['./solution.component.css'],
})
export class SolutionComponent implements OnInit {
  @Input() playerName!: string;
  @Input() comments!: SolutionComment[];
  @Input() editedCode!: string;
  @Input() exerciseCode!: string;
  @Input() solutionId!: number;
  @Input() creationTimestamp!: string;
  @Input() upVotes!: number;
  @Input() downVotes!: number;
  @Input() data_bs_target!: string;
  @Input() score!: number;
  @Input() refactoringResult!: boolean;
  @Input() smells!: string;
  @Input() isAutoValutative!: boolean;

  commentForm: any;
  constructor(private leaderboardService: LeaderboardService, private userService: UserService) { }
  voteUp: boolean = false;
  voteDown: boolean = false;
  userVote = 0;

  smellResult: string[] = [];
  smellList: string[] = [];
  methodList: string[] = []


  ngOnInit(): void {
    if(!this.isAutoValutative){
      this.leaderboardService.getVoteForUser(this.solutionId, this.userService.user.getValue().userId).subscribe(vote =>{
        if(vote == "UP"){
          this.voteUp = true;
          this.userVote = 1;
        }
        else if(vote == "DOWN"){
          this.voteDown = true;
          this.userVote = 2;
        }
      })
      const json = JSON.parse(this.smells);
      this.smellList = Object.keys(json);
      this.smellResult = Object.values(json);
      for (let i=0;i<this.smellResult.length;i++){
        this.methodList.push(JSON.parse(JSON.stringify(this.smellResult[i])).methods)
      }
    }
  }

  writeComment(commentForm: NgForm) {
    this.leaderboardService.postComment(commentForm.value.comment, this.solutionId, this.userService.user.getValue().userName).subscribe();
    let comment = new SolutionComment(this.userService.user.getValue().userName, commentForm.value.comment);
    this.comments.push(comment);
    commentForm.value.comment = "";
  }

  vote(number: number) {
    if(number == 1 && !this.voteUp){
      this.voteUp = true;
      this.voteDown = false;
      this.leaderboardService.voteSolution(this.solutionId, this.userService.user.getValue().userId,"UP").subscribe();
      this.upVotes = this.upVotes + 1;
      if(this.userVote == 2){
        this.downVotes = this.downVotes - 1;
        this.userVote = 1;
      }
    }else if(number == 2 && !this.voteDown){
      this.voteUp = false;
      this.voteDown = true;
      this.leaderboardService.voteSolution(this.solutionId, this.userService.user.getValue().userId,"DOWN").subscribe();
      this.downVotes = this.downVotes + 1;
      if(this.userVote == 1){
        this.upVotes = this.upVotes - 1;
        this.userVote = 2;
      }
    }
  }
}
