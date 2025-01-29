import { Component, OnInit, ViewChild } from '@angular/core';
import Chart from 'chart.js/auto';
import { HttpClient } from '@angular/common/http';
import { TestService } from 'src/app/services/test/test.service';
import { User } from 'src/app/model/user/user.model';
import { UserService } from 'src/app/services/user/user.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TranslateService } from '@ngx-translate/core';
import { TestHistory } from 'src/app/model/test-history';
import { Subject, takeUntil, tap } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-test-history',
  templateUrl: './test-history.component.html',
  styleUrls: ['./test-history.component.css']
})
export class TestHistoryComponent implements OnInit {

  public testHistory: TestHistory[] = []
  private chartInstance: Chart | null = null;
  user!: User;

  public testScores: number[] = [];
  public testDates: string[] = [];
  private destroy$ = new Subject<void>();
    


  constructor(
    private testService: TestService,
    private userService: UserService,
    private snackBar: MatSnackBar,
    private translate: TranslateService,
    private router: Router
  ) { }


  ngOnInit(): void {
    this.loadTestHistory();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
    if (this.chartInstance) {
      this.chartInstance.destroy();
    }
  }


  loadTestHistory(): void {
    this.userService.getCurrentUser().pipe(
      tap((user): void => {
        this.user = user;
      }, 
      takeUntil(this.destroy$))
    ).subscribe();

    this.testService.loadTestHistoryFromServer(this.user.userId)
          .pipe(
            tap((testHistory : TestHistory[]) => {
              this.testHistory = [...testHistory];
              this.testScores = testHistory.map(test => test.totalScore); 
              this.testDates = testHistory.map(test => new Date(test.date).toLocaleDateString());

              console.log('testh', this.testHistory);
              //this.renderScoreChart();
    
            }),
            takeUntil(this.destroy$)
          )
          .subscribe({
            error: (err) => console.error('Errore durante il caricamento della cronologia dei test:', err)
          });
  }


  renderScoreChart(): void {
    const canvas = document.getElementById('scoreChart') as HTMLCanvasElement;
    
    if (!canvas) {
      console.error('Canvas element with ID "scoreChart" not found.');
      return;
    }

    if (this.chartInstance) {
      this.chartInstance.destroy();
    }


    this.chartInstance = new Chart(canvas, {
      type: 'line',
      data: {
        labels: this.testDates,
        datasets: [
          {
            label: 'Total Score',
            data: this.testScores,
            borderColor: 'rgba(75, 192, 192, 1)',
            backgroundColor: 'rgba(75, 192, 192, 0.2)',
            borderWidth: 2
          }
        ]
      },
      options: {
        responsive: true,
        plugins: {
          legend: {
            display: true
          }
        },
        scales: {
          x: {
            title: {
              display: true,
              text: 'Data'
            }
          },
          y: {
            title: {
              display: true,
              text: 'Score'
            },
            beginAtZero: true
          }
        }
      }
    });
  }



  deleteHistory() {
    const message = this.translate.currentLang == 'it' ? 'Vuoi eliminare la cronologia anche dal server?' : 'Do you also want to delete the history from the server?'
    const userConfirmed = confirm(message);
    if (userConfirmed) {
      this.userService.getCurrentUser().subscribe((user: User | any) => {
        this.user = user;
      });
      this.testService.removeTestFromServer(this.user.userId).subscribe(
        () => {
          this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
            this.router.navigate(['test-history']);
          });

          const message = this.translate.currentLang == 'it' ? 'Cronologia eliminata con successo.' : 'History successfully deleted.'
          this.showNotification(message);
        },
        (error) => {
          const errorMessage = this.translate.currentLang == 'it' 
          ? 'Si è verificato un errore durante l\'eliminazione della cronologia: ' 
          : 'An error occurred while deleting the history: ';
        alert(errorMessage + error.message);        }
      );
    }
  }


  showNotification(message: string, action: string = 'Close') {
    this.snackBar.open(message, action, {
      duration: 3000,
      horizontalPosition: 'center',
      verticalPosition: 'bottom'
    });
  }

  ngAfterViewInit(): void {
    this.renderScoreChart();
  }
  

}
