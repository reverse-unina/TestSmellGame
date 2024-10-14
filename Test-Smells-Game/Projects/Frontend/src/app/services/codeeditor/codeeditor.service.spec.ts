import {TestBed} from '@angular/core/testing';

import {CodeeditorService} from './codeeditor.service';

describe('CodeeditorService', () => {
  let service: CodeeditorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CodeeditorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
