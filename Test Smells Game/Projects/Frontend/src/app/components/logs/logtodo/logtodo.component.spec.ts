import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LogtodoComponent } from './logtodo.component';

describe('LogtodoComponent', () => {
  let component: LogtodoComponent;
  let fixture: ComponentFixture<LogtodoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LogtodoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LogtodoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
