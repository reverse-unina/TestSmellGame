import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MissionsListRouteComponent } from './missions-list-route.component';

describe('MissionsListRouteComponent', () => {
  let component: MissionsListRouteComponent;
  let fixture: ComponentFixture<MissionsListRouteComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MissionsListRouteComponent]
    });
    fixture = TestBed.createComponent(MissionsListRouteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
