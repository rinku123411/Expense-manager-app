import { Injectable } from '@angular/core';
import { doc, getDoc } from 'firebase/firestore';
import { db } from '../core/api/firebase/firebase.config';

@Injectable({
  providedIn: 'root',
})
export class FirebaseService {
  async getuserById(uid: string) {
    const userRef = doc(db, 'users', uid);
    const snapshot = await getDoc(userRef);
    if(!snapshot.exists()) {
      throw new Error('User not found');
    };
    return snapshot.data();
  }
  
}
