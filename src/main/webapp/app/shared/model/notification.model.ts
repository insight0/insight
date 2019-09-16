import { Moment } from 'moment';

export interface INotification {
  id?: string;
  userName?: string;
  userPictureUrl?: string;
  userProfileUrl?: string;
  text?: string;
  date?: Moment;
}

export class Notification implements INotification {
  constructor(
    public id?: string,
    public userName?: string,
    public userPictureUrl?: string,
    public userProfileUrl?: string,
    public text?: string,
    public date?: Moment
  ) {}
}
