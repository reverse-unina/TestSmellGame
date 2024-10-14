import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LogelementComponent } from './logelement.component';

describe('LogelementComponent', () => {
  let component: LogelementComponent;
  let fixture: ComponentFixture<LogelementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LogelementComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LogelementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
