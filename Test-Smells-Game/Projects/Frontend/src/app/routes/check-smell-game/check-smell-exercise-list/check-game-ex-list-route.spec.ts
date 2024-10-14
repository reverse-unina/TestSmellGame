import {ComponentFixture, TestBed} from '@angular/core/testing';

import {CheckGameExListRoute} from './check-game-ex-list-route';

describe('WritingGameRouteComponent', () => {
  let component: CheckGameExListRoute;
  let fixture: ComponentFixture<CheckGameExListRoute>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CheckGameExListRoute ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CheckGameExListRoute);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
