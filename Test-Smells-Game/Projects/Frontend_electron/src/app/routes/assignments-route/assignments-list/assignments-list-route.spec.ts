import {ComponentFixture, TestBed} from '@angular/core/testing';
import {AssignmentsListRoute} from './assignments-list-route';

describe('AssignmentsListRoute', () => {
  let component: AssignmentsListRoute;
  let fixture: ComponentFixture<AssignmentsListRoute>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AssignmentsListRoute ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AssignmentsListRoute);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
