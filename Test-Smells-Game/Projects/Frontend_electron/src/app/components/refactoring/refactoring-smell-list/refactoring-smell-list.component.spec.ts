import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RefactoringSmellListComponent } from './refactoring-smell-list.component';

describe('RefactoringSmellListComponent', () => {
  let component: RefactoringSmellListComponent;
  let fixture: ComponentFixture<RefactoringSmellListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RefactoringSmellListComponent]
    });
    fixture = TestBed.createComponent(RefactoringSmellListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
