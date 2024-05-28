import {ComponentFixture, TestBed} from '@angular/core/testing';

import {SettingsRouteComponent} from './settings-route.component';

describe('SettingsRouteComponent', () => {
  let component: SettingsRouteComponent;
  let fixture: ComponentFixture<SettingsRouteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SettingsRouteComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SettingsRouteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
