import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CheckSmellStatisticsComponent } from './check-smell-statistics.component';

describe('ChecksmellSolutionComponent', () => {
  let component: CheckSmellStatisticsComponent;
  let fixture: ComponentFixture<CheckSmellStatisticsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CheckSmellStatisticsComponent]
    });
    fixture = TestBed.createComponent(CheckSmellStatisticsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
