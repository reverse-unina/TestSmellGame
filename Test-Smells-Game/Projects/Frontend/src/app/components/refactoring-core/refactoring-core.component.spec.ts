import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RefactoringCoreComponent } from './refactoring-core.component';

describe('RefactoringCoreComponent', () => {
  let component: RefactoringCoreComponent;
  let fixture: ComponentFixture<RefactoringCoreComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RefactoringCoreComponent]
    });
    fixture = TestBed.createComponent(RefactoringCoreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
