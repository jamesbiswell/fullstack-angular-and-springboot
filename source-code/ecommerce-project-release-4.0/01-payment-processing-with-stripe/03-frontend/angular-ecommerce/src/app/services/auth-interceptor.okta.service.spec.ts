import { TestBed } from '@angular/core/testing';

import { AuthInterceptorOktaService } from './auth-interceptor.okta.service';

describe('AuthInterceptorService', () => {
  let service: AuthInterceptorOktaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AuthInterceptorOktaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
