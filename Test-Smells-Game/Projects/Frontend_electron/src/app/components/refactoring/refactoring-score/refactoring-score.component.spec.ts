import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RefactoringScoreComponent } from './refactoring-score.component';

describe('RefactoringScoreComponent', () => {
  let component: RefactoringScoreComponent;
  let fixture: ComponentFixture<RefactoringScoreComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RefactoringScoreComponent]
    });
    fixture = TestBed.createComponent(RefactoringScoreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
