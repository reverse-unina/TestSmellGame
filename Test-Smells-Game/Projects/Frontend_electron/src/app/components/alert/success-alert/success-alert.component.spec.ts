import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SuccessAlertComponent } from './success-alert.component';

describe('AlertComponent', () => {
  let component: SuccessAlertComponent;
  let fixture: ComponentFixture<SuccessAlertComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SuccessAlertComponent]
    });
    fixture = TestBed.createComponent(SuccessAlertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
