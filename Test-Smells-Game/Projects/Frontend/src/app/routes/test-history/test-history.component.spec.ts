import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TestHistoryComponent } from './test-history.component';

describe('TestHistoryComponent', () => {
  let component: TestHistoryComponent;
  let fixture: ComponentFixture<TestHistoryComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TestHistoryComponent]
    });
    fixture = TestBed.createComponent(TestHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
