import { Moment } from 'moment';
import { IUser } from 'app/core';

export const enum DayOffStatus {
  NEW = 'NEW',
  DECLINED = 'DECLINED',
  APPROVED = 'APPROVED'
}

export const enum DayOffType {
  ANNUAL = 'ANNUAL',
  SICKNESS = 'SICKNESS'
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
  user?: IUser;
  type?: DayOffType;
  generatedApprovalFilePath?: string;
  approvalFilePath?: string;
  medicalCertificateFilePath?: string;
  note?: string;
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
    public days?: number,
    public user?: IUser,
    public type?: DayOffType,
    public generatedApprovalFilePath?: string,
    public approvalFilePath?: string,
    public medicalCertificateFilePath?: string,
    public note?: string
  ) {
    this.forced = this.forced || false;
  }
}
