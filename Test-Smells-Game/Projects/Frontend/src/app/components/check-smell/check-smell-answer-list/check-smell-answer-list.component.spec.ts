import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CheckSmellAnswerListComponent } from './check-smell-answer-list.component';

describe('CheckSmellAnswerListComponent', () => {
  let component: CheckSmellAnswerListComponent;
  let fixture: ComponentFixture<CheckSmellAnswerListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CheckSmellAnswerListComponent]
    });
    fixture = TestBed.createComponent(CheckSmellAnswerListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
