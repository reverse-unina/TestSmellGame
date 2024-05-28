import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LogknownissuesComponent } from './logknownissues.component';

describe('LogknownissuesComponent', () => {
  let component: LogknownissuesComponent;
  let fixture: ComponentFixture<LogknownissuesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LogknownissuesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LogknownissuesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
