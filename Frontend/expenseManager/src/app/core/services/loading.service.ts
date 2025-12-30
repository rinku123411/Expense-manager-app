import { Injectable } from '@angular/core';
import { LoadingController } from '@ionic/angular';

@Injectable({ providedIn: 'root' })
export class LoadingService {
  private loadingCount = 0;
  private current?: HTMLIonLoadingElement;

  constructor(private loadingCtrl: LoadingController) {}

  async show(message = 'Loading...') {
    this.loadingCount++;
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
  }

  async hide() {
    if (this.loadingCount > 0) {
      this.loadingCount--;
    }

    if (this.loadingCount === 0 && this.current) {
      try {
        await this.current.dismiss();
      } catch (e) {
        // ignore if already dismissed
      }
      this.current = undefined;
    }
  }

  async reset() {
    this.loadingCount = 0;
    if (this.current) {
      try {
        await this.current.dismiss();
      } catch (e) {}
      this.current = undefined;
    }
  }
}
