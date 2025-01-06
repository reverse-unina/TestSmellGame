import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RefactoringCompilationResultsComponent } from './refactoring-compilation-results.component';

describe('RefactoringCompilationResultsComponent', () => {
  let component: RefactoringCompilationResultsComponent;
  let fixture: ComponentFixture<RefactoringCompilationResultsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RefactoringCompilationResultsComponent]
    });
    fixture = TestBed.createComponent(RefactoringCompilationResultsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
