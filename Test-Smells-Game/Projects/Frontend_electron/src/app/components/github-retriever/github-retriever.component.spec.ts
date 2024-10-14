import {ComponentFixture, TestBed} from '@angular/core/testing';

import {GithubRetrieverComponent} from './github-retriever.component';

describe('GithubRetrieverComponent', () => {
  let component: GithubRetrieverComponent;
  let fixture: ComponentFixture<GithubRetrieverComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GithubRetrieverComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GithubRetrieverComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
