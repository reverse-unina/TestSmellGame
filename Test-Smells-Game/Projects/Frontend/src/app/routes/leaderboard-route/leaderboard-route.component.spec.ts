import {ComponentFixture, TestBed} from '@angular/core/testing';

import {LeaderboardRouteComponent} from './leaderboard-route.component';

describe('LeaderboardRouteComponent', () => {
  let component: LeaderboardRouteComponent;
  let fixture: ComponentFixture<LeaderboardRouteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LeaderboardRouteComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LeaderboardRouteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
