import { TestBed } from '@angular/core/testing';

import { RefactoringService } from './refactoring.service';

describe('RefactoringService', () => {
  let service: RefactoringService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RefactoringService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
