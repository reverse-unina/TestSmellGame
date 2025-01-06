import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MissionProgressComponent } from './mission-progress.component';

describe('MissionProgressComponent', () => {
  let component: MissionProgressComponent;
  let fixture: ComponentFixture<MissionProgressComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MissionProgressComponent]
    });
    fixture = TestBed.createComponent(MissionProgressComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
