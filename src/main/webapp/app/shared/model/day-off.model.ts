import { Moment } from 'moment';

export const enum DayOffStatus {
  NEW = 'NEW',
  DECLINED = 'DECLINED',
  APPROVED = 'APPROVED'
}

export interface IDayOff {
  id?: string;
  startDate?: Moment;
  endDate?: Moment;
  dayOffObject?: string;
  status?: DayOffStatus;
  forced?: boolean;
  employeId?: string;
  validatorId?: string;
  days?: number;
}

export class DayOff implements IDayOff {
  constructor(
    public id?: string,
    public startDate?: Moment,
    public endDate?: Moment,
    public dayOffObject?: string,
    public status?: DayOffStatus,
    public forced?: boolean,
    public employeId?: string,
    public validatorId?: string,
    public days?: number
  ) {
    this.forced = this.forced || false;
  }
}
