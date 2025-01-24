import { Component, ElementRef, Input, OnChanges, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { BehaviorSubject, filter, Subject, switchMap, takeUntil, tap } from 'rxjs';
import { User } from 'src/app/model/user/user.model';
import { TestService } from 'src/app/services/test/test.service';
import { UserService } from 'src/app/services/user/user.service';
import Chart from 'chart.js/auto';
import { TestHistory } from 'src/app/model/test-history';


@Component({
  selector: 'app-grafico',
  templateUrl: './graphic.component.html',
  styleUrls: ['./graphic.component.css']
})
export class GraficoComponent implements OnInit, OnChanges, OnDestroy {
  /**
   *
   */

  @Input("visualizzaGriglia")
  visualizzaGriglia: boolean = false;
  @Input() testScores: number[] = [];
  @Input() testDates: string[] = [];

  loader: boolean = true;
  private chartInstance: Chart | null = null;
  testHistory: TestHistory[]= [];

  user: User | undefined
  
  private destroy$ = new Subject<void>();
  private loader$ = new BehaviorSubject<boolean>(true);
  $loader = this.loader$.asObservable();

  

  constructor(
    public userService: UserService,
    public testService: TestService
  ) {
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();

    if (this.chartInstance) {
      this.chartInstance.destroy();
    }
  }

  

  ngOnInit(): void {
    this.userService.user.pipe(
      filter(user => !!user),
      switchMap(user => this.testService.loadTestHistoryFromServer(user.userId)
        .pipe(
          tap((testHistory : TestHistory[])=> {
            this.testHistory = [...testHistory]
            this.testScores = testHistory.map(test => test.totalScore);
            this.testDates = testHistory.map(test => new Date(test.date).toLocaleDateString());
            
            this.loader$.next(false);
            //Devi inizializzare la canvas
            //this.renderScoreChart();
          })
        )
      ),
      takeUntil(this.destroy$)
    ).subscribe( {
      error: (err) => {
        console.error('Errore durante il caricamento della cronologia dei test:', err);
        this.loader$.next(false); // Ferma il caricamento in caso di errore
      }
    });
  }


ngOnChanges(): void {
    this.renderScoreChart();
  }
      

  private renderScoreChart(): void {
      const canvas = document.getElementById('scoreChart') as HTMLCanvasElement;
  
      if (!canvas) {
        console.error('Canvas element with ID "scoreChart" not found.');
        return;
      }

      if (this.chartInstance) {
        this.chartInstance.destroy();
        this.chartInstance = null; // Distruggi il grafico precedente per evitare duplicati
      }

      console.log('Canvas:', canvas);
console.log('Test Scores:', this.testScores);
console.log('Test Dates:', this.testDates);
        
  
      this.chartInstance = new Chart(canvas, {
        type: 'line',
        data: {
          labels: this.testDates,
          datasets: [
            {
              label: 'Test Scores',
              data: this.testScores,
              borderColor: '#4caf50',
              backgroundColor: 'rgba(76, 175, 80, 0.2)',
              borderWidth: 2
            }
          ]
        },
        options: {
          responsive: true,
          plugins: {
            legend: {
              display: true,
              position: 'top'
            }
          },
          scales: {
            x: {
              title: {
                display: true,
                text: 'Date'
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


    ngAfterViewInit(): void {
      this.renderScoreChart();
    }
    

}
