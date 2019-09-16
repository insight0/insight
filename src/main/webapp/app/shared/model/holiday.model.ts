import { Moment } from 'moment';

export interface IHoliday {
  id?: string;
  name?: string;
  description?: string;
  year?: number;
  dayOfYear?: number;
  weekOfYear?: number;
  dayOfWeek?: number;
  paid?: boolean;
  duration?: number;
  date?: Moment;
}

export class Holiday implements IHoliday {
  constructor(
    public id?: string,
    public name?: string,
    public description?: string,
    public year?: number,
    public dayOfYear?: number,
    public weekOfYear?: number,
    public dayOfWeek?: number,
    public paid?: boolean,
    public duration?: number,
    public date?: Moment
  ) {
    this.paid = this.paid || false;
  }
}
