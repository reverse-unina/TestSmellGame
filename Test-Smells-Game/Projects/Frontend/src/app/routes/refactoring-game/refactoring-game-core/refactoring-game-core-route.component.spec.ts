import {ComponentFixture, TestBed} from '@angular/core/testing';

import {RefactoringGameCoreRouteComponent} from './refactoring-game-core-route.component';

describe('CompilerrouteComponent', () => {
  let component: RefactoringGameCoreRouteComponent;
  let fixture: ComponentFixture<RefactoringGameCoreRouteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RefactoringGameCoreRouteComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RefactoringGameCoreRouteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
