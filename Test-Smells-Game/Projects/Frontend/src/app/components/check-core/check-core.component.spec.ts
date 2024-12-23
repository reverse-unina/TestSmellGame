import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CheckCoreComponent } from './check-core.component';

describe('CheckCoreComponent', () => {
  let component: CheckCoreComponent;
  let fixture: ComponentFixture<CheckCoreComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CheckCoreComponent]
    });
    fixture = TestBed.createComponent(CheckCoreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
