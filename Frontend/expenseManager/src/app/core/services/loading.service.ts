import { Injectable } from '@angular/core';
import { LoadingController } from '@ionic/angular';

@Injectable({ providedIn: 'root' })
export class LoadingService {
  private loadingCount = 0;
  private current?: HTMLIonLoadingElement;
  private lastShownAt: number | null = null;
  private watchdogTimer: any;
  private readonly WATCHDOG_MS = 15000; // 15s
  private debug = true;

  constructor(private loadingCtrl: LoadingController) {}

  async show(message = 'Loading...') {
    this.loadingCount++;
    if (this.debug)
      console.debug('[LoadingService] show()', {
        loadingCount: this.loadingCount,
      });

    if (this.current) {
      // already shown
      return;
    }

    this.current = await this.loadingCtrl.create({
      message,
      spinner: 'crescent',
      backdropDismiss: false,
    });

    await this.current.present();

    this.lastShownAt = Date.now();
    this.clearWatchdog();
    this.startWatchdog();

    // If hide() was called while we were creating/presenting the loader,
    // loadingCount may have reached 0. In that case dismiss immediately to
    // avoid a stuck loader (race between show() and hide()).
    if (this.loadingCount === 0 && this.current) {
      try {
        await this.current.dismiss();
      } catch (e) {
        // ignore if already dismissed
      }
      this.current = undefined;
      this.lastShownAt = null;
      this.clearWatchdog();
    }
  }

  async hide() {
    if (this.loadingCount > 0) {
      this.loadingCount--;
    }
    if (this.debug)
      console.debug('[LoadingService] hide()', {
        loadingCount: this.loadingCount,
      });

    if (this.loadingCount === 0 && this.current) {
      try {
        await this.current.dismiss();
      } catch (e) {
        console.warn('[LoadingService] dismiss failed in hide()', e);
      }
      this.current = undefined;
      this.lastShownAt = null;
      this.clearWatchdog();
    }
  }

  async reset() {
    if (this.debug) console.debug('[LoadingService] reset()');
    this.loadingCount = 0;
    if (this.current) {
      try {
        await this.current.dismiss();
      } catch (e) {
        console.warn('[LoadingService] dismiss failed in reset()', e);
      }
      this.current = undefined;
    }
    this.lastShownAt = null;
    this.clearWatchdog();
  }

  private startWatchdog() {
    if (this.watchdogTimer) return;
    this.watchdogTimer = setTimeout(async () => {
      if (this.current && this.loadingCount > 0) {
        console.warn(
          '[LoadingService] Watchdog: loader present for >' +
            this.WATCHDOG_MS +
            'ms, forcing reset',
          {
            loadingCount: this.loadingCount,
            lastShownAt: this.lastShownAt,
          }
        );
        await this.reset();
      } else if (this.current && this.loadingCount === 0) {
        console.warn(
          '[LoadingService] Watchdog: loader present but loadingCount=0, dismissing',
          {
            loadingCount: this.loadingCount,
          }
        );
        try {
          await this.current.dismiss();
        } catch (e) {
          // ignore
        }
        this.current = undefined;
        this.lastShownAt = null;
        this.clearWatchdog();
      } else {
        this.clearWatchdog();
      }
    }, this.WATCHDOG_MS);
  }

  private clearWatchdog() {
    if (this.watchdogTimer) {
      clearTimeout(this.watchdogTimer);
      this.watchdogTimer = undefined;
    }
  }
}
