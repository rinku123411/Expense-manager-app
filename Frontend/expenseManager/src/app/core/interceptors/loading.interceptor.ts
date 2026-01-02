import { Injectable } from '@angular/core';
import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';
import { LoadingService } from '../services/loading.service';

@Injectable()
export class LoadingInterceptor implements HttpInterceptor {
  constructor(private loadingService: LoadingService) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    // Optionally exclude certain URLs (e.g., static assets).
    const url = req.url;
    if (url.includes('/assets/') || url.match(/\.(png|jpg|svg|json)$/i)) {
      return next.handle(req);
    }

    // Show global loading
    console.debug('[LoadingInterceptor] start', url);
    void this.loadingService.show();

    return next.handle(req).pipe(
      finalize(() => {
        void this.loadingService.hide();
        console.debug('[LoadingInterceptor] end', url);
      })
    );
  }
}
