import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LearningPageRouteComponent } from './learning-page-route.component';

describe('EdicationalPageComponent', () => {
  let component: LearningPageRouteComponent;
  let fixture: ComponentFixture<LearningPageRouteComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LearningPageRouteComponent]
    });
    fixture = TestBed.createComponent(LearningPageRouteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
