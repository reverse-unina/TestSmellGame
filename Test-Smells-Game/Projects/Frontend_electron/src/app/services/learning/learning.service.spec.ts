import { TestBed } from '@angular/core/testing';

import { LearningService } from './learning.service';

describe('LearningService', () => {
  let service: LearningService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LearningService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
