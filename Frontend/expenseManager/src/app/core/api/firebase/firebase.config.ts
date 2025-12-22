// Import the functions you need from the SDKs you need
import { initializeApp } from 'firebase/app';
import { getAnalytics } from 'firebase/analytics';
import { getAuth } from 'firebase/auth';
import { getFirestore } from 'firebase/firestore';
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: 'AIzaSyDeFzYXKniBp2Q02ho3oKz3ZNJ76quJmTI',
  authDomain: 'expense-manager-50a7b.firebaseapp.com',
  projectId: 'expense-manager-50a7b',
  storageBucket: 'expense-manager-50a7b.firebasestorage.app',
  messagingSenderId: '77449771156',
  appId: '1:77449771156:web:4aeb31fb583873321e5c81',
  measurementId: 'G-ZQXDZCDC94',
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const analytics = getAnalytics(app);
export const auth = getAuth(app);
export const db = getFirestore(app);
