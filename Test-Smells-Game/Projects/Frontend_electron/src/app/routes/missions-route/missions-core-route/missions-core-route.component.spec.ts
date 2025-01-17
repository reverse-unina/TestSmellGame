import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MissionsCoreRouteComponent } from './missions-core-route.component';

describe('MissionsCoreRouteComponent', () => {
  let component: MissionsCoreRouteComponent;
  let fixture: ComponentFixture<MissionsCoreRouteComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MissionsCoreRouteComponent]
    });
    fixture = TestBed.createComponent(MissionsCoreRouteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
