import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CheckSmellQuestionComponent } from './check-smell-question.component';

describe('CheckSmellQuestionComponent', () => {
  let component: CheckSmellQuestionComponent;
  let fixture: ComponentFixture<CheckSmellQuestionComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CheckSmellQuestionComponent]
    });
    fixture = TestBed.createComponent(CheckSmellQuestionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
