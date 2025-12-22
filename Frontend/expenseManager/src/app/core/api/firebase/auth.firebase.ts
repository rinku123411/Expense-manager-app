import { signInWithEmailAndPassword } from 'firebase/auth';
import { auth } from './firebase.config';
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class FirebaseAuthService {
  login(email: string, password: string) {
    return signInWithEmailAndPassword(auth, email, password);
  }
}
