import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AchievementAlertComponent } from './achievement-alert.component';

describe('AchivementAlertComponent', () => {
  let component: AchievementAlertComponent;
  let fixture: ComponentFixture<AchievementAlertComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AchievementAlertComponent]
    });
    fixture = TestBed.createComponent(AchievementAlertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
