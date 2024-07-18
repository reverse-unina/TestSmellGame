import {ComponentFixture, TestBed} from '@angular/core/testing';

import {CheckGameCoreRouteComponent} from './check-game-core-route.component';

describe('CheckGameCoreRouteComponent', () => {
  let component: CheckGameCoreRouteComponent;
  let fixture: ComponentFixture<CheckGameCoreRouteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CheckGameCoreRouteComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CheckGameCoreRouteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
