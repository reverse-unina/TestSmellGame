import {ComponentFixture, TestBed} from '@angular/core/testing';

import {RefactoringGameExListRouteComponent} from './refactoring-game-ex-list-route.component';

describe('RefactoringGameRouteComponent', () => {
  let component: RefactoringGameExListRouteComponent;
  let fixture: ComponentFixture<RefactoringGameExListRouteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RefactoringGameExListRouteComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RefactoringGameExListRouteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
