import {ComponentFixture, TestBed} from '@angular/core/testing';
import {AssignmentsCoreRouteComponent} from './assignmnets-core-route.component';

describe('AssignmentsCoreRouteComponent', () => {
  let component: AssignmentsCoreRouteComponent;
  let fixture: ComponentFixture<AssignmentsCoreRouteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AssignmentsCoreRouteComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AssignmentsCoreRouteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
