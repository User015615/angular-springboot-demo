import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';

import { routes } from './app.routes';

export const appConfig: ApplicationConfig = {
  providers: [
    provideHttpClient(), // Correctly invoking the function to get the provider
    provideZoneChangeDetection({ eventCoalescing: true }), // Correctly invoking the function to get the provider
    provideRouter(routes) // Correctly invoking the function to get the provider
  ]
};
