import { IUser } from 'app/core';
import { IDayOff } from 'app/shared/model/day-off.model';

export interface IUserDayOff {
  user: IUser;
  dayOffs: IDayOff[];
  annualDayOffAuthorised: number;
  sicknessDayOffAuthorised: number;
  annualDayOffConsumed: number;
  sicknessDayOffConsumed: number;
  unpaidDayOffConsumed: number;
}

export class UserDayOff implements IUserDayOff {
  constructor(
    public user: IUser,
    public dayOffs: IDayOff[],
    public annualDayOffAuthorised: number,
    public sicknessDayOffAuthorised: number,
    public annualDayOffConsumed: number,
    public sicknessDayOffConsumed: number,
    public unpaidDayOffConsumed: number
  ) {}
}
