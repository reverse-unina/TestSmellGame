import { TestBed } from '@angular/core/testing';

import { CheckSmellService } from './check-smell.service';

describe('CheckSmellService', () => {
  let service: CheckSmellService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CheckSmellService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
