import { Moment } from 'moment';

export const enum ChangeTrigger {
  USER = 'USER',
  SYSTEM = 'SYSTEM'
}

export const enum ChangeType {
  CREATE = 'CREATE',
  UPDATE = 'UPDATE',
  DESACTIVATE = 'DESACTIVATE',
  DELETE = 'DELETE'
}

export interface IChangeLog {
  id?: string;
  userId?: string;
  date?: Moment;
  changeTrigger?: ChangeTrigger;
  changeType?: ChangeType;
  description?: string;
}

export class ChangeLog implements IChangeLog {
  constructor(
    public id?: string,
    public userId?: string,
    public date?: Moment,
    public changeTrigger?: ChangeTrigger,
    public changeType?: ChangeType,
    public description?: string
  ) {}
}
