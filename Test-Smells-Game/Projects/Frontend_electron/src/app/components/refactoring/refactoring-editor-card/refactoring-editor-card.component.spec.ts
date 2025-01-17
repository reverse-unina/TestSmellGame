import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RefactoringEditorCardComponent } from './refactoring-editor-card.component';

describe('RefactoringEditorCardComponent', () => {
  let component: RefactoringEditorCardComponent;
  let fixture: ComponentFixture<RefactoringEditorCardComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RefactoringEditorCardComponent]
    });
    fixture = TestBed.createComponent(RefactoringEditorCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
