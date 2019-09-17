import { Injectable } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { Observable, Observer, Subscription } from 'rxjs';
import { Location } from '@angular/common';

import { CSRFService } from '../auth/csrf.service';
import { AuthServerProvider } from '../auth/auth-jwt.service';

import * as SockJS from 'sockjs-client';
import * as Stomp from 'webstomp-client';

@Injectable({ providedIn: 'root' })
export class JhiTrackerService {
  stompClient = null;
  subscriber = null;
  userNotifSubscriber = null;
  connection: Promise<any>;
  connectedPromise: any;

  listener: Observable<any>;
  listenerObserver: Observer<any>;

  userNotifListener: Observable<any>;
  userNotifListenerObserver: Observer<any>;

  alreadyConnectedOnce = false;
  private subscription: Subscription;

  constructor(
    private router: Router,
    private authServerProvider: AuthServerProvider,
    private location: Location,
    // tslint:disable-next-line: no-unused-variable
    private csrfService: CSRFService
  ) {
    this.connection = this.createConnection();
    this.listener = this.createListener();
    this.userNotifListener = this.createUserNotifListener();
  }

  connect() {
    if (this.connectedPromise === null) {
      this.connection = this.createConnection();
    }
    // building absolute path so that websocket doesn't fail when deploying with a context path
    let url = '/websocket/tracker';
    url = this.location.prepareExternalUrl(url);
    const authToken = this.authServerProvider.getToken();
    if (authToken) {
      url += '?access_token=' + authToken;
    }
    const socket = new SockJS(url);
    this.stompClient = Stomp.over(socket);
    const headers = {};
    this.stompClient.connect(headers, () => {
      this.connectedPromise('success');
      this.connectedPromise = null;
      this.sendActivity();
      if (!this.alreadyConnectedOnce) {
        this.subscription = this.router.events.subscribe(event => {
          if (event instanceof NavigationEnd) {
            this.sendActivity();
          }
        });
        this.alreadyConnectedOnce = true;
      }
    });
  }

  disconnect() {
    if (this.stompClient !== null) {
      this.stompClient.disconnect();
      this.stompClient = null;
    }
    if (this.subscription) {
      this.subscription.unsubscribe();
      this.subscription = null;
    }
    this.alreadyConnectedOnce = false;
  }

  receive() {
    return this.listener;
  }

  receiveUserNotification() {
    return this.userNotifListener;
  }

  sendActivity() {
    if (this.stompClient !== null && this.stompClient.connected) {
      this.stompClient.send(
        '/topic/activity', // destination
        JSON.stringify({ page: this.router.routerState.snapshot.url }), // body
        {} // header
      );
    }
  }

  subscribe() {
    this.connection.then(() => {
      this.subscriber = this.stompClient.subscribe('/topic/tracker', data => {
        this.listenerObserver.next(JSON.parse(data.body));
      });
    });
  }

  subscribeToUserNotification() {
    this.connection.then(() => {
      this.userNotifSubscriber = this.stompClient.subscribe('/topic/user-notification', data => {
        console.log('DATAAAAAAAAAAAA');
        console.log(data);
        this.userNotifListenerObserver.next(JSON.parse(data.body));
      });
    });
  }

  unsubscribeUserNotification() {
    if (this.userNotifSubscriber !== null) {
      this.userNotifSubscriber.unsubscribe();
    }
    this.listener = this.createListener();
  }

  unsubscribe() {
    if (this.subscriber !== null) {
      this.subscriber.unsubscribe();
    }
    this.listener = this.createListener();
  }

  private createListener(): Observable<any> {
    return new Observable(observer => {
      this.listenerObserver = observer;
    });
  }

  private createUserNotifListener(): Observable<any> {
    return new Observable(observer => {
      this.userNotifListenerObserver = observer;
    });
  }

  private createConnection(): Promise<any> {
    return new Promise((resolve, reject) => (this.connectedPromise = resolve));
  }
}
