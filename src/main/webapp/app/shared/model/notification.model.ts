import { Moment } from 'moment';

export interface INotification {
  id?: string;
  userName?: string;
  userPictureUrl?: string;
  userProfileUrl?: string;
  text?: string;
  seen?: boolean;
  date?: Moment;
}

export class Notification implements INotification {
  constructor(
    public id?: string,
    public userName?: string,
    public userPictureUrl?: string,
    public userProfileUrl?: string,
    public text?: string,
    public seen?: boolean,
    public date?: Moment
  ) {}
}
